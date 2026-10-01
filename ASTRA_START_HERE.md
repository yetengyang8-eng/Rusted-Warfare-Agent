# Astra Start Here

You are working through GitHub only. Assume you cannot inspect the user's local machine, desktop test folders, or `G:\deepseek 工作台` unless the needed facts have been copied into this repository.

## First reading pass

Read in this order:

1. `AGENTS.md`
2. `project-state/ASTRA_ORIENTATION_ACCEPTANCE.md`
3. `project-state/ASTRA_CONTEXT.md`
4. `project-state/CURRENT_STATE.md`
5. `project-state/NEXT_STAGE_PLAN.md`
6. `evidence/desktop-feedback-2026-10-01/README.md`
7. `astra-relay/HEADLESS_ENGINE_README.md`
8. only then inspect the source files relevant to the task

Do not use old root-level Astra mission/review files as current instructions. They were removed from `main`; the pre-refresh public state is preserved on branch `archive/pre-astra-refresh-20261001` and in Git history.

## Current engineering state

The active fixed candidate is `RW-CANDIDATE-2026-10-01-FEEDBACK-v1`, JAR SHA256 `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`.

The latest user desktop runs show that economy can now grow far beyond the Agent's ability to consume it usefully. Spain reached about 1.32M credits while production and operations remained bounded. The next mainline is therefore production-capacity scaling plus predictable production routes, followed by higher useful parallel operational throughput.

## Expected first conversation with the operator

The first Astra conversation is a **qualification/orientation acceptance**, not a broad rewrite. Follow `project-state/ASTRA_ORIENTATION_ACCEPTANCE.md` as the acceptance contract.
Astra should prove, not merely claim, that it can:

- read the current project state and source accurately;
- distinguish verified evidence from interpretation and future work;
- prepare the repository-provided headless engine and verify its identity;
- run the standard regression entry point appropriate to its platform;
- run a minimal headless smoke with the fixed FEEDBACK-v1 JAR;
- create, commit, and push an isolated orientation branch/report;
- identify the minimum source surface for the production-capacity/production-route task;
- point out material contradictions or missing GitHub evidence before implementation.

Do not spend this round re-deriving old project history or implementing the next feature.

## Expected second conversation

After orientation passes, advance one substantial engineering breakpoint. The preferred first breakpoint is:

**Production Capacity Controller v1 + minimum ProductionRoute data model.**

The result should expose measurable economic inflow, recent spend/throughput, producer capacity, backlog/bottleneck, and a bounded decision to request more capacity. It should not yet attempt to solve every downstream scheduler/expansion/recon problem.

Follow `project-state/NEXT_STAGE_PLAN.md` for detailed constraints and validation expectations.

## Writeback

Use `ASTRA_WRITEBACK.md`. Prefer a branch `astra/<task>-20261001`; do not force-push `main`. Preserve failures and evidence boundaries in the handoff.