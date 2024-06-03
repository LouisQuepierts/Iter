package quepierts.iter.client.util;

import quepierts.iter.client.Iter;
import quepierts.iter.client.event.CharEvent;
import quepierts.iter.client.event.KeyEvent;
import quepierts.iter.client.event.MouseEvent;
import quepierts.iter.client.gui.screen.Screen;
import quepierts.iter.client.util.input.KeyHelper;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.observer.Event;
import quepierts.iter.observer.SubscribeManager;
import quepierts.iter.util.Time;

import static org.lwjgl.glfw.GLFW.*;

public class GuiHelper {
    private final Iter iter;
    private Screen screen;
    private boolean openGui;
    private double lastSwitchTime;
    private double lastUpdateTime = 0;
    private final MouseHelper mouseHelper;
    private final KeyHelper inputHelper;

    public GuiHelper(MouseHelper mouseHelper, KeyHelper inputHelper, Iter iter) {
        this.iter = iter;

        this.mouseHelper = mouseHelper;
        this.inputHelper = inputHelper;

        SubscribeManager.subscribe(this);
        SubscribeManager.subscribe(this::click, MouseEvent.Click.class);
    }

    public void init() {
        this.screen = new Screen();
        openGui = true;
    }

    public void openGui() {
        openGui = true;
        lastSwitchTime = Time.getCurrentTime();

        glfwSetInputMode(iter.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void closeGui() {
        openGui = false;
        lastSwitchTime = Time.getCurrentTime();

        glfwSetInputMode(iter.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void updateGui() {
        double currentTime = Time.getCurrentTime();
        if (currentTime - lastUpdateTime > 0.05) {
            if (openGui) {
                screen.update();
            }

            lastUpdateTime = currentTime;
        }
    }

    @Event.Subscribe
    public void click(MouseEvent.Click event) {
        if (openGui) {
            screen.click(event.posX, event.posY, event.button);
        }
    }

    @Event.Subscribe
    public void release(MouseEvent.Release event) {
        if (openGui) {
            screen.release(event.posX, event.posY, event.button);
        }
    }

    @Event.Subscribe
    public void scroll(MouseEvent.Scroll event) {
        if (openGui) {
            screen.scroll(event.posX, event.posY, event.scroll);
        }
    }

    @Event.Subscribe
    public void updateGui(MouseEvent.Update event) {
        if (openGui) {
            screen.updateByMouse(event.posX, event.posY, event.dx, event.dy);
        }
    }

    @Event.Subscribe
    public void charInput(CharEvent event) {
        if (openGui) {
            screen.charInput(event.c);
        }
    }

    @Event.Subscribe
    public void keyInput(KeyEvent event) {
        if (openGui) {
            screen.keyInput(event.key, event.press);
        }
    }

    public boolean isGuiOpened() {
        return openGui;
    }

    public double getTimeDelta() {
        return Time.getCurrentTime() - lastSwitchTime;
    }

    public void draw() {
        if (openGui) {
            screen.draw();
        }
    }

    public Screen getScreen() {
        return screen;
    }
}
