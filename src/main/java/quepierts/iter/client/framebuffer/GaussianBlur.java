package quepierts.iter.client.framebuffer;

public class GaussianBlur {
    private final FrameBufferBlur horizontal;
    private final FrameBufferBlur vertical;

    public GaussianBlur(float blurFraction) {
        this.horizontal = new FrameBufferBlur(true, blurFraction);
        this.vertical = new FrameBufferBlur(false, blurFraction);
    }

    public GaussianBlur(int blurSize, float blurFraction) {
        this.horizontal = new FrameBufferBlur(blurSize, true, blurFraction);
        this.vertical = new FrameBufferBlur(blurSize,false, blurFraction);
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
