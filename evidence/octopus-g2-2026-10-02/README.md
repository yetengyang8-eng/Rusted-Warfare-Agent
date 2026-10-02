# 八爪鱼 G2 验证证据 — 2026-10-02

**最终结果：60/60 Windows 步通过，31 Java harness、26 Python 套件/448 tests，failed steps: 0、exit 0。** Python skip 0；原 ReportCommitHarness 的 Windows/POSIX displacement 子项平台跳过保留。代码身份固定为 `5dd76e41d95887ec6c8dc6647476491b9b5241a6`，从 ff693c8 开始。

候选 **RW-CANDIDATE-2026-10-02-OCTOPUS-G2-v1 / NOT_DEPLOYED**。JAR SHA256 `1dbeb3084f9ee7dd154b7290daef6c9fc2fe780d9e033fd45539fb3aa01ac7d9`，362190 bytes；contentDigest `9e51d99cc67e00833080ae1b1a23387015f7b8bafc460b70206bee9a4c03998b`。完整身份在 [jar-identity.json](jar-identity.json)和[manifest](../../deliveries/octopus-g2-2026-10-02/candidate-manifest.json)。

## 最终证据入口

- [完整回归输出](full-windows-regression.txt)及[逐项结果](final-validation-summary.json)：最终矩阵 2026-10-02 18:07:33 完成。不是前一轮被中断的矩阵。
- [冻结 G1 基线结果](baseline-validation-summary.json)：58/58、30 Java、25 Python/439 tests、failed steps: 0；[输出](baseline-windows-regression.txt)。重建 ZIP 的非 manifest 条目与 G1 冻结交付一致。
- [73 core checks](focused-WorldStateHarness.txt)、[独立 12 checks](independent-final-probes.txt)及[缺时间覆盖 2 checks](independent-missing-coverage.txt)；[独立复核报告](independent-review.md)和[实际源码哈希](independent-source-hashes.json)。证据目录中的 Probe.java 只是审计脚本，不进入 agent 编译。
- [等价与导出量](g2-equivalence-and-volume.json)：五正常输入 + 四 guard 三方，共 27 raw reports。完整测试 G2 9/9、原 G1 4/4。双方 G1 Trace 均开启；所有 HTTP 请求/原响应、命令、旧事件数据对照相等，只有 request UUID 与三项原墙钟诊断归一化。
- [132 个源文件原哈希](final-source-hashes-before.json)、[源码一致性](final-source-verification.json)、[隔离 staging 核对](final-staging-verification.json)、[旧机制保留](policy-preservation-checks.json)及[保护输入检查](protected-input-verification.json)。源码、测试、资源、tools 和 runner 与最终测试输入逐字一致。
- [控制文档及冻结件未变](controlling-input-final-verification.json)、[任务输入身份](G2_INPUT_IDENTITY.json)。
- [真实事件示例](selected-event-examples.json)：从最终 fixture report 摘取，并附对应原始 Observation，不是自然局证据。
- [81 份 raw 文件路径/哈希索引](raw-g2-artifact-index.json)：27 reports + 各 HTTP/outcome。完整 raw 保留在本地 `_validation/octopus-g2-20261002/final-v2-full/g2-four-path`；仓库只收录索引与小型示例，远程审阅者不能假定 raw 路径可访问。
- [本地复现说明](LOCAL_VALIDATION_README.md)及[总审计摘要](audit-summary.json)。该说明中的相对运行目录指本地验证根，非本 evidence 目录。

## 行为等价的范围

Recon、LocalCrisis、工程师/两栖 provider、普通 production 均与 G2-off/冻结 ff693c8 比较；native-stamps 变体移除 scout gameTime 和生产菜单 frame/gameTime。原 fixture 缺失 teamId 的情况在三方输入中对称补齐 teamId=0，此操作只发生在测试适配器，不是生产兜底。原始 G1 未适配夹具另行执行。

四 guard 在 lastState 已建立的 frame3 注入，比较真实 exit1、拒绝原因和无新增 POST；被 guard 拒绝的读取可保留 G1 原 Observation，但不进入 G2。session/frame guard 本身未变，适配器 reset 能力不代表客户端自动跨局恢复。

| 路径 | 各变体命令数 | G2 on/off 导出行数 | G2 on/off 字节 | 字节比 |
| --- | ---: | ---: | ---: | ---: |
| Recon | 1 | 238 / 136 | 785967 / 284871 | 2.759028 |
| LocalCrisis | 6 | 402 / 230 | 1374372 / 594878 | 2.310343 |
| Engineer provider | 17 | 1033 / 645 | 3461373 / 1523414 | 2.272116 |
| Ordinary production | 78 | 2520 / 1637 | 9186497 / 5022825 | 1.828950 |

这些是诊断导出量；不证明 CPU/内存/延迟、实际游戏吞吐或自然轮询等价。自然局、桌面 G2 验收 **NOT_RUN**；JAR 未安装，未启停用户桌面游戏，引擎、已安装 agent、设置、存档、20 libraries 与 868 assets 未改。

## 保留的失败与边界

- [初版独立探针失败](initial-independent-failure.txt)保留，修复后最终 14 checks 通过。
- [首次 HTTP focused 失败](initial-focused-failure.txt)：四 guard 被旧成功 fixture 的 exit0 断言错误判败；修正测试出口采集后 9/9，[修复日志](repaired-focused-http.txt)。没有改生产 guard/finally。
- [G1 不具备 G2 的预期负例](expected-baseline-no-G2-failure.txt)，不纳入绿色基线矩阵。
- [冻结 G1 早期 guard 问题](preexisting-early-guard-limit.json)：第二次 state 触发 guard 时原有 report commit NPE，本轮未修；正式三方对照在 frame3 比較，不能隐藏这个已有问题。
- [被替代矩阵状态](superseded-full-status.json)及[原输出](superseded-full-transcript.txt)：5e30a34 跑完 12 PASS 后因发现缺 native time 的空列表可误造失联而主动中断；PARTIAL_SOURCE_SUPERSEDED，不计为 60/60。最终补充覆盖校验后在新 staging 完整重跑。

本目录的 [EVIDENCE_INDEX.json](EVIDENCE_INDEX.json)对文件逐一列出 SHA256/bytes（索引不包含自身），源身份固定为 5dd76e41。[交接](../../handoff/HANDOFF_Octopus_G2_2026-10-02.md)与[契约](../../docs/OCTOPUS_G2_WORLD_STATE.md)是继续任务的入口。

冻结 GS 血统只读核对仍报告 `source_drift`（exit1），对应其后已授权的 capacity/G1/G2 改动；不是当前 ff693c8→G2 回归失败。环境核对无其他失败，本轮不 reset。原输出见 [frozen-lineage-verification.json](frozen-lineage-verification.json)。
