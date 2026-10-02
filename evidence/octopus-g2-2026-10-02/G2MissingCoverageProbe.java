package io.rwagent.client;
import java.util.*;
public final class G2MissingCoverageProbe {
  public static void main(String[] args) {
    EventAdapter a=new EventAdapter("r"); GameClock c=new GameClock("r");
    G2IndependentProbe.feed(a,c,"/state",G2IndependentProbe.state("s",1000,Arrays.asList(G2IndependentProbe.unit(1))),1);
    G2IndependentProbe.feed(a,c,"/combat/observe",G2IndependentProbe.m("sessionId","s","frame",1000L,"gameTimeMs",1000L,"visibleEnemies",Arrays.asList(G2IndependentProbe.enemy(1000))),2);
    EventAdapter.Update combat=G2IndependentProbe.feed(a,c,"/combat/observe",G2IndependentProbe.m("sessionId","s","frame",2000L,"visibleEnemies",Collections.emptyList()),3);
    G2IndependentProbe.check(!combat.accepted&&"MISSING_COMBAT_SOURCE_TIME_COVERAGE".equals(combat.disposition)
      &&combat.events.stream().noneMatch(e->e.kind.contains("NOT_VISIBLE"))&&a.snapshot().enemies().get(9L).current.isEmpty(),
      "empty combat without native time invalidates coverage without visibility-loss delta");
    G2IndependentProbe.feed(a,c,"/scout/observe",G2IndependentProbe.m("sessionId","s","frame",2000L,"visibleThreats",Arrays.asList(G2IndependentProbe.m("id",5L,"x",1,"y",1))),4);
    EventAdapter.Update scout=G2IndependentProbe.feed(a,c,"/scout/observe",G2IndependentProbe.m("sessionId","s","visibleThreats",Collections.emptyList()),5);
    G2IndependentProbe.check(!scout.accepted&&"MISSING_SCOUT_NATIVE_STAMP_COVERAGE".equals(scout.disposition)
      &&scout.events.stream().noneMatch(e->e.kind.contains("NOT_VISIBLE")),
      "empty scout without all native stamps invalidates coverage without threat-loss delta");
    System.out.println("G2_MISSING_COVERAGE checks="+G2IndependentProbe.checks+" failed="+G2IndependentProbe.failed);
    if(G2IndependentProbe.failed>0)System.exit(1);
  }
}
