package cn.booling.bakahdt

import net.mamoe.mirai.message.data.*

object TextFields {
    // do not move to [commands.json]
    val INFO = MessageChainBuilder()
        .append(
            """
            ${BOT_NAME}是由[冰凌sama]为Minecraft魔改交流群开发的QQ bot，由[科星]友情提供Server
            GitHub仓库地址：https://github.com/bingling-sama/BakaHDT/
            有任何意见或建议(或整活的idea)都可以来发issues。
            """.trimIndent()
        )
        .asMessageChain()
    var HELP = MessageChainBuilder()
        .append("${BOT_NAME}指令一览：")
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
}
