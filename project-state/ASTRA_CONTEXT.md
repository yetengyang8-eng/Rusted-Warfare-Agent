# Astra Context — 2026-10-01

This file exists because remote Astra cannot access the user's local workspace. It contains the latest desktop acceptance facts and the engineering direction that have not existed in older GitHub-only handoffs.

## Project identity

- Game target: Rusted Warfare PC 1.15, Build 28 / Game Code 176.
- Frozen engine `game-lib.jar` SHA256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`.
- Frozen baseline: `RW-BASELINE-2026-09-30-GS-v1`.
- Active candidate: `RW-CANDIDATE-2026-10-01-FEEDBACK-v1`.
- Production implementation commits: `f5b1709` plus audit/observation fix `5020820`; delivery/docs closeout `590030d`.
- Candidate JAR SHA256: `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`.

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

1. Production/capacity scaling does not follow economic growth. Only two land factories remained in the latest Spain run despite >1.3M credits.
2. Production paths are not yet represented as predictable, replaceable routes with throughput/cost/producer/tech/role semantics.
3. The operation layer still shares narrow scheduling. More real-time parallel task execution is needed as armies and logistics grow.
4. New-mine expansion still has coarse global danger gates; builder loss remained 22 in the latest Spain run.
5. Capability needs can produce valid amphibious responders, but need-resolution closure is still weak.
6. Early healthy tanks do not yet split into bounded recoverable reconnaissance probes.
7. Mammoth/high-value quality spending is not implemented.
8. Local-crisis return ownership often times out rather than observing a clean regroup.
9. `lastAcceptedAgeMs` diagnostics are not fully trustworthy: the latest Spain raw contains fairness/local orders that are not consistently reflected by that age field. Fix instrumentation before using it as proof of long idleness.

## Evidence discipline for these desktop runs

The large raw desktop JSONL files remain on the user's machine and are not mirrored into GitHub because of size. GitHub contains a structured summary under `evidence/desktop-feedback-2026-10-01/`. If exact raw evidence is required, ask the operator to provide a narrowed excerpt or derived artifact rather than inventing unavailable facts.
