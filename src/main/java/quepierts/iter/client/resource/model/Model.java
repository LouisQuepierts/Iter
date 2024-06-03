package quepierts.iter.client.resource.model;

import quepierts.iter.client.resource.ResourceID;
import quepierts.iter.util.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Mesh> modelDataList;

    private Model() {
        this.modelDataList = new ArrayList<>();
    }

    public Mesh getModelData() {
        return modelDataList.get(0);
    }

    public List<Mesh> getModelDataList() {
        return modelDataList;
    }

    public static Model createSingleMeshModel(Mesh mesh) {
        Model model = new Model();
        model.modelDataList.add(mesh);
        return model;
    }

    public static Model loadModel(ResourceID resourceID) throws Exception {
        byte[] bytes = ResourceLoader.getResourceBytes(resourceID.getResourcePath(0));
        if (bytes == null) {
            throw new Exception("Error load Model");
        }

        final ModelInfo fileInfo = ModelInfo.loadFromMemory(bytes);

        if (fileInfo == null) {
            throw new Exception("Error load Model");
        }

        Model model = new Model();

        model.processInfo(fileInfo);

        return model;
    }

    public static Model loadModel(byte[] bytes) throws Exception {
        final ModelInfo modelInfo = ModelInfo.loadFromMemory(bytes);

        if (modelInfo == null) {
            throw new Exception("Error load Model");
        }

        Model model = new Model();

        model.processInfo(modelInfo);

        return model;
    }

    public static Model loadModel(float[] vertices, float[] textureUV, float[] normals, int[] indices) {
        Model model = new Model();
        model.modelDataList.add(ModelLoader.loadToVAOElement(vertices, textureUV, normals, indices));
        return model;
    }

    public void cleanUp() {
        for (Mesh modelData : modelDataList) {
            modelData.cleanUp();
        }
    }

    private void processInfo(ModelInfo fileInfo) {
        List<Mesh> meshes = fileInfo.meshes();
        for (Mesh mesh : meshes) {
            mesh.loadToVAOElement();
            modelDataList.add(mesh);
        }
    }
}
