# Astra 交接｜公开 GitHub 中转复核与修复｜2026-09-29

本轮从公开仓库实际 clone，基线提交 `dbbe6274cc6b2ae042a91ac3cb0aa777160f720b`。
按 `ASTRA_START_HERE.md` 指定顺序读取 CURRENT_STATE、工程交接、KnowledgeBacked 最新交接、
知识包与构建说明。执行范围是最新交接建议的独立复核和明确工程缺陷修复；本轮入口由用户
指定为 GitHub，不要求或声称访问 G 盘。

## 结论

公开制品身份通过，客户端/工具回归有可复核结果；**尚不能给当前候选完整独立 ACCEPT**。
缺少原版引擎依赖及最新原生 raw 证据，未重跑完整回归、原生地形对照或 headless 对局。
本轮没有新的胜率、桌面实机或策略增益结论。

候选 JAR 未修改：whole-file SHA256
`5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`；contentDigest
`82438a7b373111dca88b38e7a1c2779527a9cef136cdb9a1297bdf4c511273a7`。
这是对已发布字节的复核，不是本环境完成全量源码重建的谱系证明。

## 已修复

1. **Windows build.bat 漏 resources**：旧打包命令不带能力 JSON，`TargetCatalog` 会失效。
   增加资源打包参数，与 build.sh/test-win.ps1 一致。用当前 TargetCatalog/Json 源码和现有
   TargetCompatibilityHarness 编译最小测试制品：旧参数下预期失败，新参数下通过。
   这是 Linux 上对相同 JAR 打包参数的实测；没有声称执行过 Windows BAT 或全量 Agent 构建。
2. **Linux 回归少三项**：test.sh 增加 ReachabilityHarness 的常规/诊断夹具两次运行和
   test_recon_frontier_client。静态登记已与 Windows 对齐为 19 Java + 15 Python。
   诊断标志仅用于隔离夹具；未重启或改动用户实际游戏。
3. **非 Windows --reap 错误宣称进程已退出**：旧 process_info/port_owners 在不支持的平台
   返回空字典，导致真实存活的旁观进程被登记为 GONE。现在非空查询返回 None，现有回收逻辑
   明确 FAIL、保留登记、不执行 kill。原生 Windows 查询逻辑未变，未增加 Linux 回收能力。
   跨平台失败注入覆盖有限重试与数据保留；两个依赖 Windows 文件句柄语义的原生用例保留并
   在 Linux 显式 SKIP。新增用例检查不支持的查询不能冒充空结果；真实旁观进程用例检查
   进程仍存活且登记原字节不变。

另提供 `tools/verify_public_relay.py` 和 `docs/PUBLIC_RELAY_CN.md`，让后续 Agent 可在没有
本机 G 盘或游戏安装时复核已公开内容。没有改动 `agent/src`、目录 JSON 或当前运行 JAR。

**旧 campaign 兼容边界**：本轮 runner 源码 SHA 改变。已绑定旧 runnerSha256 的 campaign
继续使用原 runner 续跑；不得修改旧 plan/manifest 绕过身份检查。新修复用于新运行目录。

## 本轮验证

- 公开 ZIP 的 23 项全部精确匹配 manifest。checkout 中 17 个 BAT 与 ZIP 只有 LF/CRLF
  差异，已单列；未重写 manifest 或归档。JAR、目录 JSON 三处副本均一致。
- 知识包 559 个 manifest 条目中，本镜像存在的 23 项全部通过大小/SHA 检查；536 项证据
  未公开，其中 534 项是 sources。九图 72 份派生 RLE 的长度/直方图/身份一致。
  这些是制品一致性，不能升格为原始源码语义或引擎运行验证。
- `TargetCompatibilityHarness`、`ReportCommitHarness` 在原候选 JAR 上独立编译并运行通过。
- 15 套 Python 的首次运行及修复后必要复跑均保留。最终集合 **258 项：254 通过、2 SKIP、
  1 failure + 1 error**；14 套退出 0。下面两项失败来自同一历史报告套件缺文件，未删断言、
  未造历史数据，也未改成自动跳过。

| Python 套件 | 用例数 | 本轮结果 |
| --- | ---: | --- |
| `test_development` | 13 | 通过 |
| `test_opening` | 8 | 通过 |
| `test_economy` | 12 | 通过 |
| `test_client` | 11 | 通过 |
| `test_reports` | 16 | 14 通过 / 2 历史输入缺失 |
| `test_frontier` | 8 | 通过 |
| `test_frontier_reports` | 10 | 通过 |
| `test_battle_reports` | 15 | 通过 |
| `test_battle_client` | 78 | 通过 |
| `test_target_compatibility` | 6 | 通过 |
| `test_recon_client` | 7 | 通过 |
| `test_recon_frontier_client` | 16 | 通过 |
| `test_headless_parallel` | 27 | 25 通过 / 2 Windows-only SKIP |
| `test_ab_aggregate` | 17 | 通过 |
| `test_ab_campaign` | 14 | 通过 |

缺项定位：`test_reports.py::test_earlier_reports` 需要
`docs/acceptance-0.02-alpha2.jsonl`、`docs/acceptance-0.03-alpha2.jsonl`；
`test_historical_acceptance` 需要 `docs/acceptance-0.04/*.jsonl` 的三份原始样本。
原始退出码/traceback 见 `evidence/public-relay-2026-09-29/py_test_reports.log`。

## 证据与复跑

证据目录：`evidence/public-relay-2026-09-29/`。`summary.json` 是最终测试集合；
`python-results.json` 和原日志保留首次运行（包括修复前的失败），`python-rechecks.json`
与 `_after.log` 保留修复后的编排/聚合/续跑结果。`identity.json`、`identity-negative.json`、
`packaging-results.json`、`java-results.json`、`runner-parity.json` 分别记录身份、损坏副本
拒绝、漏打包前后对照、两项独立 Java 测试和入口登记对齐。
`source-review.json` 只记合法视野/UNKNOWN/混编分配/terrain 缓存的窄范围源码检查，不作为原生证明。

从仓库根目录：

```bash
python tools/verify_public_relay.py
python agent/tests/test_headless_parallel.py
python agent/tests/test_ab_aggregate.py
python agent/tests/test_ab_campaign.py
python agent/tests/test_target_compatibility.py astra-relay/runtime-overlay/rw-agent-bootstrap.jar
python agent/tests/test_recon_frontier_client.py astra-relay/runtime-overlay/rw-agent-bootstrap.jar
```

原候选 15 套 Python 按 `agent/test-win.ps1` 中的 PyStep 清单串行执行；具体命令、用例数、
退出码和日志 SHA 在 summary 中。没有同时运行两套完整回归或写同一份 dist。

## 后续动作

下一步仍是补齐合法引擎依赖与最新原始证据，完成 KnowledgeBacked v0 的独立原生验收；
既有 37/37、438450 格对照及 PASS/VICTORY 均保持“上一轮交接记录”的身份。
本轮不重复生成知识库、不扩展经济/多人/self-play/Replay、不启用真实比赛危险反射。
