package quepierts.iter.client.resource.model;

import quepierts.iter.util.ListUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ModelInfo {
    private final List<Mesh> meshes;

    private ModelInfo() {
        meshes = new ArrayList<>();
    }

    public int meshAmount() {
        return meshes.size();
    }

    public List<Mesh> meshes() {
        return meshes;
    }

    public static ModelInfo loadFromMemory(byte[] bytes) {
        ModelInfo modelFileInfo = new ModelInfo();
        String[] lines = new String(bytes, StandardCharsets.UTF_8).split("\n");

        List<Vector3f> verticesList = new ArrayList<>();
        List<Vector2f> texturesUVList = new ArrayList<>();
        List<Vector3f> normalList = new ArrayList<>();
        List<Vector3i> faceList = new ArrayList<>();

        String name = "";

        for (String line : lines) {
            String[] info = line.split("\\s+");

            switch (info[0]) {
                case "o":
                    if (!verticesList.isEmpty()) {
                        load(name, verticesList, texturesUVList, normalList, faceList, modelFileInfo);
                    }
                    name = info[1];
                    break;
                case "v":
                    verticesList.add(new Vector3f(Float.parseFloat(info[1]),
                            Float.parseFloat(info[2]),
                            Float.parseFloat(info[3])));
                    break;
                case "vt":
                    texturesUVList.add(new Vector2f(Float.parseFloat(info[1]),
                            Float.parseFloat(info[2])));
                    break;
                case "vn":
                    normalList.add(new Vector3f(Float.parseFloat(info[1]),
                            Float.parseFloat(info[2]),
                            Float.parseFloat(info[3])));
                    break;
                case "f":
                    for (int i = 1; i < 4; i++) {
                        String[] faceInfo = info[i].split("/");
                        faceList.add(new Vector3i(Integer.parseInt(faceInfo[0]) - 1,
                                Integer.parseInt(faceInfo[1]) - 1,
                                Integer.parseInt(faceInfo[2]) - 1));
                    }
                    break;
                default:
                    break;
            }
        }

        if (verticesList.isEmpty()) {
            return null;
        }

        load(name, verticesList, texturesUVList, normalList, faceList, modelFileInfo);
        return modelFileInfo;
    }

    private static void load(String name, List<Vector3f> verticesList, List<Vector2f> texturesUVList,
                             List<Vector3f> normalList, List<Vector3i> faceList,
                             ModelInfo fileInfo) {
        List<Float> positions = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        int count = 0;
        for (Vector3i face : faceList) {
            ListUtils.add(positions, verticesList.get(face.x));
            ListUtils.add(textureCoords, texturesUVList.get(face.y));
            ListUtils.add(normals, normalList.get(face.z));

            indices.add(count++);
        }

        fileInfo.addMesh(new Mesh(name, positions, textureCoords, normals, indices));

        verticesList.clear();
        texturesUVList.clear();
        normalList.clear();
        faceList.clear();
    }

    private void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }
}
