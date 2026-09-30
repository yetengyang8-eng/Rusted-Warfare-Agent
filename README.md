# Rusted Warfare Agent

Rusted Warfare PC 1.15（Build 28 / Game Code 176）的规则型自动对战 Agent。Java Agent 与独立客户端通过本地桥接读取合法观察并执行命令；工程目标是行为可解释、证据可复算、修改可回归。

## 当前工程基线

**RW-BASELINE-2026-09-30-GS-v1 — Global Strategy / Feasibility / Combat Engineer**

- [基线定义、候选身份与环境角色](project-state/BASELINE.md)
- [当前状态](project-state/CURRENT_STATE.md)
- [下一阶段工程计划](project-state/NEXT_STAGE_PLAN.md)
- [本次基线验收](evidence/baseline-2026-09-30/VALIDATION.md)
- [构建与回归入口](agent/README_CN.md)

生产输入起点为 Git 6214f073d1b76429c0b827268db918e1e90e9f5f；交付 JAR SHA256 为 76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed。后续任务先核对 project-state/baseline-manifest.json，不使用旧环境副本作为最新源码。

当前候选已有三场 Windows 桌面 5x / 约 2400 游戏秒长局，全部 PARTIAL/ONGOING；峰值移动武装单位为 88 / 21 / 101。它们证明自然运行与部分任务链，尚不证明胜率提升。Spain 两局在 Match 前人工生产一个 builder，此后自主运行。当前主线是解释同图轨迹差异及需求到投资/响应之间的断点。

## 目录

- agent/：Java 源码、资源、测试与构建入口。
- tools/：分析、无画面运行、A/B 编排和战术查看器。
- project-state/：唯一当前基线、状态、计划与历史入口归档。
- evidence/：按候选区分的原始证据、索引和审计。
- astra-deliveries/：不可变交付件。
- handoff/、project-history/：历史合同和交接。
- knowledge/：有来源的静态知识；不替代合法动态观察。

兼容 game-lib.jar 的 SHA256 固定为 8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9。运行资源和原版资产的获取、身份、使用边界见 astra-relay/HEADLESS_ENGINE_README.md。
