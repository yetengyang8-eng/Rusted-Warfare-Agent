# FEEDBACK-v1 Independent Desktop Acceptance — 2026-10-01

This directory summarizes the user's independent desktop acceptance of `RW-CANDIDATE-2026-10-01-FEEDBACK-v1` after the candidate delivery.

Candidate JAR SHA256: `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`.
Frozen game-lib SHA256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`.

The large raw desktop Battle JSONL files are not mirrored here because they are approximately 330 MB and 89 MB. This summary records derived metrics and proof boundaries. Ask the operator for narrowed raw excerpts when exact event-level proof is required.

## Spain long run

- Battlefield dimensions: 400×370 tiles; team 5.
- Battle budget reached at ~4801.6 game seconds; `PARTIAL / ONGOING`.
- Final observed credits: **1,321,176**.
- Cumulative recorded spend: **479,600**.
- Spend categories: unit production 282,900; mine upgrades 135,400; strategic construction 29,000; strategic capability 18,500; new mines 9,100; factory upgrades 4,000; new factory 700.
- 13 new mines; 54 observed mine upgrades = **31 T2 + 23 T3**; 14 ready mines at end.
- End mobile armed units: **81**. End major composition: 69 heavyTank, 6 heavyArtillery, 4 amphibiousJet, 2 combatEngineer.
- Own losses: **283**; heavyTank 184; heavyArtillery 17; builder 22; combatEngineer 0.
- Local crisis tasks: 43; responder group sizes were mostly 2 (40 tasks), with two 3-unit and one 6-unit task.
- LocalArmy accepted orders: 423; fairness grants: 287; max active cohorts: 4.
- Rear-provider chain: 4 amphibiousJet constructed, observed ready and transferred. No legal Dive response occurred because relevant underwater targets were not currently visible.
- Surplus heavyArtillery orders/products: 23/23.
## Big Island run

- Battlefield dimensions: 180×180 tiles; team 0.
- Battle budget reached at ~2402.4 game seconds; `PARTIAL / ONGOING`.
- Final observed credits: **401,708**.
- 23 observed mine upgrades = **13 T2 + 10 T3**.
- End mobile armed units: **66**; own losses: **47**; combatEngineer losses: 0.
- Local crisis tasks: 26; all selected two responders.
- LocalArmy accepted orders: 295; fairness grants: 81.
- Rear-provider chain: 6 amphibiousJet constructed/ready/transferred.
- 3 Dive commands were accepted. One responder was later observed as `WATER`, range 100, `COMPATIBLE`, and received 3 response orders.
- The associated target was later observed at 260→170 HP. Attribution in the raw event is **team damage, not exclusive responder damage**.

## Economic interpretation

A 60-game-second sample of the Spain run shows the balance starting to rise almost monotonically after roughly 25–30 game minutes. The logged economic-capacity estimate reached >600 credits/game-second around the peak, while a 300-second rolling spend rate in much of the middle/late game was roughly 70–110 credits/game-second.

This supports a production-throughput bottleneck. It does **not** by itself prove that any specific factory count, unit mix, or APM value is optimal.

## Important limitations

- These runs are not controlled same-seed A/B tests against Operations-v1.
- Audit PASS means the recorded contract was respected; it does not mean the match was won.
- `lastAcceptedAgeMs` is not fully reliable in the latest Spain run: raw local/fairness accepted orders exist that are not consistently reflected by that age field. Fix diagnostics before treating the maximum reported age as real inactivity.
- Existing-mine upgrades are now locally gated, but new-mine expansion still has coarse global danger logic. The latest Spain run still lost 22 builders.
- Capability responder production works, but capability-need terminal resolution remains incomplete.

Machine-readable summary: `metrics.json`.