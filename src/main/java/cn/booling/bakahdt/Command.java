package cn.booling.bakahdt;

import net.mamoe.mirai.event.events.MessageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Command {
    public static Map<String, Command> commands = new HashMap<>();

    public String name;
    public String info;
    public Consumer<MessageEvent> consumer;

    private Command(String name, String info, Consumer<MessageEvent> consumer) {
        this.name = name;
        this.info = info;
        this.consumer = consumer;
    }

    public static void registerCommand(String name, String info, Consumer<MessageEvent> consumer) {
        Command cmd = new Command(name, info, consumer);
        commands.put(name, cmd);
        TextFields.HELP = TextFields.HELP.plus("&" + name + "  " + info + "\n");
    }
}
