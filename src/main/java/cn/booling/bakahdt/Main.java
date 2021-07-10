package cn.booling.bakahdt;

public class Main {
    public static void main(String[] args) {
        BakaConfig.BAKA.login();
        BakaConfig.BAKA_CHANNEL.registerListenerHost(new BakaListener());
    }
}
