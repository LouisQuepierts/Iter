package quepierts.iter.client.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import quepierts.iter.client.EnumRenderType;
import quepierts.iter.client.Iter;
import quepierts.iter.client.object.Object;
import quepierts.iter.client.object.*;

import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private final Iter iter = Iter.getInstance();

    private final RendererLightOrigin lightRenderer;
    private final RendererMultipleLight multipleLightRenderer;

    private final float FOV = 70f;
    private final float NEAR = 0.1f;
    private final float FAR = 1000f;

    private Matrix4f projectionMatrix;

    public Light lightDir = new Light(new Vector3f(-0.2f, -1.0f, -0.2f), new Vector3f(1.0f, 1.0f, 1.0f));
    private Object object = new Object("scene");
    private LightObject lightObject = new LightObject("cube");
    private LightObject lightObject1 = new LightObject("cube");
    private LightObject lightObject2 = new LightObject("cube");
    private LightObject lightObject3 = new LightObject("cube");

    private List<Renderable> objectList = new ArrayList<>();

    public RenderManager() {
        this.lightObject.setPosition(0f, 1, 0f);
        this.lightObject.setColor(1, 1, 1);
        this.lightObject.setSpecular(1.0f, 1.0f, 1.0f);

        this.lightObject1.setPosition(1, 2, 2);
        this.lightObject1.setColor(0.55f, 1.0f, 0.95f);
        this.lightObject1.setSpecular(1.0f, 1.0f, 1.0f);

        this.lightObject2.setPosition(-4, 3, 3);
        this.lightObject2.setColor(1.0f, 0.7f, 0.8f);
        this.lightObject2.setSpecular(1.0f, 1.0f, 1.0f);

        this.lightObject3.setPosition(2, 3, -6);
        this.lightObject3.setColor(1.0f, 0.8f, 0.12f);
        this.lightObject3.setSpecular(1.0f, 1.0f, 1.0f);
        this.lightObject3.setLightStrength(0.5f);

        lightDir.setLightSpecular(new Vector3f(0.5f, 0.5f, 0.5f));

        object.setColor(1.0f, 1.0f, 1.0f);
        object.setSpecular(0.5f, 0.5f, 0.5f);

        this.initProjectionMatrix();

        this.lightRenderer = new RendererLightOrigin(iter.shaderManager.get("light"), this.projectionMatrix);
        this.multipleLightRenderer = new RendererMultipleLight(iter.shaderManager.get("lightMultiple"), this.projectionMatrix);
        this.multipleLightRenderer.setDirectionLight(lightDir);
        this.multipleLightRenderer.addLights(lightObject, lightObject1, lightObject2);

    }

    public void renderObjects(Camera camera) {
//        object.setPosition((float) Math.sin(Time.getCurrentTime()) * 5, 0, (float) Math.cos(Time.getCurrentTime()) * 5);

        this.multipleLightRenderer.addToQueue(object);
        //this.multipleLightRenderer.addToQueue(objectList);
        this.multipleLightRenderer.render(camera, lightDir);

        this.lightRenderer.addToQueue(lightObject, lightObject1, lightObject2);
        this.lightRenderer.render(camera, lightObject);
        GL20.glUseProgram(0);
    }

    public void draw(EnumRenderType type, Renderable renderable) {
        switch (type) {
            case NORMAL:
                this.multipleLightRenderer.addToQueue(renderable);
                break;
            case LIGHT:
                this.lightRenderer.addToQueue(renderable);
                break;
        }
    }

    public void cleanUp() {

    }

    private void initProjectionMatrix() {
        float aspectRatio = (float) Iter.getWidth() / (float) Iter.getHeight();
        projectionMatrix = new Matrix4f();
        projectionMatrix.setPerspective(FOV, aspectRatio, NEAR, FAR);
    }
}
