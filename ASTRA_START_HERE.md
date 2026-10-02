# Shared Agent Start Here

当前工程断点为 **Octopus G2**；源码 `5dd76e41`，分支 `codex/octopus-g2-world-state-20261002`，起点用户验收的 ff693c8。当前仓库的 docs 提交可能在源码之后，构建身份以候选 manifest 为准。

这个历史文件名是共享导航入口。后续子智能体优先 `gpt-6.1-sol / high`，不使用 Astra。远程 GPT/DeepSeek 等审阅者不能假定可以访问用户本机路径；共享摘要和证据已在仓库中，完整 raw 的本地路径及哈希单独列出。

建议阅读顺序：

1. `AGENTS.md` 与 `project-state/BASELINE.md`、`baseline-manifest.json`（冻结基线仅用于血统核对）。
2. [CURRENT_STATE](project-state/CURRENT_STATE.md)与[NEXT_STAGE_PLAN](project-state/NEXT_STAGE_PLAN.md)。
3. [G2 handoff](handoff/HANDOFF_Octopus_G2_2026-10-02.md)、[WorldState/Event contract](docs/OCTOPUS_G2_WORLD_STATE.md)、[evidence](evidence/octopus-g2-2026-10-02/README.md)。
4. [G1 Trace](docs/OCTOPUS_G1_TRACE.md)与[保留上下文](project-state/ASTRA_CONTEXT.md)，然后看本次任务涉及源码。

G2 完整 Windows 60/60、448 Python tests，WorldState 73/独立 14 checks；五正常三方对照及四 guard 三方对照，共 27 raw reports。NOT_DEPLOYED；自然局/桌面验收 NOT_RUN。现有命令 gate、策略、资金/slot 未迁移。G3 是下一接口建议，需用户单独授权。历史生产扩容/战争方向及更旧 orientation 文件均不自动成为当前任务。
