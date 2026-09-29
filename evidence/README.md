# Public evidence index for Astra

This directory contains public-safe evidence needed to review and continue the current Agent work without access to the user's G: drive.

## Current live desktop evidence

`user-live-2026-09-29/battle-raw-4runs.zip` contains the four latest complete desktop `battle-*.jsonl` reports from the current KnowledgeBacked v0 candidate.

- `battle-1790676200741-0ae5abf2.jsonl`: 180x180, 841.97 game s, `PASS/VICTORY`, 77 new combat units, 43 losses, 4 frontier tasks / 4 blocked.
- `battle-1790676924825-0eb20dc0.jsonl`: 400x370, 901.18 game s, `PARTIAL/ONGOING`, 67 new combat units, 34 losses, 10 frontier tasks / 8 blocked / 2 preempted.
- `battle-1790677528555-447d7a4d.jsonl`: 400x370, 902.32 game s, `PARTIAL/ONGOING`, 67 new combat units, 35 losses, 16 frontier tasks / 15 blocked / 1 preempted.
- `battle-1790677835041-634910e1.jsonl`: 400x370, 1801.43 game s, `PARTIAL/ONGOING`, 77 new combat units, 50 losses, 34 frontier tasks / 31 blocked / 3 preempted.

The last run is the 10-player Spain stress test described in the Astra review request. Its final observation shows credits about 120785.5; the summary records 12 new mines, 11 ready mines, 2 total land factories, 209 confirmed attack orders, and 40 recon tasks.

`SHA256SUMS.txt` hashes the uncompressed source reports. `../last_1800_spain_10p.html` is a compact Agent-POV viewer derived from the last report and is not a substitute for the raw JSONL.
## KnowledgeBacked / Recon reference evidence

`knowledge-backed-v0/` now exposes the main text/JSON evidence that was previously only summarized in handoffs:

- `knowledge_backed_v0_evidence.txt`
- `knowledge_backed_v0_native.json`
- `knowledge_backed_v0_terrain_e2.json`
- `knowledge_backed_v0_regression.log`
- `candidate_sha_lineage.json`
- `recon_v02_evidence.txt`
- `recon_v02_native_final.json`
- `recon_v02_trigger_forensics.txt`

These are intended for engineering review and regression comparison. Evidence grades and caveats in the files remain authoritative; an old native victory or deterministic fixture is not a causal performance claim.

## Historical report fixtures

The historical acceptance JSONL files required by `agent/tests/test_reports.py` are restored under `docs/acceptance-0.02-alpha2.jsonl`, `docs/acceptance-0.03-alpha2.jsonl`, and `docs/acceptance-0.04/`. This closes the two missing-input failures found in Astra's previous public-relay audit.

## Intentionally not public

The repository still does **not** distribute the commercial Rusted Warfare executable/JAR/assets/JVM, private third-party source snapshots, the full 3320-replay archive, or personal chat archives. Native-engine execution therefore still requires a compatible user-supplied Rusted Warfare 1.15 installation. Their absence is intentional, not a missing relay upload.