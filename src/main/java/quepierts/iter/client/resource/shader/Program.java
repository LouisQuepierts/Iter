package quepierts.iter.client.resource.shader;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class Program {
    private final int ID;

    public static Program createProgram(Shader vertex, Shader fragment) throws Exception {
        if (vertex.isVertexShader() && !fragment.isVertexShader()) {
            Program program = new Program(vertex, fragment);
            return program;
        }

        throw new Exception("Illegal Parameters");
    }

    private Program(Shader vertex, Shader fragment) {
        ID = GL20.glCreateProgram();
        GL20.glAttachShader(ID, vertex.getID());
        GL20.glAttachShader(ID, fragment.getID());

        GL20.glLinkProgram(ID);
        GL20.glValidateProgram(ID);
    }

    protected final Map<String, Integer> uniformLocations = new HashMap<>();

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private int getUniformLocation(String uniformName) {
        if (uniformLocations.containsKey(uniformName)) {
            return uniformLocations.get(uniformName);
        } else {
            int location = GL20.glGetUniformLocation(ID, uniformName);
            uniformLocations.put(uniformName, location);
            return location;
        }
    }

    public final void setFloat(String uniformName, float value) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform1f(uniformLocation, value);
        }
    }

    public final void setInt(String uniformName, int value) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform1i(uniformLocation, value);
        }
    }

    public final void setBoolean(String uniformName, boolean value) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform1i(uniformLocation, value ? 1 : 0);
        }
    }

    public final void setVector(String uniformName, Vector4f vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform4f(uniformLocation, vec.x, vec.y, vec.z, vec.w);
        }
    }

    public final void setVector(String uniformName, Vector3f vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform3f(uniformLocation, vec.x, vec.y, vec.z);
        }
    }

    public final void setVector(String uniformName, Vector2f vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform2f(uniformLocation, vec.x, vec.y);
        }
    }

    public final void setVector(String uniformName, Vector4i vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform4f(uniformLocation, vec.x, vec.y, vec.z, vec.w);
        }
    }

    public final void setVector(String uniformName, Vector3i vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform3i(uniformLocation, vec.x, vec.y, vec.z);
        }
    }

    public final void setVector(String uniformName, Vector2i vec) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            GL20.glUniform2i(uniformLocation, vec.x, vec.y);
        }
    }

    public final void setMatrix(String uniformName, Matrix4f matrix4f) {
        int uniformLocation = getUniformLocation(uniformName);
        if (uniformLocation != -1) {
            matrixBuffer = matrix4f.get(matrixBuffer);
            GL20.glUniformMatrix4fv(uniformLocation, false, matrixBuffer);
        }
    }

    public void use() {
        GL20.glUseProgram(ID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void clean() {
        GL20.glDeleteProgram(ID);
    }

    public int getPROGRAM_ID() {
        return ID;
    }
}
