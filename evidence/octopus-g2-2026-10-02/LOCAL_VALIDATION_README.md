# Octopus G2 isolated validation, 2026-10-02

**Final result: PASS, 60/60 steps, 31 Java harness runs, 26 Python suites /
448 tests, 0 Python tests skipped, failed steps: 0, process exit 0.** The corrected final run
completed at 18:07:33 local time. `final-v2-full/validation-summary.json` contains
every step, raw-log hash, protected-input check and source/JAR identity.
`audit-summary.json` links the baseline, final result and retained earlier attempts.

The fixed final candidate is `fixed-candidate/rw-agent-bootstrap.jar`, built from
`5dd76e41d95887ec6c8dc6647476491b9b5241a6`:

- SHA256: `1dbeb3084f9ee7dd154b7290daef6c9fc2fe780d9e033fd45539fb3aa01ac7d9`
- Bytes: 362190
- Canonical contentDigest: `9e51d99cc67e00833080ae1b1a23387015f7b8bafc460b70206bee9a4c03998b`
- Canonical entries excluding manifest, including directories: 121
- The JAR remained byte-identical throughout the full regression.
- All 132 staged files remained unchanged and still exactly match the repository.
- Protected engine, installed agent, settings, saves, 20 libraries and 868 original
  assets remained unchanged; isolated asset files match the originals.
- Status: **VALIDATED_NOT_DEPLOYED**. No natural match or desktop acceptance run.

Key final contracts: WorldState 73 checks, G1Trace 41, Strategy 169,
EngineerProvider 61, StrategyNative 51; new G2 HTTP suite 9/9 and G1 HTTP suite 4/4.
See `final-v2-full/java-contract-checks.json` and the individual raw logs.

G2's final 27 raw reports preserve five normal three-way comparisons plus four
guard three-way comparisons. G1 trace stays enabled in every G2 comparison.
Commands and HTTP request/response bodies match G2-off and frozen G1 in all cases.
Export size for the four principal paths:

| Fixture | Commands in each variant | G2 on / off rows | G2 on / off bytes | Byte ratio |
| --- | ---: | ---: | ---: | ---: |
| Recon | 1 | 238 / 136 | 785967 / 284871 | 2.759028 |
| Local crisis | 6 | 402 / 230 | 1374372 / 594878 | 2.310343 |
| Engineer provider | 17 | 1033 / 645 | 3461373 / 1523414 | 2.272116 |
| Ordinary production | 78 | 2520 / 1637 | 9186497 / 5022825 | 1.828950 |

These numbers measure diagnostic export volume only, not execution cost or game
performance. Exact paths, native-stamp comparison and guard process results are
in `final-v2-full/g2-evidence-summary.json`.

Starting commit: `ff693c805c4069a62ed451e33cffb882245cea35` (G1).
The baseline source is a fresh `git archive` extraction of `agent`, `tools`, and
`docs`, with a separate copy of 868 game asset files for the native harnesses.
Source archive SHA256:
`193892a3919ce7e37dd7101692ecc33d3542fdd7f6163e5e9a892d25cae54e28`.

Baseline Windows regression completed in one pass: **58/58 steps, 30 Java runs,
25 Python suites / 439 tests, 0 Python tests skipped, failed steps: 0, process exit 0**.
The run began at approximately 17:19 and ended at 17:34 local time. Original
transcript is `baseline-full/transcript.txt`; all per-step raw logs are copied
to `baseline-full/raw-logs`, with machine-readable results in
`baseline-full/validation-summary.json`.

The rebuilt, fixed baseline JAR is `baseline-frozen/rw-agent-bootstrap.jar`:

- SHA256: `ec69c1f0fdb552ddd4e2059ef466f2f509f6c99ec6b40053a111de6f4da474d9`
- Bytes: 336094
- Canonical contentDigest: `43da2c441785314a2089f5fab01715103b5d2e82af252fd49daf98e65e1eec13`
- All 112 ZIP entries excluding `META-INF/MANIFEST.MF`, including directory
  entries, match the frozen G1 final JAR SHA256
  `ab5b7fd1fe97a4e4ae0b8cb59cfd7d189c492e8a8e99a8400ce1d3eb93e7287a`.

The digest uses `tools/run_headless.py:jar_content_digest`; it excludes only the
manifest and ZIP metadata. See `baseline-frozen/identity.json`.

Runtime: Windows 10.0.26200, PowerShell 7.6.5, bundled OpenJDK 13+33,
Python 3.12.14. Exact runtime paths are in `runtime.json`. All builds/tests use
the isolated source directory as working directory, with temporary files below
this validation root. The original engine and 20 library files are read-only
inputs from `游戏环境/P1F-GPTSol61-ProductionCapacity-2026-10-02`.

Baseline command, after selecting the explicit Java/Python binaries on PATH:

```powershell
$env:JAVA_HOME = 'G:\deepseek 工作台\游戏环境\P1F-GPTSol61-ProductionCapacity-2026-10-02\jvm64'
$env:PATH = "$env:JAVA_HOME\bin;C:\Users\Administrator\.cache\codex-runtimes\codex-primary-runtime\dependencies\python;$env:PATH"
$env:TEMP = 'G:\deepseek 工作台\_validation\octopus-g2-20261002\temp'
$env:TMP = $env:TEMP
$env:RW_G1_BASELINE_JAR = 'G:\deepseek 工作台\GitHub发布\Rusted-Warfare-Agent\deliveries\production-capacity-2026-10-01\rw-agent-bootstrap.jar'
$env:RW_G1_EVIDENCE_DIR = 'G:\deepseek 工作台\_validation\octopus-g2-20261002\baseline-full\g1-four-path'
# Working directory: this validation root's baseline-source
& .\agent\test-win.ps1 -GameJar 'G:\deepseek 工作台\游戏环境\P1F-GPTSol61-ProductionCapacity-2026-10-02\game-lib.jar' -LibsDir 'G:\deepseek 工作台\游戏环境\P1F-GPTSol61-ProductionCapacity-2026-10-02\libs'
```

`protected-before.json` and `baseline-full/protected-after.json` prove the
installed agent, original engine, configuration and existing save files were
unchanged. `validation-input-identity.json` records document, asset and library
hashes; `baseline-full/source-hashes.json` records the tested source.
No desktop game was started or closed. These runs establish offline contracts
and deterministic HTTP fixture behavior, not natural combat or desktop acceptance.

`baseline-negative` is a separate sensitivity check: the new G2 Recon positive
test ran against the fixed old G1 JAR and failed exactly because no G2 adapter
updates existed (one expected failure). Its command/response comparisons passed
before that assertion. This expected failure is not part of the baseline matrix.

Candidate focused evidence:

- `candidate-focused`: initial builds and Java G1Trace 41 / Strategy 169 /
  EngineerProvider 61 checks passed; existing G1 HTTP suite passed 4/4. The first
  G2 suite passed five normal comparisons and failed four guard fixtures because
  the reused Recon success fixture expected exit 0 from an intentional guard stop.
  This initial failure is retained, not rewritten as a zero-failure run.
- `candidate-focused-repaired`: G2 9/9 passed after adapting the fixture's expected
  exit assertion. The actual process result remains unchanged: each guard requires
  real exit 1 and its exact `IllegalStateException` reason. Raw packets and reports
  are captured before temporary fixture cleanup.
- `candidate-guard-three-way`: all four guards passed G2-on / G2-off / frozen G1
  comparisons, including the real exit reasons, every HTTP request/response and
  legacy event. All 12 reports are retained. These comparisons are also included
  in the final nine-test G2 suite.
- `preexisting-frame2-guard`: a separate frozen `ff693c8` experiment confirms that
  a networked-state guard at the second observation can produce the existing
  `Report commit failed: java.lang.NullPointerException`. The guard still stops
  with exit 1. The staged report survives in `raw-reports`; a committed `.jsonl`
  is unavailable for that early failure. Production cleanup code was not changed.
  Main guard comparisons inject at frame 3, after report state initializes.

The G2-only HTTP adapter supplies missing native `/state.player.teamId=0` in all
three variants. The native-stamp comparison also removes scout `gameTimeMs` and
production `gameTimeMs` / `frame`. No existing policy fixture file is edited.
Raw transformed packets are preserved, and commands/outcomes use identical inputs
across each comparison. G1's original unadapted fixtures are tested separately.

`final-source` / `final-full` contain the superseded first full candidate attempt
from `5e30a344f7a6f46e53765ba9b95e86df7b3f3691`. It was deliberately interrupted
after 12 completed passing steps and zero failed test steps when a missing-native-
timestamp coverage bug was found independently. It is **PARTIAL_SOURCE_SUPERSEDED**,
not a completed regression result. See `final-full/superseded.json`.

The corrected final candidate is commit
`5dd76e41d95887ec6c8dc6647476491b9b5241a6`. Its new immutable staging directory is
`final-v2-source`; all 132 staged source/test/resource/tool/runner files matched
the repository byte for byte before and after execution. The complete matrix was launched
by `run-final-v2.ps1`, using `baseline-frozen` for the G2 three-way comparison and
the pre-G1 capacity JAR for the separate G1 comparison. Current run evidence is
under `final-v2-full`; the completed matrix in `validation-summary.json` confirms
60 passing steps / 31 Java / 26 Python / 448 Python tests.
The final G2 suite exports 27 reports: five normal three-way
comparisons (15) and four guard three-way comparisons (12).

`collect_g2_evidence.py` measures fixture report bytes/row counts and unchanged
command counts only. These export-volume ratios are not CPU, memory, latency,
throughput, natural-game or combat-benefit measurements.

Existing ReportCommitHarness POSIX displacement subcase is platform-skipped on Windows; the Python skip count does not imply all Java platform branches ran.
