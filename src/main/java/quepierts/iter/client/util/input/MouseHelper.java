package quepierts.iter.client.util.input;

import quepierts.iter.client.Iter;
import quepierts.iter.client.event.MouseEvent;
import quepierts.iter.observer.SubscribeManager;
import org.lwjgl.glfw.GLFW;

public class MouseHelper {
    private final Iter iter;
    private final long window;
    private double posX;
    private double posY;
    private double lastPosX;
    private double lastPosY;

    private double scrollX;
    private double scrollY;

    private final boolean[] state;
    private boolean dragging;

    public MouseHelper(Iter iter, long window) {
        this.iter = iter;
        this.window = window;
        lastPosX = 0;
        lastPosY = 0;
        posX = 0;
        posY = 0;

        scrollX = 0;
        scrollY = 0;

        state = new boolean[3];
    }

    public void mousePositionCallBack(long window, double posX, double posY) {
        this.lastPosX = this.posX;
        this.lastPosY = this.posY;
        this.posX = posX;
        this.posY = posY;

        this.dragging = this.state[0] || this.state[1] || this.state[2];

        SubscribeManager.publish(new MouseEvent.Update(posX, posY, getDY(), getDX()));


    }

    public void mouseButtonCallBack(long window, int button, int action, int mods) {
        switch (action) {
            case GLFW.GLFW_PRESS:
                if (button < this.state.length) {
                    this.state[button] = true;

                    SubscribeManager.publish(new MouseEvent.Click(posX, posY, button));
                }
                break;
            case GLFW.GLFW_RELEASE:
                if (button < this.state.length) {
                    this.state[button] = false;
                    this.dragging = false;

                    SubscribeManager.publish(new MouseEvent.Release(posX, posY, button));
                }
                break;
        }
    }

    public void updateMouseScroll(long window, double x, double y) {
        scrollX = x;
        scrollY = y;

        SubscribeManager.publish(new MouseEvent.Scroll(posX, posY, y));
    }

    public void updateMouse() {
        if (lastPosX != posX || lastPosY != posY) {
            lastPosX = posX;
            lastPosY = posY;
        }
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getLastPosX() {
        return lastPosX;
    }

    public double getLastPosY() {
        return lastPosY;
    }

    public double getDX() {
        double dx = posX - lastPosX;
        return dx;
    }

    public double getDY() {
        double dy = posY - lastPosY;
        return dy;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isLeftPressed() {
        return state[0];
    }

    public boolean isRightPressed() {
        return state[1];
    }

    public boolean isMiddlePressed() {
        return state[2];
    }

    public boolean[] getState() {
        return state;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }
}
