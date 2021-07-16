import cn.booling.bakahdt.network.CrTVersionGetter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String message = "&test &ping &ask &pastebin";
        Matcher matcher = Pattern.compile("&.*?\\s|&.*$").matcher(message);
//        System.out.println(matcher.toMatchResult().group());
//        System.out.println(matcher.results().count());
        matcher.results().forEach(matchResult -> System.out.println(matchResult.group() + "  res"));
        CrTVersionGetter.updateVersion();
        new Thread(() -> {
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(CrTVersionGetter.getVersions());
        }).start();
    }
}
