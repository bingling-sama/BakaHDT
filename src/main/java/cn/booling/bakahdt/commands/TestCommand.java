package cn.booling.bakahdt.commands;

import cn.booling.bakahdt.CommandManager;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends CommandManager.BaseCommand {
    public static final TestCommand INSTANCE = new TestCommand();

    private TestCommand() {
        super("test");
    }

    @Override
    public <E extends MessageEvent> void executeCommand(@NotNull E event) {
        event.getSubject().sendMessage("Alive!");
    }
}
