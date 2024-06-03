package quepierts.iter.client.renderer.screen;

import org.lwjgl.opengl.GL11;

public class ActionSwitchGLScissor implements GLAction {
    private final boolean enable;

    public ActionSwitchGLScissor(boolean state) {
        this.enable = state;
    }

    @Override
    public void execute() {
        if (enable) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        } else {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }
}
