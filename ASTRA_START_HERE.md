# Astra 中转入口

本仓库是 `G:\deepseek 工作台` 的公开工程中转镜像，供无法访问用户本机文件系统的 Astra / Codex / 其他工程 Agent 使用。

## 当前任务优先入口
1. `ASTRA_BREAKTHROUGH_MISSION_2026-09-29.md` — 当前正式突破任务；高自主性推进。
2. `ASTRA_PROJECT_REVIEW_RESPONSE_2026-09-29.md` — Astra 上一轮项目总评，作为路线背景。
3. `evidence/README.md` — 最新桌面 raw battle、KnowledgeBacked / Recon 原始证据索引与公开边界。

## 工程基线再读
1. `project-state/CURRENT_STATE.md` — 当前候选、已验证事实、边界、backlog。
2. `project-state/HANDOFF_FOR_CODEX.md` — 现有工程交接。
3. `handoff/HANDOFF_Codex_KnowledgeBacked_v0_2026-09-29.md` — KnowledgeBacked 交付。
4. `knowledge/README.md` 与 `knowledge/VALIDATION.md` — 当前知识包与证据口径。
5. `agent/README_CN.md` — 构建和完整回归方法。

## 当前运行候选
- Agent JAR SHA-256: `5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`
- 兼容原版 `game-lib.jar` SHA-256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`
- `astra-relay/runtime-overlay/` 含当前 Agent JAR 与用户实际运行的 BAT 入口；ZIP 是同一 overlay 的便携包。
- 上一轮 Astra public-relay patch 仍视为**待吸收工程修复**，除非当前任务明确将其合入新候选。

## 重要边界
公开仓库不分发 Rusted Warfare 原版游戏、`game-lib.jar`、assets、JVM、原版 DLL/EXE、存档或完整 Replay 档案。需要运行原生游戏时，执行环境必须自行提供合法兼容的 1.15 安装。

不要把静态 TMX/单位知识直接当作实时合法视野；未探索动态信息保持 UNKNOWN。不要重新启用已隔离的危险反射探针。若只做审计、设计、Agent 源码、确定性测试或既有证据分析，本仓库已提供主要上下文，不需要本地 G 盘。