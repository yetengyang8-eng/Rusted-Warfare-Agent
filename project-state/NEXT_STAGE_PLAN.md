# Next Stage Plan

Updated: 2026-10-01 after independent FEEDBACK-v1 desktop acceptance.

## Strategic goal

Build the first closed-loop macro system where economic growth automatically causes production capacity and useful military throughput to grow, while the operation layer can concurrently control the resulting larger force.

The target loop is:

`economic growth → capacity demand → production routes → unit delivery → parallel operations → map control → more economic growth`

The next implementation round should advance **one coherent slice** of this loop rather than simultaneously redesigning every subsystem.

## Priority 1 — Production capacity model

Create an explicit model of current production throughput and backlog. The controller must be able to answer:

- current observed/estimated income rate;
- recent spend rate and persistent net resource accumulation;
- producers available by route and their queue/availability state;
- sustainable resource-consumption capacity;
- military demand/backlog that could use more production;
- whether the bottleneck is money, producer throughput, army slots, capability constraints, or operations.

Do not start with a fixed `credits > X => build factory` threshold. Use sustained mismatch between economic inflow and useful production throughput as the main signal.
## Priority 2 — Predictable, replaceable production routes

Represent production as routes rather than hard-coded unit special cases. A route should expose at least:

- requested military/capability role;
- eligible producer type(s) and required tech/mode;
- current legal native action/quote;
- expected production/build duration where observable or safely estimated;
- resource throughput and capacity occupancy;
- ready-product matching and task handoff;
- fallback route(s) when the preferred producer is unavailable.

Initial routes should cover landFactory→heavyTank, upgraded landFactory→mammothTank candidate, combatEngineer→heavyTank, combatEngineer→amphibiousJet, and existing heavyArtillery surplus production. Runtime menu evidence overrides static price assumptions.

## Priority 3 — Capacity scaling and quality spending

When sustained economic inflow exceeds useful consumption capacity, increase **capacity itself** before allowing balance to grow without bound.

Candidate actions include additional land factories, factory upgrades where justified, bounded engineer-provider throughput, and a small high-value mammoth allocation. Preserve all existing reserves, hard safety caps, native menu legality, paid-but-not-ready occupancy, and role compatibility.

The controller should be able to substitute routes: if one producer is blocked/lost, remaining demand should be reassigned rather than abandoned or duplicated.

Success is not “cash decreased”. Success is that production throughput rises in response to persistent surplus and delivered units enter legal operational ownership.
## Priority 4 — Operational throughput and parallel execution

The Agent must control more units without making every subsystem wait behind one monolithic reasoning loop.

First fix instrumentation: accepted-age metrics must include fairness/local accepted orders, active-mode windows, rule-main commands, and newly joined reinforcements. Distinguish no target, no frontier, cooldown, global gate occupancy, ownership conflict, and genuine no-order starvation.

Then separate planning from execution. Strategic decisions can remain slower, while already-approved tasks should have persistent execution state and compete fairly for command opportunities. Keep ownership and legal-target checks centralized; do not create multiple uncontrolled actuators.

Do not assume “higher APM” alone is the solution. The goal is more **useful concurrent task progress**: multiple cohorts, local defense, recon, expansion, provider production, and replacement production should continue without starving each other.

## Priority 5 — Expansion safety and secondary gaps

After the production/operations slice is stable, continue these known gaps:

- replace coarse global new-mine veto with HOME / WORKER / ROUTE / SITE local risk; latest Spain still lost 22 builders;
- improve capability-need closure after a legal responder is produced;
- add bounded recoverable multi-direction early tank reconnaissance;
- tighten local-crisis return/regroup observation instead of relying heavily on handoff timeout.

## Validation and evidence

Every implementation round should add focused contracts, affected regression, and a small number of natural native/desktop checks. Preserve PARTIAL/failure evidence. Do not claim causality from unmatched desktop runs.

For the production-capacity work, log enough to reconstruct: income estimate, spend rate, capacity estimate, chosen production route, producer occupancy, backlog, capacity-expansion decision, delivered product, and operational handoff.

The next Astra implementation round should preferably start with **Priority 1 + the minimum data structures needed for Priority 2**, then stop at a verifiable candidate rather than attempting all five priorities at once.