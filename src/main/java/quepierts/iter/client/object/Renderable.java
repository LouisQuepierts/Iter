package quepierts.iter.client.object;

import quepierts.iter.client.Iter;
import quepierts.iter.client.resource.model.Model;
import org.joml.Vector3f;

public abstract class Renderable {
    protected final Model model;
    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f color;
    protected final Vector3f specular;
    protected float shininess;

    public Renderable(Model model) {
        this.model = model;
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.color = new Vector3f(1, 1, 1);
        this.specular = new Vector3f(0, 0, 0);
        this.shininess = 32;
    }

    public Renderable(String id) {
        this.model = Iter.getInstance().modelManager.get(id);
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.color = new Vector3f(1, 1, 1);
        this.specular = new Vector3f(0, 0, 0);
        this.shininess = 32;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getColor() {
        return color;
    }

    public Model getModel() {
        return model;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void setColor(Vector3f color) {
        this.color.set(color);
    }

    public void setRotation(Vector3f rotation) {
        this.rotation.set(rotation);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void movePosition(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void setColor(float r, float g, float b) {
        this.color.set(r, g, b);
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public void moveRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
    }

    public void setSpecular(float x, float y, float z) {
        this.specular.set(x, y, z);
    }

    public void setSpecular(Vector3f specular) {
        this.specular.set(specular);
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getShininess() {
        return shininess;
    }
}
