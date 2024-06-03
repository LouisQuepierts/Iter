package quepierts.iter.client.framebuffer;

public class GaussianBlurDyn {
    private final FrameBufferBlurDyn horizontal;
    private final FrameBufferBlurDyn vertical;

    public GaussianBlurDyn(float[] gaussianKernel, float blurFraction) {
        this.horizontal = new FrameBufferBlurDyn(gaussianKernel, true, blurFraction);
        this.vertical = new FrameBufferBlurDyn(gaussianKernel,false, blurFraction);
    }

    public final void render(int textureID) {
        this.horizontal.bind();
        this.horizontal.render(textureID);

        this.vertical.bind();
        this.vertical.render(this.horizontal.getTextureBuffer());
    }

    public final void render(int textureID, int times) {
        for (int i = 0; i < times; i++) {
            this.horizontal.bind();
            this.horizontal.render(i == 0 ? textureID : this.vertical.getTextureBuffer());

            this.vertical.bind();
            this.vertical.render(this.horizontal.getTextureBuffer());
        }
    }

    public final int getResult() {
        return this.vertical.getTextureBuffer();
    }

    public final void cleanUp() {
        this.horizontal.cleanUp();
        this.vertical.cleanUp();
    }
}
