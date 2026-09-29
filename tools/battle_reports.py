"""Independent evidence checks for completed and interrupted battle reports."""
import math
from urllib.parse import urlsplit,parse_qs

def validate_battle(rows,summary,issue):
    state=None;enemies=None;plan=None;intent=None;action=None
    session=None;last_time=-1;last_action=None;initial=None
    receipts={};confirmed=set();new=set();losses=set();upgrades=set()
    # Units that are known to be ours. combat_unit_observed only marks armed mobile units, so it can
    # never be the sole birth evidence for mines and factories; those are known from any observation
    # they appear in, or from the completion the client recorded for them.
    seen=set()
    for row in rows:
        event,d=row['event'],row['data']
        if event=='observation':
            if session is None:session=d.get('sessionId')
            if d.get('sessionId')!=session:issue('BATTLE_SESSION_CHANGED','observation')
            t=d.get('gameTimeMs')
            if not isinstance(t,(int,float)) or t<last_time:issue('BATTLE_TIME_INVALID',str(t))
            else:last_time=t
            state=d
            if initial is None:initial={u.get('id') for u in d.get('ownUnits',[])}
            seen.update(u.get('id') for u in d.get('ownUnits',[]))
        elif event=='combat_observation':
            enemies=d
            if d.get('sessionId')!=session:issue('BATTLE_SESSION_CHANGED','combat observation')
        elif event=='army_frontier_plan':plan=d
        elif event=='tactical_intent':
            intent=d
            if d.get('reason')=='OBSERVED_ENEMY':
                enemy=d.get('enemy') or {}
                old=next((u for u in (enemies or {}).get('rememberedEnemies',[]) if u.get('id')==enemy.get('id')),None)
                if old is None or any(old.get(k)!=enemy.get(k) for k in ('x','y','lastSeenGameTimeMs')):
                    issue('BATTLE_TARGET_NOT_OBSERVED',str(enemy.get('id')))
                if enemy.get('x')!=d.get('targetX') or enemy.get('y')!=d.get('targetY'):
                    issue('BATTLE_TARGET_COORDINATES','enemy intent')
            if d.get('reason')=='KNOWN_FRONTIER':
                if not plan or plan.get('status')!='planned' or plan.get('pathKnown') is not True or any(plan.get(k)!=d.get(k) for k in ('targetX','targetY')):
                    issue('BATTLE_FRONTIER_NOT_PLANNED','intent')
        elif event=='action':
            t=d.get('gameTimeMs')
            if not isinstance(t,(int,float)) or (last_action is not None and t-last_action<1000):
                issue('BATTLE_COMMAND_RATE','minimum gap is 1000 game ms')
            last_action=t;action=urlsplit(d.get('path',''))
        elif event=='command_result' and d.get('status')=='queued':
            if action is None:issue('BATTLE_RECEIPT_NO_ACTION',str(d.get('requestId')));continue
            q=parse_qs(action.query)
            if q.get('requestId')!=[d.get('requestId')] or q.get('sessionId')!=[session] or d.get('sessionId')!=session:
                issue('BATTLE_RECEIPT_IDENTITY',str(d.get('requestId')))
            if action.path=='/command/attack-move':
                try:
                    ids=[int(i) for i in q['unitIds'][0].split(',')]
                    if ids!=d.get('unitIds') or float(q['x'][0])!=d.get('targetX') or float(q['y'][0])!=d.get('targetY'):
                        issue('BATTLE_RECEIPT_MISMATCH','attack move')
                    if not intent or any(intent.get(k)!=d.get(k) for k in ('targetX','targetY')):
                        issue('BATTLE_ATTACK_NO_INTENT',str(d.get('requestId')))
                    receipts[d['requestId']]=d
                except (KeyError,ValueError,TypeError):issue('BATTLE_INVALID_ATTACK','receipt')
            action=None
        elif event=='attack_order_confirmed':
            receipt=receipts.get(d.get('requestId'));ids=d.get('unitIds',[])
            if not receipt or not ids or d.get('requestId') in confirmed:issue('BATTLE_CONFIRMATION_INVALID',str(d.get('requestId')));continue
            confirmed.add(d.get('requestId'))
            for uid in ids:
                u=next((u for u in (state or {}).get('ownUnits',[]) if u.get('id')==uid),None)
                if (uid not in receipt.get('unitIds',[]) or u is None or u.get('dead') or u.get('orderType')!='attackMove'
                    or any(not isinstance(u.get(k),(int,float)) for k in ('orderX','orderY'))
                    or math.hypot(u['orderX']-receipt['targetX'],u['orderY']-receipt['targetY'])>=1):
                    issue('BATTLE_ATTACK_NOT_EXECUTED',str(uid))
        elif event=='combat_unit_observed':
            uid=d.get('id');u=next((u for u in (state or {}).get('ownUnits',[]) if u.get('id')==uid),None)
            if uid in (initial or set()) or uid in new or u is None or u.get('dead') or not u.get('canAttack') or not u.get('mobile') or u.get('buildProgress',0)<1:
                issue('BATTLE_NEW_UNIT_NOT_OBSERVED',str(uid))
            new.add(uid)
        elif event in ('extractor_completed','factory_completed','tank_completed'):
            # A completed own unit is ours even if it never survived into an observation.
            seen.add(d.get('unitId'))
        elif event=='own_loss':
            uid=d.get('unitId');u=next((u for u in (state or {}).get('ownUnits',[]) if u.get('id')==uid),None)
            if uid in losses or uid not in seen or (u and not u.get('dead') and u.get('hp',0)>0):
                issue('BATTLE_LOSS_NOT_OBSERVED',str(uid))
            losses.add(uid)
        elif event=='upgrade_completed':
            uid=d.get('factoryId');u=next((u for u in (state or {}).get('ownUnits',[]) if u.get('id')==uid),None)
            if u is None or u.get('techLevel')!=d.get('tier') or d.get('tier',0)<2:issue('BATTLE_UPGRADE_NOT_OBSERVED',str(uid))
            upgrades.add((uid,d.get('tier')))
        elif event=='match_terminal':
            native=(state or {}).get('match',{})
            if d!=native:issue('BATTLE_RESULT_NOT_OBSERVED','terminal event differs from latest state')
    for key,actual in {'ownLosses':len(losses),'newCombatUnits':len(new),'attackOrders':len(receipts),
                       'attackOrdersConfirmed':len(confirmed),'upgradesCompleted':len(upgrades),
                       'retreatOrders':sum(r['event']=='combat_retreat' for r in rows)}.items():
        if summary.get(key)!=actual:issue('BATTLE_COUNT_MISMATCH',key)
    terminals=[r['data'] for r in rows if r['event']=='match_terminal']
    if summary.get('outcome')=='PASS':
        outcome=summary.get('matchOutcome');native=(state or {}).get('match',{})
        if (len(terminals)!=1 or outcome not in ('VICTORY','DEFEAT') or native.get('outcome')!=outcome
            or native.get('source')!='native_result_screen'
            or (outcome=='VICTORY' and (native.get('nativeVictory') is not True or native.get('nativeDefeat') is not False))
            or (outcome=='DEFEAT' and native.get('nativeDefeat') is not True)):
            issue('BATTLE_NO_NATIVE_TERMINAL','PASS requires a native VICTORY or DEFEAT, not a time limit')
