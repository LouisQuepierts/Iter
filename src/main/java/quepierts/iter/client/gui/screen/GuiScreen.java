package quepierts.iter.client.gui.screen;

import quepierts.iter.client.gui.Gui;
import quepierts.iter.client.gui.component.Component;
import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.util.input.MouseHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiScreen extends Gui {
    protected final List<ComponentInteractable> buttonList;
    protected final MouseHelper mouseHelper;

    public GuiScreen() {
        buttonList = new ArrayList<>();
        mouseHelper = iter.mouseHelper;
    }

    @Override
    protected void add(Component component) {
        if (component instanceof ComponentInteractable) {
            this.buttonList.add((ComponentInteractable) component);
        } else {
            super.add(component);
        }
    }

    public void click(double posX, double posY, int button) {
        for (ComponentInteractable component : buttonList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                component.click(mouseHelper, posX, posY, button);
            }
        }
    }

    public void release(double posX, double posY, int button) {
        for (ComponentInteractable component : buttonList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                component.release(mouseHelper, posX, posY, button);
            }
        }
    }

    public void scroll(double posX, double posY, double scrollY) {
        for (ComponentInteractable component : buttonList) {
            component.scroll(mouseHelper, posX, posY, scrollY);
        }
    }

    @Override
    public void updateByMouse(double posX, double posY, double dx, double dy) {
        for (ComponentInteractable component : buttonList) {
            component.update(mouseHelper, posX, posY, dx, dy);
        }
    }

    @Override
    public void draw() {
        for (Component component : componentList) {
            component.draw();
        }

        for (ComponentInteractable component : buttonList) {
            component.draw();
        }
    }
}
