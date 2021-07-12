package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.events.MessageEvent;

public class AskCommand extends CommandManager.BaseCommand {
    public static final AskCommand INSTANCE = new AskCommand();

    private AskCommand() {
        super("ask");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage(TextFields.ASK);
    }
}
