package quepierts.iter.client.framebuffer;

import quepierts.iter.client.Iter;

public class FrameBufferBlur extends FrameBuffer {
    private final boolean horizontal;
    private final float blurFraction;
    public FrameBufferBlur(boolean horizontal, float blurFraction) {
        super("blur5", 1, false);

        this.horizontal = horizontal;
        this.blurFraction = blurFraction;
    }

    public FrameBufferBlur(int blurSize, boolean horizontal, float blurFraction) {
        super("blur" + blurSize, 1, false);

        this.horizontal = horizontal;
        this.blurFraction = blurFraction;
    }

    @Override
    public void setUniform() {
        this.shader.setVector("textureSize", Iter.getSizeVecf().div(blurFraction));
        this.shader.setBoolean("horizontal", horizontal);
    }
}
