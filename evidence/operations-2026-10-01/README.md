# 运营与队伍调动证据

候选 **RW-CANDIDATE-2026-10-01-OPERATIONS-v1**，最终JAR `28668be7…`。输入起点 `8109e11`，实现 `3d96d98`，最终报告边界修正 `542a59d`；后者不改变JAR。基线v1未回写或晋级。

先读 [输入与逐行证据](INPUT_ANALYSIS.md)、[完整身份清单](candidate-manifest.json)、[回归摘要](validation-summary.json)、[JAR内容谱系](jar-lineage.json)、[交接](../../handoff/HANDOFF_Codex_Operations_2026-10-01.md)。

## 验证

| 验证 | 结果 |
| --- | --- |
| 完整Windows入口 | 47/47步，23 Java runs / 21 Python suites，342 Python tests，0 Python skipped，failed steps=0 |
| 最终报告解析器补验 | 25/25契约＋7/7控兵联动；最终矩阵352不同Python用例，保留原完整入口342计数 |
| 策略/编队/经济合同 | 169 / 372 / 42项通过 |
| 起步专项 | 30 HTTP场景＋5离线审计契约，35/35通过；BuilderHarness 102项 |
| 多队/经济专项 | 各7/7，旧JAR反例、原生扣款及成品间隙全部保留 |
| JAR谱系 | 固定与完整回归重建98个非manifest归档项一致：91文件＋7目录 |
| 4800入口 | 参数合同4项通过；实际4800长局未运行 |

POSIX-only Java模拟按平台跳过；Windows文件锁真实执行，Python跳过0。Linux本轮未执行。

## 原生样本和重新解析

| 样本 | Bootstrap | Battle实际结果 | 原运行器结果 | 最终离线解析 |
| --- | --- | --- | --- | --- |
| 普通Big Island第一次 | 已有builder，新增命令0 | VICTORY，438.848游戏秒 | FAIL：仍要求旧3报告 | 4份PASS，无issues |
| 普通Big Island复核 | 已有builder，新增命令0 | VICTORY | 整轮PASS | 4份PASS，无issues |
| 无builder受控夹具第一次 | 正常原生生产1次，队列＋新ready builder | PARTIAL/ONGOING，1201.040游戏秒 | FAIL：旧3报告检查 | 3份PASS＋Battle PARTIAL，无issues |
| 无builder受控夹具复核 | 正常原生生产1次，队列＋新ready builder | PARTIAL/ONGOING，1201.104游戏秒 | FAIL：旧解析器不识别多队前沿 | 3份PASS＋Battle PARTIAL，无issues |

两无builder样本均完成经济/发展；不是限时获胜。最新夹具实际最多4队、106次接受局部命令、5笔原生报价3100的重炮订单（15500）、5个ready成品、末待产0。首次夹具也有84次局部命令、最多4队。普通局局部控制未触发，不补造覆盖。

四组16份报告运营审计PASS，4个Battle的全局/专属策略审计无违规。最新夹具没有自然触发矿收入拒绝、付款空窗；专项HTTP覆盖这两分支，不能替代自然触发或因果收益。真实合法攻击收益仍需重复桌面/长局轨迹。

受控地图在独立副本只删除己方初始builder，原Big Island文件不变；商业原图/引擎不进入证据包。它不是未修改Spain验收。原引擎、用户设置/存档/回放、旧JAR与桌面游戏未被替换或启停；唯一桌面目录写入是用户新要求的独立4800启动入口。

## 原始包与边界

[raw-evidence.zip](raw-evidence.zip) 及 [逐文件索引](raw-evidence-index.json) 包含完整入口日志、旧新HTTP、四组原生raw/运行上下文/原始失败batch、初末只读核对、原生夹具生成器与身份、全部审计及新解析结果。旧运行器结果不回写。输入桌面两组6份原始报告也封存，不能和本轮新候选混用。

首次经济合流失败的targeted路径被后续复用；原日志字节未留存，明确标注的工具输出转录保存在包内 `validation/first-integration-failure.md`，不补造原SHA。Bootstrap旧Match夹具只证明未先生产builder，末尾NPE为不完整战斗夹具，不当产品缺陷。经济审计从wrapper.events重新序列化的临时JSONL，原wrapper SHA和非原始bytes边界见包内input-provenance。

v1只读验证检查106文件及环境，只有已完成增量源码漂移，没有冻结文件/环境身份失败。该工具退出1反映源码不同，不授权回滚。所有测试引擎已由各自运行器清理；原始runtime的隔离自动存档重命名失败日志亦保留，与用户存档无关。
