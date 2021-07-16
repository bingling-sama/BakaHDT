package cn.booling.bakahdt.version;

/**
 * @author youyihj
 */
public enum ReleaseType {
    RELEASE("正式版"),
    BETA("Beta 版"),
    ALPHA("Alpha 版");

    private final String displayName;

    public static ReleaseType getType(int code) {
        return values()[code - 1];
    }

    public String getDisplayName() {
        return displayName;
    }

    ReleaseType(String name) {
        this.displayName = name;
    }
}
