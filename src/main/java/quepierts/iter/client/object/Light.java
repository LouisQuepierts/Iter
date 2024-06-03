package quepierts.iter.client.object;

import org.joml.Vector3f;

public class Light implements ILight {
    private final Vector3f position;
    private final Vector3f color;
    private final Vector3f specular;
    private float strength;

    public Light() {
        this.position = new Vector3f();
        this.color = new Vector3f(1);
        this.specular = new Vector3f(1);
        this.strength = 1;
    }

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
        this.specular = new Vector3f(1.0f, 1.0f, 1.0f);
        this.strength = 1;
    }

    public Vector3f getLightPosition() {
        return position;
    }

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
    public void setLightPosition(Vector3f position) {
        this.position.set(position);
    }

    @Override
    public void setLightColor(Vector3f color) {
        this.color.set(color);
    }

    @Override
    public void setLightSpecular(Vector3f specular) {
        this.specular.set(specular);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setColor(float r, float g, float b) {
        this.color.set(r, g, b);
    }

    public void setSpecular(float r, float g, float b) {
        this.specular.set(r, g, b);
    }

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
}
