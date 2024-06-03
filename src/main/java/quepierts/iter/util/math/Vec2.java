package quepierts.iter.util.math;

import org.joml.Vector2f;

public class Vec2 {
    public static Vector2f getOffsetPosition(Vector2f vec2, Vector2f target) {
        return new Vector2f(vec2).sub(target);
    }

    public static Vector2f getOffsetPosition(float vec_x, float vec_y, float target_x, float target_y) {
        return new Vector2f(vec_x - target_x, vec_y - target_y);
    }
}
