package quepierts.iter.client.object;

import quepierts.iter.util.math.MathHelper;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Vector3f front;
    private Vector3f lastPosition;
    private Vector3f rotate;

    private float gamma;
    private float exposure;

    public Camera() {
        this.position = new Vector3f(0);
        this.front = new Vector3f(0);
        this.lastPosition = new Vector3f(0);
        this.rotate = new Vector3f(0);

        this.gamma = 1.0f;
        this.exposure = 1.0f;

        this.rotate(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getLastPosition() {
        return lastPosition;
    }

    public Vector3f getRotation() {
        return rotate;
    }

    public float getPitch() {
        return rotate.x;
    }

    public float getYaw() {
        return rotate.y;
    }

    public float getRoll() {
        return rotate.z;
    }

    public Vector3f getFront() {
        return front;
    }

    public float getGamma() {
        return gamma;
    }

    public float getExposure() {
        return exposure;
    }

    public void update() {
        this.lastPosition.set(position);
    }

    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void translate(Vector3f vector3f) {
        this.position.add(vector3f);
    }

    public void translateRotated(float x, float y, float z) {
        if (x != 0) {
            this.position.add((float) (Math.sin(Math.toRadians(this.rotate.y)) * -1 * x), 0, (float) (Math.cos(Math.toRadians(this.rotate.y)) * x));
        }
        if (z != 0) {
            this.position.add((float) (Math.sin(Math.toRadians(this.rotate.y - 90)) * -1 * z), 0, (float) (Math.cos(Math.toRadians(this.rotate.y - 90)) * z));
        }

        this.position.y += y;
    }

    public void translateRotated(Vector3f vector3f) {
        Vector3f rotatedTranslation = new Vector3f(vector3f)
                .rotateY((float) Math.toRadians(this.rotate.y));
        this.position.add(rotatedTranslation);
    }

    public void rotate(float pitch, float yaw, float roll) {
        this.rotate.add(pitch, -yaw, roll);

        MathHelper.limit(this.rotate, new Vector3f(1, 0, 0), 89, -89);

        this.front.set(
                Math.cos(Math.toRadians(this.rotate.x)) * Math.cos(Math.toRadians(this.rotate.y)),
                Math.sin(Math.toRadians(this.rotate.x)),
                Math.cos(Math.toRadians(this.rotate.x)) * Math.sin(Math.toRadians(this.rotate.y))
        );
    }
}
