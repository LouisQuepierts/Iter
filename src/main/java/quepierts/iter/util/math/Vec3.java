package quepierts.iter.util.math;

import org.joml.Vector3f;

public class Vec3 {
    public static boolean bigger(Vector3f target, float var) {
        return target.x > var && target.y > var && target.z > var;
    }

    public static boolean bigger(Vector3f target, float x, float y, float z) {
        return target.x > x && target.y > y && target.z > z;
    }

    public static boolean bigger(Vector3f target, Vector3f vec3) {
        return target.x > vec3.x && target.y > vec3.y && target.z > vec3.z;
    }
}
