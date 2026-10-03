# G5.1 evidence index

产品验收起点ca2fc92，最终测试产品源码8498c10；交付文档提交在其后。manifest与validation-summary为当前身份入口，不把早期smoke/中断回归当最终结果。

- `validation-summary.json`：最终Windows、focused/native/natural类别、源码输入hash一致性、失败记录与NEEDS_EVIDENCE。
- `human-root-causes.json`：707MB真人Spain raw的hash/精简统计/修复映射；原raw在用户指定目录，未修改。
- `native-summary.json`：同候选Jar原生495 checks；fixture单位/位置/时钟可控、零自然simulation ticks。
- `environment-correction.json`：Windows完整首遍79/80，StrategyNativeHarness缺静态asset；同Jar/同classes在正确隔离cwd补验51checks通过，有效80/80。原始失败保留，无第二full。
- `natural-smoke.json`：同候选Jar Small Island5×600game seconds；PARTIAL/ONGOING，不是真人Spain或受控AB。

外部完整证据：`G:\deepseek 工作台\_validation\octopus-g51-20261003`。最终full console/transcript/test logs、snapshot与219份输入hash、G3/G51 native rows、自然raw、早期失败、abort与provenance修正均保留。只提交小摘要，未重新打包大审计资料。
