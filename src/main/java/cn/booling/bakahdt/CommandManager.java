package cn.booling.bakahdt;

import cn.booling.bakahdt.commands.*;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandManager {
    public static Map<String, BaseCommand> commands = new HashMap<>();

    public static <T extends MessageEvent> void parseCommand(@NotNull final T event) {
        String message = event.getMessage().contentToString();
        Queue<BaseCommand> cmds = new ArrayDeque<>();
        Matcher matcher = Pattern.compile("&.*?\\s|&.*$").matcher(message);
        matcher.results().forEach(
                matchResult -> cmds.add(commands.get(matchResult.group().replace("&", "").replace(" ", "")))
        );
        for (BaseCommand cmd : cmds) {
            cmd.executeCommand(event);
        }
    }

    public static abstract class BaseCommand {

        public String primaryName;

        public BaseCommand(String cmd) {
            this.primaryName = cmd;
        }

        public abstract <E extends MessageEvent> void executeCommand(E event);
    }

    public static void init() {
        registerCommand(TestCommand.INSTANCE);
        registerCommand(PingCommand.INSTANCE);
        registerCommand(HelpCommand.INSTANCE);
        registerCommand(AskCommand.INSTANCE);
        registerCommand(RulesCommand.INSTANCE);
        registerCommand(PastebinCommand.INSTANCE);
        registerCommand(InfoCommand.INSTANCE);
    }

    public static <T extends BaseCommand> void registerCommand(T cmd) {
        commands.put(cmd.primaryName, cmd);
    }

}
