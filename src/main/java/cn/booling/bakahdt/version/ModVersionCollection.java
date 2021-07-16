package cn.booling.bakahdt.version;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class ModVersionCollection implements Serializable {
    private final Map<String, SortedSet<ModVersion>> versions = new HashMap<>();

    private static final long serialVersionUID = 1919810L;

    public void updateVersion(ModVersion version) {
        SortedSet<ModVersion> modVersions = this.versions.get(version.getMcVersion());
        if (modVersions == null) {
            this.versions.put(version.getMcVersion(), new TreeSet<>(Collections.singleton(version)));
        } else {
            modVersions.add(version);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModVersionCollection that = (ModVersionCollection) o;
        return Objects.equals(versions, that.versions);
    }

    public Map<String, SortedSet<ModVersion>> getVersions() {
        return versions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(versions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ModVersionCollection: \n");
        versions.forEach((mcVersion, version) -> {
            sb.append(mcVersion);
            sb.append(": ");
            sb.append(version.stream().map(ModVersion::toString).collect(Collectors.joining(", ", "[", "]\n")));
        });
        return sb.toString();
    }
}
