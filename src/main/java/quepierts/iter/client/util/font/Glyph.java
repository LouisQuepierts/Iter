package quepierts.iter.client.util.font;

public class Glyph {
    public final char c;
    public final int width;
    public final int height;
    public final int x;
    public final int y;

    public Glyph(char c, int width, int height, int x, int y) {
        this.c = c;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
}
