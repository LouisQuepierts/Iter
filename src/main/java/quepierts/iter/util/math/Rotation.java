package quepierts.iter.util.math;

import org.joml.Vector3f;

public class Rotation {

    public static Vector3f toVector(Vector3f vectorDir, float yaw) {
        vectorDir.x += (float) Math.cos(Math.toRadians(yaw));
        vectorDir.z += (float) Math.sin(Math.toRadians(yaw));

        return vectorDir;
    }
}
