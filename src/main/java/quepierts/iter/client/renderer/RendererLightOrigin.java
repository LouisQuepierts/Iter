package quepierts.iter.client.renderer;

import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.client.object.Camera;
import quepierts.iter.client.object.ILight;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.resource.shader.Program;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RendererLightOrigin extends Renderer {
    public RendererLightOrigin(Program shader, Matrix4f projectionMatrix) {
        super(shader);

        this.setProjectionMatrix(projectionMatrix);
    }

    @Override
    protected void setUniform(Camera camera, ILight light, Vector3f cameraPos) {

    }

    @Override
    protected void setUniform(Mesh model) {

    }

    @Override
    protected void setUniform(Renderable renderable) {

    }
}
