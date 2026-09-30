# Global Strategy 证据包

输入是用户已有的 Target Stability 桌面 E4，没有重新整理旧网页报告或重复声称通过 E2/E4。最终候选从 main `4d80b4e` 施工，源码 commit `2f310ec`；总说明在 `handoff/HANDOFF_Astra_GlobalStrategy_2026-09-30.md`。

| 文件 | 含义 |
| --- | --- |
| `candidate-manifest.json` | 最终源码、JAR、contentDigest、冻结游戏与知识资源身份 |
| `desktop-input-analysis.json` | 已读桌面原始文件 SHA 与问题数据，不是候选运行结果 |
| `regression-summary.json` | 最终候选完整回归、平台跳过与 native E2 检查 |
| `native-summary.json` / `final-native-audit.json` | 最终 JAR 的自然原生样本、结果与只读事件审计 |
| `intermediate-native-summary.json` | 三个不可冒充最终 JAR 的迭代原生样本 |
| `intermediate-seaFactory-2610-events.jsonl` | 自然海厂任务闭环摘录；每条含原始报告行号 |
| `raw-evidence.zip` / `raw-evidence-manifest.json` | 原始 JSONL、运行 provenance、日志及原生样本 JAR 的逐文件 SHA |

## 已观察到的结构性变化

中间 2400 秒原生样本 `run-20260929T185449-f6f306` 使用 JAR `1fded785...cf091`，Big Island、5x、250 ms 显式轮询、正常迷雾。原始 battle SHA `2dd6bf5d65a1a4a55145259130c93e7d3c8b4b78b20352d7baefd695ddd9def5`。它不是控制 fixture，也不是最终 JAR：

- `seaFactory #2610 @ (490,2890)` 触发 `MOVEMENT_APPROACH_GAP`，证据里 LAND 为 COMPATIBLE + BLOCKED_TERRAIN。
- 工程师通过原生建造产生 `amphibiousJet #2905`，喷气机持有独占响应任务；合法可见 HP 从 1000 持续下降。
- 最终该海厂原址可见且敌人消失，enemyIntel 为 CLEARED，触发 `capability_need_resolved`。没有把“收到命令回执”当成任务完成。
- 全局共观察到 11 次 T2 矿升级、5 架工程师建造的喷气机，最大 mobile armed 为 71；ownership acquire/release 为 114/114。
- 仍是 PARTIAL/ONGOING；末尾 credits 272439，响应者有损失。不能由此推出经济溢出已解决、独占击杀或胜率改善。

最终候选在此后补上在途容量计数、额外 Builder 的远处矿点接近、UNKNOWN 不释放能力需求、重伤尝试预算，以及明确的完整编队证据事件；最终运行单独列在 `native-summary.json`：最终 JAR 提前原生 VICTORY，Battle 644.512 游戏秒，84/84 攻击命令确认、最大 56 个 mobile armed；报告和事件审计均无违规。该局未执行工程师/矿升级等战略投资，不能混用中间候选的正例。

随后 JAR `a3a320dd...34d1b` 的 2400 秒样本也已完整归档：14 次已观察到的远处矿点接近、6 座由战略工人完成的新矿、13 次 T2 矿升级，ownership 76/76，事件审计零违规；没有任务清场正例。单个 battle 为 **87,341,909 bytes**，超过旧 64 MiB 写入上限且完整提交，是真实原生报告基础设施证据。最终发布件在此之后只补上原生目标域读取失败时的 UNKNOWN 保护，不能混用 SHA。

## E2 与原生行为的区别

`StrategyContractHarness` 使用固定输入检验未知地形、负证据保持、容量与所有权。`StrategyNativeHarness` 加载冻结 1.15 资产，使用桌面 #230 坐标、近岸正例、隐藏目标移动、真实 action menu、原生命令回执、非法权限/未知资源拒绝；它显式布置单位/资金/迷雾、不推进游戏，所以仍是 E2。

自然 headless 样本从原生地图正常开局，客户端经相同桥执行。它们支持实现确实在原生运行中产生行为，不是用户桌面 E4，也不是固定种子 A/B 因果证据；runtime 明确 `seedReproducibilityVerified=false`。

## 保留的审计告警

`candidate-intermediate-audit.json` 是早期审计输出，报告一条针对移动直升机 #4332 的告警。审计当时把两段原始 48+15 单位 HTTP 观察直接当成完整编队证据：两段目标坐标为约 (638.54,3255.52) 和 (659.081,3255.009)，相差超过 20；随后记录的战术命令仍针对之前合法观察的 (541.37,3257.917)。负证据不能跨坐标套用。

策略已经拒绝把不一致批次拼成完整证明；最终版本另外输出 `engagement_assessment` / `engagement_assessment_deferred`，审计按提交契约消费，并明确列出坐标范围外的比较。`intermediate-coordinate-scoped-audit.json` 保留修正后的审计和这一条 `coordinateScopeSkips`，没有删除告警历史或篡改原始报告。

## 复核

解压 raw ZIP 后可直接运行：

```text
python tools/audit_global_strategy.py <某个完整 battle.jsonl> --out audit.json
```

原生 runner 将预算到期 PARTIAL 记为 episode FAIL / client exit 2；这是未获得原生终局，不等于崩溃。应同时检查 `matchOutcome`、报告 issues、完整 summary、JAR SHA 与清理结果。最终 JAR 的完整回归不能继承之前用户的 Windows 20/15 结果；本轮 Linux 跳过项明确记录，Windows 留给中转环境验证。

原始 ZIP 也保留验证过程：`validation/release-native-e2.log` 是初始化引擎前反射加载适配器引发的 fixture 初始化失败，修正顺序的提交为 `2f310ec`，未改生产代码；其后 `release-native-e2-complete.log` 对最终 JAR 完成 46 项检查。`final-full-regression.log` 曾被活跃日志路径的陈旧文件系统快照截断，不能单独当成完整通过证据；之后的完整捕获日志与最终 JAR 的 `release-full-regression.log` 分别保留身份和退出状态。
