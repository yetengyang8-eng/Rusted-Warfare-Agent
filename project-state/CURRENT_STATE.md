# Current State

Updated: 2026-10-02 (Asia/Shanghai). 当前工程断点 **Octopus G3 / G3.5**，已验证，未部署。

- 分支 `codex/octopus-g3-execution-20261002`；起点用户验收 G2 `cdb9252e19ffcfaceba7f0d74f6523b7b0ae606d`。
- 实施 `5678998`；最终测试源码 `95a917ef1157b7a7dcebcce50bf5fff4ff8bc80d`。后续 docs 提交不改变已测试输入。
- 候选 `RW-CANDIDATE-2026-10-02-OCTOPUS-G3-G35-v1 / VALIDATED_NOT_DEPLOYED`；JAR SHA256 `7b51bf4a34db9fbae860cf559ab9fa9eb94e02c97f2898182201a4287136703d`，394924 bytes，contentDigest `ffcfea8260fd6617a6c66ec77b2dde1d12aa7bb9c027e397e31d2aa7129124d8`。
- 原桌面安装仍为 capacity JAR `a392f692…`；冻结 `RW-BASELINE-2026-09-30-GS-v1` 只核血统，不授权回滚新源码。

已实际迁移 owner generation/显式 Intent/统一 native dispatch、elapsed game time 每1000ms一token/默认burst4、批内credits与producer/military slots。原 Pending/Purchase/paidConstruction/ghost 保留，主 caller 多 actor 可继续。运行时同步按原 lane 顺序，首次实际 native attempt 裁决同 actor；没有全局异步收集/抢占。原 bridge guards 未改，receipt 不证明执行。

成熟 engineer→jet→Dive/Fly/return 纵向接入；CapabilityTask 集体目标、Mode 逐 actor。SearchAreaNeed/SearchTask 仅契约，未激活；coverage adapter/水下发现规则 NEEDS_EVIDENCE。持续相同敌情只更新来源/LastObservation，关键变化才 ENEMY_UPDATED。没有 G4/General/FREE/JOINING/ThreatTask/HOT/COLD 或新生产偏好。

有效 Windows 验证 **63/63，33 Java，27 Python/463 tests，Python skip0，校正汇总failedsteps0**。首遍raw full为62/63/exit1，仅因隔离复制漏5份历史docs夹具；补齐并仅重跑test_reports16/16PASS，源码/JAR不变，失败原始记录保留。历史Python显式旧gate；新G3 suite显式true且11/11。142份测试输入与5份历史fixture一致，7保护文件/888只读assets+libs未变。

合成HTTP截至7000game ms旧2命令→新7；总7→12；long-gap批次最多4。15次成本测量中关闭附加导出约减少95.1%日志字节；elapsed非CPU，不证明自然局/墙钟因果。自然局/桌面验收NOT_RUN，未push/部署，未启停用户桌面游戏。

接手：[G3/G3.5 handoff](../handoff/HANDOFF_Octopus_G3_G35_2026-10-02.md)、[Execution/Intent契约](../docs/OCTOPUS_G3_EXECUTION_INTENT.md)、[证据](../evidence/octopus-g3-2026-10-02/README.md)、[manifest](../deliveries/octopus-g3-2026-10-02/candidate-manifest.json)、[下一阶段](NEXT_STAGE_PLAN.md)。G1/G2历史身份保留原交接与manifest。

后续子智能体优先 `gpt-6.1-sol / high`，不使用Astra；ASTRA_*只是历史导航文件名。
