package cn.booling.bakahdt.util;

/**
 * @author youyihj
 */
public final class Util {
    private Util() {}

    public static boolean isValidNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
