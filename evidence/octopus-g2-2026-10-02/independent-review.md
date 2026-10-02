# G2 independent read-only review

Review date: 2026-10-02 (Asia/Shanghai). Source baseline: `ff693c8`; final reviewed commit: `5dd76e41d95887ec6c8dc6647476491b9b5241a6`. This review does not change policy, scheduler, native bridges or repository source. Review status: **review complete; final independent probe 12/12 PASS plus missing-coverage negative probes 2/2 PASS, no remaining identified acceptance blocker**. Initial failures and assertion clarification are retained below.

## Inputs read

- Repository AGENTS/current state/next-stage plan and G1 handoff/Trace contract.
- `_audit/octopus-g0-20261002/G0_AUDIT.md` and `notes-time.md`.
- `_validation/octopus-g1-20261002/八爪鱼系统_工程语义_v0.1_G0校准版.txt`.
- Actual RuntimeBridge, CombatBridge and ScoutBridge response builders; current BattleClient GET/validation sites, GameClock and G1Trace.

## Verified ingestion boundary

| Source | Actual native identity | Authoritative scope | Current validated caller |
| --- | --- | --- | --- |
| `/state` | session, player.teamId, frame, gameTimeMs | Complete observed own-unit list; own fields and credits | End of observe(), after active/local/session/frame and execution Stamp guards |
| `/combat/observe` | session, frame, gameTimeMs; no player | Current legal visibleEnemies; rememberedEnemies and enemyIntel remain historical | Main-loop get followed by existing check |
| `/scout/observe` | session, frame; no top-level gameTimeMs/player | Legal map/resource history and source-local visible/remembered threats | Main-loop get followed by existing check |
| `/combat/production` | session only; no source frame/gameTime/player | Current completed-landFactory menu subset, actions/prices/queue quote | produce get/check and Strategy readStrategy/check |

`/state.productionQueue` and menu `factories[].queue` are different source facts. Menu absence cannot make an own unit unavailable. Scout resource `currentlyVisible` does not make the whole remembered resource collection a current dynamic census. Combat item lastSeen/domainObservedAt fields must not be replaced by source receipt time or latest state anchor.

Other optional GETs such as `/scout/visible`, direct `/expansion/plan`, builder action diagnostics and builder-production do not uniformly call existing check. G2 must not claim universal ingestion or silently add old-policy validation. Unsupported/unchecked reads remain explicitly outside the world projection.

## BattleClient integration review

- Pending raw Map identity plus immutable Observation is consumed only at the caller's validation point. Every GET clears the previous pending identity; rejected/non-200/malformed reads cannot reuse it.
- State bypasses the session-only check hook and ingests after all existing state guards. The initial state still obtains a valid observation even before BattleClient assigns its session field.
- Native POST receipts are separate parsed Map objects, so check(receipt) cannot ingest them as GET state.
- G2 is read-only double-write; old policy returns and reads the same raw Map. No GET, owner, gate, funds, slot or scheduling change was found.
- G2 requires Trace enabled. `g1Trace=false` disables both, while `g2WorldState=false` disables only G2. The original GameClock path remains unchanged.
- Optional G2 exceptions are contained and counted. This does not prove zero overhead or natural polling equivalence.
- Initial review found a classification conflict: using legacy event() with a source traceObservation stamped derived G2 events as OBSERVED in top-level G1 trace. Root corrected this with worldRecord(): explicit original source observation, null command link, OBSERVATION only for OBSERVED data, otherwise DERIVED_STATE. Re-read confirmed the correction.

No remaining BC integration acceptance blocker identified in the reviewed diff. Final acceptance also requires core reset/order/freshness contracts and deterministic command/legacy equivalence tests.

## Core review and independent probes

WorldState immutable projection was inspected: source-specific snapshots, historical LastObservation, current facts cleared on coverage expiry, unknown confirmed enemy losses and no commitment authority.

An independent temporary Java harness compiles only GameClock, Json, WorldState, EventAdapter and the probe, using the existing game-bundled Java 13 compiler. It neither starts a game nor edits repository source. Files: `independent/G2IndependentProbe.java`, `compile-1.log`, `probe-1.log`, `probe-2.log`.

Initial probe-2 result: **11 checks, 6 passed, 5 failed**. All five failures were sent to the implementing agent and root; they are core ingestion/event issues rather than strategy changes.

| Case | Initial result | Mechanism |
| --- | --- | --- |
| Invalid own HP row followed by valid rebase | FAIL, ClassCastException | diffOwn computed oldAlive from invalid prior payload before checking continuity |
| Invalid own list followed by valid empty list | FAIL, stale current unit revived | Missing reconciliation iterated only the invalid prior list, leaving old own-map rows AVAILABLE under the newly fresh source |
| Same unstamped production Observation replay | FAIL, accepted again/revision changed | Native timestamp comparison provided no Observation-identity dedup for untimed menus |
| Older unstamped production Observation after newer menu | FAIL, stale menu accepted | No read-order rejection when both native source timestamps are absent |
| Previously observed same-epoch unit absent then reappears | FAIL, UNIT_FIRST_OBSERVED emitted again | before-list absence ignored retained unit identity/history |
| Visible enemy row with stale lastSeen time | PASS | Source invalidated; current enemy dynamics remained unknown |
| Delayed foreign combat session after new state context | PASS | Refused without reset or epoch change |
| Conflicting same source stamp | PASS | No ready or queue-empty event |
| Rebase after same-stamp conflict | PASS | Unsupported ready/queue-empty deltas suppressed |
| Different query scope has empty rows | PASS | No false disappearance event from unrelated coverage |
| Enemy HP change without maxHp | PASS | No relative HP-band event |

Do not treat the initial failures as final delivery results.

## Final correction verification

The implementing agent corrected all five issues. A fresh Java 13 / `--release 8` compile of the corrected core and independent harness produced **12 checks / 0 failed**, in `independent/probe-5.log`. That intermediate source identity is retained in `independent/source-hashes-probe-5.json`.

At the prior core freeze (strict native `/state.player.teamId` identity and same-stamp long-wall-gap diagnostics), the same 12 probes were recompiled and rerun once: **12/12 PASS** in `independent/probe-final.log`. That result and `independent/source-hashes-final.json` describe the superseded EventAdapter SHA256 `128f7d3f8163b0259b1998aa4bb2ab157dde0de6ea0fb5a69ae0440b441afdae`; they are not the final delivery identity.

Final commit `5dd76e41` adds two validateRows coverage guards: combat observations need native game time even when visibleEnemies is empty, and scout observations need at least one native stamp even when visibleThreats is empty. The unchanged original 12-probe harness was compiled and rerun against this commit: **12/12 PASS** in `independent/probe-final2.log`. Two additional isolated negative probes in `G2MissingCoverageProbe.java` prove that each missing-coverage empty packet is refused and cannot emit a visibility-loss delta: **2/2 PASS** in `independent/coverage-final2.log`. The combat case also checks that stale current enemy dynamics become unknown. Compile output: `independent/compile-final2.log`; exact compiler inputs: `independent/source-hashes-final2.json`. Final EventAdapter SHA256 is `5fa8ffa9d6c14acd89dc3367b1c5c4cfb3771be2894d7e007c66da7e91e74d51`; WorldState remains `45c791026665ac53597117068a4da413cbd09d3aaed17584ce2e3a64d853c3aa`.

BattleClient SHA256 `3394a4335f7ad8b68243ae09a1883f06359f538ec835f3c742b355f3e3117e52` matches the already reviewed writer fix. The final `docs/OCTOPUS_G2_WORLD_STATE.md` was read against source: source authority, G1/G2 switches, timestamp nullability, identity replay, gap/rebase, observation-only readiness, event ordering and bounded history match. No blocking contract mismatch was identified. An optional documentation clarification is to state explicitly that state identity requires a genuine integral native player.teamId and does not use a generic player.id fallback.

In addition to the original 11 cases, the final harness verifies that a same-stamp ordered `routeTiles` reversal is a conflict, not an unordered-list duplicate. Fingerprint normalization now limits order-insensitive treatment to entity collections and retains route/action ordering.

An assertion clarification is preserved in probe-3 and probe-4: the initial test prohibited every UNIT_READY_OBSERVED after an invalid sample. The corrected core explicitly reports a newly valid ready snapshot with `readinessEvidence=READY_AT_FIRST_OR_REOBSERVATION`, no previous observation link and no production-lineage claim. That is valid current readiness evidence, not an unsupported cross-gap completion transition. The independent assertion now permits only this labelled current observation while still forbidding a fabricated ready transition or QUEUE_BECAME_EMPTY. The conflicting same-stamp packet itself remains prohibited from emitting readiness.

Remaining evidence boundary: these are deterministic interface tests and source review. They do not prove natural match polling equivalence, gameplay benefit, enemy kills, factory-to-product lineage, scheduling throughput or G3 ownership generations. No native game was started by this reviewer. No repository source/test/runner/commit was changed by this reviewer; only isolated review/probe artifacts were written.
