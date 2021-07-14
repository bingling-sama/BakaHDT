package cn.booling.bakahdt;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BakaListener extends SimpleListenerHost {
    public BakaListener() {
    }

    @EventHandler
    public static void onMessage(@NotNull MessageEvent event) {
        if (event.getMessage().contentToString().contains(TextFields.IDENTIFIER)) {
            parseCommand(event);
        }
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

    public static void parseCommand(@NotNull final MessageEvent event) {
        String message = event.getMessage().contentToString();
        Queue<Command> cmds = new ArrayDeque<>();
        // 匹配Command
        Matcher matcher = Pattern.compile("&.*?\\s|&.*$").matcher(message);
        matcher.results().forEach(
                matchResult -> cmds.add(Command.commands.get(matchResult.group().replace("&", "").replace(" ", "")))
        );
        // 执行Command
        for (Command cmd : cmds) {
            cmd.consumer.accept(event);
        }
    }
}
