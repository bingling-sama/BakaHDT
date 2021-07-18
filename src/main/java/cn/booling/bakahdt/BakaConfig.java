package cn.booling.bakahdt;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.utils.BotConfiguration;

public class BakaConfig {
    public static final Bot BAKA = BotFactory.INSTANCE.newBot(
            Secret.BOT_ID,
            Secret.BOT_PWD,
            new BotConfiguration() {{
                fileBasedDeviceInfo();
                autoReconnectOnForceOffline();
                setProtocol(MiraiProtocol.ANDROID_PHONE);
            }}
    );
    public static final EventChannel<BotEvent> BAKA_CHANNEL = BAKA.getEventChannel();
    public static final EventChannel<Event> TWEAKER_CHANNEL = GlobalEventChannel.INSTANCE.filter(ev -> ev instanceof GroupEvent && ((GroupEvent) ev).getGroup().getId() == 624487948L);
}
