package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.events.MessageEvent;

public class InfoCommand extends CommandManager.BaseCommand {
    public static final InfoCommand INSTANCE = new InfoCommand();

    private InfoCommand() {
        super("info");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage(TextFields.INFO);
    }
}
