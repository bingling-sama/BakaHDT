package cn.booling.bakahdt;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class BakaListener extends SimpleListenerHost {
    public BakaListener() {}

    @EventHandler
    public static void onMessage(GroupMessageEvent event) {
        String message = event.getMessage().contentToString();
        Contact subject = event.getSubject();

        if (message.contains("&ping")) {
            subject.sendMessage("Pong!");
        }
    }

    @EventHandler
    public static void onMemberJoin(MemberJoinEvent event) {
        MessageChain message = new MessageChainBuilder()
                .append(new At(event.getMember().getId()))
                .append(" 欢迎加入Minecraft魔改交流群，进群请先阅读所有置顶公告。")
                .append(" 提问请携带尽可能多的相关信息")
                .asMessageChain();
        event.getGroup().sendMessage(message);
        event.getGroup().sendMessage(message);
        event.getGroup().sendMessage(message);
    }
}
