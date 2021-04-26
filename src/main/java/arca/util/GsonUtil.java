package arca.util;

import arca.domain.entities.Error;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    public static final Gson GSON = new GsonBuilder().create();

    public static Error erro(final String json){
        return GSON.fromJson(json, Error.class);
    }
}
