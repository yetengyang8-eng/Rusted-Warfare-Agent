# 反馈驱动改进证据

候选 **RW-CANDIDATE-2026-10-01-FEEDBACK-v1**，固定JAR `0116e7c6…`，生产f5b1709、最终工具5020820；基线v1未晋级。入口是 [交接](../../handoff/HANDOFF_Codex_Feedback_2026-10-01.md)、[输入逐行分析](INPUT_ANALYSIS.md)、[候选manifest](candidate-manifest.json)、[JAR逐项身份](candidate-identity.json)、[回归摘要](validation-summary.json)。

## 回归与合同

有效Windows矩阵54/54步、failedSteps0、28 Java runs、23 Python suites、420个不同Python用例、0 Python skipped。首次完整入口53/54，隔离快照缺历史文档夹具；补齐后test_reports16/16，再复核工具变更影响套件。原完整失败与最终受影响复核在包内分开，不能写成一次54步全绿或累计重跑覆盖。两处纯测试host schema在执行前重新编译，不改变生产JAR；source-candidate-comparison证明32生产Java/resource字节一致。

LocalArmy372、LocalCrisis16、MineInvestment30、EngineerProvider47、NativeMorph102、StrategyNative51合同通过。NativeMorph含继承的Opening fixture，不是102次自然潜水。33HTTP（army14/provider8/surplus11）逐例通过feedback、operations、global strategy、specialist lifecycle、strict parser；surplus recorded events重建流与原JSON的SHA分别记录，不能当原始字节。见 [HTTP汇总](HTTP_SUMMARY.md) 和 HTTP_SUMMARY.json。

## 两场隔离原生局

两局同一固定JAR、原图不改、difficulty1/requested speed5；预算到期PARTIAL/ONGOING，无自然结算。每局4raw的五项审计通过，前置三阶段PASS、Battle PARTIAL、issues=[]；没有用户桌面验收、胜率/因果收益或专属击杀证据。

| 自然行为 | Spain | Big Island |
| --- | ---: | ---: |
| T2 / T3 observed ready | 2 / 0 | 5 / 2 |
| jet下单 / ready / 转交 | 6 / 6 / 6 | 4 / 4 / 4 |
| Dive / 实际当前兼容 / jet响应 | 0 / 0 / 0 | 3 / 3 / 3 |
| 危机开始 / accepted响应 / accepted撤回 | 24 / 30 / 24 | 7 / 9 / 6 |
| 最大队伍 / fairness grants | 1 / 0 | 4 / 49 |

详见 [Spain摘要](SPAIN_NATIVE_SUMMARY.md)、[Big Island摘要](BIG_ISLAND_NATIVE_SUMMARY.md) 和各 metrics/suite JSON。Spain来自同字节复制的实际400×370原图，出生只有commandCenter，脚本正常自动生产1builder，无地图修改。地图SHA `342db6d8…`；原图/商业资源不入包。Big Island使用原版地图。seed记录存在但可重复性未验证。

Spain没有自然Dive，因为转交后目标不可见，UNKNOWN不开放反潜。Big Island的3次实际潜水响应来自同一架1241复用，WATER/range100证实；不是3架独立成品均反潜。目标变域期间mode完成的联合状态校验仍待补，不能只凭DIVE event证明潜水。最长275.264秒accepted age跨过inactive单队模式，期间20main attack/27queue，最大诊断30人只有4人无订单且远离目标；不是全队停摆。Spain accepted age没有样本为null，不能写实测0。末现金/损失只作观察，旧桌面team5不构成受控对照。

## 原始包、失败与保护

[raw-evidence.zip](raw-evidence.zip) 和 [逐文件索引](raw-evidence-index.json) 包含8份新候选原始阶段报告、运行设置/初态/进程身份、原runner/batch结果、完整首次回归/受影响复核、HTTP成功与失败、旧候选负对照、原生读取合同、全部审计、初末只读核对与源码快照身份。索引逐文件SHA及来源保留；商业引擎/地图/资源、偏好、存档、JAR重建中间件与缓存不入证据包。

原Spain runner FAIL包含RETURN坐标误报，后来严格复现Float32→原生三位字符串，未放宽任意偏差、未修改raw；最初parser/owner/schema误报与最终离线结果均留存。Big Island原runner FAIL明确要求自然终局而本局预算到期。Operations新mine-deferral只校验schema，完整quiet/payback/reserve约束显式由同流feedback audit覆盖；不把旧blanket规则冒充新投资审计。

最新桌面输入约366MB长局不重复打包，INPUT_ANALYSIS/metrics保留其实际绝对路径、SHA、行号，输入JAR28668be7与候选0116e7c6严格区分。反馈与思想文档是参考数据而非额外授权。

开始/结束核对106保护文件，唯一失败类别source_drift，冻结引擎/资源/交付无身份失败。本轮仅隔离构建及原生测试，两个测试引擎由各自运行器记录EXITED；原版、历史交付、用户设置/存档/回放及桌面游戏未被改写或启停。未做桌面安装，v1不晋级。
