package quepierts.iter.client.renderer;

import quepierts.iter.client.object.Camera;
import quepierts.iter.client.object.ILight;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.client.resource.model.Mesh;
import org.joml.Vector3f;

public class RendererTransparent extends Renderer {
    public RendererTransparent(Program shader) {
        super(shader);
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
