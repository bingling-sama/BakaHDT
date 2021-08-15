package cn.booling.bakahdt

import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.Image.Key.fromId

object TextFields {
    const val IDENTIFIER = "&"
    val INFO = MessageChainBuilder()
        .append(
            """
            ${BOT_NAME}是由[冰凌sama]为Minecraft魔改交流群开发的QQ bot，由[科星]友情提供Server
            GitHub仓库地址：https://github.com/bingling-sama/BakaHDT/
            有任何意见或建议(或整活的idea)都可以来发issues。
            """.trimIndent()
        )
        .asMessageChain()
    val RULES = MessageChainBuilder()
        .append(
            """
            《Minecraft魔改交流群 群规》请熟读并背诵()
            1.不允许多次加群，超过[3次]将被加入本群黑名单;
            2.禁止以任何形式发布有关 [键政/商业广告/非私人服务器宣传] 的消息;
            3.禁止以任何形式发布R18等[限制级]内容，擦边球也不行;
            4.禁止以任何形式发布付费资源;
            5.看Wiki(真的有很多问题看Wiki就能解决);
            6.看Wiki(第一次警告，第二次十分钟，第三次飞机票);
            7.看Wiki(重要的事情说三遍);
            8.群内大佬不是神，拒绝伸手党和随意@;
            更多群规详见群公告...
        """.trimIndent()
        )
        .asMessageChain()
    val MEMBER_JOIN_TIP = MessageChainBuilder()
        .append(
            """
            欢迎加入Minecraft魔改交流群，进群请先阅读所有置顶公告。提问请携带尽可能多的相关信息
            Discord群：https://discord.gg/sB9PhGcutE/
            CRT等魔改类模组错误还需附带脚本内容和输出LOG。
            详见&ask&pastebin&log
            -----------------
            能解决你大部分疑惑的视频:
            https://b23.tv/Qu6aAY
            https://b23.tv/d2brHg
            -----------------
            本群会不定期清理长期不发言的人。
            本群允许分享整合包(私人整合包自建整合包领域服个人服务器包)。
            -----------------
            群内分享的代码片段、音效、材质等资源，使用协议和最终解释权归[发布者]，[商业使用]请提前咨询以避免踩雷。
            -----------------
        """.trimIndent()
        )
        .asMessageChain()
    val ASK = MessageChainBuilder()
        .append(
            """
            提问的正确方式：
            简明清晰地描述你的游戏版本&遇到的问题
            魔改问题请将[crafttweaker.log]和log中提到/你认为可能出错的代码使用pastebin发送到群内
            (详见&pastebin)
        """.trimIndent()
        ).asMessageChain()
    val PASTEBIN = MessageChainBuilder()
        .append(
            """
            Ubuntu Pastebin使用介绍：
            1.打开https://pastebin.ubuntu.com/
            2.填写Poster(随便写)
            3.将信息复制到Content框内
            4.点击Paste!
            5.复制网址栏网址发送至群内
        """.trimIndent()
        )
        .asMessageChain()
    var HELP = MessageChainBuilder()
        .append(
            """
            ${BOT_NAME}指令一览：
        """.trimIndent()
        )
        .asMessageChain()
    val LOG: MessageChain = MessageChainBuilder()
        .append(fromId("{F1B925A7-72C1-1666-B861-E002C4492E59}.jpg"))
        .append(
            """
            关于log：
            首先在[.minecraft]文件夹找到[crafttweaker.log]和[.minecraft/logs]文件夹下的[debug.log]文件
            如果自己百度/Bing/Google无果实在无法解决，请使用 pastebin 将文件内容发送到群内等待群友/管理解答(详见 &ask 和 &pastebin)
        """.trimIndent()
        )
        .asMessageChain()
    val CT_COMMANDS = MessageChainBuilder()
        .append(
            """
            CraftTweaker 可用的部分实用指令：
            /ct hand 输出玩家手上物品的 ID / 矿辞 (1.12-) / 标签 (1.14+) 等信息，你可以点击有关信息将其复制到剪贴板里。
            /ct syntax 检查脚本的语法是否准确。注意语法正确也不代表一定能运行的符合预期，此指令仅能检查语法问题，无法检查逻辑问题。
            /ct log 打开 CrT 日志文件，请配合 &pastebin 发送。
            /ct inventory 输出玩家物品栏的所有物品的 ID。
            /ct conflict 打印所有冲突配方和其总数。 (仅限工作台和熔炉)
            /reload (1.14+ 可用) 重载脚本。但 CoT 脚本不可重载。
        """.trimIndent()
        )
        .asMessageChain()
    val MT_COMMANDS = MessageChainBuilder()
        .append(
            """
            MineTweaker 可用的部分实用指令: 
            /mt hand 输出玩家手上物品的 ID 等信息，你可以点击有关信息将其复制到剪贴板里。
            /mt inventory 输出玩家物品栏的所有物品的 ID。
            /mt oredict 打印所有游戏内存在的矿辞。
            /mt liquids 输出游戏内所有流体的注册 ID。
            /mt blocks 可以输出游戏内所有方块的注册ID。
            /mt blockinfo 开启时左右键可查看方块数据，再次输入指令以关闭。
            /mt reload 重载脚本。
        """.trimIndent()
        )
        .asMessageChain()
    val WHY_VSCODE = MessageChainBuilder()
        .append(
            """
            VisualStudio Code(以下简称VSCode) 是当下编写 ZenScripts 代码的最佳选择之一, 也是本群极力推荐的 文本编辑工具。
            为什么? 请花半分钟阅读以下理由:
            VSCode 是一款由微软维护的, 功能十分丰富, 用户社区和插件社区极其发达的文本编辑器。
            对于 ZenScript 这种脚本语言, 是最佳的编写工具之一, 安装了插件后, 可以提供代码高亮和语法纠错, NBT 预览和编辑, Language 文件高亮等等功能。
            就算对于其他脚本语言, 甚至程序设计语言, 其都是一个值得考虑的选择。
            想要下载并安装 VSCode, 前往 https://code.visualstudio.com/download, 下载适合自己系统的安装包安装即可。
            如果需要安装 ZenScript 高亮插件, 请参考 https://github.com/GBLodb/GBLodb/blob/master/personalModpackDevExperience.md。
        """.trimIndent()
        )
        .asMessageChain()
    val USEFUL_LINKS = MessageChainBuilder()
        .append(
            """
            实用链接:
            CraftTweaker *官方英文文档*: https://docs.blamejared.com/
            MineTweaker 官方 Wiki: http://minetweaker3.powerofbytes.com/wiki/Main_Page/
            TweakerGroup Discord: https://discord.gg/4XsUtUfDFt/
            BlameJared Discord: https://discord.blamejared.com/
            Ubuntu Pastebin: https://paste.ubuntu.com/
            Hastebin (DimDev): https://paste.dimdev.org/
            CurseForge Minecraft Mods: https://www.curseforge.com/minecraft/mc-mods/
        """.trimIndent()
        )
        .asMessageChain()
}