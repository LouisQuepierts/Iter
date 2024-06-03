package quepierts.iter.client.resource.i18n;

import com.alibaba.fastjson.JSONObject;
import quepierts.iter.reflex.Execute;
import quepierts.iter.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class I18n {
    private static final Map<String, Language> languages = new HashMap<>();
    private static Language selectedLanguage;

    @Execute
    private static void init() {
        try {
            Map<String, InputStream> lang = ResourceLoader.getAllResources("assets/lang", "json");
            InputStream stream;
            JSONObject json;

            for (String key : lang.keySet()) {
                if (languages.containsKey(key)) {
                    stream = lang.get(key);
                    String info = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    json = JSONObject.parseObject(info);
                    languages.get(key).add(json);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String localizationText(String key) {
        return selectedLanguage.get(key);
    }

    public static String localizationTextFormat(String key, Object... args) {
        return String.format(selectedLanguage.get(key), args);
    }

    public static void selectLanguage(String lang) {
        if (languages.containsKey(lang)) {
            selectedLanguage = languages.get(lang);
        }
    }
}
