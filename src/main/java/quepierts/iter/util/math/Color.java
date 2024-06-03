package quepierts.iter.util.math;

import org.joml.Vector4f;
import org.joml.Vector4i;

public class Color {
    public static final int WHITE = -1;
    public static final int BLACK = -16777216;

    public static Vector4i getColor(int color, Vector4i vec) {
        int alpha = (color >> 24) & 255;
        int red = (color >> 16) & 255;
        int green = (color >> 8) & 255;
        int blue = color & 255;

        vec.set(red, green, blue, alpha);
        return vec;
    }

    public static Vector4f getColor(int color, Vector4f vec) {
        float alpha = (float) (color >> 24 & 255) / 255;
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;

        vec.set(red, green, blue, alpha);
        return vec;
    }

    public static int getColor(Vector4i color) {
        return (Math.min(color.w, 255) << 24 |
                Math.min(color.x, 255) << 16 |
                Math.min(color.y, 255) << 8 |
                Math.min(color.z, 255));
    }

    public static int getColor(int r, int g, int b, int a) {
        return (Math.min(a, 255) << 24 |
                Math.min(r, 255) << 16 |
                Math.min(g, 255) << 8 |
                Math.min(b, 255));
    }
}
