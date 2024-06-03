package quepierts.iter.client.resource.shader;

import quepierts.iter.util.IterLogManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.charset.StandardCharsets;

public class Shader {
    private final int ID;
    private final boolean vertexShader;

    public Shader(byte[] bytes, boolean vertex) {
        this.vertexShader = vertex;

        ID = GL20.glCreateShader(vertex ? GL20.GL_VERTEX_SHADER : GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(ID, new String(bytes, StandardCharsets.UTF_8));
        GL20.glCompileShader(ID);

        if (GL20.glGetShaderi(ID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            IterLogManager.getLogger().info(GL20.glGetShaderInfoLog(ID, 500));
        }
    }

    public void cleanUp() {
        GL20.glDeleteShader(ID);
    }

    public boolean isVertexShader() {
        return vertexShader;
    }

    public int getID() {
        return ID;
    }
}
