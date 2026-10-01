# Engineer provider / amphibious responder — 2026-10-01

This subtask continues repository HEAD `82c8ff8`. Original engine/assets, player settings,
saves/replays and the desktop game were not changed or started/stopped.

## Actual breakpoints and resulting behavior

The prior controller purchased `combatEngineer` for a weapon-domain gap, allowed the
engineer itself to respond, constructed jets only for `MOVEMENT_APPROACH_GAP`, and
explicitly skipped jets for `WEAPON_DOMAIN_GAP`. A produced flight-mode jet also has
no native underwater attack: the mode conversion was missing entirely.

The controller now binds an engineer as a rear provider for a weapon gap. A forward
or locally threatened provider uses an ordinary owned move to return toward its own
command center. Production reads the existing audited local construction plan, checks
the local site and all reserves, uses the real native quote/action, and emits one
normal construction command. The minimum ordinary builder keeps its original role.
Existing ready compatible/mode-capable jets are considered before buying another jet.

A new ready owned jet within the recorded construction site inherits the explicit
need. The matching records the before-ID set and a session-wide used-product set;
one product cannot satisfy two commitments. This is a role match, not a proof that
the unit was created by that particular engineer. Half-built, old and missing products
do not fulfill the commitment. Paid construction retains a combat-cap slot while the
product is unobserved, including when the provider is critically wounded and returned
home. Provider loss / construction timeout / funding timeout release their bounded
commitment with a reason; surviving native unfinished units still count in the native
armed capacity bound.

The responder first obtains a current visible engagement observation. A future Dive
plan leaves current flight compatibility `INCOMPATIBLE`. Its goal is water already
present in legal terrain memory, with the frozen future-mode 100-unit range. It uses
ordinary owned movement to that position, submits only the own native Dive action,
waits for actual observed compatibility, then attacks through the same ownership and
global command gate. Accepted Dive is never treated as a ready underwater weapon.
Hidden weapon-gap contacts cannot start a fresh Dive or flight-mode investigation
attack. Return can use the own native Fly action. Repeated mode failures use the
existing maximum-three need-attempt budget rather than an unbounded action loop.

## Frozen mechanism audit

Frozen `game-lib.jar` SHA256:
`8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`.

`assets/units/combat_engineer/combat_engineer.ini` SHA256:
`db1f23edd7fb4bab30449278f53d446f8598ed20035665ae9ede9ea557b855a5`.
Lines 6/12/43/44 establish price 3500, build speed 0.0005, nano range 95,
nano build speed 2. Lines 206/208 show an underwater torpedo conditional on
`self.overwater`. Those capabilities do not make the constructor a preferred
front-line response unit.

**Do not use `assets/units/amphibious_jet/amphibious_jet.ini` as the native jet's
price or Dive timing.** It names `c_amphibiousJet`, explicitly says it is not yet
used in-game, and quotes 1800. Its SHA256 is
`0824704b62d7732023a94555e85433c2f85495e845a45f2f63b9a4094a372b6c`.
The production chain uses builtin `amphibiousJet`, frozen enum implementation
`ar$33`, price **2000** and build speed **0.001**. These constants were read directly
from the frozen JAR (`amphibious-native-metadata.javap.txt`) and native price was
asserted through a real unit in `NativeMorphHarness`.

Frozen class `com.corrodinggames.rts.game.units.b.c` has `ae()` true only when `Q()`
reports actual submerged height (`eq < -1`). Flight has native range 170; desired /
submerged mode has range 100. Frozen static initialization installs Fly=151,
Dive=152. `c$2.a(am,boolean)` permits Dive only while the native unit still desires
flight and its own current native position is over water; `c$1` permits Fly while
it desires Dive. Bytecode evidence is in `dive-native-action.javap.txt`,
`fly-native-action.javap.txt` and sibling `../engineer-amphibious.javap.txt`.

Community construction-speed figures were useful to select this chain. No fixed
construction-time claim was encoded: native quote and new ready observations govern
the controller. No new reflective member discovery or banned path/fire query was
introduced. The new mode endpoint reuses the already audited action ID, availability,
affordability, cost and native dispatcher accessors.

## Validation evidence and boundaries

- `NativeMorphHarness`: **102 checks PASS**, `native-morph-final.log`. Uses explicit
  frozen native fixtures, not a natural battle. It verifies current flight
  incompatibility, legal known-water future plan, no new hidden-target mode plan,
  actual submerged compatibility and native command admission/idempotency. Fixture
  height is changed explicitly to isolate native capability accessors; this does
  not prove a real game's Dive completion time.
- `EngineerProviderHarness`: **47 checks PASS**, `provider-contract-final.log`,
  repeated after the mine-policy merge. Covers funding/native price change,
  ownership/builder floor, paid capacity gaps, wounded-provider preemption,
  unique ready product, forward-provider return, missing/unknown/hidden evidence,
  and actual mode readiness before attack.
- Real `BattleClient` HTTP timelines: **8/8 PASS**, `http-final.log` and raw
  `http-final/*.jsonl`. Full chain, rear return, funding, unfinished/missing product,
  unknown water, lost contact and accepted-mode-without-readiness bounded failure.
  These fake legal observations do not claim real battle effectiveness.
- Prior specialist lifecycle HTTP timelines: **4/4 PASS**, `specialist-final.log`.
  Existing movement-gap investigation and extra ordinary builder behavior remain.

`http-first.log` is preserved: two fixture assertions initially overconstrained
legitimate bounded retries / lacked observed target damage. The third failure
identified a real hidden weapon-gap flight investigation bug; root applied the
skip and the final timelines passed. `native-morph-first.log` / `extended.log`
retain missing fixture cost-grid failures; native fields were initialized and
the final native contract passed. A separate native attempt had the known Windows
JDK default TEMP loopback issue; all final native runs used this short task TEMP.

Root subsequently tightened every actual jet response order to re-query a fresh
same-session, same-target, exact-actor compatible engagement rather than reuse the
ordinary ten-second assessment cache. `http-fresh-commit/` and related logs are the
follow-up evidence for that additional revision: **8/8 HTTP timelines PASS**
(`http-fresh-commit.log`, 35.512 seconds) and **47 provider contracts PASS**
(`provider-contract-fresh-commit.log`). The independent feedback auditor passed
`http-fresh-commit/chain.jsonl` with no gap / violation, including one provider,
new ready transfer, Dive and actual compatible mode plus 14 fresh accepted response
orders. Its result is `../audit-targeted/engineer-fresh-chain.json`; the original
stale-response finding remains preserved and was not rewritten.

No full regression or autonomous game run was launched by this subtask. Root owns
the final merged source, full regression, native Spain verification and handoff.
