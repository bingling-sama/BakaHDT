package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import net.mamoe.mirai.event.events.MessageEvent;

public class PingCommand extends CommandManager.BaseCommand {
    public static final PingCommand INSTANCE = new PingCommand();

    private PingCommand() {
        super("ping");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage("Pong!");
    }
}
