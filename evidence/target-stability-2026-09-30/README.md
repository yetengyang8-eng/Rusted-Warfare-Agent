# Target Stability 证据

源码基线 main `795146a`，先集成已验收 Recon `28e4655`，然后修目标资格。完整解释和复现命令见 `handoff/HANDOFF_Astra_TargetStability_2026-09-30.md`。

- `baseline-replay-summary.json` / `candidate-replay-summary.json`：相同固定桌面观察输入，旧/新真实 BattleClient 的 E2 决策重放。原局 #1026 114 次目标意图；重放旧 106、新 0。生产/Scout planner 关闭、Recon 禁用、命令不改变后续位置或视野；不是新原生比赛。
- `native-summary.json`：正式新 5x headless 局的事件审计。三只 lightSub 自然触发抑制，之后 28 次失联记忆观察，抑制目标进军意图 0；ownership 16/16。局为有效 PARTIAL/ONGOING，完整性校验无问题，不是胜利。
- `regression-summary.json`：20 Java / 15 Python 的分段完整矩阵；Python 271 passed、2 Windows 文件锁用例 skipped。最初暴露的两个 Linux 问题及修复后恢复步骤如实保留。
- `candidate-manifest.json` / `artifact-comparison.json`：交付身份、源码 SHA、相同编译器集成基线对比。与桌面 overlay 的三处匿名桥接类字节不同，但 JVM descriptors/指令反汇编一致；与全源码集成基线的 native/headless/knowledge 条目逐字节一致。
- `raw-evidence-manifest.json`：交付 ZIP 中 `raw-evidence.zip` 的条目大小/SHA。原始输出包含 E2 新旧重放、最终/失败测试日志、原生有效样本和 64 MiB 报告上限失败尝试。原有桌面输入不重复打包，继续引用 main 的原始 reports ZIP。

新原生局使用交付 JAR `165bd3b2...`、冻结 game-lib `8a550a37...`；完整源码构建/回归与原生报告身份均可复算。没有新桌面验收或胜率结论。
