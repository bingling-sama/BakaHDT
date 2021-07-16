package cn.booling.bakahdt;

import cn.booling.bakahdt.network.CrTVersionGetter;
import cn.booling.bakahdt.version.ModVersion;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.Map;
import java.util.SortedSet;

public class Main {
    public static boolean doCrTVersionChecking = true;
    private static Group tweakerGroup;

    public static void main(String[] args) {
        AllCommands.init(Command::registerCommand);
        CrTVersionGetter.readCachedVersion();
        BakaConfig.BAKA.login();
        tweakerGroup = BakaConfig.BAKA.getGroupOrFail(624487948L);
        BakaConfig.BAKA_CHANNEL.registerListenerHost(new BakaListener());
        new Thread(Main::versionWork).start();
    }

    private static void versionWork() {
        while (doCrTVersionChecking) {
            if (System.currentTimeMillis() % (10 * 60 * 1000) == 0) {
                CrTVersionGetter.updateVersion();
            }
            if (CrTVersionGetter.versionUpdated) {
                CrTVersionGetter.versionUpdated = false;
                Map<String, SortedSet<ModVersion>> versions = CrTVersionGetter.getVersions().getVersions();
                ModVersion mc1_12_2LatestVersion = versions.get("1.12").last();
                ModVersion mc1_16_5LatestVersion = versions.get("1.16.5").last();
                MessageChain message = new MessageChainBuilder()
                        .append("CraftTweaker 版本已更新！\n")
                        .append("最新 1.12.2 版本：")
                        .append(mc1_12_2LatestVersion.getModVersion())
                        .append("(")
                        .append(mc1_12_2LatestVersion.getReleaseType().getDisplayName())
                        .append(")\n")
                        .append("下载地址：")
                        .append(mc1_12_2LatestVersion.getDownloadLink())
                        .append("\n")
                        .append("最新 1.16.5 版本：")
                        .append(mc1_16_5LatestVersion.getModVersion())
                        .append("(")
                        .append(mc1_16_5LatestVersion.getReleaseType().getDisplayName())
                        .append(")\n")
                        .append("下载地址：")
                        .append(mc1_16_5LatestVersion.getDownloadLink())
                        .build();
                tweakerGroup.sendMessage(message);
            }
        }
    }
}
