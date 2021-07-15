package cn.booling.bakahdt;

import cn.booling.bakahdt.util.TriConsumer;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AllCommands {
    public static void init(TriConsumer<String, String, Consumer<MessageEvent>> registerCustomCommand) {
        TriConsumer.createChainCaller(registerCustomCommand)
                .accept("ping", "测试 bot 是否存活", (e) -> simpleReply(e, "Pong!"))
                .accept("help", "显示此帮助信息", (e) -> simpleReply(e, TextFields.HELP))
                .accept("ctcommands", "显示 CrT 实用指令", e -> simpleReply(e, TextFields.CT_COMMANDS))
                .accept("info", "显示 bot 信息", (e) -> simpleReply(e, TextFields.INFO))
                .accept("rules", "显示群规", (e) -> simpleReply(e, TextFields.RULES))
                .accept("ask", "教 你 提 问", (e) -> simpleReply(e, TextFields.ASK))
                .accept("pastebin", "Ubuntu Pastebin 使用方法", (e) -> simpleReply(e, TextFields.PASTEBIN))
                .accept("log", "显示 log 帮助", (e) -> simpleReply(e, TextFields.LOG));
    }

    public static void simpleReply(@NotNull MessageEvent event, String message) {
        event.getSubject().sendMessage(message);
    }

    public static void simpleReply(@NotNull MessageEvent event, Message message) {
        event.getSubject().sendMessage(message);
    }
}
