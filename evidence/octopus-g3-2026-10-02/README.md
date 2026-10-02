# 八爪鱼 G3 / G3.5 验证证据

候选 `RW-CANDIDATE-2026-10-02-OCTOPUS-G3-G35-v1`；最终源码 `95a917e`，未部署。只保存必要摘要、完整矩阵 transcript、关键套件日志和可复现测量；完整 HTTP raw 留在本地隔离目录。

- [最终验证汇总](final-validation-summary.json)：有效 63/63、33 Java、27 Python/463 tests；raw full 与环境补验分列。
- [完整首遍 transcript](full-win-transcript.txt)：62/63，唯一缺历史 fixture 的 `test_reports` 失败，原始 exit 1。
- [该套件首遍失败](py_test_reports-initial-missing-fixture.log)、[补齐原夹具后的 16/16](py_test_reports-repaired-input.log)、[5 份夹具身份](historical-fixtures.json)：无源码改变，其他完整矩阵结果复用，未重复整轮。
- [G3 11/11](py_test_g3_execution.log)、[G1 4/4](py_test_g1_trace.log)、[G2 9/9](py_test_g2_world_state.log)。历史 Python suites 显式旧 gate；G3 suite 显式 true。
- [Execution 87](java_ExecutionSchedulerHarness.log)、[Capability 32](java_CapabilityLifecycleHarness.log)、[WorldState 144](java_WorldStateHarness.log)、[Trace 46](java_G1TraceHarness.log)、[Strategy 169](java_StrategyContractHarness.log)、[Provider 61](java_EngineerProviderHarness.log)。
- [15 次交错成本/吞吐](cost-measurements.json)、[测量脚本](measure_g3_costs.py)、[完整回归调用脚本](run-final.ps1)、[候选与测量 JAR 身份](jar-identities.json)。脚本复现需本地原路径/隔离 source，不能在 GitHub 环境直接推定运行成功。
- [必要旧失败与本地索引](retained-failure-index.json)。旧 fixture cap、native queue integration、错误 mine mutation、test javac 选项失败均保留。

完整原始目录 `G:\deepseek 工作台\_validation\octopus-g3-20261002\final-full`；15 次性能 raw 在 `events\cost-final`。源输入、保护输入的前后哈希清单同根保存。测试 JAR 与测量 JAR 整包 SHA 不同，非 manifest 内容完全一致；没有把 ZIP 包身份混为一谈。

采样/差分/execution elapsed 含 HTTP/IO 且范围可能重叠，不能相加或称为 CPU；三次重复不提供自然局性能/战绩因果。ReportCommitHarness 仍有原 Windows/POSIX 子项平台 skip，Python skip 0。原版安装、设置、存档/回放未写，未启停用户桌面游戏。
