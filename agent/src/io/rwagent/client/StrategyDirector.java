package io.rwagent.client;

import java.net.URLEncoder;
import java.util.*;
import static io.rwagent.client.BattleClient.*;

/** Session-local capability demand, investment allocation and leased specialist tasks.
 * It consumes legal observations only. Geometry is evidence about an approach, never a kill promise.
 */
final class StrategyDirector {
    interface Host {
        Map<String,Object> readStrategy(String path,String event)throws Exception;
        Map<String,Object> orderStrategy(String owner,String path)throws Exception;
        void emitStrategy(String event,Map<String,Object> data)throws Exception;
        void spendStrategy(String category,long cost,String type,long actor)throws Exception;
        void strategicAttack(Map<String,Object> receipt);
    }
    private final Host host;
    private final CommandArbiter arbiter;
    private boolean enabled;
    private long now,started,remaining,lastCapacity=-100000,lastAllocation=-100000,taskSequence=1000000;
    private int hardCap=40,armyTarget=40,activeTarget=24,reserveTarget=8,builderTarget=1;
    private double income,consumption,protectedFunds,homeX,homeY;
    private boolean homeEmergency;
    private Map<String,Object> state,enemies,scout;
    private final Map<Long,Need> needs=new LinkedHashMap<Long,Need>();
    private final Map<Long,Assessment> assessments=new LinkedHashMap<Long,Assessment>();
    private final Map<Long,Worker> workers=new LinkedHashMap<Long,Worker>();
    private final Map<Long,Purchase> purchases=new LinkedHashMap<Long,Purchase>();
    private int investments,upgrades,completedJobs,resolvedNeeds;
    private CapabilityFunding capabilityFunding;
    private long capabilityFundingRetryAt;
    private int capabilityReservesStarted,capabilityReservesReleased;
    private static final class CapabilityFunding {
        final long need,producer,started,deadline;
        final String action;
        final double cost;
        CapabilityFunding(long need,long producer,String action,double cost,long now){
            this.need=need;this.producer=producer;this.action=action;this.cost=cost;started=now;deadline=now+90000;
        }
    }
    private static final class Assessment {
        long checked=-100000;String signature="";double x,y;
        final Set<Long> blocked=new HashSet<Long>();
    }
    private static final class Need {
        final long id;String type,reason;double x,y;long seen,awaitVisibleAfter=-1;int failures;boolean building;
        Need(long id){this.id=id;}
    }
    private static final class Worker {
        final long unit,task;final String owner;
        String job="IDLE",product;long target=-1,jobAt,lastOrder=-100000,lastProgress,retryAt,lastAssessment=-100000,investigateAt,investigateSeen;
        double x,y,best=Double.MAX_VALUE,hp=Double.MAX_VALUE;
        Set<Long> before=new HashSet<Long>();
        Set<Long> prospects=new HashSet<Long>();
        Worker(long unit,long task){this.unit=unit;this.task=task;owner="strategy:"+task;}
    }
    private static final class Purchase {
        final String product;final long at,selectedNeed;long need;boolean active,queueFinished,fulfilled;
        Purchase(String product,long at){this(product,at,-1);}
        Purchase(String product,long at,long need){this.product=product;this.at=at;this.need=need;selectedNeed=need;}
    }
    StrategyDirector(Host host,CommandArbiter arbiter){this.host=host;this.arbiter=arbiter;}
    void enable(Map<String,Object> health,int hardCap){
        enabled=number(health,"strategyContractVersion",0)>=1
                &&!"false".equalsIgnoreCase(System.getProperty("rwagent.globalStrategy","true"));
        this.hardCap=hardCap;armyTarget=Math.min(40,hardCap);
    }
    boolean enabled(){return enabled;}
    int armyTarget(){return enabled?armyTarget:hardCap;}
    int activeTarget(){return activeTarget;}
    int builderTarget(){return builderTarget;}
    double capabilityReserve(){return capabilityFunding==null?0:capabilityFunding.cost;}
    boolean wouldBreachCapabilityReserve(double credits,double cost,double otherReserved){
        return capabilityFunding!=null&&credits-cost<capabilityFunding.cost+Math.max(0,otherReserved);
    }
    boolean pending(long actor){return purchases.containsKey(actor);}
    boolean safetyCapacityAvailable(){return !enabled||state==null||committedArmed()<hardCap;}
    private int committedArmed(){
        int count=0;
        for(Map<String,Object> u:units(state))if(alive(u)){
            if(Boolean.TRUE.equals(u.get("mobile"))&&Boolean.TRUE.equals(u.get("canAttack")))count++;
            // Queue contents are not exported per item. Counting every factory slot as armed is a
            // conservative bound, including upgrades/builders rather than undercounting paid units.
            if("landFactory".equals(u.get("type")))count+=(int)Math.max(0,number(u,"productionQueue",0));
        }
        for(Purchase purchase:purchases.values())if(!purchase.active&&"combatEngineer".equals(purchase.product))count++;
        for(Worker worker:workers.values())if("BUILD".equals(worker.job)
                &&("heavyTank".equals(worker.product)||"amphibiousJet".equals(worker.product)))count++;
        return count;
    }
    void observe(Map<String,Object> state,Map<String,Object> enemies,Map<String,Object> scout,
                 List<Map<String,Object>> main,long start,long remainingMs,double income,double consumption,
                 double reserved,boolean buildBacklog)throws Exception{
        if(!enabled)return;
        this.state=state;this.enemies=enemies;this.scout=scout;now=(long)n(state,"gameTimeMs");started=start;
        remaining=remainingMs;this.income=income;this.consumption=consumption;protectedFunds=reserved;
        for(Map<String,Object> unit:units(state))if("commandCenter".equals(unit.get("type"))){homeX=n(unit,"x");homeY=n(unit,"y");break;}
        homeEmergency=false;
        for(Map<String,Object> enemy:items(enemies,"visibleEnemies"))if(Boolean.TRUE.equals(enemy.get("canAttack"))
                &&distance(enemy,homeX,homeY)<500)homeEmergency=true;
        claimWorkers();updatePurchases();refreshAssessments(main);clearNeeds();updateWorkers();
        validateCapabilityFunding();
        if(now-lastCapacity>=30000){
            lastCapacity=now;
            int backlog=resourceBacklog(),armed=army(state).size();
            Map<String,Object> map=obj(state.get("map"));
            double area=number(map,"tilesWide",110)*number(map,"tilesHigh",110);
            int threats=0;for(Map<String,Object> enemy:items(enemies,"visibleEnemies"))if(Boolean.TRUE.equals(enemy.get("canAttack")))threats++;
            Capacity value=capacity(area,income,threats,needs.size(),backlog,buildBacklog,armed,
                    now-started,hardCap,System.getProperty("rwagent.mobileUnitHardCap")!=null);
            // Grow at most eight slots per evaluation. Never cancel already paid units when income falls.
            armyTarget=Math.min(value.total,armyTarget+8);activeTarget=Math.min(value.active,armyTarget);
            reserveTarget=Math.max(0,armyTarget-activeTarget);builderTarget=value.builders;
            emit("strategy_capacity",map("armyTarget",armyTarget,"activeArmyTarget",activeTarget,"reserveTarget",reserveTarget,
                "hardSafetyCap",hardCap,"builderTarget",builderTarget,"mapTiles",area,"incomeEstimate",income,
                "productionConsumption",consumption,"knownCapabilityNeeds",needs.size(),"resourceBacklog",backlog,
                "buildBacklog",buildBacklog,"fixedCapOverride",System.getProperty("rwagent.mobileUnitHardCap")!=null));
        }
    }
    /** Bounded policy targets. Area/observed threats/tasks create demand; income supports capacity. */
    static final class Capacity {
        final int active,total,builders;
        Capacity(int active,int total,int builders){this.active=active;this.total=total;this.builders=builders;}
    }
    static Capacity capacity(double area,double income,int threats,int needs,int backlog,boolean buildBacklog,
                             int army,long age,int hard,boolean fixed){
        double scale=Math.max(1,Math.min(3,Math.sqrt(area/12100.0)));
        int demand=24+(int)Math.ceil((scale-1)*24)+Math.min(24,threats*2)+(needs>0?8:0);
        int supported=32+(int)Math.max(0,(income-45)*.65);
        int total=fixed?hard:Math.min(hard,age<90000?40:Math.max(32,Math.min(96,Math.min(demand+12,supported))));
        int builders=age>=90000&&army>=12&&income>=70&&backlog>=2&&(buildBacklog||area>=20000)?2:1;
        return new Capacity(Math.min(demand,Math.max(1,total-8)),total,builders);
    }
    private void refreshAssessments(List<Map<String,Object>> force)throws Exception{
        if(force.isEmpty())return;
        List<Map<String,Object>> actors=new ArrayList<Map<String,Object>>();
        for(Map<String,Object> actor:force)if(!arbiter.reserved(id(actor)))actors.add(actor);
        if(actors.isEmpty())return;
        String ids=ids(actors);int queried=0;
        List<Map<String,Object>> contacts=new ArrayList<Map<String,Object>>(items(enemies,"visibleEnemies"));
        Collections.sort(contacts,(a,b)->Boolean.compare(Boolean.TRUE.equals(b.get("building")),Boolean.TRUE.equals(a.get("building"))));
        for(Map<String,Object> enemy:contacts){
            long eid=id(enemy);Assessment assessment=assessments.get(eid);
            if(assessment==null){assessment=new Assessment();assessments.put(eid,assessment);}
            if(now-assessment.checked<10000&&ids.equals(assessment.signature)
                    &&Math.hypot(n(enemy,"x")-assessment.x,n(enemy,"y")-assessment.y)<20)continue;
            if(queried++>=4)break;
            Map<String,Object> answer=null;List<Map<String,Object>> combined=new ArrayList<Map<String,Object>>();boolean complete=true;
            for(int offset=0;offset<actors.size();offset+=48){
                Map<String,Object> part=host.readStrategy("/combat/engagement?unitIds="+ids(actors.subList(offset,Math.min(actors.size(),offset+48)))+"&targetId="+eid,"engagement_observation");
                if(part==null||!Boolean.TRUE.equals(part.get("targetVisible"))){complete=false;break;}
                if(answer!=null&&Math.hypot(number(part,"targetX",0)-number(answer,"targetX",0),number(part,"targetY",0)-number(answer,"targetY",0))>20){complete=false;break;}
                answer=new LinkedHashMap<String,Object>(part);combined.addAll(items(part,"actors"));
            }
            assessment.checked=now;assessment.signature=ids;
            if(!complete||answer==null){
                emit("engagement_assessment_deferred",map("targetId",eid,"reason","CONTACT_LOST_OR_MOVED_BETWEEN_FORMATION_BATCHES"));
                continue;
            }
            answer.put("actors",combined);
            host.emitStrategy("engagement_assessment",answer);
            if(Math.hypot(number(answer,"targetX",0)-assessment.x,number(answer,"targetY",0)-assessment.y)>20)
                assessment.blocked.clear();
            assessment.x=number(answer,"targetX",0);assessment.y=number(answer,"targetY",0);
            int blocked=0,incompatible=0,total=0;boolean possible=false;
            for(Map<String,Object> actor:items(answer,"actors")){
                long uid=(long)n(actor,"unitId");total++;
                if("BLOCKED_TERRAIN".equals(actor.get("status"))){assessment.blocked.add(uid);blocked++;}
                else if("APPROACH_PATH_KNOWN".equals(actor.get("status")))assessment.blocked.remove(uid);
                // UNKNOWN cannot release a prior terrain rejection for the same last-known objective.
                if("INCOMPATIBLE".equals(actor.get("compatibility")))incompatible++;
                if("APPROACH_PATH_KNOWN".equals(actor.get("status"))&&"COMPATIBLE".equals(actor.get("compatibility")))possible=true;
            }
            if(total>0&&(blocked==total||incompatible==total)){
                Need need=needs.get(eid);boolean fresh=need==null;
                if(fresh){need=new Need(eid);needs.put(eid,need);}
                need.type=String.valueOf(enemy.get("type"));need.x=assessment.x;need.y=assessment.y;
                need.building=Boolean.TRUE.equals(enemy.get("building"));
                need.seen=(long)number(answer,"targetObservedAtGameTimeMs",now);
                need.reason=incompatible==total?"WEAPON_DOMAIN_GAP":"MOVEMENT_APPROACH_GAP";
                if(fresh)emit("capability_need_created",map("targetId",eid,"targetType",need.type,"reason",need.reason,
                    "x",need.x,"y",need.y,"observedAtGameTimeMs",need.seen,"evidence",answer));
            }else if(possible&&needs.containsKey(eid)&&!assigned(eid)){
                needs.remove(eid);emit("capability_need_released",map("targetId",eid,"reason","CURRENT_FORCE_HAS_NEW_APPROACH_EVIDENCE"));
            }
        }
        // Bound historical geometry without forgetting an unresolved need.
        if(assessments.size()>256){Iterator<Long> it=assessments.keySet().iterator();while(it.hasNext()&&assessments.size()>256){Long id=it.next();if(!needs.containsKey(id))it.remove();}}
    }
    List<Map<String,Object>> eligible(Map<String,Object> enemy,List<Map<String,Object>> force){
        if(!enabled)return force;
        Assessment a=assessments.get(id(enemy));if(a==null||a.blocked.isEmpty())return force;
        List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
        for(Map<String,Object> actor:force)if(!a.blocked.contains(id(actor)))result.add(actor);
        return result;
    }
    boolean rejected(long enemyId){Assessment a=assessments.get(enemyId);return enabled&&a!=null&&!a.blocked.isEmpty()&&needs.containsKey(enemyId);}
    private void clearNeeds()throws Exception{
        for(Map<String,Object> intel:items(enemies,"enemyIntel"))if("CLEARED".equals(intel.get("status"))){
            long id=id(intel);if(needs.remove(id)!=null){resolvedNeeds++;emit("capability_need_resolved",map("targetId",id,"reason","LEGAL_SITE_CLEARED","evidence",intel));}
            assessments.remove(id);
        }
    }
    private boolean assigned(long target){
        for(Worker w:workers.values())if(w.target==target)return true;
        for(Purchase p:purchases.values())if(p.need==target)return true;
        return false;
    }
    private boolean available(Need need){return need!=null&&need.failures<3&&need.seen>need.awaitVisibleAfter;}
    private Need eligibleNeed(){for(Need need:needs.values())if(available(need)&&!assigned(need.id))return need;return null;}
    private void bind(Worker worker,Need need,String reason,long producer)throws Exception{
        worker.target=need.id;
        emit("strategy_worker_committed",map("taskId",worker.task,"unitId",worker.unit,"needId",need.id,
            "reason",reason,"producerId",producer,"assignmentSemantics","OBSERVED_AVAILABLE_ROLE_NOT_FACTORY_PROVENANCE"));
    }
    private void claimWorkers()throws Exception{
        long baselineBuilder=Long.MAX_VALUE;
        for(Map<String,Object> unit:units(state))if(ready(unit)&&"builder".equals(unit.get("type")))baselineBuilder=Math.min(baselineBuilder,id(unit));
        for(Map<String,Object> unit:units(state)){
            long uid=id(unit);String type=String.valueOf(unit.get("type"));
            if(!ready(unit)||!("combatEngineer".equals(type)||"amphibiousJet".equals(type)||"builder".equals(type)&&uid!=baselineBuilder))continue;
            if(workers.containsKey(uid)||arbiter.reserved(uid))continue;
            Worker w=new Worker(uid,++taskSequence);
            if(arbiter.claim(w.owner,uid)){
                workers.put(uid,w);emit("task_ownership_acquired",map("taskId",w.task,"owner",w.owner,"unitId",uid,"role","STRATEGIC_SPECIALIST"));
            }
        }
        Worker baseline=workers.get(baselineBuilder);
        if(baseline!=null&&"IDLE".equals(baseline.job)){release(baseline,"BASELINE_BUILDER_HANDOVER");workers.remove(baselineBuilder);}
        for(Map.Entry<Long,Purchase> entry:purchases.entrySet()){
            Purchase purchase=entry.getValue();Need need=needs.get(purchase.need);
            if(!"combatEngineer".equals(purchase.product)||need==null||purchase.fulfilled)continue;
            for(Worker worker:workers.values())if(worker.target<0&&"IDLE".equals(worker.job)){
                Map<String,Object> actor=find(state,worker.unit);
                if(actor!=null&&ready(actor)&&"combatEngineer".equals(actor.get("type"))){
                    purchase.need=-1;purchase.fulfilled=true;bind(worker,need,"PURCHASE_NEED_FULFILMENT",entry.getKey());break;
                }
            }
        }
    }
    private void updatePurchases()throws Exception{
        Iterator<Map.Entry<Long,Purchase>> it=purchases.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Long,Purchase> entry=it.next();Purchase p=entry.getValue();Map<String,Object> unit=find(state,entry.getKey());
            if(p.need>=0&&!needs.containsKey(p.need))p.need=-1;
            if(unit==null||!alive(unit)){Need need=needs.get(p.need);if(need!=null)need.failures++;
                emit("strategy_purchase_lost",map("actorId",entry.getKey(),"product",p.product,"needId",p.need));it.remove();continue;}
            if(number(unit,"productionQueue",0)>0)p.active=true;
            if("extractorT2".equals(p.product)&&"extractorT2".equals(unit.get("type"))){
                upgrades++;emit("mine_upgrade_observed",map("unitId",id(unit),"unit",unit));it.remove();
            }else if(!"extractorT2".equals(p.product)&&(p.active||p.fulfilled)&&number(unit,"productionQueue",0)==0){
                if(!p.queueFinished){p.queueFinished=true;emit("strategy_production_queue_finished",map("producerId",id(unit),"product",p.product,
                    "needId",p.selectedNeed,"evidence",p.active?"QUEUE_BECAME_EMPTY":"AVAILABLE_WORKER_ASSIGNED"));}
                if(!"combatEngineer".equals(p.product)||p.fulfilled||p.need<0)it.remove();
                else if(now-p.at>180000){Need need=needs.get(p.need);if(need!=null)need.failures++;
                    emit("strategy_purchase_unconfirmed",map("actorId",entry.getKey(),"product",p.product,"needId",p.need,"reason","OBSERVATION_TIMEOUT"));it.remove();}
            }else if(now-p.at>180000){Need need=needs.get(p.need);if(need!=null)need.failures++;
                emit("strategy_purchase_unconfirmed",map("actorId",entry.getKey(),"product",p.product,"needId",p.need,"reason","OBSERVATION_TIMEOUT"));it.remove();}
        }
    }
    private void updateWorkers()throws Exception{
        Iterator<Worker> it=workers.values().iterator();
        while(it.hasNext()){
            Worker w=it.next();Map<String,Object> actor=find(state,w.unit);
            if(actor==null||!alive(actor)){
                Need need=needs.get(w.target);if(need!=null)need.failures++;
                emit("strategy_task_lost",map("taskId",w.task,"unitId",w.unit,"job",w.job));release(w,"ACTOR_LOST");it.remove();continue;
            }
            if(w.target>=0&&!needs.containsKey(w.target)){
                emit("strategy_task_completed",map("taskId",w.task,"targetId",w.target,"reason","NEED_RELEASED_BY_LEGAL_EVIDENCE"));
                w.target=-1;if(!"BUILD".equals(w.job))w.job="IDLE";
            }
            Need commitment=needs.get(w.target);
            if(commitment!=null&&commitment.failures>=3){
                emit("strategy_worker_commitment_released",map("taskId",w.task,"unitId",w.unit,"needId",w.target,"reason","NEED_ATTEMPT_LIMIT"));
                w.target=-1;if(!"RETURN".equals(w.job)&&!"BUILD".equals(w.job))returnHome(w,15000);
            }
            if("BUILD".equals(w.job)||"MINE".equals(w.job)){
                for(Map<String,Object> unit:units(state))if(!w.before.contains(id(unit))&&ready(unit)
                        &&w.product.equals(unit.get("type"))&&distance(unit,w.x,w.y)<120){
                    completedJobs++;emit("strategy_construction_observed",map("taskId",w.task,"builderId",w.unit,"product",w.product,"unit",unit));
                    Worker responder=workers.get(id(unit));Need supported=needs.get(w.target);
                    if("amphibiousJet".equals(w.product)&&responder!=null&&responder.target<0&&supported!=null){
                        w.target=-1;emit("strategy_worker_commitment_released",map("taskId",w.task,"unitId",w.unit,
                            "needId",supported.id,"reason","SUPPORT_RESPONDER_HANDOVER"));
                        bind(responder,supported,"OBSERVED_SUPPORT_RESPONDER",-1);
                        emit("strategy_support_transferred",map("taskId",w.task,"unitId",w.unit,"responderId",responder.unit,"needId",supported.id));
                    }
                    w.job="IDLE";w.retryAt=now+5000;break;
                }
                if(!"IDLE".equals(w.job)&&now-w.jobAt>150000){
                    emit("strategy_task_blocked",map("taskId",w.task,"job",w.job,"reason","CONSTRUCTION_TIMEOUT"));w.job="IDLE";w.retryAt=now+30000;
                }
            }else if("PROSPECT".equals(w.job)){
                double d=distance(actor,w.x,w.y);
                if(d+20<w.best){w.best=d;w.lastProgress=now;}
                if(d<75){emit("strategy_prospect_observed",map("taskId",w.task,"unitId",w.unit,"unit",actor));w.job="IDLE";w.retryAt=now;}
                else if(now-w.lastProgress>45000||now-w.jobAt>120000){
                    emit("strategy_task_blocked",map("taskId",w.task,"job",w.job,"reason","PROSPECT_NO_PROGRESS"));w.job="IDLE";w.retryAt=now+15000;
                }
            }else if("RESPONSE".equals(w.job)||"INVESTIGATE".equals(w.job)){
                if(!needs.containsKey(w.target)){emit("strategy_task_completed",map("taskId",w.task,"targetId",w.target,"reason","NEED_RESOLVED"));w.job="IDLE";w.target=-1;continue;}
                assessResponse(w,actor);
                if("INVESTIGATE".equals(w.job)){
                    if(now-w.investigateAt>=15000){
                        Need need=needs.get(w.target);need.awaitVisibleAfter=Math.max(need.awaitVisibleAfter,w.investigateSeen);
                        emit("strategy_investigation_exhausted",map("taskId",w.task,"unitId",w.unit,"targetId",w.target,
                            "lastSeenGameTimeMs",w.investigateSeen,"reason","LAST_KNOWN_APPROACH_REACHED_WITHOUT_FRESH_CONTACT"));
                        returnHome(w,15000);
                    }
                    continue;
                }
                if(!"RESPONSE".equals(w.job))continue;
                double d=distance(actor,w.x,w.y);
                if(d+20<w.best){w.best=d;w.lastProgress=now;emit("strategy_response_progress",map("taskId",w.task,"unitId",w.unit,"targetId",w.target,"distance",d,"unit",actor));}
                for(Map<String,Object> enemy:items(enemies,"visibleEnemies"))if(id(enemy)==w.target){
                    if(n(enemy,"hp")<w.hp-0.01){
                        if(w.hp<Double.MAX_VALUE)emit("strategy_target_damage_observed",map("taskId",w.task,"targetId",w.target,"hpBefore",w.hp,"hpNow",n(enemy,"hp"),"attribution","TEAM_DAMAGE_NOT_EXCLUSIVE_TO_RESPONDER"));
                        w.hp=n(enemy,"hp");w.lastProgress=now;
                    }
                }
                if(now-w.lastProgress>60000||now-w.jobAt>240000){
                    Need need=needs.get(w.target);need.failures++;
                    emit("strategy_task_blocked",map("taskId",w.task,"targetId",w.target,"reason","NO_OBSERVED_PROGRESS","attempts",need.failures));
                    returnHome(w,45000);
                }
            }else if("RETURN".equals(w.job)&&distance(actor,homeX,homeY)<180&&now>=w.retryAt){
                Need waiting=needs.get(w.target);
                if(waiting!=null&&waiting.seen<=waiting.awaitVisibleAfter){
                    emit("strategy_worker_commitment_released",map("taskId",w.task,"unitId",w.unit,"needId",w.target,
                        "reason","STALE_SITE_INVESTIGATION_RETURNED_HOME"));w.target=-1;
                }
                if(n(actor,"hp")>=n(actor,"maxHp")*.3)w.job="IDLE";
            }
        }
    }
    /** One actuator slot for the entire policy. Existing combat/recon use the same arbiter. */
    boolean act(double reserved,long factoryTarget)throws Exception{
        if(!enabled||!arbiter.ready(now))return false;
        protectedFunds=reserved;
        if(capabilityFunding!=null&&fulfilCapabilityFunding())return true;
        for(Worker worker:workers.values())if(workerAction(worker))return true;
        if(now-lastAllocation<8000)return false;
        lastAllocation=now;
        double credits=n(obj(state.get("player")),"credits"),free=credits-protectedFunds-capabilityReserve();
        int force=army(state).size(),engineers=count("combatEngineer"),builders=count("builder");
        int backlog=resourceBacklog();
        Need selectedNeed=eligibleNeed();boolean unserved=selectedNeed!=null;
        // Explicit alternatives: a task capability gap outranks throughput, then safe growth.
        String product=null,reason=null;
        if(capabilityFunding==null&&now>=capabilityFundingRetryAt&&unserved&&engineers+pendingCount("combatEngineer")<Math.min(2,needs.size())&&force>=6&&!homeEmergency&&safetyCapacityAvailable()){product="combatEngineer";reason="UNSERVED_CAPABILITY_NEED";}
        else if(builders+pendingCount("builder")<builderTarget&&!homeEmergency){product="builder";reason="PARALLEL_CONSTRUCTION_BACKLOG";}
        Map<String,Object> alternatives=map("militaryDeficit",Math.max(0,armyTarget-force),"unservedCapabilityNeed",unserved,
            "constructionBacklog",backlog,"builders",builders,"builderTarget",builderTarget,"engineers",engineers,
            "freeCredits",free,"protectedFunds",protectedFunds,"remainingGameSeconds",remaining/1000);
        if(product!=null){
            Map<String,Object> menu=host.readStrategy("/combat/production","strategy_production_menu");
            if(menu!=null)for(Map<String,Object> factory:items(menu,"factories"))if(number(factory,"queue",-1)==0&&!pending(id(factory))){
                for(Map<String,Object> action:items(factory,"actions"))if(product.equals(action.get("type"))
                        &&Boolean.TRUE.equals(action.get("affordable"))&&n(action,"cost")<=free){
                    alternatives.put("selected",reason);alternatives.put("action",action);emit("strategy_allocation",alternatives);
                    Map<String,Object> receipt=host.orderStrategy(CommandArbiter.DEFAULT_OWNER,"/command/queue?unitId="+id(factory)+"&actionId="+encode(action.get("actionId")));
                    if(receipt!=null){long needId="combatEngineer".equals(product)?selectedNeed.id:-1;
                        purchases.put(id(factory),new Purchase(product,now,needId));
                        if(needId>=0)emit("strategy_purchase_committed",map("needId",needId,"producerId",id(factory),"product",product));
                        investments++;host.spendStrategy("STRATEGIC_CAPABILITY",(long)n(action,"cost"),product,id(factory));return true;}
                }
            }
            if("combatEngineer".equals(product)&&startCapabilityFunding(menu,free))free-=capabilityReserve();
        }
        // Upgrades compete against military recovery and new-site expansion. The gain is a labelled
        // estimate: calibrated T1 income times the frozen 12/8 native generation ratio.
        double upgradeGain=12.07*.5;
        if(!homeEmergency&&force>=activeTarget&&(!unserved||engineers>0)&&remaining/1000.0>1400/upgradeGain+180
                &&(force<armyTarget||consumption>income*.3)){
            Map<String,Object> menu=host.readStrategy("/economy/investments","strategy_investment_menu");
            if(menu!=null)for(Map<String,Object> candidate:items(menu,"units")){
                Map<String,Object> mine=find(state,id(candidate));
                if(mine==null||nearThreat(n(mine,"x"),n(mine,"y"),450)||pending(id(candidate))
                        ||number(candidate,"queue",-1)!=0||!Boolean.TRUE.equals(candidate.get("affordable")))continue;
                double cost=n(candidate,"cost"),payback=cost/upgradeGain;
                // A near, safe new site is cheaper per added income; leave its funds and builder first.
                boolean cheaperMineReady=backlog>0&&idleWorkerNearResource();
                if(cheaperMineReady||free<cost+800||remaining/1000.0<payback+180)continue;
                alternatives.put("selected","MINE_T2_INCOME_INVESTMENT");alternatives.put("paybackEstimateGameSeconds",payback);
                alternatives.put("incomeModel","T1_MEASURED_X_FROZEN_12_OVER_8_RATIO");alternatives.put("action",candidate);emit("strategy_allocation",alternatives);
                Map<String,Object> receipt=host.orderStrategy(CommandArbiter.DEFAULT_OWNER,"/command/invest?unitId="+id(candidate)+"&actionId="+encode(candidate.get("actionId")));
                if(receipt!=null){purchases.put(id(candidate),new Purchase("extractorT2",now));investments++;host.spendStrategy("MINE_UPGRADE",(long)cost,"extractorT2",id(candidate));return true;}
            }
        }
        // Combat engineers remain capability specialists. Only additional builders enter the
        // ordinary economic lane; a specialist may construct a responder for its explicit need.
        for(Worker worker:workers.values())if("IDLE".equals(worker.job)&&now>=worker.retryAt){
            Map<String,Object> actor=find(state,worker.unit);if(actor==null)continue;
            if("combatEngineer".equals(actor.get("type"))){
                if(startSupportBuild(worker,actor,free))return true;
                continue;
            }
            if(!"builder".equals(actor.get("type")))continue;
            if(!nearThreat(n(actor,"x"),n(actor,"y"),400)){
                Map<String,Object> plan=host.readStrategy("/expansion/plan?unitId="+worker.unit,"strategy_expansion_plan");
                if(plan!=null&&number(plan,"extractorCost",Double.MAX_VALUE)<=free
                        &&!nearThreat(n(plan,"extractorX"),n(plan,"extractorY"),350)){
                    if(startBuild(worker,plan,"/command/build-extractor?unitId="+worker.unit+"&x="+n(plan,"extractorX")+"&y="+n(plan,"extractorY"),
                        "extractorT1",n(plan,"extractorX"),n(plan,"extractorY"),(long)n(plan,"extractorCost"),"MINE"))return true;
                }
                String build=count("landFactory")<factoryTarget?"landFactory":null;
                if(build!=null){
                    Map<String,Object> plan2=host.readStrategy("/economy/construction-plan?unitId="+worker.unit+"&type="+build,"strategy_construction_plan");
                    if(plan2!=null&&Boolean.TRUE.equals(plan2.get("affordable"))&&n(plan2,"cost")<=free){
                        if(startBuild(worker,plan2,"/command/construct?unitId="+worker.unit+"&actionId="+encode(plan2.get("actionId"))+"&x="+n(plan2,"x")+"&y="+n(plan2,"y"),
                            build,n(plan2,"x"),n(plan2,"y"),(long)n(plan2,"cost"),"BUILD"))return true;
                    }
                }
                // Additional construction capacity must actually reach its backlog, not stand at
                // home once all sites within the native 600-unit building search are occupied.
                List<Map<String,Object>> resources=new ArrayList<Map<String,Object>>(items(scout,"resources"));
                Collections.sort(resources,(a,b)->Double.compare(distance(actor,n(a,"x"),n(a,"y")),distance(actor,n(b,"x"),n(b,"y"))));
                int queriedResources=0;
                for(Map<String,Object> resource:resources){
                    long tile=(long)n(resource,"tile");
                    if(worker.prospects.contains(tile)||occupiedResource(resource)||nearThreat(n(resource,"x"),n(resource,"y"),350))continue;
                    if(queriedResources++>=4)break;
                    worker.prospects.add(tile);
                    Map<String,Object> approach=host.readStrategy("/scout/resource-approach?unitId="+worker.unit+"&tile="+tile,"strategy_resource_approach");
                    if(approach==null||!Boolean.TRUE.equals(approach.get("pathKnown")))continue;
                    if(host.orderStrategy(worker.owner,"/command/move?unitId="+worker.unit+"&x="+n(approach,"x")+"&y="+n(approach,"y"))!=null){
                        worker.job="PROSPECT";worker.x=n(approach,"x");worker.y=n(approach,"y");worker.jobAt=worker.lastProgress=now;worker.best=distance(actor,worker.x,worker.y);
                        emit("strategy_prospect_ordered",map("taskId",worker.task,"unitId",worker.unit,"tile",tile,"evidence",approach));return true;
                    }
                    break;
                }
            }
            worker.retryAt=now+15000;
        }
        return false;
    }
    private void validateCapabilityFunding()throws Exception{
        if(capabilityFunding==null)return;
        Need need=needs.get(capabilityFunding.need);
        Map<String,Object> producer=find(state,capabilityFunding.producer);
        String reason=null;
        if(now>=capabilityFunding.deadline)reason="TIMEOUT";
        else if(!available(need))reason="NEED_UNAVAILABLE";
        else if(assigned(need.id))reason="NEED_ALREADY_SERVED";
        else if(homeEmergency)reason="HOME_EMERGENCY";
        else if(army(state).size()<6)reason="FORCE_BELOW_MINIMUM";
        else if(producer==null||!alive(producer))reason="PRODUCER_LOST";
        else if(!safetyCapacityAvailable())reason="CAPACITY_UNAVAILABLE";
        else if(count("combatEngineer")+pendingCount("combatEngineer")>=Math.min(2,needs.size()))reason="ENGINEER_CAPACITY_SATISFIED";
        if(reason!=null)releaseCapabilityFunding(reason);
    }
    private boolean startCapabilityFunding(Map<String,Object> menu,double free)throws Exception{
        if(capabilityFunding!=null||now<capabilityFundingRetryAt||!(income>0)||!Double.isFinite(income))return false;
        Need selected=eligibleNeed();
        if(selected==null)return false;
        for(Map<String,Object> factory:items(menu,"factories")){
            Map<String,Object> producer=find(state,id(factory));
            if(producer==null||!alive(producer)||pending(id(factory)))continue;
            for(Map<String,Object> action:items(factory,"actions")){
                double cost=number(action,"cost",0);
                if(!"combatEngineer".equals(action.get("type"))||!(action.get("actionId") instanceof String)
                        ||String.valueOf(action.get("actionId")).isEmpty()||!Double.isFinite(cost)||cost<=0||free>=cost)continue;
                double fundingSeconds=(cost-free)/income;
                if(fundingSeconds>60)continue;
                capabilityFunding=new CapabilityFunding(selected.id,id(factory),(String)action.get("actionId"),cost,now);
                capabilityReservesStarted++;
                Map<String,Object> data=capabilityFundingData("BOUNDED_CAPABILITY_FUNDING");
                data.put("freeCredits",free);data.put("protectedFunds",protectedFunds);data.put("incomeEstimate",income);
                data.put("estimatedFundingGameSeconds",fundingSeconds);data.put("estimateSemantics","BUDGET_FEASIBILITY_NOT_OUTCOME_PREDICTION");
                emit("strategy_capability_reserve_started",data);return true;
            }
        }
        return false;
    }
    /** A funded purchase keeps its original hard-reserve deduction, never its own reserve. */
    private boolean fulfilCapabilityFunding()throws Exception{
        CapabilityFunding funding=capabilityFunding;
        Map<String,Object> menu=host.readStrategy("/combat/production","strategy_production_menu");
        if(menu==null){releaseCapabilityFunding("MENU_UNAVAILABLE");return false;}
        Map<String,Object> producer=null,chosen=null;
        for(Map<String,Object> factory:items(menu,"factories"))if(id(factory)==funding.producer){producer=factory;break;}
        if(producer!=null)for(Map<String,Object> action:items(producer,"actions"))
            if("combatEngineer".equals(action.get("type"))&&funding.action.equals(action.get("actionId"))){chosen=action;break;}
        if(chosen==null){releaseCapabilityFunding("ACTION_UNAVAILABLE");return false;}
        if(number(chosen,"cost",Double.NaN)!=funding.cost){releaseCapabilityFunding("PRICE_CHANGED");return false;}
        double free=n(obj(state.get("player")),"credits")-protectedFunds;
        if(number(producer,"queue",-1)!=0||pending(funding.producer)||!Boolean.TRUE.equals(chosen.get("affordable"))||free<funding.cost)return false;
        Map<String,Object> data=capabilityFundingData("CAPABILITY_COMMITMENT_READY");
        data.put("selected","UNSERVED_CAPABILITY_NEED");data.put("action",chosen);data.put("freeCredits",free);data.put("protectedFunds",protectedFunds);
        emit("strategy_allocation",data);
        Map<String,Object> receipt=host.orderStrategy(CommandArbiter.DEFAULT_OWNER,"/command/queue?unitId="+funding.producer+"&actionId="+encode(funding.action));
        if(receipt==null){releaseCapabilityFunding("ORDER_REJECTED");return false;}
        purchases.put(funding.producer,new Purchase("combatEngineer",now,funding.need));investments++;
        emit("strategy_purchase_committed",map("needId",funding.need,"producerId",funding.producer,"product","combatEngineer"));
        host.spendStrategy("STRATEGIC_CAPABILITY",(long)funding.cost,"combatEngineer",funding.producer);
        releaseCapabilityFunding("PURCHASE_ACCEPTED");return true;
    }
    private Map<String,Object> capabilityFundingData(String reason){
        CapabilityFunding f=capabilityFunding;
        return map("needId",f.need,"producerId",f.producer,"product","combatEngineer","actionId",f.action,"cost",f.cost,
            "startedAtGameTimeMs",f.started,"deadlineGameTimeMs",f.deadline,"ageGameMs",now-f.started,"reason",reason);
    }
    private void releaseCapabilityFunding(String reason)throws Exception{
        if(capabilityFunding==null)return;
        Map<String,Object> data=capabilityFundingData(reason);capabilityFunding=null;capabilityReservesReleased++;
        if(!"PURCHASE_ACCEPTED".equals(reason))capabilityFundingRetryAt=now+60000;
        emit("strategy_capability_reserve_released",data);
    }
    private boolean workerAction(Worker w)throws Exception{
        Map<String,Object> actor=find(state,w.unit);if(actor==null||!ready(actor))return false;
        if(n(actor,"hp")<n(actor,"maxHp")*.3&&!"RETURN".equals(w.job)){
            Need interrupted=needs.get(w.target);
            if("RESPONSE".equals(w.job)&&interrupted!=null)interrupted.failures++;
            emit("strategy_task_preempted",map("taskId",w.task,"previousJob",w.job,"reason","CRITICAL_HP"));
            returnHome(w,45000);
        }
        if("RETURN".equals(w.job)){
            if(distance(actor,homeX,homeY)<180)return false;
            if("move".equals(actor.get("orderType"))&&actor.get("orderX") instanceof Number&&actor.get("orderY") instanceof Number
                    &&Math.hypot(n(actor,"orderX")-homeX,n(actor,"orderY")-homeY)<1)return false;
            if(now-w.lastOrder<10000)return false;
            if(host.orderStrategy(w.owner,"/command/move?unitId="+w.unit+"&x="+homeX+"&y="+homeY)!=null){w.lastOrder=now;return true;}return false;
        }
        boolean engineer="combatEngineer".equals(actor.get("type")),jet="amphibiousJet".equals(actor.get("type"));
        if("IDLE".equals(w.job)&&now>=w.retryAt&&(engineer||jet)){
            List<Need> ordered=new ArrayList<Need>();
            if(w.target>=0){Need bound=needs.get(w.target);if(bound!=null)ordered.add(bound);}
            else ordered.addAll(needs.values());
            Collections.sort(ordered,(a,b)->Boolean.compare(b.building,a.building));
            for(Need need:ordered)if(available(need)&&(w.target==need.id||!assigned(need.id))){
                if(jet&&!"MOVEMENT_APPROACH_GAP".equals(need.reason))continue;
                if(engineer&&threatCount(need.x,need.y,450)>1)continue;
                Map<String,Object> response=host.readStrategy("/combat/engagement?unitIds="+w.unit+"&targetId="+need.id,"response_engagement_observation");
                if(response==null)continue;
                List<Map<String,Object>> options=items(response,"actors");if(options.isEmpty())continue;
                Map<String,Object> choice=options.get(0);
                boolean current=Boolean.TRUE.equals(response.get("targetVisible"));
                boolean investigate=!current&&(need.building||now-need.seen<180000)
                        &&"APPROACH_PATH_KNOWN".equals(choice.get("lastKnownPositionApproachStatus"));
                if(!investigate&&(!"COMPATIBLE".equals(choice.get("compatibility"))||!"APPROACH_PATH_KNOWN".equals(choice.get("status"))))continue;
                if(w.target<0)bind(w,need,"AVAILABLE_SPECIALIST",-1);
                w.job="RESPONSE";w.x=n(choice,"approachX");w.y=n(choice,"approachY");
                w.jobAt=w.lastProgress=now;w.lastOrder=-100000;w.best=Double.MAX_VALUE;w.hp=Double.MAX_VALUE;
                w.lastAssessment=now;
                emit("strategy_task_assigned",map("taskId",w.task,"unitId",w.unit,"role","CROSS_DOMAIN_RESPONSE","targetId",w.target,
                    "approachX",w.x,"approachY",w.y,"objectiveSemantics",investigate?"LAST_KNOWN_SITE_INVESTIGATION":"CURRENT_CONTACT_APPROACH","evidence",response));break;
            }
        }
        if("RESPONSE".equals(w.job)&&now-w.lastOrder>=12000){
            // Hold one objective. Other visible targets cannot steal this specialist through the main army.
            if("attackMove".equals(actor.get("orderType"))&&number(actor,"orderX",-1)==w.x&&number(actor,"orderY",-1)==w.y)return false;
            emit("tactical_intent",map("reason","STRATEGIC_RESPONSE","targetX",w.x,"targetY",w.y,"targetId",w.target,"taskId",w.task));
            Map<String,Object> receipt=host.orderStrategy(w.owner,"/command/attack-move?unitIds="+w.unit+"&x="+w.x+"&y="+w.y);
            if(receipt!=null){host.strategicAttack(receipt);w.lastOrder=now;emit("strategy_response_ordered",map("taskId",w.task,"unitId",w.unit,"targetId",w.target,"receipt",receipt));return true;}
        }
        return false;
    }
    private void returnHome(Worker w,long cooldown){w.job="RETURN";w.x=homeX;w.y=homeY;w.lastOrder=-100000;w.retryAt=now+cooldown;}
    private void assessResponse(Worker w,Map<String,Object> actor)throws Exception{
        // Reads are independent of the actuator slot, so an arrival/contact transition cannot
        // starve behind another task's command. Investigation polls retain the normal decision rate.
        if(!"INVESTIGATE".equals(w.job)&&now-w.lastAssessment<10000)return;
        w.lastAssessment=now;
        Map<String,Object> response=host.readStrategy("/combat/engagement?unitIds="+w.unit+"&targetId="+w.target,"response_engagement_observation");
        if(response==null)return;
        Need need=needs.get(w.target);if(need==null)return;
        if(Boolean.FALSE.equals(response.get("targetVisible"))){
            if("RESPONSE".equals(w.job)&&distance(actor,w.x,w.y)<=75){
                w.job="INVESTIGATE";w.investigateAt=now;w.investigateSeen=need.seen;
                emit("strategy_investigation_started",map("taskId",w.task,"unitId",w.unit,"targetId",w.target,
                    "lastSeenGameTimeMs",w.investigateSeen,"deadlineGameTimeMs",now+15000,"distance",distance(actor,w.x,w.y)));
            }
            return;
        }
        if(!Boolean.TRUE.equals(response.get("targetVisible"))||items(response,"actors").isEmpty())return;
        long seen=(long)number(response,"targetObservedAtGameTimeMs",-1);
        if(seen>=0)need.seen=Math.max(need.seen,seen);
        Map<String,Object> choice=items(response,"actors").get(0);
        if("INCOMPATIBLE".equals(choice.get("compatibility"))||"BLOCKED_TERRAIN".equals(choice.get("status"))){
            emit("strategy_task_preempted",map("taskId",w.task,"targetId",w.target,"reason","NEW_VISIBLE_NEGATIVE_EVIDENCE","evidence",response));
            returnHome(w,30000);return;
        }
        if("COMPATIBLE".equals(choice.get("compatibility"))&&"APPROACH_PATH_KNOWN".equals(choice.get("status"))){
            boolean resumed="INVESTIGATE".equals(w.job);
            if(resumed){w.job="RESPONSE";w.lastProgress=now;w.lastOrder=-100000;
                emit("strategy_investigation_contact_restored",map("taskId",w.task,"unitId",w.unit,"targetId",w.target,"observedAtGameTimeMs",seen));}
            if(resumed||Math.hypot(n(choice,"approachX")-w.x,n(choice,"approachY")-w.y)>60){
                w.x=n(choice,"approachX");w.y=n(choice,"approachY");w.best=distance(actor,w.x,w.y);
                emit("strategy_response_replanned",map("taskId",w.task,"targetId",w.target,"x",w.x,"y",w.y,"reason","SAME_TARGET_NEW_VISIBLE_APPROACH","evidence",response));
            }
        }
    }
    private boolean startSupportBuild(Worker worker,Map<String,Object> actor,double free)throws Exception{
        if(homeEmergency||nearThreat(n(actor,"x"),n(actor,"y"),400)||!safetyCapacityAvailable())return false;
        Need need=needs.get(worker.target);
        if(worker.target<0)for(Need candidate:needs.values())if(available(candidate)&&!assigned(candidate.id)
                &&"MOVEMENT_APPROACH_GAP".equals(candidate.reason)){need=candidate;break;}
        if(!available(need)||!"MOVEMENT_APPROACH_GAP".equals(need.reason))return false;
        int jets=count("amphibiousJet");for(Worker other:workers.values())if("amphibiousJet".equals(other.product)&&"BUILD".equals(other.job))jets++;
        if(jets>=4)return false;
        Map<String,Object> plan=host.readStrategy("/economy/construction-plan?unitId="+worker.unit+"&type=amphibiousJet","strategy_construction_plan");
        if(plan==null||!Boolean.TRUE.equals(plan.get("affordable"))||n(plan,"cost")>free)return false;
        if(startBuild(worker,plan,"/command/construct?unitId="+worker.unit+"&actionId="+encode(plan.get("actionId"))+"&x="+n(plan,"x")+"&y="+n(plan,"y"),
                "amphibiousJet",n(plan,"x"),n(plan,"y"),(long)n(plan,"cost"),"BUILD")){
            if(worker.target<0)bind(worker,need,"MOVEMENT_GAP_SUPPORT_CONSTRUCTION",-1);
            emit("strategy_support_construction",map("taskId",worker.task,"unitId",worker.unit,"needId",need.id,"product","amphibiousJet"));return true;
        }
        return false;
    }
    private boolean startBuild(Worker w,Map<String,Object> evidence,String path,String product,double x,double y,long cost,String job)throws Exception{
        if(host.orderStrategy(w.owner,path)==null)return false;
        w.job=job;w.product=product;w.x=x;w.y=y;w.jobAt=now;w.before.clear();for(Map<String,Object> u:units(state))w.before.add(id(u));
        investments++;host.spendStrategy("STRATEGIC_CONSTRUCTION",cost,product,w.unit);
        emit("strategy_construction_ordered",map("taskId",w.task,"unitId",w.unit,"product",product,"x",x,"y",y,"evidence",evidence));return true;
    }
    private int count(String type){int count=0;for(Map<String,Object> u:units(state))if(ready(u)&&type.equals(u.get("type")))count++;return count;}
    private int pendingCount(String type){int count=0;for(Purchase p:purchases.values())if(type.equals(p.product))count++;return count;}
    private boolean nearThreat(double x,double y,double radius){
        for(Map<String,Object> e:items(scout,"rememberedThreats"))if(distance(e,x,y)<Math.max(radius,number(e,"range",0)+100))return true;return false;
    }
    private int threatCount(double x,double y,double radius){int count=0;for(Map<String,Object> e:items(scout,"rememberedThreats"))if(distance(e,x,y)<radius)count++;return count;}
    private int resourceBacklog(){
        int count=0;for(Map<String,Object> resource:items(scout,"resources")){
            if(nearThreat(n(resource,"x"),n(resource,"y"),350))continue;
            boolean occupied=false;for(Map<String,Object> unit:units(state))if(alive(unit)&&String.valueOf(unit.get("type")).startsWith("extractor")
                    &&distance(unit,n(resource,"x"),n(resource,"y"))<60){occupied=true;break;}
            if(!occupied)count++;
        }return count;
    }
    private boolean occupiedResource(Map<String,Object> resource){
        for(Map<String,Object> unit:units(state))if(alive(unit)&&String.valueOf(unit.get("type")).startsWith("extractor")
                &&distance(unit,n(resource,"x"),n(resource,"y"))<60)return true;
        for(Map<String,Object> unit:items(enemies,"visibleEnemies"))if(String.valueOf(unit.get("type")).startsWith("extractor")
                &&distance(unit,n(resource,"x"),n(resource,"y"))<60)return true;
        return false;
    }
    private boolean idleWorkerNearResource(){
        for(Worker w:workers.values())if("IDLE".equals(w.job)){
            Map<String,Object> actor=find(state,w.unit);if(actor==null)continue;
            if(!"builder".equals(actor.get("type")))continue;
            for(Map<String,Object> res:items(scout,"resources"))if(Boolean.TRUE.equals(res.get("currentlyVisible"))
                    &&distance(actor,n(res,"x"),n(res,"y"))<600&&!nearThreat(n(res,"x"),n(res,"y"),350)){
                boolean occupied=false;for(Map<String,Object> unit:units(state))if(alive(unit)&&String.valueOf(unit.get("type")).startsWith("extractor")&&distance(unit,n(res,"x"),n(res,"y"))<60)occupied=true;
                if(!occupied)return true;
            }
        }return false;
    }
    private void release(Worker w,String reason)throws Exception{
        if(arbiter.release(w.owner))emit("task_ownership_released",map("taskId",w.task,"owner",w.owner,"reason",reason));
    }
    void close()throws Exception{releaseCapabilityFunding("CONTROLLER_ENDED");for(Worker w:workers.values())release(w,"CONTROLLER_ENDED");workers.clear();}
    Map<String,Object> summary(){return map("enabled",enabled,"armyTarget",armyTarget,"activeTarget",activeTarget,"reserveTarget",reserveTarget,
        "hardSafetyCap",hardCap,"builderTarget",builderTarget,"capabilityNeedsAtEnd",needs.size(),"needsResolvedByLegalEvidence",resolvedNeeds,
        "investmentOrders",investments,"mineUpgradesObserved",upgrades,"constructionCompletionsObserved",completedJobs,
        "capabilityReservesStarted",capabilityReservesStarted,"capabilityReservesReleased",capabilityReservesReleased,"capabilityReserveAtEnd",capabilityReserve());}
    private void emit(String event,Map<String,Object> data)throws Exception{
        data.put("gameTimeMs",now);data.put("sessionId",arbiter.stamp().session);data.put("player",arbiter.stamp().player);host.emitStrategy(event,data);
    }
    static Map<String,Object> map(Object... kv){Map<String,Object> m=new LinkedHashMap<String,Object>();for(int i=0;i<kv.length;i+=2)m.put((String)kv[i],kv[i+1]);return m;}
    static double number(Map<String,Object> value,String key,double fallback){return value!=null&&value.get(key) instanceof Number?((Number)value.get(key)).doubleValue():fallback;}
    static boolean ready(Map<String,Object> u){return alive(u)&&number(u,"buildProgress",0)>=1;}
    static List<Map<String,Object>> items(Map<String,Object> m,String key){return m!=null&&m.get(key) instanceof List<?>?list(m,key):Collections.<Map<String,Object>>emptyList();}
    private static String ids(List<Map<String,Object>> units){StringBuilder s=new StringBuilder();for(Map<String,Object> u:units){if(s.length()>0)s.append(',');s.append(id(u));}return s.toString();}
    private static String encode(Object value)throws Exception{return URLEncoder.encode(String.valueOf(value),"UTF-8");}
}
