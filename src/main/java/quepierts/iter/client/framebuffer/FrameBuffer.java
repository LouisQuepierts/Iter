package quepierts.iter.client.framebuffer;

import quepierts.iter.client.Iter;
import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.reflex.Execute;
import quepierts.iter.util.IterLogManager;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

public abstract class FrameBuffer {
    protected static final Iter iter = Iter.getInstance();
    protected static Mesh quad;

    protected final int frameBufferObject;
    protected int rendererBufferObject;

    protected int[] textureBuffers;

    protected final Program shader;

    @Execute
    private static void init() {
        quad = iter.modelManager.get("quad").getModelData();
    }

    public FrameBuffer(String shaderID) {
        this(shaderID, 1, true);
    }

    public FrameBuffer(String shaderID, int textureAmount, boolean hasRenderBuffer) {
        this.shader = iter.shaderManager.get(shaderID);

        //Create frame buffer
        this.frameBufferObject = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBufferObject);

        //Create color attachment texture
        this.createTextures(textureAmount);

        //Create framebuffer buffer object
        if (hasRenderBuffer) this.createRenderBuffer();

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
            IterLogManager.getLogger().info("error");
        }

        GL30.glBindFramebuffer(GL30.GL_RENDERBUFFER, 0);
    }

    public final void bind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBufferObject);
    }

    public final void unbind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public final void cleanUp() {
        GL30.glDeleteFramebuffers(this.frameBufferObject);
        GL30.glDeleteRenderbuffers(this.rendererBufferObject);
        GL11.glDeleteTextures(this.textureBuffers);
    }

    public void render(int textureID) {
        this.shader.use();
        this.setUniform();
        GL30.glBindVertexArray(this.quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        this.shader.stop();
    }

    public void render() {
        this.render(this.textureBuffers[0]);
    }

    public int getFrameBufferObject() {
        return frameBufferObject;
    }

    public int getTextureBuffer() {
        return textureBuffers[0];
    }

    public int[] getTextureBuffers() {
        return textureBuffers;
    }

    public int getRendererBufferObject() {
        return rendererBufferObject;
    }

    public abstract void setUniform();

    protected void createTextures(int textureSize) {
        int width = Iter.getWidth();
        int height = Iter.getHeight();

        int[] attachments = new int[textureSize];

        this.textureBuffers = new int[textureSize];

        for (int i = 0; i < this.textureBuffers.length; i++) {
            this.textureBuffers[i] = GL12.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureBuffers[i]);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB16, width, height, 0, GL11.GL_RGBA, GL11.GL_FLOAT, (ByteBuffer) null);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0 + i, GL11.GL_TEXTURE_2D, this.textureBuffers[i], 0);

            attachments[i] = GL30.GL_COLOR_ATTACHMENT0 + i;
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL30.glDrawBuffers(attachments);
    }

    protected void createRenderBuffer() {
        this.rendererBufferObject = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.rendererBufferObject);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH_COMPONENT, Iter.getWidth(), Iter.getHeight());
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, this.rendererBufferObject);
    }

    public static void drawQuad() {

    }
}
