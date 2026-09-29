# Astra 直接回写 GitHub 的建议方式

上一轮 Astra 能读公开仓库，但因没有写入凭据无法 `git push`。这不是仓库结构问题，而是 Astra 运行环境没有 GitHub 写权限。

## 首选：GitHub 连接 / OAuth
在运行 Astra Work 的 ChatGPT 环境中连接 GitHub，并授权账号 `yetengyang8-eng` 的 `Rusted-Warfare-Agent` 仓库。若连接器提供写操作，让 Astra：
1. 从最新 `main` 建 `astra/<task>-YYYYMMDD` 分支；
2. 在该分支 commit + push；
3. 最好创建 PR，不直接 force-push `main`；
4. 交接时给 branch、commit SHA、验证结果和未完成项。

这样不需要用户手工下载再上传 ZIP，也不需要把密钥发进对话。

## 次选：短期 fine-grained PAT
只有 Astra 环境无法使用 OAuth/GitHub 连接时才考虑。Token 应只授权这一个仓库、设置短有效期，只给 `Contents: Read and write`（需要 PR 时再加对应权限）。把 token 放进 Astra 执行环境的 secret/credential 注入位置，**不要粘贴到聊天正文、仓库文件、日志或补丁中**；任务结束后立即撤销。

## 无写权限时的回退
继续使用当前交付格式：源码 patch + JAR + evidence + handoff ZIP。ChatGPT/本机中转负责把它落为 GitHub 分支。当前最新候选已经按这种方式推到 `astra/recon-execution-20260929`。
