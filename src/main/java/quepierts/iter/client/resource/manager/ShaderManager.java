package quepierts.iter.client.resource.manager;

import com.alibaba.fastjson.JSONObject;
import quepierts.iter.client.resource.shader.Shader;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.util.IterLogManager;
import quepierts.iter.util.ResourceLoader;
import quepierts.iter.util.ResourceManager;

import java.nio.charset.StandardCharsets;

public class ShaderManager extends ResourceManager<Program> {
    private final ShaderFileManager manager;
    public ShaderManager(ShaderFileManager manager) {
        this.manager = manager;
    }

    @Override
    public void load() {
        try {
            byte[] bytes = ResourceLoader.getResourceBytes("assets/shader/config.json");
            String info = new String(bytes, StandardCharsets.UTF_8);
            JSONObject json = JSONObject.parseObject(info);
            JSONObject programInfo;

            Shader vertexShader;
            Shader fragmentShader;

            for (String key : json.keySet()) {
                programInfo = json.getJSONObject(key);
                String vertex = programInfo.getString("vertex");
                String fragment = programInfo.getString("fragment");

                if ("%n".equals(vertex)) {
                    vertex = key;
                }

                if ("%n".equals(fragment)) {
                    fragment = key;
                }

                vertexShader = manager.get(vertex, true);
                fragmentShader = manager.get(fragment, false);

                resources.put(key, Program.createProgram(vertexShader, fragmentShader));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        IterLogManager.getLogger().info("[Iter Initialization] Shader Loaded");
    }

    @Override
    public void cleanUp() {
        for (Program shader : this.resources.values()) {
            shader.clean();
        }

        IterLogManager.getLogger().info("[Iter Clean Up] Shader Unloaded");
    }
}
