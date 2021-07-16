package cn.booling.bakahdt.version;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author youyihj
 */
public class ModVersionDeserializer implements JsonDeserializer<ModVersion> {
    @Override
    public ModVersion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String fileName = jsonObject.get("fileName").getAsString();
        String[] split = fileName.split("-");
        String downloadLink = jsonObject.get("downloadUrl").getAsString();
        int releaseType = jsonObject.get("releaseType").getAsInt();
        String mcVersion = split[1];
        String modVersion = split[2];
        modVersion = modVersion.substring(0, modVersion.lastIndexOf("."));
        return new ModVersion(mcVersion, modVersion, downloadLink, releaseType);
    }
}
