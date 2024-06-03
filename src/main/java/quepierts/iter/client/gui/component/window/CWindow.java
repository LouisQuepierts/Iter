package quepierts.iter.client.gui.component.window;

import quepierts.iter.client.gui.component.Component;
import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.gui.screen.Screen;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.math.Color;
import quepierts.iter.util.tree.Quad;

import java.util.ArrayList;
import java.util.List;

public abstract class CWindow extends ComponentInteractable {
    protected static final Quad buttonClose = new Quad(-24, 8, 16, 16);
    protected static final int COLOR_CLOSE = Color.getColor(220, 53, 60, 255);

    protected final List<Component> componentList;
    protected final List<ComponentInteractable> interactsList;
    protected final Screen screen;
    protected boolean shouldClose = false;
    protected boolean tabHovered = false;
    protected boolean insideHovered = false;
    protected boolean dragging = false;

    protected boolean closable = true;
    protected boolean movable = true;

    protected int topHeight = 32;

    public CWindow(int posX, int posY, int width, int height, Screen screen) {
        super(posX, posY, width, height);

        componentList = new ArrayList<>();
        interactsList = new ArrayList<>();

        this.screen = screen;
    }

    protected void add(Component component) {
        if (component instanceof ComponentInteractable) {
            this.interactsList.add((ComponentInteractable) component);
        } else {
            this.componentList.add(component);
        }
    }

    @Override
    public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
        if (hovered) {
            dragging = true;

            if (!tabHovered) {
                return clickComponents(mouseHelper, posX, posY, button);
            } else {
                return clickHead(mouseHelper, posX, posY, button);
            }
        }

        return false;
    }

    @Override
    public void release(MouseHelper mouseHelper, double posX, double posY, int button) {
        dragging = false;
        if (!tabHovered) {
            posX -= this.posX;
            posY -= this.posY + topHeight;

            releaseComponents(mouseHelper, posX, posY, button);
        }
    }


    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        if (!tabHovered) {
            return scrollComponents(mouseHelper, posX - this.posX, posY - this.posY - topHeight, roll);
        }

        return false;
    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        if (!tabHovered) {
            if (hovered) {
                updateComponents(mouseHelper, posX - this.posX, posY - this.posY - topHeight, dx, dy);
            } else {
                dragging = false;
            }
        } else {
            if (dragging && movable) {
                this.posX += dx;
                this.posY += dy;
            }
        }

        super.update(mouseHelper, posX, posY, dx, dy);

        this.tabHovered = this.hovered && posY < this.posY + topHeight;
    }



    protected void updateComponents(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        for (ComponentInteractable component : interactsList) {
            component.update(mouseHelper, posX, posY, dx, dy);
        }

        insideHovered = posX > this.posX + 2 && posX < this.posX + width - 2 && posY > this.posY + 2 && posY < this.posX + height - 2;
    }

    protected boolean clickComponents(MouseHelper mouseHelper, double posX, double posY, int button) {
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                if (component.click(mouseHelper, posX, posY, button)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean clickHead(MouseHelper mouseHelper, double posX, double posY, int button) {
        if (buttonClose.intersects((int) posX - this.posX - width, (int) posY - this.posY) && closable) {
            shouldClose = true;
            return true;
        }

        return false;
    }

    protected void releaseComponents(MouseHelper mouseHelper, double posX, double posY, int button) {
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                component.release(mouseHelper, posX, posY, button);
            }
        }
    }

    protected boolean scrollComponents(MouseHelper mouseHelper, double posX, double posY, double roll) {
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                if (component.scroll(mouseHelper, posX - this.posX, posY - this.posY - topHeight, roll)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void draw(float posX, float posY,
                                                int limitX, int limitY, int limitWidth, int limitHeight) {
        ScreenRenderer.glDrawFramedQuad(this.posX, this.posY, width, height, COLOR_G90, COLOR_G60);

        drawContains();

        drawHead();
    }

    protected void drawHead() {
        ScreenRenderer.glDrawFramedQuad(posX, posY, width, topHeight, COLOR_G90, COLOR_G40);
        if (closable) {
            ScreenRenderer.glDrawQuad(posX + width - 24, posY + 8, 16, 16, COLOR_CLOSE);
        }
    }

    protected void drawContains() {
        for (Component component : componentList) {
            if (component.isVisible()) {
                component.draw(posX, posY + topHeight, this.posX, this.posY, this.width, this.height);
            }
        }

        for (ComponentInteractable component : interactsList) {
            if (component.isVisible()) {
                component.draw(posX, posY + topHeight, this.posX, this.posY, this.width, this.height);
            }
        }
    }

    public boolean isShouldClose() {
        return shouldClose;
    }

    public void update() {
        for (ComponentInteractable componentInteractable : interactsList) {
            componentInteractable.update();
        }
    }
}
