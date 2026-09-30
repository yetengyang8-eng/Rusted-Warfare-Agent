# 跨环境接手入口

本仓库是 G:\deepseek 工作台 的工程中转仓库。以后任务统一从 **RW-BASELINE-2026-09-30-GS-v1** 开始。

## 阅读顺序

1. [BASELINE.md](project-state/BASELINE.md)：固定源码、运行件和证据边界。
2. [baseline-manifest.json](project-state/baseline-manifest.json)：机器可读身份。
3. [CURRENT_STATE.md](project-state/CURRENT_STATE.md)：当前进度。
4. [NEXT_STAGE_PLAN.md](project-state/NEXT_STAGE_PLAN.md)：下一阶段依赖、交付与验收标准。
5. [最新桌面证据](evidence/global-strategy-desktop-2026-09-30/README.md) 和 [本轮基线核验](evidence/baseline-2026-09-30/VALIDATION.md)。

基线生产输入已保存于 6214f07；最终运行件为 astra-deliveries/global-strategy-2026-09-30/rw-agent-bootstrap.jar（SHA256 76711a8e…）。交付来源声明 2f310ec 的原始 Git 对象在本地缺失，不能用未解析的提交代替可取得的基线。

最新三场桌面长局均 PARTIAL/ONGOING，Spain A/B 的方差是下一阶段首要诊断对象。旧交付说明“未桌面验证”只代表历史时点；不得覆盖后补桌面证据，也不得改写旧 manifest 破坏原始 SHA。

## 执行边界

阅读仓库 AGENTS.md。先做只读基线核对，再在隔离目录构建和运行授权验证。不要从历史 developer 树或旧解压包起步，不覆盖用户环境。

无画面引擎准备入口仍为 [astra-relay/HEADLESS_ENGINE_README.md](astra-relay/HEADLESS_ENGINE_README.md)。引擎包存在不授权读取迷雾隐藏动态信息。直接远程回写流程见 [ASTRA_WRITEBACK.md](ASTRA_WRITEBACK.md)，是否发布按当前任务授权执行。

知识、历史对话、Replay 语料入口分别为 knowledge/README.md、project-history/raw-dialogue/README.md 和 REPLAY_CORPUS_README.md；它们不定义当前候选。
