package cn.booling.bakahdt;

import cn.booling.bakahdt.listener.BakaListener;
import cn.booling.bakahdt.listener.TweakerListener;
import cn.booling.bakahdt.network.CrTVersionGetter;
import net.mamoe.mirai.contact.Group;

import java.util.Timer;

public class Main {
    public static boolean doCrTVersionChecking = true;
    public static Group tweakerGroup;

    public static void main(String[] args) {
        // registry commands
        AllCommands.init(Command::registerCommand);
        // load CrT version
        CrTVersionGetter.readCachedVersion();
        if (doCrTVersionChecking) {
            new Timer("CrTVersionChecker", true).schedule(new CrTVersionGetter(), 0, 600000);
        }
        // login
        BakaConfig.BAKA.login();
        // init listener
        tweakerGroup = BakaConfig.BAKA.getGroupOrFail(624487948L);
        BakaConfig.BAKA_CHANNEL.registerListenerHost(new BakaListener());
        BakaConfig.TWEAKER_CHANNEL.registerListenerHost(new TweakerListener());
    }
}
