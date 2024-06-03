package quepierts.iter.util.math;

import org.joml.Vector3f;

public class MathHelper {
    public static float limit(float input, float max, float min) {
        return (input > max) ? max : (Math.max(input, min));
    }

    public static double limit(double input, double max, double min) {
        return (input > max) ? max : (Math.max(input, min));
    }

    public static void limit(Vector3f vec3, Vector3f axis, float max, float min) {
        if (axis.x == 1) {
            vec3.x = limit(vec3.x, max, min);
        }

        if (axis.y == 1) {
            vec3.y = limit(vec3.y, max, min);
        }

        if (axis.z == 1) {
            vec3.z = limit(vec3.z, max, min);
        }
    }
}
