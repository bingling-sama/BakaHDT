package cn.booling.bakahdt.version;

import cn.booling.bakahdt.util.Util;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author youyihj
 */
public class ModVersion implements Comparable<ModVersion>, Serializable {
    private final String mcVersion;
    private final String modVersion;
    private final String downloadLink;
    private final ReleaseType releaseType;

    private static final long serialVersionUID = 114514L;

    public ModVersion(String mcVersion, String modVersion, String downloadLink, int releaseType) {
        this.mcVersion = mcVersion;
        this.modVersion = modVersion;
        this.downloadLink = downloadLink;
        this.releaseType = ReleaseType.getType(releaseType);
        if (!Arrays.stream(modVersion.split("\\.")).allMatch(Util::isValidNumber)) {
            throw new IllegalArgumentException("Invalid Mod Version: " + modVersion);
        }
    }

    public String getMcVersion() {
        return mcVersion;
    }

    public String getModVersion() {
        return modVersion;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public ReleaseType getReleaseType() {
        return releaseType;
    }

    @Override
    public int compareTo(@NotNull ModVersion o) {
        return Arrays.compare(this.getNumberVersion(), o.getNumberVersion());
    }

    private int[] getNumberVersion() {
        return Arrays.stream(modVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModVersion that = (ModVersion) o;
        return Objects.equals(mcVersion, that.mcVersion) && Objects.equals(modVersion, that.modVersion) && Objects.equals(downloadLink, that.downloadLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mcVersion, modVersion, downloadLink);
    }

    @Override
    public String toString() {
        return "{" +
                "mcVersion='" + mcVersion + '\'' +
                ", modVersion='" + modVersion + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", releaseType=" + releaseType +
                '}';
    }
}
