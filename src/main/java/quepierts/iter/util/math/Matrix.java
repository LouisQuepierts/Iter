package quepierts.iter.util.math;

import quepierts.iter.client.object.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Matrix {

    public static Matrix4f createTransformMatrix(Vector3f translate, Vector3f scale) {
        Matrix4f trans = new Matrix4f();
        trans.identity()
                .translate(translate)
                .scale(scale);

        return trans;
    }

    public static Matrix4f createTransformMatrix(Vector3f translate, float rx, float ry, float rz, float scale) {
        Matrix4f trans = new Matrix4f();
        trans.identity()
                .translate(translate)
                .rotateX((float) Math.toRadians(rx))
                .rotateY((float) Math.toRadians(ry))
                .rotateZ((float) Math.toRadians(rz))
                .scale(new Vector3f(scale, scale, scale));

        return trans;
    }

    public static Matrix4f createTransformMatrix(Vector3f translate, Vector3f rotate, float scale) {
        return createTransformMatrix(translate, rotate.x, rotate.y, rotate.z, scale);
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix4f = new Matrix4f();
        Vector3f rotation = camera.getRotation();
        Vector3f position = camera.getPosition();
        matrix4f.identity()
                .rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1))
                .translate(position.x, - position.y, position.z);
        return matrix4f;
    }

    public static Matrix4f createViewMatrixLookAt(Camera camera) {
        Matrix4f matrix4f = new Matrix4f();
        Vector3f position = new Vector3f(camera.getPosition());
        Vector3f center = new Vector3f(position).add(camera.getFront());
        matrix4f.identity()
                .lookAt(position, center, new Vector3f(0, 1, 0));

        return matrix4f;
    }
}
