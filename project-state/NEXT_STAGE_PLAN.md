# Next Stage Plan

Updated: 2026-10-02 after Octopus G3/G3.5. 本轮施工已结束，下列规划不自动授权G4或桌面部署。

先读[G3/G3.5交接](../handoff/HANDOFF_Octopus_G3_G35_2026-10-02.md)和[Execution/Intent契约](../docs/OCTOPUS_G3_EXECUTION_INTENT.md)。从当前最新提交继续，最终测试源码95a917e，不回到cdb9252或旧解压目录。

1. 推荐先做独立受控native验收：隔离引擎/地图/固定候选，多厂在高倍速下的有界game-time预算、真实付款先于队列可见、两架jet的湿/干独立Dive与Fly/return，记录actual receipt及later witness。不开用户桌面游戏，结果不冒充自然局获益。
2. 对比全导出/尽量关闭附加导出，记录poll间隔、report体积、采样/执行elapsed；现有15次小样本只证明HTTP命令与日志体积，不是CPU因果。保持原策略/偏好，必要时再单独优化copy/JSON/flush。
3. SearchArea激活前，把合法地图/探索覆盖接成显式coverage/lastSearched/confidence来源，证明“陆军无合法可达目标”的触发，确认水下发现是否需Dive；缺项NEEDS_EVIDENCE。当前契约未自动搜索，Pool血缘匹配也未实现。
4. G4的General/FREE/JOINING及后续ThreatTask/HOT/COLD需要新授权。若安排全局延后收集Intent/抢占，必须重定义同步caller的receipt、commitment和取消语义；不能把现有dispatchBatch单测说成运行时已有全局排序。

兼容开关 `-Drwagent.g3Execution=false` 保留旧gate/caller。G3启用时WorldState计算与导出开关分离；回退不能丢现有paid/ghost。跨session/player/回退仍按现有guard停止，不自动用world epoch重建控制权。

回归范围已覆盖63项（62首遍PASS+历史fixture修复后16/16补验），33Java、27Python/463。后续不要重复G0/G2全局审计或同一未改源码的完整矩阵；新增风险或源码变化时跑相应focused和必要完整回归。子智能体仅Sol6.1/high或用户指定同级，禁Astra。
