package io.rwagent.client;
import java.util.*;
public final class G2IndependentProbe {
  static int failed,checks;
  static Map<String,Object> m(Object...x){Map<String,Object> m=new LinkedHashMap<>();for(int i=0;i<x.length;i+=2)m.put((String)x[i],x[i+1]);return m;}
  static Map<String,Object> unit(long id){return m("id",id,"type","tank","hp",100,"maxHp",100,"buildProgress",1,"dead",false,"productionQueue",1);}
  static Map<String,Object> state(String session,long t,Object units){return m("sessionId",session,"player",m("teamId",0),"frame",t,"gameTimeMs",t,"ownUnits",units);}
  static Map<String,Object> enemy(long seen){return m("id",9L,"type","tank","hp",100,"x",10,"y",10,"lastSeenGameTimeMs",seen,"domainObservedAtGameTimeMs",seen,"targetDomain","SURFACE");}
  static EventAdapter.Update feed(EventAdapter a,GameClock c,String path,Map<String,Object> raw,long wall){return a.accept(c.observe(path,raw,wall,wall+1,1000L),raw);}
  static void check(boolean ok,String name){checks++;if(!ok){failed++;System.out.println("FAIL "+name);}else System.out.println("PASS "+name);}
  public static void main(String[] args){
    EventAdapter a=new EventAdapter("r");GameClock c=new GameClock("r");
    feed(a,c,"/state",state("s",1000,Arrays.asList(unit(1))),1);
    Map<String,Object> bad=unit(1);bad.put("hp","bad");feed(a,c,"/state",state("s",2000,Arrays.asList(bad)),2);
    try{feed(a,c,"/state",state("s",3000,Arrays.asList(unit(1))),3);check(true,"invalid hp followed by valid row rebases without exception");}catch(Exception e){check(false,"invalid hp followed by valid row: "+e);}
    a=new EventAdapter("r");c=new GameClock("r");feed(a,c,"/state",state("s",1000,Arrays.asList(unit(1))),1);
    feed(a,c,"/state",state("s",2000,"wrong-list"),2);feed(a,c,"/state",state("s",3000,Collections.emptyList()),3);
    check(a.snapshot().ownUnits().get(1L).current.isEmpty(),"invalid list then empty rebase does not revive stale current unit");
    a=new EventAdapter("r");c=new GameClock("r");feed(a,c,"/state",state("s",1000,Arrays.asList(unit(1))),1);
    Map<String,Object> oldMenu=m("sessionId","s","factories",Arrays.asList(m("id",10L,"queue",0,"actions",Collections.emptyList())));
    GameClock.Observation old=c.observe("/combat/production",oldMenu,100,101,1000L);a.accept(old,oldMenu);
    long revision=a.snapshot().revision;EventAdapter.Update repeated=a.accept(old,oldMenu);check(!repeated.accepted&&a.snapshot().revision==revision,"same unstamped Observation cannot apply twice");
    Map<String,Object> newMenu=m("sessionId","s","factories",Arrays.asList(m("id",10L,"queue",1,"actions",Collections.emptyList())));
    feed(a,c,"/combat/production",newMenu,200);check(!a.accept(old,oldMenu).accepted,"older unstamped Observation cannot replace newer menu");
    a=new EventAdapter("r");c=new GameClock("r");feed(a,c,"/state",state("s",1000,Arrays.asList(unit(1))),1);
    feed(a,c,"/combat/observe",m("sessionId","s","frame",1000L,"gameTimeMs",1000L,"visibleEnemies",Arrays.asList(enemy(1000))),2);
    EventAdapter.Update stale=feed(a,c,"/combat/observe",m("sessionId","s","frame",2000L,"gameTimeMs",2000L,"visibleEnemies",Arrays.asList(enemy(1000))),3);
    check(!stale.accepted&&a.snapshot().enemies().get(9L).current.isEmpty(),"stale visible row cannot refresh current hidden dynamics");
    feed(a,c,"/state",state("new",3000,Arrays.asList(unit(2))),4);
    long epoch=a.snapshot().epoch;EventAdapter.Update foreign=feed(a,c,"/combat/observe",m("sessionId","s","frame",4000L,"gameTimeMs",4000L,"visibleEnemies",Collections.emptyList()),5);
    check(!foreign.accepted&&a.snapshot().epoch==epoch&&"new".equals(a.snapshot().sessionId),"delayed foreign combat source cannot reset new context");
    a=new EventAdapter("r");c=new GameClock("r");Map<String,Object> partial=unit(1);partial.put("buildProgress",0.5);
    feed(a,c,"/state",state("s",1000,Arrays.asList(partial)),1);Map<String,Object> done=unit(1);done.put("productionQueue",0);
    EventAdapter.Update conflict=feed(a,c,"/state",state("s",1000,Arrays.asList(done)),2);
    check(!conflict.accepted&&conflict.events.stream().noneMatch(e->e.kind.equals("UNIT_READY_OBSERVED")||e.kind.equals("QUEUE_BECAME_EMPTY")),"same stamp conflict cannot claim ready or empty queue");
    EventAdapter.Update rebased=feed(a,c,"/state",state("s",2000,Arrays.asList(done)),3);
    check(rebased.events.stream().noneMatch(e->e.kind.equals("QUEUE_BECAME_EMPTY")||e.kind.equals("UNIT_READY_OBSERVED")&&
      (e.previous!=null||!"READY_AT_FIRST_OR_REOBSERVATION".equals(e.data.get("readinessEvidence")))),
      "rebase after conflict permits labelled current readiness but suppresses unsupported ready-transition and empty deltas");
    feed(a,c,"/scout/observe?tile=1",m("sessionId","s","frame",2000L,"visibleThreats",Arrays.asList(m("id",5L,"x",1,"y",1))),4);
    EventAdapter.Update otherQuery=feed(a,c,"/scout/observe?tile=2",m("sessionId","s","frame",3000L,"visibleThreats",Collections.emptyList()),5);
    check(otherQuery.events.stream().noneMatch(e->e.kind.contains("NOT_VISIBLE")||e.kind.contains("UNAVAILABLE")),"different query scope cannot create entity disappearance");
    feed(a,c,"/combat/observe",m("sessionId","s","frame",2000L,"gameTimeMs",2000L,"visibleEnemies",Arrays.asList(enemy(2000))),6);
    Map<String,Object> hurt=enemy(3000);hurt.put("hp",10);
    EventAdapter.Update health=feed(a,c,"/combat/observe",m("sessionId","s","frame",3000L,"gameTimeMs",3000L,"visibleEnemies",Arrays.asList(hurt)),7);
    check(health.events.stream().noneMatch(e->e.kind.contains("BAND")),"enemy hp without maxHp cannot become relative health band");
    a=new EventAdapter("r");c=new GameClock("r");feed(a,c,"/state",state("s",1000,Arrays.asList(unit(1))),1);
    feed(a,c,"/state",state("s",2000,Collections.emptyList()),2);
    EventAdapter.Update reappear=feed(a,c,"/state",state("s",3000,Arrays.asList(unit(1))),3);
    check(reappear.events.stream().noneMatch(e->e.kind.equals("UNIT_FIRST_OBSERVED")),"same-epoch previously observed unit cannot be first-observed again after absence");
    feed(a,c,"/scout/plan?unitId=1",m("sessionId","s","frame",3000L,"routeTiles",Arrays.asList(1,2,3)),4);
    EventAdapter.Update routeConflict=feed(a,c,"/scout/plan?unitId=1",m("sessionId","s","frame",3000L,"routeTiles",Arrays.asList(3,2,1)),5);
    check(!routeConflict.accepted&&routeConflict.disposition.contains("CONFLICTING"),"ordered route change is not mistaken for unordered entity-list duplicate");
    System.out.println("G2_INDEPENDENT checks="+checks+" failed="+failed);if(failed>0)System.exit(1);
  }
}
