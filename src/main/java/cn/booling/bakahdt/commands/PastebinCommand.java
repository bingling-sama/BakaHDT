package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import cn.booling.bakahdt.TextFields;
import net.mamoe.mirai.event.events.MessageEvent;

public class PastebinCommand extends CommandManager.BaseCommand {
    public static final PastebinCommand INSTANCE = new PastebinCommand();

    private PastebinCommand() {
        super("pastebin");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(E event) {
        event.getSubject().sendMessage(TextFields.PASTEBIN);
    }
}
