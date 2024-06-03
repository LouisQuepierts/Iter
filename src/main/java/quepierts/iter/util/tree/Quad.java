package quepierts.iter.util.tree;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2ic;

public class Quad {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;

    public Quad(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidthPos() {
        return posX + width;
    }

    public int getHeightPos() {
        return posY + height;
    }

    public Vector2f getAbsPosition(float posX, float posY) {
        return new Vector2f(posX - this.posX, posY - this.posY);
    }

    public boolean intersects(Quad quad) {
        return quad.posX >= posX &&
                quad.posY >= posY &&
                quad.posX + quad.width <= posX + width &&
                quad.posY + quad.height <= posY + height;
    }

    public boolean intersects(int x, int y) {
        return x >= posX &&
                y > posY &&
                x <= posX + width &&
                y <= posY + height;
    }

    public boolean intersects(Vector2ic vec) {
        return vec.x() >= posX &&
                vec.y() >= posY &&
                vec.x() <= posX + width &&
                vec.y() <= posY + height;
    }

    public boolean intersects(Vector2fc vec) {
        return vec.x() >= posX &&
                vec.y() >= posY &&
                vec.x() <= posX + width &&
                vec.y() <= posY + height;
    }

    @Override
    public String toString() {
        return "Pos: (" + this.posX + ", " + this.posY + ")\nSize: (" + this.width + ", " + this.height  + ")";
    }
}
