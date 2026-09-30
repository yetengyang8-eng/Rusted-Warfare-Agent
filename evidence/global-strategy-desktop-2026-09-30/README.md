# Global Strategy 桌面 E4｜2026-09-30

本目录补充 Astra `Global Strategy / Feasibility / Combat Engineer` 最终候选在用户 Windows 桌面的真实长局证据。候选 JAR SHA256：`76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`；contentDigest：`8092b61130c3497b370e12ce249d3ec17e6331d502fefbcb50bbd95a32f03120`；冻结 `game-lib.jar` SHA256：`8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`。

三局均为桌面 Match、正常引擎、5x，Battle 预算约 2400 game sec；三局都在预算到期时仍为 `PARTIAL / ONGOING`，不能写成胜利或整局 PASS。专项审计均 `pass=true` 且 `violations=[]`。

## 三局映射

| Battle | 地图/范围 | Player scope | 结果摘要 |
| --- | --- | --- | --- |
| `battle-1790741949748-c01f222f.jsonl` | Big Island，180×180 | `team:0` | 2402.295s；127 新战斗单位；64 损失；最高 88 armed；13 capability needs；1 个被合法证据解决；识别 seaFactory `MOVEMENT_APPROACH_GAP`，跨域响应对该目标观察到伤害 |
| `battle-1790742800972-4064cebd.jsonl` | 西班牙 10p，400×370 | `team:5` | 2401.865s；237 新战斗单位；243 损失；最高 21 armed；8 capability needs；本局没有形成战略投资/响应任务，体现当前长局方差与失守路径 |
| `battle-1790743783098-ae01b65e.jsonl` | 西班牙 10p，400×370 | `team:5` | 2402.320s；139 新战斗单位；82 损失；最高 101 armed；2 capability needs；52 次 strategy investment orders；13 次矿升级；30 次战略施工；对潜艇响应任务观察到进展与伤害 |

地图身份说明：raw battle 会自证尺寸和 player scope，但目前**不写 mapPath / difficulty**。Big Island / 西班牙名称来自本次桌面操作记录；西班牙本地 TMX `[10p] 10p 西班牙混战_by_MP97.tmx` 自身尺寸为 400×370，与两份 raw report 一致。不要从这些报告反推未记录的难度。

## 西班牙局唯一人工干预（重要）

西班牙地图的该出生位开局只有基地、没有建造者，因此无法直接启动现有 Match 流程。两次西班牙测试中，操作者都在 **Match 启动前人工生产 1 个 builder**，然后才启动 Match；**这是唯一人工干预**。Match 启动后没有手动控制单位、补建筑、改目标或替 Agent 下命令。

因此两次西班牙结果应表述为：`operator-bootstrapped start (one builder before Match), then autonomous Match`，不能表述为从地图原生零干预出生状态全自动起步。这个 bootstrap 不应用来解释 Match 启动后的战略命令、投资、战斗与损失为人工行为。

## 证据文件

- `reports.md` / `reports.json`：三次 Match 的统一离线汇总。
- `battle-*.json`：`tools/audit_global_strategy.py` 对三份原始 Battle JSONL 的专项审计；包含 candidate provenance、battle config、capability needs、response targets、damage/progress、投资产品与违规检查。
- `bundle_index.json` / `.csv`：压缩原始报告的逐文件 session / phase / bytes / SHA256 索引。
- `RW-Agent-Reports-...p1of3...zip`：session `0434d623...`，西班牙第二局，含 economy/development/battle raw JSONL。
- `RW-Agent-Reports-...p2of3.zip`：session `8bc1ad58...`，西班牙第一局，含 economy/development/battle raw JSONL。
- `RW-Agent-Reports-...p3of3...zip`：session `d37b52f9...`，Big Island 局，含 economy/development/battle raw JSONL。

压缩包只是为了绕开 GitHub 单文件限制；`bundle_index.json` 中保留未压缩 raw report 的 SHA256。原始 Battle 分别约 129 MB、72 MB、160 MB，仓库没有把它们直接作为单个 blob 提交。

## 当前能支持的结论

1. 最终 Global Strategy JAR 已经在 Windows 桌面长局自然执行，而不是只存在于 Linux/headless 或 E2 fixture。
2. Feasibility / capability need / task ownership / 战略投资链在真实桌面局中可自然触发；Big Island 至少出现 `MOVEMENT_APPROACH_GAP → response → progress → visible damage → legal need resolution`。
3. 动态容量确实突破旧 cap=40：三局最高 armed 分别 88 / 21 / 101；但表现方差很大，不能据此宣称胜率改善。
4. 西班牙第一局显示严重消耗路径：237 新战斗单位对 243 损失、最高 armed 仅 21，且没有战略投资；第二局则能扩展到 101 armed 并执行大量建设/升级。这种差异是下一轮应解释的对象。
5. 三局均没有原生终局，因此没有新的胜负因果结论；也不能把单局伤害/清场归因扩张成整体策略优于旧版。

下一轮优先分析：为什么同一 400×370 西班牙地图会出现“几乎纯消耗”与“大规模扩张”两种轨迹；能力需求为何有时不进入投资/响应；以及响应者生存、成组响应、终局清理与经济溢出。不要先继续抬高 hard cap。
