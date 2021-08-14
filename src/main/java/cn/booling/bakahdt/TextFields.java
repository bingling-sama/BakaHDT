package cn.booling.bakahdt;

import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class TextFields {
    public static final String IDENTIFIER = "&";

    public static final MessageChain INFO = new MessageChainBuilder()
            .append(Secret.BOT_NAME + "是由[冰凌sama]为Minecraft魔改交流群开发的QQ bot，由[DRAMING]友情提供Server\n")
            .append("GitHub仓库地址：https://github.com/bingling-sama/BakaHDT/\n")
            .append("有任何意见或建议(或整活的idea)都可以来发issues。")
            .asMessageChain();

    public static final MessageChain RULES = new MessageChainBuilder()
            .append("《Minecraft魔改交流群 群规》请熟读并背诵()\n")
            .append("1.不允许多次加群，超过[3次]将被加入本群黑名单;\n")
            .append("2.禁止以任何形式发布有关 [键政/商业广告/非私人服务器宣传] 的消息;\n")
            .append("3.禁止以任何形式发布R18等[限制级]内容，擦边球也不行;\n")
            .append("4.禁止以任何形式发布付费资源;\n")
            .append("5.看Wiki(真的有很多问题看Wiki就能解决);\n")
            .append("6.看Wiki(第一次警告，第二次十分钟，第三次飞机票);\n")
            .append("7.看Wiki(重要的事情说三遍);\n")
            .append("8.群内大佬不是神，拒绝伸手党和随意@;\n")
            .append("更多群规详见群公告...")
            .asMessageChain();

    public static final MessageChain MEMBER_JOIN_TIP = new MessageChainBuilder()
            .append(" 欢迎加入Minecraft魔改交流群，进群请先阅读所有置顶公告。提问请携带尽可能多的相关信息\n")
            .append("Discord群：https://discord.gg/sB9PhGcutE/\n")
            .append("CRT等魔改类模组错误还需附带脚本内容和输出LOG。\n")
            .append("详见&ask&pastebin&log\n")
            .append("-----------------\n")
            .append("能解决你大部分疑惑的视频:\n" +
                    "https://b23.tv/Qu6aAY\n" +
                    "https://b23.tv/d2brHg\n")
            .append("-----------------\n")
            .append("本群会不定期清理长期不发言的人。\n" +
                    "本群允许分享整合包(私人整合包自建整合包领域服个人服务器包)。\n")
            .append("-----------------\n")
            .append("群内分享的代码片段、音效、材质等资源，使用协议和最终解释权归[发布者]，[商业使用]请提前咨询以避免踩雷。\n")
            .append("-----------------\n")
            .asMessageChain();

    public static final MessageChain ASK = new MessageChainBuilder()
            .append("提问的正确方式：\n")
            .append("简明清晰地描述你的游戏版本&遇到的问题\n")
            .append("魔改问题请将[crafttweaker.log]和log中提到/你认为可能出错的代码使用pastebin发送到群内\n")
            .append("(详见&pastebin)")
            .asMessageChain();

    public static final MessageChain PASTEBIN = new MessageChainBuilder()
            .append("Ubuntu Pastebin使用介绍：\n")
            .append("1.打开https://pastebin.ubuntu.com/\n")
            .append("2.填写Poster(随便写)\n")
            .append("3.将信息复制到Content框内\n")
            .append("4.点击Paste!\n")
            .append("5.复制网址栏网址发送至群内")
            .asMessageChain();

    public static MessageChain HELP = new MessageChainBuilder()
            .append(Secret.BOT_NAME + "指令一览：\n")
            .asMessageChain();

    public static final MessageChain LOG = new MessageChainBuilder()
            .append(Image.fromId("{F1B925A7-72C1-1666-B861-E002C4492E59}.jpg"))
            .append("关于log：\n")
            .append("首先在[.minecraft]文件夹找到[crafttweaker.log]和[.minecraft/logs]文件夹下的[debug.log]文件\n")
            .append("如果自己百度/Bing/Google无果实在无法解决，请使用 pastebin 将文件内容发送到群内等待群友/管理解答(详见 &ask 和 &pastebin)\n")
            .asMessageChain();

    public static final MessageChain CT_COMMANDS = new MessageChainBuilder()
            .append("CraftTweaker 可用的部分实用指令：\n")
            .append("/ct hand 输出玩家手上物品的 ID / 矿辞 (1.12-) / 标签 (1.14+) 等信息，你可以点击有关信息将其复制到剪贴板里。\n")
            .append("/ct syntax 检查脚本的语法是否准确。注意语法正确也不代表一定能运行的符合预期，此指令仅能检查语法问题，无法检查逻辑问题。\n")
            .append("/ct log 打开 CrT 日志文件，请配合 &pastebin 发送。\n")
            .append("/ct inventory 输出玩家物品栏的所有物品的 ID。\n")
            .append("/ct conflict 打印所有冲突配方和其总数。 (仅限工作台和熔炉)\n")
            .append("/reload (1.14+ 可用) 重载脚本。但 CoT 脚本不可重载。\n")
            .asMessageChain();

    public static final MessageChain MT_COMMANDS = new MessageChainBuilder()
            .append("MineTweaker 可用的部分实用指令: \n")
            .append("/mt hand 输出玩家手上物品的 ID 等信息，你可以点击有关信息将其复制到剪贴板里。\n")
            .append("/mt inventory 输出玩家物品栏的所有物品的 ID。\n")
            .append("/mt oredict 打印所有游戏内存在的矿辞。 \n")
            .append("/mt liquids 输出游戏内所有流体的注册 ID。")
            .append("/mt blocks 可以输出游戏内所有方块的注册ID。\n")
            .append("/mt blockinfo 开启时左右键可查看方块数据，再次输入指令以关闭。\n")
            .append("/mt reload 重载脚本。\n")
            .asMessageChain();

    public static final MessageChain WHY_VSCODE = new MessageChainBuilder()
            .append("VisualStudio Code(以下简称VSCode) 是当下编写 ZenScripts 代码的最佳选择之一, 也是本群极力推荐的 文本编辑工具。")
            .append("为什么? 请花半分钟阅读以下理由: \n")
            .append("VSCode 是一款由微软维护的, 功能十分丰富, 用户社区和插件社区极其发达的文本编辑器。\n")
            .append("对于 ZenScripts 这种脚本语言, 是最佳的编写工具之一, 安装了插件后, 可以提供代码高亮和语法纠错, NBT 预览和编辑, Language 文件高亮等等功能。\n")
            .append("就算对于其他脚本语言, 甚至程序设计语言, 其都是一个值得考虑的选择。\n")
            .append("想要下载并安装 VSCode, 前往 https://code.visualstudio.com/download, 下载适合自己系统的安装包安装即可。\n")
            .append("如果需要安装 ZenScripts 高亮插件, 请参考 https://github.com/GBLodb/GBLodb/blob/master/personalModpackDevExperience.md。\n")
            .asMessageChain();

    public static final MessageChain USEFUL_LINKS = new MessageChainBuilder()
            .append("实用链接:\n")
            .append("CraftTweaker *官方英文文档*: https://docs.blamejared.com/\n")
            .append("MineTweaker 官方 Wiki: http://minetweaker3.powerofbytes.com/wiki/Main_Page/\n")
            .append("TweakerGroup Discord: https://discord.gg/4XsUtUfDFt/\n")
            .append("BlameJared Discord: https://discord.blamejared.com/\n")
            .append("Ubuntu Pastebin: https://paste.ubuntu.com/\n")
            .append("Hastebin (DimDev): https://paste.dimdev.org/\n")
            .append("CurseForge Minecraft Mods: https://www.curseforge.com/minecraft/mc-mods/\n")
            .asMessageChain();
}
