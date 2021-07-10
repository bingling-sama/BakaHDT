package cn.booling.bakahdt;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class TextFields {
    public static final Message INFO = new MessageChainBuilder()
            .append(Secret.BOT_NAME + "是由[冰凌sama]为Minecraft魔改交流群开发的QQ bot，由[雾之湖上的小冰精]友情提供Server\n")
            .append("GitHub仓库地址：https://github.com/bingling-sama/BakaHDT/\n")
            .append("有任何意见或建议(或整活的idea)都可以来发issues。")
            .asMessageChain();

    public static final Message HELP = new MessageChainBuilder()
            .append(Secret.BOT_NAME + "指令一览：\n")
            .append("&ping  测试bot是否存活\n")
            .append("&help  显示此帮助信息\n")
            .append("&info  显示bot信息\n")
            .append("&rules  显示群规\n")
            .asMessageChain();

    public static final Message RULES = new MessageChainBuilder()
            .append("《Minecraft魔改交流群 群规》请熟读并背诵()\n")
            .append("1.不允许多次加群，超过[3次]将被加入本群黑名单;\n")
            .append("2.禁止以任何形式发布有关 [键政/脏话(除了\"草\")/商业广告/非私人服务器宣传] 的消息;\n")
            .append("3.禁止以任何形式发布R18等[限制级]内容，擦边球也不行;\n")
            .append("4.禁止以任何形式发布付费资源;\n")
            .append("5.看Wiki;\n")
            .append("6.看Wiki(重要的事情说三遍);\n")
            .append("7.看Wiki(第一次警告，第二次十分钟，第三次飞机票);\n")
            .append("8.群内大佬不是神，拒绝伸手党和随意@;\n")
            .append("更多群规详见群公告...")
            .asMessageChain();

    public static final Message MEMBER_JOIN_TIP = new MessageChainBuilder()
            .append(" 欢迎加入Minecraft魔改交流群，进群请先阅读所有置顶公告。提问请携带尽可能多的相关信息\n")
            .append("Discord群：https://discord.gg/sB9PhGcutE/\n")
            .append("CRT等魔改类模组错误还需附带脚本内容和输出LOG。\n")
            .append("-----------------\n")
            .append("能解决你大部分疑惑的视频:\n" +
                    "https://b23.tv/Qu6aAY\n" +
                    "https://b23.tv/d2brHg\n")
            .append("-----------------\n")
            .append("本群会定期清理长期不上线的人。\n" +
                    "本群允许分享整合包(私人整合包自建整合包领域服个人服务器包)。\n")
            .append("-----------------\n")
            .append("群内分享的代码片段、音效、材质等资源，使用协议和最终解释权归[发布者]，[商业使用]请提前咨询以避免踩雷。\n")
            .append("-----------------\n")
            .asMessageChain();
}
