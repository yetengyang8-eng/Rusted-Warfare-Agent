# Production capacity v1 evidence — 2026-10-01

This round extends the existing StrategyDirector army target and BattleClient factory target. It does not replace the scheduler, LocalArmy, LocalCrisis, mine policy or engineer providers.

## Spain: the self-lock hypothesis is partially correct

Raw source (local, not redistributed):
`G:\deepseek 工作台\游戏环境\P1F-GPTSol61-Feedback-2026-10-01\rw-agent-reports\battle-1790844561819-c02d0138.jsonl`.

SHA256 `e8aba1885566d30487cdf696c23f2fde1fced6f5de5bd5991ad142b17e3632aa`; 330,145,906 bytes; 36,346 lines; zero malformed JSON. Run `python tools/analyze_production_capacity.py RAW --out OUTPUT` to reproduce the bounded extraction in [spain-capacity.json](spain-capacity.json).

| Reading | Independently extracted result |
| --- | --- |
| Outcome / battle duration | PARTIAL / ONGOING, 4801.610 game seconds |
| Final credits | 1,321,176 |
| Recorded spend / ordinary unit production / new factory | 479,600 / 282,900 / 700 |
| Factory target increases / end target / max | 0 / 2 / 5 |
| End factory utilization / threshold | 47.35% / 80% |
| End production consumption / sustainable surplus | 48.0 / 304.8 credits per game second |
| Last strategy income estimate / army target / hard cap | 352.79 / 96 / 128 |
| Final observable committed | 81 mobile armed + 1 exported queue = 82 |
| Sample-held time at/above strategy target | 47.720 game seconds |
| Sample-held time at/above hard cap | 0 |
| First observable target-full sample | gameTimeMs 4485884, 94 armed + 2 queue = 96 |

The source really stops ordinary production at the smaller of strategy target and hard cap; the strategy formula normally clamps at96. Factory expansion separately requires at least80% long-window busy time. This permits the hypothesised lock when the strategic target is full while hard capacity remains.

Spain does not establish that this caused most accumulated cash. For most exported observations, military slots remained free. The only explicit strategy-target idle transition occurred once; the factory refusal log also emitted one `FACTORY_NOT_SATURATED` transition (72.23% at gameTimeMs376384). Old transition-only logs cannot reconstruct every inactive production opportunity. Slots available plus idle factories is therefore **UNKNOWN**, not automatically producer throughput limitation. Shared scheduling/refill delay is a follow-up hypothesis, not a proven root cause or an implemented scheduler change.

The duration calculation holds each sampled observation until the next and excludes unexported specialist/paid commitments. It is a diagnostic estimate, not exact blocked time. An earlier temporary extraction used another interval attribution and reported about48.43s; the checked-in reproducible algorithm reports47.720s. Neither number proves causal idle duration.

## Implemented behavior and decision evidence

- Native ordinary menu quotes expose producer, product, action, tech, queue, price and affordability. Duration and operational throughput remain UNKNOWN.
- `production_capacity_assessment` distinguishes money, reserves, no useful demand, military target, hard cap, producer throughput, tech, route absence, outstanding expansion and UNKNOWN.
- Only fresh current-visible `COMPATIBLE` plus `APPROACH_PATH_KNOWN` observations for an ordinary product establish useful demand. Missing/stale/UNKNOWN observations cannot authorise extra capacity.
- Default150s windows require at least80% observed coverage and at least80% positive-surplus/useful-demand/safe observations. Gaps over6s are excluded. Factory expansion retains the80% utilization threshold. Tests override only window length to the existing60s minimum.
- Military capacity grows at most8 slots per window with reserves/recovery/native affordability checked and room below128. The raised session floor survives the original96 formula; each later increase must satisfy the gates again. The floor does not authorise cash-only production or increase hard cap.
- Producer expansion reuses the existing factory target and BuildJob, one target increase at a time. Native builder factory quote, reserves, fresh useful demand, recovery and hard cap are checked again before unpaid execution. Queue-empty frames do not cancel an already proven commitment.
- Commitment ID connects `production_facility_committed`, planned, order accepted, observed, ready and `production_capacity_after_expansion`. Accepted but unobserved expansion is held without a duplicate debit/order.
- Ordinary accepted-before-queue-visible occupancy is conservatively counted. The existing ordinary queue-completion contract and dedicated heavyArtillery ledger remain in place. No full migration to per-product ordinary ready matching was shipped.

## Validation provenance

All tests/builds use disposable directories under `%TEMP%`. No desktop game was started/stopped; original game engine, FEEDBACK-v1 installed JAR, settings, saves and replays were preserved.

Baseline `51af35d`: the first JDK17 matrix had20 failed steps (19 native Selector/loopback environment failures and one omitted-docs staging failure). JDK13 isolated re-run had one missing-assets failure; assets-complete StrategyNativeHarness recheck passed51 native fixture checks. Effective baseline:54/54 steps. Raw outputs are retained in `validation/`.

An intermediate implementation full matrix had one real LocalArmy regression caused by an attempted ordinary-product ready wait. That wait was removed, preserving the old completion contract; final LocalArmy recheck passed12 tests. Preserve this failure output; do not present intermediate results as final green.

The first final matrix passed55 of56 steps; the new ProductionCapacityHarness had omitted CommandArbiter observation initialization in its military-floor fixture. Only the test fixture was repaired, and its27 checks passed on recheck. A further complete matrix was then run with the repaired fixture; use its explicit summary in `validation-results.json`, not an inferred green from the failed matrix.

The delivered317,988-byte JDK13 JAR is the exact artifact used by the first final matrix, the27-check supplement and the natural match. A later full rebuild has a different whole-archive SHA because ZIP metadata changed. All107 ZIP entries, including manifest and106 non-manifest entries, were compared byte-for-byte and are identical; contentDigest is `cdb11872abddb6df1917023b92f851c7b6d39823d324418d3993cf12828c1cba`. Both archive identities are recorded explicitly; the delivery keeps the natural-tested archive.

## Natural native match boundary

One disposable Big Island match at requested5x / difficulty0 reached native `PASS / VICTORY` at594.272 Battle game seconds, before its900s budget. Bootstrap/economy/development/battle report checks all had `issues=[]`. The engine exited0 and was no longer alive after cleanup. See [natural summary](natural-headless/summary.json), [runtime](natural-headless/runtime.json), and [raw reports ZIP](natural-headless/match-evidence.zip).

There were36 capacity assessments and0 military-capacity increases /0 producer-target increases. The normal initial second factory completed; that is not a new capacity expansion. This run demonstrates natural completion and diagnosis/log integration. It does not naturally demonstrate the synthetic military-release or producer-expansion chain, establish Spain desktop acceptance, or prove a throughput/win-rate improvement.

Final validation and candidate identity are recorded in [validation-results.json](validation-results.json) and the delivery manifest. HTTP fixtures and native object fixtures remain separate from natural headless/desktop acceptance. This candidate has no desktop Spain acceptance and establishes no win-rate, reduced-cash or causal-throughput improvement.

Final complete Windows matrix: **56/56 steps**,29 Java harness runs,24 Python suites /435 tests, explicit `failed steps: 0`. New HTTP capacity suite15 tests and Java capacity harness27 checks pass. Windows ReportCommitHarness skips its POSIX-only open-handle displacement simulation; the remaining commit checks passed. All40 production/build/affected-test input hashes are recorded in the validation/delivery manifests.
