# Astra 中转入口

本仓库是 `G:\deepseek 工作台` 的公开工程中转镜像，供无法访问用户本机文件系统的 Astra / Codex / 其他工程 Agent 使用。

## 先读这些
1. `project-state/CURRENT_STATE.md` — 当前候选、已验证事实、边界、backlog。
2. `project-state/HANDOFF_FOR_CODEX.md` — 现有工程交接。
3. `handoff/HANDOFF_Codex_KnowledgeBacked_v0_2026-09-29.md` — 最新 KnowledgeBacked 交付。
4. `knowledge/README.md` 与 `knowledge/VALIDATION.md` — 当前知识包与证据口径。
5. `agent/README_CN.md` — 构建和完整回归方法。

## 当前运行候选
- Agent JAR SHA-256: `5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`
- 兼容原版 `game-lib.jar` SHA-256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`
- `astra-relay/runtime-overlay/` 含当前 Agent JAR 与用户实际运行的 BAT 入口。
- `astra-relay/P1F-runtime-overlay-public.zip` 是同一 overlay 的便携包。

## 重要边界
公开仓库不分发 Rusted Warfare 原版游戏、`game-lib.jar`、assets、JVM、原版 DLL/EXE、存档或 Replay。需要运行原生游戏时，必须由执行环境自行提供合法的兼容 1.15 安装。

不要把静态 TMX/单位知识直接当作实时合法视野；当前项目要求未探索动态信息保持 UNKNOWN。不要重新启用已隔离的危险反射探针。

如果任务只是审计、设计、改 Agent 源码、写测试或分析既有证据，本仓库已经提供主要工程上下文，不需要本地 G 盘。