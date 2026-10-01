# 2026-10-01 运营改进：输入证据与选题依据

这是本轮输入分析初稿，供 GPT / DeepSeek 接手核对。最终合流候选的源码提交、JAR 身份、完整回归、原生结果与部署状态由 root 在最终交付记录补齐；这里不承接中间构建的验收身份。

开发起点是 `GitHub发布/Rusted-Warfare-Agent` 最新提交 `8109e11` 的 Specialist Lifecycle 增量。冻结工程基线仍为 `RW-BASELINE-2026-09-30-GS-v1`。本轮用户明确期待无建造者开局、多军队控兵、经济冗余支出，允许自主选取实现，但不因此改变合法视野、单位归属、共享命令间隔和原版保护边界。

## 1. 最新两份桌面输入身份

原始目录：`G:\deepseek 工作台\游戏环境\P1F-Astra-SpecialistLifecycle-2026-10-01\rw-agent-reports`。
两份 Battle 的 health / provenance 都自证 JAR `b3e172de9ba54e9ebfd8da1f6787c72ad9f9f7a8702e42cdc63cc948bc57e71e`，game-lib.jar `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`，Windows / Java13。它们是上一轮 Specialist Lifecycle 的桌面输入，不是本轮新候选结果。

| 原始 Battle | SHA256 | 字节 / 行数 | raw 自证控制域 / 尺寸 | 原生结算 |
| --- | --- | --- | --- | --- |
| `battle-1790785836337-cbb16a0d.jsonl` | `8494e4c589def7f8a1cad5b312774d07ca7488838e8c82da8e92d93c5058989a` | 6,582,550 / 2,245 | session `0995512a-b299-4e3d-be1f-8ed23563dd70`，team0，180×180 tiles | 末行2245：PASS / VICTORY，Battle522.465游戏秒 |
| `battle-1790786040776-224cbca9.jsonl` | `5f42806a8398f543d248203937b37ccbaf9c833f8b5ad2e54ba7926eee785c92` | 112,272,180 / 12,591 | session `3fa0ac5a-67c0-472b-a7ec-bf4fc60c64bc`，team5，400×370 tiles | 末行12591：PARTIAL / ONGOING，Battle2401.120游戏秒 |

本次以二进制流重新计算两份 raw 的 SHA；读取前后大小和修改时间稳定，均与现有 `report-bundles/bundle_index.json` 一致。大文件 SHA 再核定仍为 `5f428...`。

桌面分析子目录分别称它们为 `giant-analysis` 和 `spain-analysis`，这些是操作方分析标签。raw 不写实际 mapPath / difficulty / seed，也不证明推荐验收流程真的被执行；不从推荐 Big Island / difficulty1 / 标准开局补猜实际条件。

两份 Battle 行3均已有1名 builder、1座 landFactory、7辆 c_tank 等工作起点；不能据此证明无人工生产的原生无 builder 开局。较小局末尾40名新观察战斗单位、15笔己方损失、63/63攻击确认；大局179名新观察战斗单位、102笔己方损失、232/233攻击确认。损失口径包含不同角色，不能直接用新观察数减损失数推导净战斗军力，也不能把较小局胜利归因于本轮尚未安装的改动。

## 2. 期待一：没有 builder 时先生产再进入后续任务

旧 `8109e11` 的 `MatchClient.run()` 直接读取 preflight。`RUN_ECONOMY_OR_OPENING` 进入原经济/开发流程；`RUN_DEVELOP` 直接进入 Battle；其他 recommendation 报告失败。旧 `EconomyBridge.preflight()` 有闲工厂时优先推荐 RUN_DEVELOP，即使当前没有 builder；无可用工厂和建造者时推荐 PREPARE_BUILDER。Battle 中的恢复路径不能替代 Match 前对开局建造者的明确准备。

最小负对照：`_validation/operations-20261001/bootstrap-targeted/old-match-case/new-match/fixture.json` 给出无 builder、已闲工厂、RUN_DEVELOP。冻结旧 JAR 的调用为 `preflight → health → state → state`，无 produce-builder，且没有 bootstrap 报告。其后 NPE 来自该夹具只为检验阶段顺序而未提供完整 Battle 配置，不作为原产品崩溃结论。

新夹具要求的闭环是：`health → 自身状态与 preflight → 当前原生 builder 菜单 → 一次正常生产 → 队列/ready成品观察 → 再核对自身状态 → 新 preflight → 后续 Match`。已有 ready builder 不买、任意已知生产者已有 builder 队列不重复买、成品必须 ready，最终玩家/session/成品丢失要阻断后续阶段。多个新 builder 只按观察可用角色选取，不证明工厂来源。

专项 E2 入口为 `agent/tests/test_match_bootstrap.py`，中间阶段30场已通过；证据 `bootstrap-targeted/http-cases-ready-recheck`。默认等待上限180游戏秒、120墙钟秒、poll500ms，配置上限300秒；这些是执行保护的工程值，暂停场景用墙钟上限结束，不能无限等待。完整最终矩阵与自然出生条件由 root 最终核对。

## 3. 期待二：多支军队分别推进，避免同一批人反复追远目标

大局 raw 的 `/command/attack-move` action 共234条，含战略响应攻击；summary普通 attackOrders=233，两者不能混算。将相邻 attack-move action 按游戏时间排序，筛选间隔<20000ms、目的地变化>1500地图单位、共同 actor>=12，共得到43次。这个数只描述命令流拉扯，不声称43次都造成战损。

已流式复算并直接复读下列反例：

| 原始行 | 游戏绝对时间 ms | 目标 / 坐标 | 相同 actor / 变向 |
| --- | ---: | --- | --- |
| 8772 intent / 8773 action | 1868754 | scout #28568，(5883.546,4927.805) | 同48人 |
| 8798 intent / 8799 action | 1874539 | hoverTank #28098，(5444.604,1755.385) | 5.785游戏秒后变化3202.6；同48人距新目标均>2000 |
| 9678 / 9679 | 2046594 | 当前AA塔 #22234，(3900,2160) | 同48人 |
| 9707 / 9708 | 2054659 | 记忆airFactory #21030，(5470,5250)，lastSeen=1894159 | 变化3466.0；同48人 |
| 9737 / 9738 | 2059989 | 当前T2塔 #31318，(3500,2640) | 再变化3270.0；三笔actor完全相同 |

最后一组在13.395游戏秒内发生两次远距离改向。中间 airFactory 的最后观察时间比该命令早160.500秒；intent 虽仍标 OBSERVED_ENEMY，来源对象是旧记忆，这个标签不能被当作当前可见证据。

末态行12587有97名移动武装单位，排除2名工程师后95名；200/400地图单位连通半径均得到44/25/15/6/4/1六团。已有工程师归属、Recon、Recall等排除逻辑仍需遵守；不能用全己方数量替代当时真正可用主力。

选定设计是在 default main owner 内保存局部 cohort，各队保持自己的目标、进展与命令时间，使用局部合法目标或本队前沿；同目标无进展判断按真实已分配成员合并，原生接受命令后才更新轮转/冷却。单团保留既有流程，未知、不兼容和 terrain approach 的守卫继续适用。

初版工程值：最多4队、每队48人，附近至少6人建队、低于3人释放；成组半径400，距旧锚点至少1000且6人可分远处队；局部目标半径1400、记忆30游戏秒、原生命令间隔8游戏秒。上述值用于限制首轮实现，不是资料证明的最优规模。共享命令门槛仍至少1游戏秒。无剩余前沿时只用当前可见远接触推进，不追远处失联旧记忆。

`LocalArmyContractHarness` 与 `test_local_army.py` 的中间专项支持上述边界：旧冻结JAR七场中六场目标场景预期失败、单团保留场通过；局部控兵中间快照七场通过。包括双前线、远接触不抢旧局部目标、无敌情前沿推进、无前沿可见远接触、UNKNOWN/潜水负证据/工程师隔离、原生拒绝后的轮转重试、各队无进展观察。专项证据在 `local-army/logs/http-old` 与 `http-final`；其中间 JAR 不能代替最终合流候选。

## 4. 期待三：已有高余额时有价值地花钱

大局余额从3054.5增至222397。大量后段军队已达目标，三座T2陆军工厂空闲；消费出口仅普通坦克和重坦，继续多造厂或增加 cap 不能直接解释为有效投资。

| raw 行 | 游戏绝对时间 ms | 余额 | 观察武装数 / 当时target | 机制 |
| --- | ---: | ---: | --- | --- |
| 4938 | 1154679 | 11475 | 66 / 94 | 两座空队列厂，重炮菜单可购 |
| 6504 | 1449369 | 21705 | 97 / 96（容量6405） | 三座厂，两座空闲 |
| 7514 | 1646124 | 63126.5 | 97 / 92（容量7391） | 三座厂全部空闲 |
| 8386 / 8424 | 1801569 / 1809729 | 103017.5 / 102178 | 95 / 96 | 缺1槽仍两次选升矿 |
| 9825 / 9859 | 2072674 / 2081604 | 159165 / 158251 | 93 / 94 | 缺1槽仍两次选升矿 |
| 12526 | 末尾窗口 | 219018.5 | activeArmy=93 | STRATEGY_ARMY_TARGET_REACHED |
| 12587 | 2507954 | 222397 | 97 / 96 | 三座厂全部空闲 |

按相邻自身观察的前一区间计数，753.555 / 2401.120游戏秒军力达到当时target；没有把未观察待产算入，不能冒充完整 committed 上限占额证明。余额>10000、闲厂且观察军力低于target的菜单有87次。末尾模型收入268.3/s、近窗口生产消费85.3/s；16次升矿花22400，单位生产花125400。

选择两个有界出口：

1. 接近target、收入>已观察生产消费，且支付本次原生矿升级价与全部储备后仍有钱包缓冲时，推迟新升矿。接近定义 `deficit<=max(2,ceil(target*0.1))`，缓冲 `max(30秒模型收入,2次普通原生补兵报价)`；保留旧 activeForce、安全与回本条件。真实大军力缺口、报价未知或钱包未达到门槛时不强行宣称饱和。
2. 在现有军力槽内买少量 `heavyArtillery` 补围城角色。额度 `min(6,target/12)`；至少24普通可用主力、非homeEmergency、当前合法可见SURFACE建筑、空原生队列和 native affordable；购买后保留全部共享储备与两次普通补兵钱。只用当前报价，不以“昂贵即强”代替攻域判断，也不以降低余额当成功标准。

重炮 ready库存和已付费 pending 一起占role quota。原生队列已空、产品尚未观察时仍保留 ghost，并进入普通生产和更早执行的战略采购/施工的硬cap保护；同一新 ready 自身ID只履约一次。出现可用角色不证明具体工厂产地；生产者丢失或180游戏秒未观察时带原因释放。

夹具反例：`old-surplus-http/rich.json` 旧代码只有重坦订单；`targeted/surplus/rich.json` 合流E2接受1笔4700重炮，再保留正常补兵。4700故意不同于冻结3100，证明不能硬编码价格。原有2重炮、target40、两个生产者；原队列清空后产品延迟3次观察出现，只应买1个角色。

`old-surplus-http/mine_saturated.json` 旧代码在8000/16000/24000ms各接受1400升矿，总4200。最初动态target场景后期target增到52，合法出现大缺口；不能为通过测试改变政策。最终仅此场景设置真实hard40、39普通军力，使全过程确实接近target；保留零矿订单断言并核对所有capacity.target=40。`fixed-surplus-http/mine_saturated.json` 的合流单场没有矿订单，并显式记录饱和拒绝。

`old-surplus-http/hard_slot.json` 旧代码仅买1重坦；`targeted/surplus/hard_slot.json` 合流E2只买1重炮。127普通军力、hard128，最后槽已付费但未观察成品时也不允许普通补兵追加。其他夹具覆盖native不可负担、仅AIR、普通军力23、基地紧急；这些测试支持执行与预算边界，不证明重炮改善真实胜率。

## 5. 资料来源与数值边界

`G:\deepseek 工作台\游戏资料\20_整理与结构化资料\README.md` 最后段明确：结构化不提高原来源可信度；具体值回原始来源、ERRATA，并优先验证冻结版本。

`星星版铁锈机制库_清洗转写.txt` SHA256 `dc4380a7cb6c9a4f036a888831d06a476ee3517efb2d5ec0f792772ab41d5254`：行34/38为重型火炮伤害概述，行222速度0.6；行169–189为矿与制造仪回本概述。行137是火炮机甲34，不是重型火炮满续兵价格。社区资料只为挑选角色提供方向，没有作为当前原生报价或胜率证据。

冻结原版 `G:\deepseek 工作台\游戏环境\rustedwarfare PC 1.15 原版\assets\units\tanks\heavy_artillery.ini` SHA256 `a03d396d96a56c6d942a916abff49f09b2e7dcf7220f05ab5f2f5fcfb31be22c`：行11 price3100、行12 hp600、行56–59攻域、行65 range310、行139–140 LAND/speed0.6。可攻地面，不可攻空中与水下；静态配置不能推断隐藏敌人实例状态或原生实战收益。下单仍由实时原生菜单价和既有兼容/接近守卫约束。

本轮所有建队、配额、缓存、现金缓冲和等待数值均为公开可测试的初始工程阈值。原始局的地图、难度、出生前人工操作、随机性未记录项保留UNKNOWN；单局结果、E2顺序闭合、观察库存或提高支出均不自动提升为因果收益结论。
