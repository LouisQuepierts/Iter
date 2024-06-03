package quepierts.iter.client.object;

import quepierts.iter.client.resource.model.Model;
import org.joml.Vector3f;

public class LightObject extends Object implements ILight {
    private float strength;
    public LightObject(Model model) {
        super(model);

        this.strength = 1;
    }

    public LightObject(String id) {
        super(id);

        this.strength = 1;
    }

    @Override
    public Vector3f getLightPosition() {
        return new Vector3f(position.x, position.y + 0.5f, position.z);
    }

    @Override
    public Vector3f getLightColor() {
        return color;
    }

    @Override
    public Vector3f getLightSpecular() {
        return specular;
    }

    @Override
    public float getLightStrength() {
        return this.strength;
    }

    @Override
    public void setLightPosition(Vector3f vector3f) {
        this.setPosition(vector3f);
    }

    @Override
    public void setLightColor(Vector3f vector3f) {
        this.setColor(vector3f);
    }

    @Override
    public void setLightSpecular(Vector3f vector3f) {
        this.setSpecular(vector3f);
    }

    @Override
    public void setLightStrength(float strength) {
        this.strength = strength;
    }

    @Override
    public void set(ILight light) {
        this.position.set(light.getLightPosition());
        this.color.set(light.getLightColor());
        this.specular.set(light.getLightSpecular());
        this.strength = light.getLightStrength();
    }

    public void setSpecular(float x, float y, float z) {
        this.specular.set(x, y, z);
    }
}
