# Global Strategy 候选交付

本轮基于 main `4d80b4e253421ef78b081ee27454eaacfe264180`，源码候选为 `2f310ec46a4225eb1ef77efe68d1823904e52717`。生产构建输入为 `ca00a20`；之后的提交仅调整 native E2 fixture 初始化，生产输入未变。

- JAR：本目录 `rw-agent-bootstrap.jar`
- SHA256：`76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`
- contentDigest：`8092b61130c3497b370e12ce249d3ec17e6331d502fefbcb50bbd95a32f03120`
- 完整说明：[最新交接](../../handoff/HANDOFF_Astra_GlobalStrategy_2026-09-30.md)
- 身份、回归与原始证据：[证据入口](../../evidence/global-strategy-2026-09-30/README.md)

交付 ZIP 中 `repository-overlay/` 保持仓库相对路径，另含 `global-strategy-source.patch`。同时单独提供 `source-commits.bundle`，避免把同一份原始证据在 ZIP 中重复两次。JAR 和原始证据 ZIP 不塞进文本源码 patch。不要在有未提交修改的工作目录直接覆盖。

建议将 bundle 导入一个独立分支，保留完整提交与二进制证据：

```bash
git fetch /path/to/source-commits.bundle refs/heads/astra/global-strategy-20260930
git switch -c astra/global-strategy-review FETCH_HEAD
```

也可从基线新建分支后应用源码 patch，再从 overlay 复制 JAR 与 `raw-evidence.zip`：

```bash
git switch -c astra/global-strategy-review 4d80b4e
git apply --check /path/to/global-strategy-source.patch
git apply /path/to/global-strategy-source.patch
```

无 GitHub 写凭据，远端未被修改。当前是待桌面验收的候选，不覆盖稳定 main 或用户安装件。最终原生对局取得 VICTORY，但尚无本轮 Windows、用户桌面或 A/B 胜率结论；跨域海厂闭环与长局大报告证据按中间候选 SHA 分开记录。
