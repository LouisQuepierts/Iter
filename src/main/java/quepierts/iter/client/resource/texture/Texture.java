package quepierts.iter.client.resource.texture;

import quepierts.iter.client.resource.ResourceID;
import quepierts.iter.util.ResourceLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public class Texture {
    protected final int id;

    private int width;
    private int height;

    public Texture(TextureLoader textureLoader) throws Exception {
        if (!textureLoader.isFinished()) throw new Exception("");
        this.id = textureLoader.getId();
        this.width = textureLoader.getWidth();
        this.height = textureLoader.getHeight();
    }

    private Texture(int width, int height) {
        this.id = GL11.glGenTextures();

        this.width = width;
        this.height = height;
    }

    public static Texture loadTexture(ResourceID resourceID) throws IOException {
        InputStream resource = ResourceLoader.getResource(resourceID.getResourcePath(0));
        BufferedImage image = ImageIO.read(resource);

        return loadTexture(image);
    }

    public static Texture loadTexture(BufferedImage image) {
        if (!image.hasTileWriters()) {
            return null;
        }

        int height = image.getHeight();
        int width = image.getWidth();

        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);

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

        Texture texture = new Texture(width, height);
        texture.readFromByteBuf(buffer);
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public void cleanUp() {
        GL11.glDeleteTextures(id);
    }

    public void bindTexture() {
        GL11.glBindTexture(GL_TEXTURE_2D, id);
    }

    private void readFromByteBuf(ByteBuffer byteBuffer) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 4);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height,
                0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
    }
}
