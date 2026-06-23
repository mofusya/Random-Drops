package net.mofusya.randomdrops.config;

import com.google.gson.JsonObject;
import net.mofusya.ornatelib.config.JsonConfig;
import net.mofusya.randomdrops.C;

public class ModConfigs {

    public static final JsonObject COMMON_DEFAULT = new JsonObject();
    static {
        COMMON_DEFAULT.addProperty("item_change_percentage", 20);
    }
    public static final JsonConfig COMMON = new JsonConfig(C.MOD_ID, COMMON_DEFAULT);
}
