package cn.booling.bakahdt.version;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author youyihj
 */
public class ModVersionCollectionDeserializer implements JsonDeserializer<ModVersionCollection> {
    @Override
    public ModVersionCollection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ModVersionCollection modVersionCollection = new ModVersionCollection();
        for (JsonElement element : jsonElement.getAsJsonArray()) {
            modVersionCollection.updateVersion(jsonDeserializationContext.deserialize(element, ModVersion.class));
        }
        return modVersionCollection;
    }
}
