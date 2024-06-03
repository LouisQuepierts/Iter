package quepierts.iter.client.renderer;

import quepierts.iter.client.Iter;
import quepierts.iter.client.EnumRenderType;
import quepierts.iter.client.object.Camera;
import quepierts.iter.client.framebuffer.FrameBufferBloom;
import quepierts.iter.client.framebuffer.FrameBufferBloomFilter;
import quepierts.iter.client.framebuffer.GaussianBlur;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.scene.Scene;
import quepierts.iter.client.scene.SceneManager;
import quepierts.iter.client.util.CameraHelper;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public final List<Scene> sceneList;

    private final Iter iter;
    private final SceneManager sceneManager;

    private final Matrix4f projectionMatrix;

    private final FrameBufferBloom bloom;
    private final FrameBufferBloomFilter bloomFilter;

    private final GaussianBlur blur3f;
    private final GaussianBlur blur13f;
    private final GaussianBlur blur5f8x;

    private final RendererLightOrigin lightRenderer;
    private final RendererMultipleLight multipleLightRenderer;

    private float FOV = 70f;
    private float NEAR = 0.1f;
    private float FAR = 100f;

    public RenderEngine(Iter iter) {
        this.iter = iter;

        this.sceneList = new ArrayList<>();

        this.sceneManager = iter.sceneManager;

        this.bloom = new FrameBufferBloom();
        this.blur3f = new GaussianBlur(3, 1);
        this.blur13f = new GaussianBlur(13, 1);
        this.blur5f8x = new GaussianBlur(8);

        this.bloomFilter = new FrameBufferBloomFilter();

        this.projectionMatrix = new Matrix4f();
        this.updateProjectionMatrix();

        this.lightRenderer = new RendererLightOrigin(iter.shaderManager.get("light"), this.projectionMatrix);
        this.multipleLightRenderer = new RendererMultipleLight(iter.shaderManager.get("lightMultiple"), this.projectionMatrix);
    }


    public void render(CameraHelper cameraHelper) {
        Camera camera = cameraHelper.getCamera();
        this.renderScene(camera);

        this.renderGui();

    }

    public void cleanUp() {
        this.bloom.cleanUp();
        this.blur3f.cleanUp();
        this.blur13f.cleanUp();
        this.blur5f8x.cleanUp();
        this.bloomFilter.cleanUp();
    }

    private void renderScene(Camera camera) {
        Scene scene = sceneManager.getCurrentScene();

        scene.draw(this);

        Vector3f backgroundColor = scene.backgroundColor;
        GL20.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1.0f);
        GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        bloom.bind();
        GL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        multipleLightRenderer.render(camera, scene.ambientLight);
        lightRenderer.render(camera, null);

        bloomFilter.bind();
        bloomFilter.render(bloom.getTextureBuffers()[1]);

        blur5f8x.render(bloomFilter.getTextureBuffer());
        blur13f.render(blur5f8x.getResult());
        //blur3f.render(blur13f.getResult());

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        bloom.renderResult(this.blur13f.getResult(), camera);
    }

    public void drawObject(EnumRenderType renderType, List<Renderable> renderableList) {
        switch (renderType) {
            case NORMAL:
                this.multipleLightRenderer.addToQueue(renderableList);
                break;
            case LIGHT:
                this.lightRenderer.addToQueue(renderableList);
                break;
        }
    }

    private void renderGui() {
        iter.guiHelper.draw();
//        RendererScreen.drawQuad(0, 0, 600, 400);
        ScreenRenderer.drawScreen();
    }

    private void updateProjectionMatrix() {
        float aspectRatio = (float) Iter.getWidth() / (float) Iter.getHeight();
        projectionMatrix.setPerspective(FOV, aspectRatio, NEAR, FAR);
    }
}

