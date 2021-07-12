package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.events.MessageEvent;

public class RulesCommand extends CommandManager.BaseCommand {
    public static final RulesCommand INSTANCE = new RulesCommand();

    private RulesCommand() {
        super("rules");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage(TextFields.RULES);
    }
}
