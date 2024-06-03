package quepierts.iter.client.resource.i18n;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Language {
    private final Map<String, String> texts;

    public Language() {
        this.texts = new HashMap<>();
    }

    public void add(JSONObject json) {
        for (String key : json.keySet()) {
            texts.put(key, json.getString(key));
        }
    }

    public String get(String key) {
        if (texts.containsKey(key)) {
            key = texts.get(key);
        }

        return key;
    }
}
