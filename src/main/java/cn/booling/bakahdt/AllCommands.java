package cn.booling.bakahdt;

import cn.booling.bakahdt.util.TriConsumer;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;

public class AllCommands {
    public static void init(TriConsumer<String, String, Consumer<MessageEvent>> registerCustomCommand) {
        TriConsumer.createChainingCaller(registerCustomCommand)
                .accept("ping", "测试 bot 是否存活", (e) -> simpleReply(e, "Pong!"))
                .accept("help", "显示此帮助信息", (e) -> simpleReply(e, TextFields.HELP))
                .accept("ctcommands", "显示 CrT 实用指令", e -> simpleReply(e, TextFields.CT_COMMANDS))
                .accept("mtcommands", "显示 CrT 实用指令", e -> simpleReply(e, TextFields.MT_COMMANDS))
                .accept("info", "显示 bot 信息", (e) -> simpleReply(e, TextFields.INFO))
                .accept("rules", "显示群规", (e) -> simpleReply(e, TextFields.RULES))
                .accept("ask", "教 你 提 问", (e) -> simpleReply(e, TextFields.ASK))
                .accept("pastebin", "Ubuntu Pastebin 使用方法", (e) -> simpleReply(e, TextFields.PASTEBIN))
                .accept("log", "显示 log 帮助", (e) -> simpleReply(e, TextFields.LOG))
                .accept("whyvscode", "为什么要用 VSCode?", (e) -> simpleReply(e, TextFields.WHY_VSCODE))
                .accept("links", "实用链接大赏", (e) -> simpleReply(e, TextFields.USEFUL_LINKS))
                .accept("jrrp", "获取今日随机人品值[roll 0-100]", AllCommands::jrrp)
        ;
    }

    public static void jrrp(@NotNull MessageEvent event) {
        Random random = new Random((int) (Integer.parseInt((new SimpleDateFormat("yyMMdd")).format(new Date())) ^ event.getSender().getId()));
        simpleReply(event, event.getSenderName() + "今天的人品值是：" + random.nextInt(101));
    }

    public static void simpleReply(@NotNull MessageEvent event, String message) {
        event.getSubject().sendMessage(message);
    }

    public static void simpleReply(@NotNull MessageEvent event, Message message) {
        event.getSubject().sendMessage(message);
    }
}
