package quepierts.iter.client.resource.model;

import quepierts.iter.util.ArrayUtils;

import java.util.List;

public class ModelLoader {
    public static Mesh loadToVAO(List<Float> vertices, List<Float> textureCoords, List<Float> normals, List<Integer> indices) {
        return loadToVAOElement(ArrayUtils.toArray(vertices), ArrayUtils.toArray(textureCoords), ArrayUtils.toArray(normals), indices.stream().mapToInt((Integer v) -> v).toArray());
    }

    public static Mesh loadToVAOElement(float[] vertices, float[] textureUV, float[] normals, int[] indices) {
        Mesh mesh = new Mesh(vertices, textureUV, normals, indices);
        mesh.loadToVAOElement();
        return mesh;
    }

    public static Mesh loadToVAOArray(float[] vertices, float[] textureUV, float[] normals) {
        Mesh mesh = new Mesh(vertices, textureUV, normals, null);
        mesh.loadToVAOArray();
        return mesh;
    }

}
