package quepierts.iter.client.renderer;

import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.client.object.Camera;
import quepierts.iter.client.object.ILight;
import quepierts.iter.client.object.Renderable;
import quepierts.iter.client.resource.shader.Program;
import quepierts.iter.util.math.Matrix;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class Renderer {
    protected final Program shader;
    protected final RenderQueue queueRender;

    public Renderer(Program shader) {
        this.shader = shader;
        this.queueRender = new RenderQueue();
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.shader.use();
        this.shader.setMatrix("projectionMatrix", projectionMatrix);
        this.shader.stop();
    }

    public void addToQueue(Renderable renderable) {
        this.queueRender.addToQueue(renderable);
    }

    public void addToQueue(Collection<Renderable> renderableCollection) {
        for (Renderable renderable : renderableCollection) {
            this.queueRender.addToQueue(renderable);
        }
    }

    public void addToQueue(Renderable... renderableList) {
        for (Renderable renderable : renderableList) {
            this.queueRender.addToQueue(renderable);
        }
    }

    public void render(Camera camera, ILight light) {
        Vector3f cameraPos = camera.getPosition();

        shader.use();
        shader.setMatrix("viewMatrix", Matrix.createViewMatrixLookAt(camera));

        this.setUniform(camera, light, cameraPos);
        this.renderQueue(queueRender.getQueue());

        queueRender.clear();
    }

    protected void renderQueue(Map<Mesh, List<Renderable>> queue) {
        for (Mesh model : queue.keySet()) {
            this.prepareModelData(model);
            List<Renderable> renderableList = queue.get(model);
            for (Renderable renderable : renderableList) {
                this.prepareRenderable(renderable);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            this.unbind();
        }
    }

    protected void prepareModelData(Mesh model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        this.setUniform(model);
    }

    protected void prepareRenderable(Renderable renderable) {
        Matrix4f matrix4f = Matrix.createTransformMatrix(renderable.getPosition(), renderable.getRotation(), 1);
        shader.setMatrix("transformationMatrix", matrix4f);

        this.setUniform(renderable);
        shader.setVector("color", renderable.getColor());
    }

    protected void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    protected abstract void setUniform(Camera camera, ILight light, Vector3f cameraPos);

    protected abstract void setUniform(Mesh model);

    protected abstract void setUniform(Renderable renderable);
}
