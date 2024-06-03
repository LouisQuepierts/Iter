package quepierts.iter.client.resource.manager;

import quepierts.iter.client.resource.model.Model;
import quepierts.iter.client.resource.model.ModelLoader;
import quepierts.iter.util.IterLogManager;
import quepierts.iter.util.ResourceLoader;
import quepierts.iter.util.ResourceManager;

import java.io.InputStream;
import java.util.Map;

public class ModelManager extends ResourceManager<Model> {

    @Override
    public void load() {
        InputStream inputStream;
        Model model;

        try {
            Map<String, InputStream> obj = ResourceLoader.getAllResources("assets/model", "obj");
            for (String fileName : obj.keySet()) {
                inputStream = obj.get(fileName);
                model = Model.loadModel(inputStream.readAllBytes());
                inputStream.close();

                resources.put(fileName, model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[] vertices = {
                -1.0f, 1.0f, 0,
                -1.0f,-1.0f, 0,
                1.0f, 1.0f, 0,
                1.0f, -1.0f, 0
        };

        float[] textureUV = {
                0, 1,
                0, 0,
                1, 1,
                1, 0
        };

        Model quad = Model.createSingleMeshModel(ModelLoader.loadToVAOArray(vertices, textureUV, null));
        this.resources.put("quad", quad);

        IterLogManager.getLogger().info("[Iter Initialization] Model Loaded");
    }

    @Override
    public void cleanUp() {
        for (Model model : this.resources.values()) {
            model.cleanUp();
        }

        IterLogManager.getLogger().info("[Iter Clean Up] Model Unloaded");
    }
}
