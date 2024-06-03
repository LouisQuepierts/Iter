package quepierts.iter.util;

import org.joml.Vector3f;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
    public static float[] toArray(List<Float> list) {
        if (list.isEmpty()) {
            return null;
        }

        float[] floats = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            floats[i] = list.get(i);
        }

        return floats;
    }

    public static float[] toFloatArray(List<Vector3f> list) {
        if (list.isEmpty()) {
            return null;
        }

        float[] floats = new float[list.size() * 3];
        int i = 0;
        for (Vector3f vector3f : list) {
            floats[i++] = vector3f.x;
            floats[i++] = vector3f.y;
            floats[i++] = vector3f.z;
        }

        return floats;
    }
}
