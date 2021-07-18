package cn.booling.bakahdt.listener;

import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

public class TweakerListener extends SimpleListenerHost {
    public TweakerListener() {
    }

    @EventHandler
    public static void onMemberJoin(@NotNull MemberJoinEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        MessageChain message = new MessageChainBuilder()
                .append(new At(event.getMember().getId()))
                .append(TextFields.MEMBER_JOIN_TIP)
                .asMessageChain();
        event.getGroup().sendMessage(message);
    }
}
