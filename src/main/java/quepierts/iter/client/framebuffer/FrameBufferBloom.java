package quepierts.iter.client.framebuffer;

import quepierts.iter.client.object.Camera;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class FrameBufferBloom extends FrameBuffer {
    public FrameBufferBloom() {
        super("bloom.combine", 2, true);

        this.shader.use();
        this.shader.setInt("textureSampler", 0);
        this.shader.setInt("blurSampler", 1);
        this.shader.stop();
    }

    @Override
    public void setUniform() {

    }

    public void renderResult(int textureID, Camera camera) {
        this.shader.use();
        this.shader.setFloat("gamma", camera.getGamma());
        this.shader.setFloat("exposure", camera.getExposure());
        GL30.glBindVertexArray(this.quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureBuffers[0]);

        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        this.shader.stop();
    }
}
