# 当前 G3/G3.5 接手入口 — 2026-10-02

从当前最新G3/G3.5交付继续：最终测试源码95a917e，分支codex/octopus-g3-execution-20261002。有效63/63验证（历史fixture环境补验另列），未部署/自然验收，未进入G4。

请先读[CURRENT_STATE](CURRENT_STATE.md)、[G3 handoff](../handoff/HANDOFF_Octopus_G3_G35_2026-10-02.md)及[契约](../docs/OCTOPUS_G3_EXECUTION_INTENT.md)。子智能体优先gpt-6.1-sol/high，禁Astra；旧文件名不代表模型授权。下面仅保留历史导航，current/latest/next表述以本段与当前project-state为准。

---

# 接手入口

最新增量及GPT/DeepSeek交接：[HANDOFF_Codex_Feedback_2026-10-01.md](../handoff/HANDOFF_Codex_Feedback_2026-10-01.md)。无builder起步、多队控制、局部危机、公平调度、后方生产与T2/T3局部投资已落在当前仓库；先读最新CURRENT_STATE，保留增量。冻结基线未回写或晋级。

从 **RW-BASELINE-2026-09-30-GS-v1** 开始后续任务。

1. 阅读 [BASELINE.md](BASELINE.md)：源码、交付件、环境角色、验证边界和保护规则。
2. 阅读 [baseline-manifest.json](baseline-manifest.json)，核对当前 Git 状态和运行件身份。
3. 阅读 [CURRENT_STATE.md](CURRENT_STATE.md) 与 [NEXT_STAGE_PLAN.md](NEXT_STAGE_PLAN.md)。
4. 在仓库根用 Python 3 运行 project-state/verify_baseline.py；本机可附加 --workspace "G:\deepseek 工作台"。发现漂移先报告，不覆盖用户改动。

默认开发目录为本仓库 agent/ 和 tools/。旧游戏环境的 developer、旧解压交付与旧临时分支仅供历史核对。

基线后的增量实现保存在本仓库最新提交中；继续任务时先读取 CURRENT_STATE 的当前候选，保留已完成工作。基线核对器报告源码与 v1 不同，可是已有候选的预期变化，不能据此回退最新源码。未晋级的候选验证不得混入 v1 的冻结结果。

新任务应说明输入基线、目标行为、验证方法和预期交付。源码/脚本变化在隔离目录做构建与完整回归；自然行为结论需要对应候选的原始证据。文档整理不需要启动游戏。不要自动运行旧交接中的安装命令。

危险反射、合法视野、冻结 game-lib、用户桌面与存档保护详见 BASELINE。新用户授权优先，旧轮次的策略范围不自动变成永久禁令。

本次整理前的完整交接已归档于 [repository-HANDOFF_FOR_CODEX.md](archive/2026-09-30-pre-baseline/repository-HANDOFF_FOR_CODEX.md)，仅用于事故和历史检索。
