package quepierts.iter.client.gui.component;

import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.util.math.Color;
import quepierts.iter.util.tree.Quad;
import org.joml.Vector2f;
import org.joml.Vector4i;

public class Component extends Quad {
    protected static final int COLOR_G40 = Color.getColor(40, 40, 40, 255);
    protected static final int COLOR_G60 = Color.getColor(60, 60, 60, 255);

    protected static final int COLOR_G90 = Color.getColor(90, 90, 90, 255);
    protected static final int COLOR_G100 = Color.getColor(100, 100, 100, 255);

    protected int rgba;
    protected int texture;
    protected boolean visible = true;

    public Component(int posX, int posY, int width, int height, int r, int g, int b, int a) {
        super(posX, posY, width, height);
        this.rgba = Color.getColor(r, g, b, a);

        this.texture = -1;
    }

    public Component(int posX, int posY, int width, int height, int r, int g, int b, int a, int texture) {
        super(posX, posY, width, height);
        this.rgba = Color.getColor(r, g, b, a);

        this.texture = texture;
    }

    public Component(int posX, int posY, int width, int height) {
        this(posX, posY, width, height, 0, 0, 0, 0);
    }

    public Component(int posX, int posY, int width, int height, int texture) {
        this(posX, posY, width, height, 255, 255, 255, 255, texture);
    }

    public Vector2f getSize() {
        return new Vector2f(width, height);
    }

    public Vector2f getPos() {
        return new Vector2f(posX, posY);
    }

    public int getColor() {
        return rgba;
    }

    public final void draw(float posX, float posY) {
        draw(posX, posY, -1, -1, -1, -1);
    }

    public final void draw() {
        draw(0, 0);
    }

    public void draw(float posX, float posY,
                                                int limitX, int limitY, int limitWidth, int limitHeight) {
        ScreenRenderer.glDrawQuadTexture(posX, posY, width, height, rgba, texture);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setColor(int r, int g, int b, int a) {
        rgba = Color.getColor(r, g, b, a);
    }

    public void setColor(Vector4i vector4i) {
        rgba = Color.getColor(vector4i);
    }

    public boolean isVisible() {
        return visible;
    }
}
