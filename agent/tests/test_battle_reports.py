import copy,sys,unittest
from pathlib import Path
sys.path.insert(0,str(Path(__file__).resolve().parents[2]/'tools'))
from battle_reports import validate_battle

def sample(outcome='VICTORY'):
    match={'outcome':outcome,'nativeVictory':outcome=='VICTORY','nativeDefeat':outcome=='DEFEAT','source':'native_result_screen'}
    unit={'id':9,'type':'c_tank','hp':200,'dead':False,'mobile':True,'canAttack':True,'buildProgress':1,'orderType':'attackMove','orderX':100,'orderY':200}
    rows=[('observation',{'sessionId':'s','gameTimeMs':0,'ownUnits':[unit],'match':{'outcome':'ONGOING'}}),
          ('army_frontier_plan',{'status':'planned','pathKnown':True,'targetX':100,'targetY':200}),
          ('tactical_intent',{'reason':'KNOWN_FRONTIER','targetX':100,'targetY':200}),
          ('action',{'gameTimeMs':1000,'path':'/command/attack-move?unitIds=9&x=100&y=200&sessionId=s&requestId=r'}),
          ('command_result',{'status':'queued','sessionId':'s','requestId':'r','unitIds':[9],'targetX':100,'targetY':200}),
          ('attack_order_confirmed',{'requestId':'r','unitIds':[9]}),
          ('observation',{'sessionId':'s','gameTimeMs':2000,'ownUnits':[unit],'match':match}),
          ('match_terminal',match)]
    summary={'outcome':'PASS','matchOutcome':outcome,'ownLosses':0,'newCombatUnits':0,'attackOrders':1,'attackOrdersConfirmed':1,'upgradesCompleted':0,'retreatOrders':0}
    return [{'event':e,'data':copy.deepcopy(d)} for e,d in rows],summary

class BattleEvidence(unittest.TestCase):
    def issues(self,rows,summary):
        issues=[];validate_battle(rows,summary,lambda code,detail:issues.append(code));return issues
    def test_victory(self):
        self.assertEqual(self.issues(*sample()),[])
    def test_defeat_is_complete_not_victory(self):
        self.assertEqual(self.issues(*sample('DEFEAT')),[])
    def test_no_result_not_pass(self):
        rows,s=sample();rows.pop();rows[-1]['data']['match']={'outcome':'ONGOING'}
        self.assertIn('BATTLE_NO_NATIVE_TERMINAL',self.issues(rows,s))
    def test_forged_result(self):
        rows,s=sample();rows[-1]['data']['nativeVictory']=False
        self.assertIn('BATTLE_RESULT_NOT_OBSERVED',self.issues(rows,s))
    def test_forged_confirmation(self):
        rows,s=sample();rows[0]['data']['ownUnits'][0]['orderType']='move'
        self.assertIn('BATTLE_ATTACK_NOT_EXECUTED',self.issues(rows,s))
    def test_unseen_target(self):
        rows,s=sample();rows[2]['data'].update(reason='OBSERVED_ENEMY',enemy={'id':88,'x':100,'y':200})
        self.assertIn('BATTLE_TARGET_NOT_OBSERVED',self.issues(rows,s))
    def test_command_spacing(self):
        rows,s=sample();rows.insert(5,{'event':'action','data':{'gameTimeMs':1500,'path':'/command/move'}})
        self.assertIn('BATTLE_COMMAND_RATE',self.issues(rows,s))
    def test_fake_new_unit(self):
        rows,s=sample();rows.insert(1,{'event':'combat_unit_observed','data':{'id':9}})
        self.assertIn('BATTLE_NEW_UNIT_NOT_OBSERVED',self.issues(rows,s))
    def test_count_mismatch(self):
        rows,s=sample();s['attackOrdersConfirmed']=5
        self.assertIn('BATTLE_COUNT_MISMATCH',self.issues(rows,s))
    def test_session_reset(self):
        rows,s=sample();rows[-2]['data']['sessionId']='new'
        self.assertIn('BATTLE_SESSION_CHANGED',self.issues(rows,s))
    def test_unproven_upgrade(self):
        rows,s=sample();rows.insert(1,{'event':'upgrade_completed','data':{'factoryId':42,'tier':2}})
        self.assertIn('BATTLE_UPGRADE_NOT_OBSERVED',self.issues(rows,s))
    def building_sample(self):
        """A mine built during the battle: visible in one observation, gone from the next."""
        rows,s=sample()
        tank=rows[0]['data']['ownUnits'][0]
        mine={'id':42,'type':'extractorT1','hp':400,'dead':False,'mobile':False,'canAttack':False,'buildProgress':1,'orderType':None}
        rows.insert(6,{'event':'observation','data':{'sessionId':'s','gameTimeMs':1500,'ownUnits':[copy.deepcopy(tank),copy.deepcopy(mine)],'match':{'outcome':'ONGOING'}}})
        rows.insert(len(rows)-1,{'event':'own_loss','data':{'unitId':42,'gameTimeMs':1800}})
        s['ownLosses']=1
        return rows,s
    def test_lost_building_is_observed(self):
        # Mines and factories never raise combat_unit_observed, so a report must not become INVALID
        # merely because a building the client had already shown us was destroyed.
        self.assertEqual(self.issues(*self.building_sample()),[])
    def test_loss_of_a_never_seen_unit_is_still_rejected(self):
        rows,s=self.building_sample();rows[8]['data']['unitId']=77
        self.assertIn('BATTLE_LOSS_NOT_OBSERVED',self.issues(rows,s))
    def test_loss_while_still_alive_is_still_rejected(self):
        rows,s=self.building_sample()
        rows.insert(1,{'event':'own_loss','data':{'unitId':9,'gameTimeMs':100}});s['ownLosses']=2
        self.assertIn('BATTLE_LOSS_NOT_OBSERVED',self.issues(rows,s))
    def test_completed_building_loss_needs_no_observation(self):
        # A building may be finished and destroyed between two observations; the completion the client
        # recorded is still proof that the unit was ours.
        rows,s=sample()
        rows.insert(len(rows)-1,{'event':'extractor_completed','data':{'unitId':55,'type':'extractorT1','minesCompleted':1}})
        rows.insert(len(rows)-1,{'event':'own_loss','data':{'unitId':55,'gameTimeMs':1800}})
        s['ownLosses']=1
        self.assertEqual(self.issues(rows,s),[])
if __name__=='__main__':unittest.main()
