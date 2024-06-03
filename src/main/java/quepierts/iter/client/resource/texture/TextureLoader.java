package quepierts.iter.client.resource.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public class TextureLoader {
    private final int id;

    private int width;
    private int height;

    private final BufferedImage image;
    private final ByteBuffer buffer;
    private boolean begin = false;
    private int flag = 0;

    private TextureLoader(BufferedImage image) {
        this.id = GL11.glGenTextures();
        this.image = image;

        height = image.getHeight();
        width = image.getWidth();

        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        buffer = ByteBuffer.allocateDirect(width * height * 4);

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                int pixel = pixels[h * width + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
    }

    public static TextureLoader loadTexture(BufferedImage image) throws Exception {
        if (!image.hasTileWriters()) throw new Exception("");
        return new TextureLoader(image);
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isFinished() {
        return flag == 15;
    }

    public TextureLoader begin() {
        begin = true;

        return this;
    }

    public Texture end() throws Exception {
        flag |= 8;
        return new Texture(this);
    }

    public TextureLoader glTextureLevel(int base, int max) throws Exception {
        if (!begin) throw new Exception("Illegal ");
        GL11.glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, base);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, max);
        flag |= 1;
        return this;
    }

    public TextureLoader glTextureFilter(int min, int mag) throws Exception {
        if (!begin) throw new Exception("");
        GL11.glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, min);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mag);
        flag |= 2;
        return this;
    }

    public TextureLoader glTextureWrap(int s, int t) throws Exception {
        if (!begin) throw new Exception("");
        GL11.glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, s);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, t);
        flag |= 4;
        return this;
    }
}
