package quepierts.iter.client.renderer;


import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.client.object.Camera;
import quepierts.iter.client.object.ILight;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.resource.shader.Program;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RendererMaterial extends Renderer {
    public RendererMaterial(Program shader, Matrix4f projectionMatrix) {
        super(shader);
    }

    @Override
    protected void setUniform(Camera camera, ILight light, Vector3f cameraPos) {
        shader.setVector("viewPos", cameraPos);

        Vector3f lightColor = light.getLightColor();
        Vector3f ambient = new Vector3f(lightColor).mul(0.2f);
        Vector3f diffuse = new Vector3f(lightColor).mul(0.5f);

        shader.setVector("light.position", light.getLightPosition());
        shader.setVector("light.ambient", ambient);
        shader.setVector("light.diffuse", diffuse);
        shader.setVector("light.specular", light.getLightSpecular());
    }

    @Override
    protected void setUniform(Mesh model) {

    }

    @Override
    protected void setUniform(Renderable renderable) {
        shader.setVector("material.color", renderable.getColor());
        shader.setVector("material.specular", renderable.getSpecular());
        shader.setFloat("material.shininess", renderable.getShininess());
    }


}
