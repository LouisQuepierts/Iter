package quepierts.iter.client.resource.model;

import quepierts.iter.util.ArrayUtils;
import quepierts.iter.util.Buffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final String name;
    private final float[] vertices;
    private final float[] textureCoords;
    private final float[] normals;
    private final int[] indices;

    private int vaoID;
    private int verticesAmount;
    private int[] vboID;

    public Mesh(String name, List<Float> vertices, List<Float> textureCoords, List<Float> normals, List<Integer> indices) {
        this.name = name;

        this.vertices = ArrayUtils.toArray(vertices);
        this.textureCoords = ArrayUtils.toArray(textureCoords);
        this.normals = ArrayUtils.toArray(normals);
        this.indices = indices.stream().mapToInt((Integer v) -> v).toArray();

        this.vaoID = -1;
    }

    public Mesh(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
        this.name = "";

        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;

        this.vaoID = -1;
    }

    public void loadToVAOElement() {
        if (vaoID == -1) {
            List<Integer> vbo = new ArrayList<>();
            vaoID = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vaoID);

            bindIndices(vbo);
            storeAttributes(0, 3, vertices, vbo);
            if (textureCoords != null) storeAttributes(1, 2, textureCoords, vbo);
            if (normals != null) storeAttributes(2, 3, normals, vbo);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);

            vboID = vbo.stream().mapToInt((Integer v) -> v).toArray();
            verticesAmount = indices.length;
        }
    }

    public void loadToVAOArray() {
        if (vaoID == -1) {
            List<Integer> vbo = new ArrayList<>();
            vaoID = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(vaoID);

            storeAttributes(0, 3, vertices, vbo);
            if (textureCoords != null) storeAttributes(1, 2, textureCoords, vbo);
            if (normals != null) storeAttributes(2, 3, normals, vbo);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);

            vboID = vbo.stream().mapToInt((Integer v) -> v).toArray();
            verticesAmount = vertices.length / 3;
        }
    }

    public void cleanUp() {
        GL30.glDeleteVertexArrays(vaoID);
        GL15.glDeleteBuffers(vboID);
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return verticesAmount;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    private void bindIndices(List<Integer> vboList) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Buffer.warpBuffer(indices), GL15.GL_STATIC_DRAW);

        vboList.add(vbo);
    }

    private void storeAttributes(int attributeNumber, int vertexSize, float[] data, List<Integer> vboList) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, vertexSize, GL11.GL_FLOAT, false, 0, 0);

        vboList.add(vbo);
    }
}
