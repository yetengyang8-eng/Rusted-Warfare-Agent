# Current State

Updated: 2026-10-01. This file describes **now**, not project history.

## Lineage

- Frozen baseline: `RW-BASELINE-2026-09-30-GS-v1` (`6214868`). Keep for lineage; do not reset current source to it.
- Current repository HEAD before this GitHub refresh: `590030d`.
- Active candidate: **RW-CANDIDATE-2026-10-01-FEEDBACK-v1**.
- Production implementation: `f5b1709`; audit/observation correction: `5020820`; delivery/docs closeout: `590030d`.
- Fixed candidate JAR SHA256: `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`.
- Frozen `game-lib.jar` SHA256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`.

## What FEEDBACK-v1 added

- Rear engineer providers for weapon-domain gaps; native-priced amphibiousJet production, legal-water approach, Dive/Fly mode handling, and current actor/target compatibility checks.
- Local T2/T3 mine investment using local threat, quiet windows, native quotes, reserves and remaining-payback budget.
- `LocalCrisisPolicy`: bounded 2–6-unit responses to small visible raids near mines/base, with ownership and return lifecycle.
- Cohort fairness scheduling and diagnostics while preserving the shared command gate.
- Inherited Operations behavior: missing-builder bootstrap, up to four local cohorts, independent local targets/frontiers, bounded surplus heavyArtillery.

Windows development matrix for the fixed candidate was 54/54 effective steps, 28 Java runs, 23 Python suites, 420 distinct Python tests, 0 Python skipped/failed after affected recheck. Synthetic/native-fixture evidence remains distinct from natural desktop evidence.
## Latest user desktop acceptance

A fresh independent FEEDBACK-v1 desktop environment was tested after the candidate delivery. Both runs used the fixed JAR above and ended `PARTIAL / ONGOING` at the requested budget.

### Spain long run — ~4801.6 game seconds

- Final credits: **1,321,176**; recorded spend: **479,600**.
- 13 new mines; 54 observed mine upgrades = **31 T2 + 23 T3**; 14 ready mines at end.
- 81 mobile armed units at end; 283 own losses, including 184 heavyTank and 22 builder.
- 43 local-crisis tasks; 423 LocalArmy accepted orders; 287 fairness grants; max four cohorts.
- 4 rear-provider amphibious jets constructed/ready/transferred; no Dive because relevant underwater targets were not legally current-visible.
- 23 surplus heavyArtillery purchases/products.

### Big Island — ~2402.4 game seconds

- Final credits: **401,708**.
- 23 mine upgrades = **13 T2 + 10 T3**.
- 66 mobile armed units at end; 47 own losses.
- 26 local-crisis tasks; 295 LocalArmy orders; 81 fairness grants.
- 6 provider jets produced/transferred; 3 Dive commands accepted; 1 jet was observed WATER/range100/COMPATIBLE and received response orders; target HP later changed 260→170 with attribution explicitly recorded as team damage.

Full remote-readable interpretation is in `ASTRA_CONTEXT.md`; structured desktop evidence is in `../evidence/desktop-feedback-2026-10-01/`.

## Current bottleneck

The Agent is no longer primarily constrained by acquiring economy. It is constrained by **converting a growing economy into growing production throughput and concurrently controlled military power**.

Current mainline objective:

`economy growth → production capacity → predictable/interchangeable production routes → military delivery → parallel operations → map control → further economy growth`

Do not treat the old Global Strategy or Operations next-step lists as the current overall priority. See `NEXT_STAGE_PLAN.md`.