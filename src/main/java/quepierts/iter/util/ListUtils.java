package quepierts.iter.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Collection;
import java.util.List;

public class ListUtils {
    public static void add(Collection<Float> collection, Vector3f vector3f) {
        collection.add(vector3f.x);
        collection.add(vector3f.y);
        collection.add(vector3f.z);
    }

    public static void add(Collection<Float> collection, Vector2f vector2f) {
        collection.add(vector2f.x);
        collection.add(vector2f.y);
    }

    public static <T extends Object> void toFirst(List<T> list, int index) {
        T o = list.get(index);

        for (int i = index; i > 0; i--) {
            T o1 = list.get(index - 1);
            list.set(index, o1);
        }

        list.set(0, o);
    }
}
