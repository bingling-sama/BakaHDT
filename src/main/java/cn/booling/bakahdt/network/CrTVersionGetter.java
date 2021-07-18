package cn.booling.bakahdt.network;


import cn.booling.bakahdt.Main;
import cn.booling.bakahdt.version.ModVersion;
import cn.booling.bakahdt.version.ModVersionCollection;
import cn.booling.bakahdt.version.ModVersionCollectionDeserializer;
import cn.booling.bakahdt.version.ModVersionDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimerTask;

/**
 * @author youyihj
 */
public class CrTVersionGetter extends TimerTask {
    private static final String API_URL = "https://addons-ecs.forgesvc.net/api/v2/addon/239197/files";
    private static ModVersionCollection versions;
    private static final File CACHE = new File("crt_versions.temp");
    public static boolean versionUpdated = false;

    static {
        if (!CACHE.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                CACHE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        updateVersion();
        if (versionUpdated) {
            versionUpdated = false;
            Map<String, SortedSet<ModVersion>> versions = CrTVersionGetter.getVersions().getVersions();
            ModVersion mc1_12_2LatestVersion = versions.get("1.12").last();
            ModVersion mc1_16_5LatestVersion = versions.get("1.16.5").last();
            Message message = new MessageChainBuilder()
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
            Main.tweakerGroup.sendMessage(message);
        }
        System.out.println("CrT Version Checked!");
    }

    public static void updateVersion() {
        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ModVersion.class, new ModVersionDeserializer())
                        .registerTypeAdapter(ModVersionCollection.class, new ModVersionCollectionDeserializer())
                        .create();
                Reader reader = new InputStreamReader(urlConnection.getInputStream());
                ModVersionCollection read = gson.fromJson(reader, ModVersionCollection.class);
                if (!read.equals(versions)) {
                    versionUpdated = true;
                    versions = read;
                    writeVersions();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static ModVersionCollection getVersions() {
        return versions;
    }

    private static void writeVersions() throws IOException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(CACHE))) {
            outputStream.writeObject(versions);
        }
    }

    public static void readCachedVersion() {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(CACHE))) {
            versions = ((ModVersionCollection) inputStream.readObject());
        } catch (ClassNotFoundException | IOException ignored) {

        }
    }
}
