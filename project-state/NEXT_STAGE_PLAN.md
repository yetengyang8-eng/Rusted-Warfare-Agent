# Next Stage Plan

Updated:2026-10-02 after Octopus G1. G1 is delivered; this plan is an interface proposal, not authorization to start later stages during the G1 round.

## Next breakpoint G2

Consume **validated raw native response Map + immutable GameClock.Observation** from the G1 contract. Preserve endpoint source game time/frame/session/player, requestPath and wall receive interval. Latest /state time is a detection anchor; missing production-menu source times remain null. An Observation log is not proof that downstream active-session/legal guards passed.

Build a shared read-only WorldState and deterministic snapshot-derived events, initially double-write or compare while old policies still read the same responses. Define per-source authority/freshness, prior entity identity, session/player reset, frame/time rollback, long coverage gap, duplicates/out-of-order data, and snapshot reconciliation. Equal source timestamps still have distinct observation IDs; the G1 event sequence is not a domain event bus.

Required evidence boundaries: hidden dynamics stay UNKNOWN; LastObservation is historical; missing enemy does not imply death; ENEMY_SITE_CLEARED does not imply confirmed kill; queue empty does not imply ready; accepted-but-unobserved paid occupancy must survive a zero queue snapshot. Do not combine endpoint samples into a fictitious atomic frame.

Minimum G2 validation: identical legal packet sequences produce deterministic derived events; reset/gap/rollback are explicit; fog-hidden enemy fields never refresh; queue/paid commitment contracts survive reconciliation; old policy commands remain equivalent under paired inputs. Add focused cases then the full platform matrix.

## Explicit later boundaries

G3: owner_generation, old Intent rejection, queue/execution scheduling, elapsed-game-time budget, same-actor conflicts and batch effectiveCredits/slots. Both command gate and produce/main caller early returns need migration; G2 must not change them.

G3.5: pilot the existing provider->amphibious chain on the new foundation, preserving funding/Purchase/paidConstruction and first-match evidence; formal Pool/task/mode/route semantics are not delivered by G1.

G4: minimal General registry then FREE/LocalResponse/reservations/Join and arrival ownership. G5: legally sourced Combat/Crisis/ThreatTask; confirmed enemy loss still needs evidence. G6: separate capability search, Scout/Reclaim and formation scaling slices. Do not pick unapproved strategy thresholds from existing cohort/HP constants.

## G1 caveat to carry forward

G1 proves deterministic four-path command/legacy equivalence, not identical natural wall-time polling. Trace export volume increased about2.25–3.01x in fixtures. Preserve failures and measure real overhead before making performance claims or choosing compression; do not change tactical gates to hide logging costs.

Start from [current state](CURRENT_STATE.md), [G1 handoff](../handoff/HANDOFF_Octopus_G1_2026-10-02.md), [trace contract](../docs/OCTOPUS_G1_TRACE.md) and [fresh evidence](../evidence/octopus-g1-2026-10-02/README.md). Previous macro-production priorities remain historical context in be7ba94, not a reason to expand the current Octopus stage.
