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

基线交付件已有三场 Windows 桌面 5x / 约 2400 游戏秒长局，全部 PARTIAL/ONGOING；峰值移动武装单位为 88 / 21 / 101。Spain 两局在 Match 前人工生产一个 builder，此后自主运行。这些是基线历史证据，不继承为后续候选验收。

最新增量为 [Specialist Lifecycle 候选](deliveries/specialist-lifecycle-2026-09-30/README.md)，基于已完成的有界资金预留，继续修复工程师经济挪用、到点失联后的调查与任务释放、重复返回。源码和验证见 [当前状态](project-state/CURRENT_STATE.md) 与 [本轮交付证据](evidence/specialist-lifecycle-2026-09-30/README.md)。继续任务时保留最新提交，不因基线核对报告增量源码差异而回退。下一主线是有证据的响应者护送。

## 目录

- agent/：Java 源码、资源、测试与构建入口。
- tools/：分析、无画面运行、A/B 编排和战术查看器。
- project-state/：唯一当前基线、状态、计划与历史入口归档。
- evidence/：按候选区分的原始证据、索引和审计。
- astra-deliveries/：不可变交付件。
- handoff/、project-history/：历史合同和交接。
- knowledge/：有来源的静态知识；不替代合法动态观察。

兼容 game-lib.jar 的 SHA256 固定为 8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9。运行资源和原版资产的获取、身份、使用边界见 astra-relay/HEADLESS_ENGINE_README.md。
