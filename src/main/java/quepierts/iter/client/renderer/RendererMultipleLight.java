package quepierts.iter.client.renderer;

import quepierts.iter.client.object.Camera;
import quepierts.iter.client.object.ILight;
import quepierts.iter.client.object.Light;
import quepierts.iter.client.resource.shader.Program;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RendererMultipleLight extends RendererMaterial {
    private final List<ILight> lightList;
    private final ILight directionLight;

    public RendererMultipleLight(Program shader, Matrix4f projectionMatrix) {
        super(shader, projectionMatrix);
        this.directionLight = new Light();
        this.lightList = new ArrayList<>();

        this.setProjectionMatrix(projectionMatrix);
    }

    public void setDirectionLight(ILight light) {
        this.directionLight.set(light);
    }

    public ILight getDirectionLight() {
        return directionLight;
    }

    public void addLights(ILight... lights) {
        Collections.addAll(this.lightList, lights);
    }

    @Override
    protected void setUniform(Camera camera, ILight dirLight, Vector3f cameraPos) {
        shader.setVector("viewPos", cameraPos);

        Vector3f dirLightColor = dirLight.getLightColor();
        Vector3f dirAmbient = new Vector3f(dirLightColor).mul(0.05f);
        Vector3f dirDiffuse = new Vector3f(dirLightColor).mul(0.2f);

        shader.setVector("dirLight.direction", dirLight.getLightPosition());
        shader.setVector("dirLight.ambient", dirAmbient);
        shader.setVector("dirLight.diffuse", dirDiffuse);
        shader.setVector("dirLight.specular", dirLight.getLightSpecular());

        for (int i = 0; i < 4; i++) {
            Vector3f lightColor;
            Vector3f position;
            Vector3f specular;

            float strength = 0;

            if (lightList.size() - 1 < i) {
                lightColor = new Vector3f(0, 0, 0);
                position = new Vector3f(0, 0, 0);
                specular = new Vector3f(0, 0, 0);
            } else {
                ILight light = lightList.get(i);
                lightColor = light.getLightColor();
                position = light.getLightPosition();
                specular = light.getLightSpecular();
                strength = light.getLightStrength();
            }

            Vector3f ambient = new Vector3f(lightColor).mul(0.05f);
            Vector3f diffuse = new Vector3f(lightColor).mul(0.6f);

            shader.setVector("pointLights[" + i + "].position", position);
            shader.setVector("pointLights[" + i + "].ambient", ambient);
            shader.setVector("pointLights[" + i + "].diffuse", diffuse);
            shader.setVector("pointLights[" + i + "].specular", specular);
//            shader.setFloat("pointLights[" + i + "].constant", 1.0f);
//            shader.setFloat("pointLights[" + i + "].linear", 0.09f);
//            shader.setFloat("pointLights[" + i + "].quadratic", 0.032f);
            shader.setFloat("pointLights[" + i + "].strength", strength);
        }
    }
}
