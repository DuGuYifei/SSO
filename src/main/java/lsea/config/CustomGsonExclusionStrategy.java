package lsea.config;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Custom Gson exclusion strategy.
 */
public class CustomGsonExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getName().equals("password");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
