# Octopus G5 evidence

Implementation source22c88a2；完整测试源码f058d3a。候选Jar与Windows/native身份见delivery manifest；未deploy/push/桌面操作。

- performance-natural.json：本轮83c/G5 Small Island真实原版5×自然ticks、fog-on；两局VICTORY，但不做seed受控AB/胜率/战损因果结论。
- desktop-reference.json：任务开始复用的两局桌面raw分析，Spain>48及FREE堆积为当前需求依据。
- validation-summary.json：full一次原始74/76 + 两套reference修正focused13项 → 有效76/76；42Java/31Python/496tests/skip0；185inputs未变；选定保护输入与外部桌面设置变化记录。
- historical-reference-correction.json：83c再现旧参考差异、本候选/83c命令与旧事件完全相同；没有为过测试改产品。
- tested-input-manifest.json：185项冻结Windows输入及hash（原版资产不发布）。
- jar-payload-equivalence.json：自然Jar877dfa…与full Jarb5d87…的183项全部未压缩payload一致，仅容器元数据不同。
- native-focused-results.json：最终同Jar G5 253 + G3 168/30/31，共482checks。fixture人工单位/fog/clock/位置不算自然效果。
- native-initial-focused-manifest.json：初版focused的schema/CLI/阈值失败与最终253绿，完整保留。
- observer-native-log-validation.json：真实85MB已提交native日志增量读取，19,317events，源SHA/mtime不变，CURRENT_ONLY/UNKNOWN、时钟口径和身份可见。

本地raw `_validation/octopus-g5-20261003/`：final-full/transcript.txt保留原始2失败；reference-corrected/g1.log、g2.log是13项补验；native/final-g5与final-g3-*是最终Jar native；before-83c/work、after-g5/work为自然局；observer/native-log-read是实际面板与自动短报告。自然记录最初SHA与full不同已明确，不冒称same archive。

自然额外General/Spain协同/混合地形rally可达性与人工桌面入口NEEDS_EVIDENCE。具体边界和使用方法见G5 handoff。
