package quepierts.iter.client.renderer.screen;

import quepierts.iter.client.Iter;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.reflex.Execute;
import quepierts.iter.util.math.Color;
import quepierts.iter.util.math.Matrix;
import quepierts.iter.util.tree.Quad;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class ScreenRenderer {
    private static final Iter iter = Iter.getInstance();
    private static Mesh quad;
    private static Program shader;

    private static int empty;

    private static final int screenWidth = Iter.getWidth();
    private static final int screenHeight = Iter.getHeight();

    private static final List<Quad> glScissorCache = new ArrayList<>();
    private static final List<GLAction> actionCache = new ArrayList<>();

    private static final Vector4f color = new Vector4f();

    @Execute
    private static void init() {
        shader = iter.shaderManager.get("texture");
        quad = iter.modelManager.get("quad").getModelData();

        empty = iter.textureManager.getDefault().getId();
    }

    public static void glDrawFramedQuad(float posX, float posY, float width, float height, int colorFrame, int colorBase) {
        GLAction action = getAction();

        ((ActionDrawQuads) action).addQuad(posX - 1, posY - 1, width + 2, height + 2, colorFrame, empty);
        ((ActionDrawQuads) action).addQuad(posX, posY, width, height, colorBase, empty);
    }

    public static void glDrawQuad(float posX, float posY, float width, float height, int color) {
        glDrawQuadTexture(posX, posY, width, height, color, empty);
    }

    public static void glDrawQuadTexture(float posX, float posY, float width, float height, int texture) {
        glDrawQuadTexture(posX, posY, width, height, -1, texture);
    }

    public static void glDrawQuadTexture(float posX, float posY, float width, float height, int rgba, int texture) {
        GLAction action = getAction();

        ((ActionDrawQuads) action).addQuad(posX, posY, width, height, rgba, texture);
    }

    public static void glDrawQuadTexture(float posX, float posY, float width, float height,
                                         int rgba, int texture,
                                         float x1, float y1, float x2, float y2) {
        GLAction action = getAction();

        ((ActionDrawQuads) action).addQuad(posX, posY, width, height, rgba, texture);
        ((ActionDrawQuads) action).setUV(x1, y1, x2, y2);
    }

    private static GLAction getAction() {
        GLAction action;
        if (actionCache.isEmpty()) {
            action = new ActionDrawQuads();
            actionCache.add(action);
        } else {
            action = actionCache.get(actionCache.size() - 1);
            if (!(action instanceof ActionDrawQuads)) {
                action = new ActionDrawQuads();
                actionCache.add(action);
            }
        }

        return action;
    }

    public static void glScissor(boolean enable) {
        actionCache.add(new ActionSwitchGLScissor(enable));
    }

    public static void glPopScissor() {
        int size = glScissorCache.size();
        if (size > 1) {
            Quad quad = glScissorCache.get(size - 2);
            actionCache.add(new ActionGLScissor(quad.getPosX(), quad.getPosY(), quad.getWidth(), quad.getHeight()));
        } else {
            glScissorCache.clear();
            glScissor(false);
        }
    }

    public static void glPushScissor(int limitX, int limitY, int limitWidth, int limitHeight) {
        glScissorCache.add(new Quad(limitX, limitY, limitWidth, limitHeight));
        actionCache.add(new ActionGLScissor(limitX, limitY, limitWidth, limitHeight));
    }

    public static void draw(QuadInfo quadInfo) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, quadInfo.texture);

        Matrix4f transformMatrix = Matrix.createTransformMatrix(quadInfo.translate, quadInfo.scale);
        shader.setMatrix("transformMatrix", transformMatrix);
        shader.setVector("color", Color.getColor(quadInfo.color, color));
        shader.setVector("textureUV", quadInfo.uv);
        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
    }

    public static void drawScreen() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        shader.use();
        GL30.glBindVertexArray(quad.getVaoID());
        GL30.glEnableVertexAttribArray(0);

//        queue.forEach((texture, renderInfos) -> {
//            GL13.glActiveTexture(GL13.GL_TEXTURE0);
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
//
//            for (RenderInfo renderInfo : renderInfos) {
//                Vector4i limit = renderInfo.limit;
//                if (limit.w != -1) {
//                    GL11.glEnable(GL11.GL_SCISSOR_TEST);
//                    GL11.glScissor(limit.x, limit.y, limit.z, limit.w);
//                }
//                Matrix4f transformMatrix = Matrix.createTransformMatrix(renderInfo.translate, renderInfo.scale);
//                shader.setMatrix("transformMatrix", transformMatrix);
//                shader.setVector("color", Color.getColor(renderInfo.color, color));
//                shader.setVector("textureUV", renderInfo.uv);
//                GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
//
//                GL11.glDisable(GL11.GL_SCISSOR_TEST);
//            }
//        });

//        for (RenderInfo renderInfo : infoList) {
//            GL13.glActiveTexture(GL13.GL_TEXTURE0);
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderInfo.texture);
//
//            Vector4i limit = renderInfo.limit;
//            if (limit.w != -1) {
//                GL11.glEnable(GL11.GL_SCISSOR_TEST);
//                GL11.glScissor(limit.x, limit.y, limit.z, limit.w);
//            }
//            Matrix4f transformMatrix = Matrix.createTransformMatrix(renderInfo.translate, renderInfo.scale);
//            shader.setMatrix("transformMatrix", transformMatrix);
//            shader.setVector("color", Color.getColor(renderInfo.color, color));
//            shader.setVector("textureUV", renderInfo.uv);
//            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
//
//            GL11.glDisable(GL11.GL_SCISSOR_TEST);
//        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        for (GLAction glAction : actionCache) {
            glAction.execute();
        }

        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();

        actionCache.clear();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static final class RenderInfo {
        private final Vector3f translate;
        private final Vector3f scale;
        private final int color;
        private final int texture;

        private final Vector4i limit;
        private final Vector4f uv;

        private boolean disable = false;

        private RenderInfo(float posX, float posY, float width, float height, int color, int texture) {
            //Calculate absolute scale
            float widthLength = width / screenWidth;
            float heightLength = height / screenHeight;
            //Adjust the quad draw from top left to down right
            this.translate = new Vector3f((posX * 2 / screenWidth) - 1 + widthLength, 1 - (posY * 2 / screenHeight) - heightLength, 0);
            this.scale = new Vector3f(widthLength, heightLength, 1);
            this.color = color;
            this.texture = texture;

            this.limit = new Vector4i(-1);
            this.uv = new Vector4f(0, 0, 1, 1);
        }

        public RenderInfo translate(float posZ) {
            this.translate.add(0, 0, posZ);

            return this;
        }

        public RenderInfo limit(int limitX, int limitY, int limitWidth, int limitHeight) {
            this.limit.set(limitX, (int) (screenHeight - limitY - limitHeight), limitWidth, limitHeight);

            return this;
        }

        public RenderInfo textureUV(float x1, float y1, float x2, float y2) {
            this.uv.set(x1, y1, x2, y2);

            return this;
        }

        private void setDisable() {
            disable = true;
        }
    }
}
