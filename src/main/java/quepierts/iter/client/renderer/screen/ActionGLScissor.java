package quepierts.iter.client.renderer.screen;

import quepierts.iter.client.Iter;
import org.lwjgl.opengl.GL11;

public class ActionGLScissor implements GLAction {
    private static final int screenHeight = Iter.getHeight();
    private final int[] info = new int[4];

    public ActionGLScissor(int limitX, int limitY, int limitWidth, int limitHeight) {
        info[0] = limitX;
        info[1] = screenHeight - limitY - limitHeight;
        info[2] = limitWidth;
        info[3] = limitHeight;
    }

    @Override
    public void execute() {
        GL11.glScissor(info[0], info[1], info[2], info[3]);
    }
}
