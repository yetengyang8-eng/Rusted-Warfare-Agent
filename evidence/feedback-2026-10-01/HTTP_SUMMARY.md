# 最终 HTTP 全链证据复核（2026-10-01）

33 个已完成 fixture：local-army 14、provider 8、surplus 11。五项独立检查（feedback、operations、global strategy、specialist lifecycle、严格报告 parser）逐例全部 PASS；feedback gaps=0。详细覆盖、来源 SHA、每项审计及分支 NOT_TRIGGERED 见 SUMMARY.json 与33个单独JSON。没有使用仍在写入的raw。

| 确有覆盖的例子 | 量化证据 |
| --- | --- |
| production_starvation | fairness8；12条诊断；有空订单样本的最长accepted age14秒 |
| raid | 1危机，2次接受小队响应；失败/UNKNOWN/不兼容/失联另有负对照 |
| provider chain | 1次native建造→1唯一readyjet→1need转交→1次Diveaccepted→1次当前compatible→14次fresh jet response |
| provider mode_timeout | 3次分隔重试，0compatible/0attack；等待期间不重复Dive，显式失败后释放/重试 |
| provider hidden | 1ready转交，0Dive/0attack，隐藏目标保持UNKNOWN |
| mine_saturated | 21次payback评估、3次接受升级；有预留/军力/安静窗/原生菜单价格约束 |
| mine_t3 | 90次payback评估、6次升级选择；实际产品及原生price详见原events |

surplus没有原始独立JSONL：11条来源JSON的events被原样记录结构重建到reconstructed-surplus/*.jsonl。每个单独审计明确标记 RECONSTRUCTED_FROM_RECORDED_FIXTURE_EVENTS_NOT_ORIGINAL_RAW，并记录原JSON与派生JSONL各自SHA。它们是HTTP夹具证据，不等于原生实战、桌面验收或同条件胜率改善。

Operations对新矿deferral只核准确reason/model/shape，显式覆盖缺口指向同一流的feedback完整接受约束校验；旧收入饱和schema仍严格核算buffer/armyDeficit。unknown reason、错误model、旧buffer错配等负夹具由48条审计合同测试覆盖。

实际原生自然触发与未触发边界分别见native-audit/spain和native-audit/big-island的NATIVE_SUMMARY.md。对诊断age为null的样本，不使用初始化零作为实测空闲时间；跨local-army active切换的长age也不直接等于整队没行动。
