# 本仓库接手规则

后续任务默认从 `project-state/BASELINE.md` 定义的 `RW-BASELINE-2026-09-30-GS-v1` 开始。

开始修改前依次阅读：

1. `project-state/BASELINE.md`
2. `project-state/baseline-manifest.json`
3. `project-state/CURRENT_STATE.md`
4. `project-state/NEXT_STAGE_PLAN.md`

先检查 Git 状态，并运行 `python project-state/verify_baseline.py` 做只读身份核对。若用户已修改源码，保留改动并报告与基线的差异；核对失败不授权重置或覆盖。

此仓库的 `agent/`、`tools/` 是默认生产源码。工作区的旧 `游戏环境/.../developer`、旧解压包和历史 worktree 不作为任务起点。`project-state/archive/` 与旧交接中“当前候选”“未部署”“复制到冒烟环境”等措辞只代表历史时点。

遵守 BASELINE 中的迷雾、危险反射、冻结引擎、用户桌面与证据边界。在独立目录构建/验证；不自动替换旧环境或用户当前程序。原始报告不可改写；最终、中间、重建候选按身份分别记录。

完成任务时更新 CURRENT_STATE 和必要证据。只有满足 BASELINE 的晋级规则才替换基线；普通任务报告不能暗中改变基线。
