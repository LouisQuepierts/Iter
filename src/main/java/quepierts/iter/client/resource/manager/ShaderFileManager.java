package quepierts.iter.client.resource.manager;

import quepierts.iter.client.resource.shader.Shader;
import quepierts.iter.util.ResourceLoader;
import quepierts.iter.util.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ShaderFileManager extends ResourceManager<Shader> {
    @Override
    public void load() {
        InputStream inputStream;
        Shader shader;

        try {
            Map<String, InputStream> input = ResourceLoader.getAllResources("assets/shader", "vsh");
            for (String key : input.keySet()) {
                inputStream = input.get(key);
                shader = new Shader(inputStream.readAllBytes(), true);
                inputStream.close();
                resources.put(key + ".vsh", shader);
            }

            input = ResourceLoader.getAllResources("assets/shader", "fsh");
            for (String key : input.keySet()) {
                inputStream = input.get(key);
                shader = new Shader(inputStream.readAllBytes(), false);
                inputStream.close();
                resources.put(key + ".fsh", shader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUp() {
        for (Shader shader : resources.values()) {
            shader.cleanUp();
        }
    }

    public Shader get(String name, boolean vertex) {
        return get(name + (vertex ? ".vsh" : ".fsh" ));
    }
}
