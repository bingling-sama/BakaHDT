package cn.booling.bakahdt;

import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;

public class AllCommands {
    public static void init() {
        Command.registerCommand("ping", "测试bot是否存活", (e) -> simpleReply(e, "Pong!"));
        Command.registerCommand("help", "显示此帮助信息", (e) -> simpleReply(e, TextFields.HELP));
        Command.registerCommand("info", "显示bot信息", (e) -> simpleReply(e, TextFields.INFO));
        Command.registerCommand("rules", "显示群规", (e) -> simpleReply(e, TextFields.RULES));
        Command.registerCommand("ask", "教 你 提 问", (e) -> simpleReply(e, TextFields.ASK));
        Command.registerCommand("pastebin", "Ubuntu Pastebin使用方法", (e) -> simpleReply(e, TextFields.PASTEBIN));
        Command.registerCommand("log", "显示log帮助", (e) -> simpleReply(e, TextFields.LOG));
    }

    public static void simpleReply(@NotNull MessageEvent event, String message) {
        event.getSubject().sendMessage(message);
    }

    public static void simpleReply(@NotNull MessageEvent event, Message message) {
        event.getSubject().sendMessage(message);
    }
}
