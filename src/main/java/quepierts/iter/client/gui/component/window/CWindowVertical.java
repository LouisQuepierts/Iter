package quepierts.iter.client.gui.component.window;

import quepierts.iter.client.gui.component.Component;
import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.gui.screen.Screen;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.math.MathHelper;

public abstract class CWindowVertical extends CWindow {
    protected boolean scrollHovered = false;

    protected int maxAmount;
    protected int scrollSize;
    protected float percent;

    public CWindowVertical(int posX, int posY, int width, int height, Screen screen) {
        super(posX, posY, width, height, screen);

        scrollSize = this.height / 10;
        maxAmount = this.height - topHeight - 4 - scrollSize;
    }

    @Override
    protected void add(Component component) {
        if (component instanceof ComponentInteractable) {
            component.setPosY(0);
            this.interactsList.add((ComponentInteractable) component);
        }
    }

    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        boolean flag = super.scroll(mouseHelper, posX, posY, roll);
        if (!flag) {
           this.percent = (float) MathHelper.limit(this.percent - roll / 20, 1, 0);
        }

        return true;
    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        scrollHovered = hovered && posX > this.posX + width - 10;

        float maxHeight = 0;
        for (ComponentInteractable componentInteractable : interactsList) {
            maxHeight = Math.max(maxHeight + componentInteractable.getHeight(), maxHeight);
        }

        scrollSize = height >= maxHeight ? height - 40 : (int) ((height / maxHeight) * height);
        maxAmount = height - 40 - scrollSize;

        super.update(mouseHelper, posX, posY, dx, dy);
    }

    @Override
    public boolean clickComponents(MouseHelper mouseHelper, double posX, double posY, int button) {
        posY += percent * maxAmount - this.posY - topHeight;
        posX -= this.posX;
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                if (component.click(mouseHelper, posX, posY, button)) {
                    return true;
                }
            }

            posY -= component.getHeightPos();
            if (posY < 0) {
                break;
            }
        }

        return false;
    }

    @Override
    public boolean scrollComponents(MouseHelper mouseHelper, double posX, double posY, double roll) {
        posY += percent * maxAmount;
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                if (component.scroll(mouseHelper, posX - this.posX, posY - this.posY - topHeight, roll)) {
                    return true;
                }
            }

            posY -= component.getHeightPos();

            if (posY < 0) {
                break;
            }
        }

        return false;
    }

    @Override
    protected void updateComponents(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        posY += percent * maxAmount;
        for (ComponentInteractable component : interactsList) {
            component.update(mouseHelper, posX, posY, dx, dy);
            posY -= component.getHeightPos();

            if (posY < 0) {
                break;
            }
        }

        insideHovered = posX > this.posX + 2 && posX < this.posX + width - 2 && posY > this.posY + 2 && posY < this.posX + height - 2;
    }

    @Override
    public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
        super.draw(posX, posY, limitX, limitY, limitWidth, limitHeight);

        ScreenRenderer.glDrawQuad(this.posX + width - 8, this.posY + topHeight + 4 + percent * maxAmount, 6, scrollSize, Component.COLOR_G90);
    }

    @Override
    protected void drawContains() {
        ScreenRenderer.glScissor(true);
        ScreenRenderer.glPushScissor(posX, posY, width, height);
        int offsetY = (int) (posY + topHeight - percent * maxAmount);
        for (ComponentInteractable component : interactsList) {
            component.draw(posX, offsetY, this.posX, this.posY, this.width, this.height);
            offsetY += component.getHeight();
        }
        ScreenRenderer.glPopScissor();
    }
}
