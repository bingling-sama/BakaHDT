package cn.booling.bakahdt;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.utils.BotConfiguration;

public class BakaConfig {
    public static final Bot BAKA = BotFactory.INSTANCE.newBot(
            Secret.BOT_ID,
            Secret.BOT_PWD,
            new BotConfiguration(){{
                fileBasedDeviceInfo();
                autoReconnectOnForceOffline();
                setProtocol(MiraiProtocol.ANDROID_PHONE);
            }}
    );
    public static final EventChannel<BotEvent> BAKA_CHANNEL = BAKA.getEventChannel();
}
