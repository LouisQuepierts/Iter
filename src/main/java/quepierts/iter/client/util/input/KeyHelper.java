package quepierts.iter.client.util.input;

import quepierts.iter.client.event.CharEvent;
import quepierts.iter.client.event.KeyEvent;
import quepierts.iter.observer.SubscribeManager;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyHelper {
    private final long window;
    private boolean[] pressedKeys;

    public KeyHelper(long window) {
        this.window = window;
        pressedKeys = new boolean[350];
    }

    public void updateKeyInput(long window, int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW_PRESS:
                if (!pressedKeys[key]) {
                    SubscribeManager.publish(new KeyEvent(key, true));
                }
                pressedKeys[key] = true;
                break;
            case GLFW_RELEASE:
                pressedKeys[key] = false;
                SubscribeManager.publish(new KeyEvent(key, false));
                break;
        }
    }



    public boolean isKeyPressed(int key) {
        return pressedKeys[key];
    }

    public void charInput(long window, int i) {
        char c = (char) i;
        SubscribeManager.publish(new CharEvent(c));
    }
}
