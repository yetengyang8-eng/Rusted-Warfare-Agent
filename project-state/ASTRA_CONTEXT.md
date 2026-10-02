# 当前 G3/G3.5 接手入口 — 2026-10-02

从当前最新G3/G3.5交付继续：最终测试源码95a917e，分支codex/octopus-g3-execution-20261002。有效63/63验证（历史fixture环境补验另列），未部署/自然验收，未进入G4。

请先读[CURRENT_STATE](CURRENT_STATE.md)、[G3 handoff](../handoff/HANDOFF_Octopus_G3_G35_2026-10-02.md)及[契约](../docs/OCTOPUS_G3_EXECUTION_INTENT.md)。子智能体优先gpt-6.1-sol/high，禁Astra；旧文件名不代表模型授权。下面仅保留历史导航，current/latest/next表述以本段与当前project-state为准。

---

# Shared Agent Context — Octopus G2 2026-10-02

当前候选为 **RW-CANDIDATE-2026-10-02-OCTOPUS-G2-v1**，分支 `codex/octopus-g2-world-state-20261002`，最终测试源码 `5dd76e41d95887ec6c8dc6647476491b9b5241a6`，起点 ff693c8。完整 Windows 60/60、31 Java、26 Python/448 tests、failed steps: 0。只读 WorldState/合法差分已双写，旧策略继续读原响应；未进入 G3。

先读 [CURRENT_STATE](CURRENT_STATE.md)、[NEXT_STAGE_PLAN](NEXT_STAGE_PLAN.md)、[G2 交接](../handoff/HANDOFF_Octopus_G2_2026-10-02.md)、[契约](../docs/OCTOPUS_G2_WORLD_STATE.md)及[证据](../evidence/octopus-g2-2026-10-02/README.md)。固定 JAR SHA256：`1dbeb3084f9ee7dd154b7290daef6c9fc2fe780d9e033fd45539fb3aa01ac7d9`。未部署，未跑自然局/桌面验收。日志成本约 1.83–2.76x，只证明导出体积。

本文件名沿用历史导航；用户后续子智能体偏好为 `gpt-6.1-sol / high`，不使用 Astra。下面是完整保留的 **历史 G1/capacity 上下文**，其中“current/latest/next”只在对应历史时期成立，不能覆盖上述 G2 身份或授权范围。

---

# Astra Context — Octopus G1 2026-10-02

Current engineering candidate: RW-CANDIDATE-2026-10-02-OCTOPUS-G1-v1, implementation5cc3e3f on codex/octopus-g1-clock-trace-20261002 from be7ba94. Full Windows58/58,30Java,25Python/439tests,failedsteps0. Four HTTP paths prove deterministic command/legacy equality; no natural G1 game or desktop deployment. JAR SHA ab5b7fd1fe97a4e4ae0b8cb59cfd7d189c492e8a8e99a8400ce1d3eb93e7287a.

Read CURRENT_STATE.md, NEXT_STAGE_PLAN.md, ../handoff/HANDOFF_Octopus_G1_2026-10-02.md and ../docs/OCTOPUS_G1_TRACE.md first. G2 is the next proposed interface breakpoint; G3/G3.5 and new war behaviors remain future stages.

The remainder is the preceding capacity context preserved for interpretation, not current G1 identity. G0 subsequently found a local a392 Spain4800.845-second derived sample; earlier no-desktop wording below is historical and not a current absence claim. G1 did not rerun or rescan that natural raw.

# Earlier capacity context — frozen 2026-10-01

This file exists because remote Astra cannot access the user's local workspace. It contains the latest desktop acceptance facts and the engineering direction that have not existed in older GitHub-only handoffs.

## Project identity

- Game target: Rusted Warfare PC 1.15, Build 28 / Game Code 176.
- Frozen engine `game-lib.jar` SHA256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`.
- Frozen baseline: `RW-BASELINE-2026-09-30-GS-v1`.
- Active engineering candidate: `RW-CANDIDATE-2026-10-01-PRODUCTION-CAPACITY-v1`.
- Production implementation/test commit: `1264b379752ca4fc3a433f0ab19d2d3ae7582cb6`, branch `codex/production-capacity-v1-20261001`, starting HEAD `51af35dd3a01f82a5f490a486646dbf06b7ec3cb`.
- Candidate JAR SHA256: `a392f692e010c8429a7a93072e60323c83a9deb1ed471bbcbddbd3bbbaf6adb6`; contentDigest `cdb11872abddb6df1917023b92f851c7b6d39823d324418d3993cf12828c1cba`.
- Frozen previous FEEDBACK-v1 JAR: `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`. The desktop runs below belong to this previous JAR, not the new candidate.

## Production Capacity v1 current evidence

The minimal adapter exposes native ordinary factory/product/action/price/tech/queue evidence. Duration and operational throughput are UNKNOWN. Persistent150s economic/useful-demand/safety windows gate at most8 extra military slots; free slots plus the unchanged80% factory utilization gate can commit one new producer through the existing target/BuildJob. Paid-before-queue-visible ordinary occupancy is protected; original ordinary queue completion, artillery ledger, engineer providers and shared execution semantics remain.

The full330MB Spain raw was independently streamed into [spain-capacity.json](../evidence/production-capacity-2026-10-01/spain-capacity.json). All supplied headline metrics matched. The self-lock is partly correct in source; it does not explain most savings because most observable samples still had military slots. Observable target-full time47.720s is sample-held and omits non-exported commitments. Do not call it exact causal blocked time.

HTTP fixtures prove both capacity behavior chains. One isolated natural Big Island match produced native `PASS / VICTORY` at594.272 Battle seconds with0 military increases and0 factory-target increases. Native extension decisions and desktop Spain acceptance remain pending; no win-rate or causal improvement is claimed. Exact regression/archive identity and retained failures are in [validation-results.json](../evidence/production-capacity-2026-10-01/validation-results.json); use the [current handoff](../handoff/HANDOFF_Codex_ProductionCapacity_v1_2026-10-01.md).

## Capabilities already demonstrated

The Agent can autonomously bootstrap a missing builder, expand, upgrade factories, produce/replace combat units, maintain bounded local cohorts, explore/frontier, attack, retreat, detect native VICTORY/DEFEAT, and preserve legal observation boundaries.

Recent layers add:

- max four spatial local-army cohorts with independent targets/frontiers;
- bounded local-crisis squads for small raids near mines/base;
- fairness opportunities for starved cohorts while keeping a shared command gate;
- rear combat engineers as special production providers rather than default frontline responders;
- amphibiousJet production, legal-water approach, native Dive/Fly mode handling, and current-compatibility checks;
- T1→T2 and T2→T3 extractor investment using local threat, quiet window, native price, reserves and remaining-payback budget;
- limited heavyArtillery surplus spending;
- capability funding/reservation and ownership/lease protection.
## Latest independent desktop acceptance

The user independently tested the fixed FEEDBACK-v1 JAR in a fresh desktop environment. These are natural desktop runs, not controlled same-seed A/B tests.

### Spain, ~4801.6 game seconds, team 5, 400×370

- Result: `PARTIAL / ONGOING`; no native terminal result.
- Final observed credits: **1,321,176**.
- Recorded cumulative spend: **479,600**.
- End economic model income estimate: about **352.79 / game second**; summary measured income was **374.41 / game second**.
- 13 new mines; 54 observed mine upgrades = **31 T2 + 23 T3**; 14 ready mines remained at end.
- End mobile armed force: **81**; end composition included 69 heavyTank, 6 heavyArtillery, 4 amphibiousJet, 2 combatEngineer.
- Own losses: **283**, including 184 heavyTank, 17 heavyArtillery, 22 builder. Engineer losses: 0.
- Local crisis tasks: 43; mostly 2-unit responses. 80 respond orders and 43 return orders.
- LocalArmy accepted orders: 423; fairness grants: 287; max active cohorts: 4.
- Rear provider chain: 4 amphibiousJet constructed/ready/transferred. No legal Dive occurred because relevant underwater targets were not currently visible.
- Surplus artillery orders: 23, all matched to ready products.

### Big Island, ~2402.4 game seconds, team 0, 180×180

- Result: `PARTIAL / ONGOING`.
- Final observed credits: **401,708**.
- 23 mine upgrades = **13 T2 + 10 T3**.
- End mobile armed force: **66**; own losses: **47**.
- Local crisis tasks: 26; all selected 2 responders.
- LocalArmy orders: 295; fairness grants: 81.
- Rear provider chain: 6 amphibiousJet constructed/ready/transferred.
- 3 Dive commands accepted; 1 responder was later observed as WATER/range100/COMPATIBLE and received 3 response orders. The target was observed dropping from 260 HP to 170 HP, recorded explicitly as team damage, not exclusive responder damage.

## Important interpretation

The recent work fixed real bottlenecks, but it exposed a larger one: **economic growth now outruns production and operational throughput**. In Spain the balance began rising almost monotonically after roughly 25–30 game minutes. A 300-second rolling spend rate mostly stayed around 70–110 credits/game-second in the long middle/late game while economic capacity was several hundred credits/game-second.

The next architecture should therefore close this loop:

`economic growth → production-capacity demand → predictable/interchangeable production routes → unit delivery → parallel operational control → map control → more economic growth`.

The objective is not high-end micro first. The objective is a robust RTS macro machine that continuously converts map resources into production throughput and useful military pressure.
## Known unresolved breakpoints

1. Production Capacity v1 is implemented but lacks Spain desktop/natural capacity-expansion acceptance. In the prior Spain run only two factories remained despite >1.3M credits; slots-available idle periods remain UNKNOWN.
2. Only the minimum native ordinary factory route adapter is implemented. Expected duration, full fallback, ready-product matching and route substitution remain future work.
3. The operation layer still shares narrow scheduling. More real-time parallel task execution is needed as armies and logistics grow.
4. New-mine expansion still has coarse global danger gates; builder loss remained 22 in the latest Spain run.
5. Capability needs can produce valid amphibious responders, but need-resolution closure is still weak.
6. Early healthy tanks do not yet split into bounded recoverable reconnaissance probes.
7. Mammoth/high-value quality spending is not implemented.
8. Local-crisis return ownership often times out rather than observing a clean regroup.
9. `lastAcceptedAgeMs` diagnostics are not fully trustworthy: the latest Spain raw contains fairness/local orders that are not consistently reflected by that age field. Fix instrumentation before using it as proof of long idleness.

## Evidence discipline for these desktop runs

The large raw desktop JSONL files remain on the user's machine and are not mirrored into GitHub because of size. GitHub contains a structured summary under `evidence/desktop-feedback-2026-10-01/`. If exact raw evidence is required, ask the operator to provide a narrowed excerpt or derived artifact rather than inventing unavailable facts.
