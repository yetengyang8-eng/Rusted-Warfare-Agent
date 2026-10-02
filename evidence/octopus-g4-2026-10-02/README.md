# Octopus G4 evidence

最终同JAR Windows68/68，37Java、28Python/466 tests，failedsteps0/exit0；native298checks绿。主智能体完整执行2次：首遍只因关闭G4的battle_config新增字段导致G1/G2比较失败，54b9de1修正事件形状后完整重跑。测试断言未放宽，首遍与之前native失败均保留。

- [最终验证汇总](final-validation-summary.json)：159输入/5历史fixtures、895保护文件、候选JAR身份、原始本地路径。
- [完整步骤结果](regression-results.txt)、[首遍失败记录](legacy-log-compatibility-failure.txt)。
- [同一JAR原生验收](native-acceptance.md)、[52源码及23原始工件哈希](native-manifest.json)。
- [G3先行硬化](../../docs/OCTOPUS_G3_NATIVE_ACCEPTANCE.md)、[G4状态契约](../../docs/OCTOPUS_G4_FORCE_LIFECYCLE.md)、[handoff](../../handoff/HANDOFF_Octopus_G4_2026-10-02.md)、[candidate manifest](../../deliveries/octopus-g4-2026-10-02/candidate-manifest.json)。

原始完整stdout、日志和测试输入在`G:\deepseek 工作台\_validation\octopus-g4-20261002`。远程GPT/DeepSeek仅能读共享摘要/manifest，不应假定访问本地文件。未redistribute商业引擎或资源，未deploy/push/桌面操作。

Native级别E2_NATIVE_FIXTURE_WITH_REAL_HTTP / NO_NATURAL_MATCH：真实原版引擎/单位、真实RuntimeBridge、真实BC force adapter/scheduler/command.k，时钟/高度/位置/生产推进显式受控。自然主循环、路线、生产耗时、开火伤害、实机5×或战术成效均不由此证明。HTTP mainloop三项为独立合成层。Spain仅PASS_BOUNDED_ACCESSORS，潜水jet可攻击选取反驳“任何目标不能打”；自然进入及射击NEEDS_EVIDENCE。
