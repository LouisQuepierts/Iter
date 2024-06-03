package quepierts.iter.client.renderer.screen;

import quepierts.iter.client.Iter;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class QuadInfo {
    private static final int screenWidth = Iter.getWidth();
    private static final int screenHeight = Iter.getHeight();
    public final Vector3f translate;
    public final Vector3f scale;
    public final int color;
    public final int texture;

    public final Vector4f uv;

    QuadInfo(float posX, float posY, float width, float height, int color, int texture) {
        //Calculate absolute scale
        float widthLength = width / screenWidth;
        float heightLength = height / screenHeight;
        //Adjust the quad draw from top left to down right
        this.translate = new Vector3f((posX * 2 / screenWidth) - 1 + widthLength, 1 - (posY * 2 / screenHeight) - heightLength, 0);
        this.scale = new Vector3f(widthLength, heightLength, 1);
        this.color = color;
        this.texture = texture;

        this.uv = new Vector4f(0, 0, 1, 1);
    }

    public void setUV(float x1, float y1, float x2, float y2) {
        this.uv.set(x1, y1, x2, y2);
    }
}
