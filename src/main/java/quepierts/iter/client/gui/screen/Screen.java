package quepierts.iter.client.gui.screen;

import quepierts.iter.client.Iter;
import quepierts.iter.client.gui.Gui;
import quepierts.iter.client.gui.component.Component;
import quepierts.iter.client.gui.component.window.CWindow;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.development.WindowEdit;
import quepierts.iter.development.WindowSceneManager;
import quepierts.iter.util.IterLogManager;

import java.util.ArrayList;
import java.util.List;

public final class Screen extends Gui {
    private final List<CWindow> cWindows = new ArrayList<>();
    private final MouseHelper mouseHelper;

    public Screen() {
        mouseHelper = iter.mouseHelper;
        addWindow(new WindowEdit(this));
        addWindow(new WindowSceneManager(Iter.getInstance().sceneManager, this));
    }

    public void tryRemoveWindow(CWindow cWindow) {
        boolean flag = this.cWindows.remove(cWindow);
    }

    public void addWindow(CWindow cWindow) {
        this.cWindows.add(cWindow);
    }

    public boolean containsWindow(CWindow cWindow) {
        return this.cWindows.contains(cWindow);
    }

    public void click(double posX, double posY, int button) {
        for (int i = cWindows.size() - 1; i > -1; i--) {
            CWindow window = cWindows.get(i);
            if (window.isEnable() && posX > window.getPosX() && posY > window.getPosY() && posX < window.getWidthPos() && posY < window.getHeightPos()) {
                window.click(mouseHelper, posX, posY, button);
                /*
                 * When click a window, try to make that window to the first
                 * */
                cWindows.remove(window);

                if (window.isShouldClose()) {
                    IterLogManager.getLogger().info(cWindows.size());
                    return;
                }

                cWindows.add(window);

                break;
            }
        }
    }

    public void release(double posX, double posY, int button) {
        for (CWindow window : cWindows) {
            if (window.isEnable() && posX > window.getPosX() && posY > window.getPosY()) {
                window.release(mouseHelper, posX, posY, button);
            }
        }
    }

    public void scroll(double posX, double posY, double scrollY) {
        for (CWindow window : cWindows) {
            if (window.isEnable()) {
                window.scroll(mouseHelper, posX, posY, scrollY);
            }
        }
    }

    @Override
    public void updateByMouse(double posX, double posY, double dx, double dy) {
        for (CWindow window : cWindows) {
            if (window.isEnable()) {
                window.update(mouseHelper, posX, posY, dx, dy);
            }
        }
    }

    public void charInput(char c) {
        for (int i = cWindows.size() - 1; i > -1; i--) {
            CWindow window = cWindows.get(i);
            if (window.isEnable()) {
                window.charInput(c);
                return;
            }
        }
    }

    public void keyInput(int key, boolean pressed) {
        for (int i = cWindows.size() - 1; i > -1; i--) {
            CWindow window = cWindows.get(i);
            if (window.isEnable()) {
                window.keyInput(key, pressed);
                return;
            }
        }
    }

    @Override
    public void update() {
        for (CWindow window : cWindows) {
            if (window.isEnable()) {
                window.update();
            }
        }
    }

    @Override
    public void draw() {
        for (Component component : this.componentList) {
            if (component.isVisible()) {
                component.draw();
            }
        }

        for (CWindow cWindow : this.cWindows) {
            if (cWindow.isVisible()) {
                cWindow.draw();
            }
        }
    }

    @Override
    public void cleanUp() {

    }
}
