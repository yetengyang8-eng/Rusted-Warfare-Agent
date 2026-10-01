# 反馈驱动的运营与调度候选

**RW-CANDIDATE-2026-10-01-FEEDBACK-v1**；固定基线仍为 **RW-BASELINE-2026-09-30-GS-v1**。

本目录 `rw-agent-bootstrap.jar` SHA256 **`0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`**，307981字节；contentDigest `4533010dc6d6717e90ea40c48e406ca6bddfa99aef2a0b05127cad2575957e4d`。源码实现f5b1709、审计修正5020820；JAR固定不变。完整身份见 [manifest](../../evidence/feedback-2026-10-01/candidate-manifest.json)。

新增后方工程师造两栖战机并正常潜水接手水下响应、T2/T3按局部风险和回本窗口投资、小规模基地/矿袭击有界小队防守、多队无订单公平调度。以前的无builder自动起步、稳定多队、重炮余钱消费保留。新矿点局部扩张、猛犸消费和开局多方向坦克侦察尚未实现。详见 [交接](../../handoff/HANDOFF_Codex_Feedback_2026-10-01.md)。

Windows有效54/54步、420不同Python用例；首次53/54隔离夹具遗漏及最终专项复核分别保留。两场同JAR未改图原生局均PARTIAL/ONGOING；Big Island自然覆盖3次潜水兼容响应和2次T3，Spain自然6次成品转交。不是用户桌面验收或胜率结论。

## 使用

当前目录是固定交付，不是游戏安装。无画面验证从仓库 `tools/run_headless.py --agent-jar` 指向本JAR，使用新的独立输出目录和原版资源。桌面使用时新建独立游戏环境，沿用匹配原版启动套件，在游戏未运行时备份该新环境JAR，再复制本JAR及所需入口到游戏根目录、与 `jvm64` 同级。不要只复制入口到旧目录却把旧JAR测试记在新候选名下。

本轮没有替换用户桌面JAR或启停用户游戏。由操作者先打开合法单机局并开启该环境Agent，再使用Match入口连接默认端口47653。入口不启动/关闭引擎；没有Java或JAR会拒绝运行。

| 入口 | Battle预算 |
| --- | ---: |
| RW-Agent-Match-1200.bat | 1200游戏秒 |
| RW-Agent-Match-2400.bat | 2400游戏秒 |
| RW-Agent-Match-4800.bat | 4800游戏秒 |

4800满足“4000以上”的长局入口要求。起步/经济/发展另计，原生胜负会提前结束；墙钟安全上限3600秒、游戏安全上限7200秒，推荐5x（Battle约16分钟）。本轮新候选只跑2400/1800自然预算，没有4800新候选自然验收。报告在运行目录 `rw-agent-reports`。原900/1800等已存在入口不在本次目录改写。

## 回退

上一 [Operations固定JAR](../operations-2026-10-01/rw-agent-bootstrap.jar) SHA256 `28668be7c49a2b5d8f7f326fbc3f365a2179d4c1b92a169959c58213ec44ecb7`；冻结v1在 `astra-deliveries/global-strategy-2026-09-30`。只在新建的独立环境、游戏未运行时恢复该环境备份或上一固定件，保留新报告。不要把回退复制到原版、历史交付或用户当前目录。本轮未部署，无需当前桌面回退。
