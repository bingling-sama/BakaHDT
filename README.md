# 这是甚么 | What's this

为Minecraft魔改交流群定制的QQ bot   
~~拯救大佬们的血压~~

## 贡献 | Contribute

### 配置开发环境 | Setup Dev Env

关于本项目的环境搭建问题，如下几点：

- 请使用 JDK11+ & Kotlin1.5.21+
- 请自行配置 Secret 内容

> Secrets 内容格式:   
> BOT_ID: Long (bot账号QQ号)   
> BOT_PWD: String (bot账号QQ密码)   
> BOT_NAME: String (自定义bot名称)   
> ADMIN: MutableList<Long> (bot最高管理员列表,填入管理员QQ号)

### 做出更改 | Make Changes

> `permission.json` 格式：直接填写 QQ 号(MutableList\<Long\>)

> `commands.json` 格式：
>  - `name` 必填
>  - `info` 可选(不填将不会出现在 /help 内)
>  - `permission` 可选(默认为 Member )
>  - `message` 可选(不填用于为内建指令添加 info )(MutableList<String> 类型)

要构建可执行 jar，请执行`gradlew shadowJar`

完成配置后可直接运行 `Main()` 方法测试项目。

## 待办 | TODO

- [x] Move To Kotlin
- [x] Permission System
- [x] Rebuild Command Register System
- [x] Commands Completion
- [x] Member Join Tips
- [ ] CrT Version Checker Fix
- [ ] And More...

## 许可 | License

本项目继承 Mirai 的 AGPL 证书。
