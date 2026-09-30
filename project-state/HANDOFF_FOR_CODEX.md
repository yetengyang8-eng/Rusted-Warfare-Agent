# 接手入口

从 **RW-BASELINE-2026-09-30-GS-v1** 开始后续任务。

1. 阅读 [BASELINE.md](BASELINE.md)：源码、交付件、环境角色、验证边界和保护规则。
2. 阅读 [baseline-manifest.json](baseline-manifest.json)，核对当前 Git 状态和运行件身份。
3. 阅读 [CURRENT_STATE.md](CURRENT_STATE.md) 与 [NEXT_STAGE_PLAN.md](NEXT_STAGE_PLAN.md)。
4. 在仓库根用 Python 3 运行 project-state/verify_baseline.py；本机可附加 --workspace "G:\deepseek 工作台"。发现漂移先报告，不覆盖用户改动。

默认开发目录为本仓库 agent/ 和 tools/。旧游戏环境的 developer、旧解压交付与旧临时分支仅供历史核对。

新任务应说明输入基线、目标行为、验证方法和预期交付。源码/脚本变化在隔离目录做构建与完整回归；自然行为结论需要对应候选的原始证据。文档整理不需要启动游戏。不要自动运行旧交接中的安装命令。

危险反射、合法视野、冻结 game-lib、用户桌面与存档保护详见 BASELINE。新用户授权优先，旧轮次的策略范围不自动变成永久禁令。

本次整理前的完整交接已归档于 [repository-HANDOFF_FOR_CODEX.md](archive/2026-09-30-pre-baseline/repository-HANDOFF_FOR_CODEX.md)，仅用于事故和历史检索。
