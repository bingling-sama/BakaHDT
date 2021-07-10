package cn.booling.bakahdt;

public class Main {
    public static void main(String[] args) {
        BakaConfig.BAKA.login();
        assert Secret.TWEAKER_GROUP != null;
        Secret.TWEAKER_GROUP.sendMessage(Secret.BOT_NAME + "正在待命");
        BakaConfig.BAKA_CHANNEL.registerListenerHost(new BakaListener());
    }
}
