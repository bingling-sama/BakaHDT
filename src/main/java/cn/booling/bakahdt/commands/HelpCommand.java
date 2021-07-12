package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.events.MessageEvent;

public class HelpCommand extends CommandManager.BaseCommand {
    public static final HelpCommand INSTANCE = new HelpCommand();

    private HelpCommand() {
        super("help");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage(TextFields.HELP);
    }
}
