"""Run interleaved isolated synthetic JVM measurements; never launch native game."""
import argparse
import collections
import hashlib
import importlib.util
import json
import os
import pathlib
import statistics
import sys
from unittest import mock

parser = argparse.ArgumentParser()
parser.add_argument('jar')
parser.add_argument('baseline')
parser.add_argument('--repo', default=r'G:\deepseek 工作台\GitHub发布\Rusted-Warfare-Agent')
parser.add_argument('--out', required=True)
parser.add_argument('--repeats', type=int, default=3)
args = parser.parse_args()
out = pathlib.Path(args.out)
out.mkdir(parents=True, exist_ok=True)
os.environ['RW_G3_EVIDENCE_DIR'] = str(out)
spec = importlib.util.spec_from_file_location('g3_measurement_fixture', pathlib.Path(args.repo)/'agent/tests/test_g3_execution.py')
fixture = importlib.util.module_from_spec(spec)
with mock.patch.object(sys, 'argv', ['test_g3_execution.py', args.jar]):
    spec.loader.exec_module(fixture)
case = fixture.G3ExecutionTests()
variants = [
    ('accepted-g2', dict(jar=args.baseline, enabled=False)),
    ('g3-legacy-gate', dict(enabled=False)),
    ('g3-g1-g2-on', dict()),
    ('g3-g1-only', dict(world=False)),
    ('g3-exports-off', dict(trace=False, world=False, diagnostics=False)),
]
records = []
for repeat in range(args.repeats):
    # Alternate order to reduce one-sided warmup/order effects. Every JVM is fresh.
    sequence = variants if repeat % 2 == 0 else list(reversed(variants))
    for variant, options in sequence:
        orders, rows, measurement = case.run_factories(label=variant+'-'+str(repeat + 1), **options)
        measurement['variant'] = variant
        measurement['repeat'] = repeat + 1
        measurement['commandsBy7000'] = sum(order['gameTimeMs'] <= 7000 for order in orders)
        measurement['batchCounts'] = dict(collections.Counter(order['gameTimeMs'] for order in orders))
        records.append(measurement)
        print(json.dumps(dict(variant=variant, repeat=repeat + 1, elapsed=measurement['elapsedWallSeconds'],
            commands=len(orders), reportBytes=measurement['reportBytes'])), flush=True)
summary = {}
for variant, _ in variants:
    group = [row for row in records if row['variant'] == variant]
    elapsed = [row['elapsedWallSeconds'] for row in group]
    metrics = dict(repeats=len(group), elapsedWallSecondsMedian=statistics.median(elapsed),
        elapsedWallSecondsMin=min(elapsed), elapsedWallSecondsMax=max(elapsed),
        reportBytesMedian=statistics.median(row['reportBytes'] for row in group),
        nativeCommands=[len(row['nativeOrders']) for row in group],
        commandsBy7000=[row['commandsBy7000'] for row in group],
        batchCounts=[row['batchCounts'] for row in group])
    if group[0]['clientElapsedCosts']:
        costs = [row['clientElapsedCosts'][0] for row in group]
        metrics['clientElapsedScopes'] = {category: dict(
            calls=[cost[category+'Calls'] for cost in costs],
            totalMsMedian=statistics.median(cost[category+'Nanos']/1e6 for cost in costs),
            msPerCallMedian=statistics.median(cost[category+'Nanos']/1e6/cost[category+'Calls'] for cost in costs))
            for category in ('sampling', 'world', 'execution')}
    summary[variant] = metrics
result = dict(schema='rw-g3-focused-wall-measurement-v1',
    limitations=['Synthetic legal local HTTP fixture, not natural game acceptance',
        'Elapsed wall time includes JVM startup, fixed polling, HTTP and report commit; not CPU',
        'Client elapsed scopes can overlap; never sum them',
        'Three fresh JVM samples by default, no significance or causal wall-speed claim'],
    currentJar=args.jar, currentSha256=hashlib.sha256(pathlib.Path(args.jar).read_bytes()).hexdigest(),
    acceptedG2Jar=args.baseline, acceptedG2Sha256=hashlib.sha256(pathlib.Path(args.baseline).read_bytes()).hexdigest(),
    variants=summary, records=records)
(out/'measurement-summary.json').write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding='utf-8')
print(json.dumps(summary, ensure_ascii=False, indent=2))
