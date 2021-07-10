package cn.booling.bakahdt;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class TextFields {
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
