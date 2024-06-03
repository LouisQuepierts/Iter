package quepierts.iter.client.framebuffer;

import quepierts.iter.client.Iter;

public class FrameBufferBlurDyn extends FrameBuffer {
    private final boolean horizontal;
    private final float blurFraction;

    private final float[] gaussianKernel;

    public FrameBufferBlurDyn(float[] gaussianKernel, boolean horizontal, float blurFraction) {
        super("blur", 1, false);

        this.horizontal = horizontal;
        this.blurFraction = blurFraction;
        this.gaussianKernel = gaussianKernel;

        if (this.gaussianKernel.length > 21) {
            throw new UnsupportedOperationException("The max supported length for Gaussian Kernel is 21");
        }
    }

    @Override
    public void setUniform() {
        this.shader.setVector("textureSize", Iter.getSizeVecf().div(blurFraction));
        this.shader.setBoolean("horizontal", horizontal);
        this.shader.setInt("blurSize", this.gaussianKernel.length);

        for (int i = 0; i < this.gaussianKernel.length; i++) {
            this.shader.setFloat("weight[" + i + "]", this.gaussianKernel[i]);
        }
    }
}
