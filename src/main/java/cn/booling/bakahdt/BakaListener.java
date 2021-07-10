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
        } if (message.contains("&test")) {
            MessageChain reply = new MessageChainBuilder()
                    .append(new At(event.getSender().getId()))
                    .append(TextFields.MEMBER_JOIN_TIP)
                    .asMessageChain();
            event.getGroup().sendMessage(reply);
        }
    }

    @EventHandler
    public static void onMemberJoin(MemberJoinEvent event) {
        MessageChain message = new MessageChainBuilder()
                .append(new At(event.getMember().getId()))
                .append(TextFields.MEMBER_JOIN_TIP)
                .asMessageChain();
        event.getGroup().sendMessage(message);
    }
}