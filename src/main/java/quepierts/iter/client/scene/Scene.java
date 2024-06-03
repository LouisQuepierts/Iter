package quepierts.iter.client.scene;

import quepierts.iter.client.EnumRenderType;
import quepierts.iter.client.object.Light;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.renderer.RenderEngine;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static quepierts.iter.client.EnumRenderType.*;

public class Scene {
    private final Map<EnumRenderType, List<Renderable>> objects;
    private final String sceneID;
    public final Light ambientLight;
    public final Vector3f backgroundColor;

    public Scene(String sceneID) {
        this.sceneID = sceneID;

        objects = new HashMap<>();
        ambientLight = new Light();
        backgroundColor = new Vector3f(0);

        init();
    }

    public void addObject(EnumRenderType type, Renderable renderable) {
        objects.get(type).add(renderable);
    }

    public void setAmbientLightColor(Vector3f vector3f) {
        ambientLight.setLightColor(vector3f);
    }

    public void setAmbientLightDirection(Vector3f vector3f) {
        ambientLight.setLightPosition(vector3f);
    }

    public void setAmbientLightSpecular(Vector3f vector3f) {
        ambientLight.setLightSpecular(vector3f);
    }

    public void setAmbientLightStrength(float strength) {
        ambientLight.setLightStrength(strength);
    }

    public void setBackgroundColor(Vector3f vector3f) {
        backgroundColor.set(vector3f);
    }

    public void draw(RenderEngine renderEngine) {
        renderEngine.drawObject(NORMAL, objects.get(NORMAL));
        renderEngine.drawObject(LIGHT, objects.get(LIGHT));
    }

    private final void init() {
        for (EnumRenderType value : EnumRenderType.values()) {
            objects.put(value, new ArrayList<>());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return sceneID.equals(obj);
        }
        return super.equals(obj);
    }

    public String getSceneID() {
        return sceneID;
    }
}
