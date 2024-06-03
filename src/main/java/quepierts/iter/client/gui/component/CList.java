package quepierts.iter.client.gui.component;

import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class CList extends ComponentInteractable {
    protected List<ComponentInteractable> interactsList = new ArrayList<>();
    protected boolean scrollHovered = false;

    protected int maxAmount;
    protected int scrollSize;
    protected float percent;

    public CList(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);

        scrollSize = this.height / 10;
        maxAmount = this.height - 4 - scrollSize;
    }

    @Override
    public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
        posY += percent * maxAmount - this.posY;
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
    public void release(MouseHelper mouseHelper, double posX, double posY, int button) {
        posY += percent * maxAmount - this.posY;
        posX -= this.posX;
        for (ComponentInteractable component : interactsList) {
            if (posX > component.getPosX() && posY > component.getPosY()) {
                component.release(mouseHelper, posX, posY, button);
            }

            posY -= component.getHeightPos();
            if (posY < 0) {
                break;
            }
        }
    }

    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        if (scrollHovered) {
            this.percent = (float) MathHelper.limit(this.percent - roll / 20, 1, 0);
            return true;
        } else {
            posY += percent * maxAmount;
            for (ComponentInteractable component : interactsList) {
                if (posX > component.getPosX() && posY > component.getPosY()) {
                    if (component.scroll(mouseHelper, posX - this.posX, posY - this.posY, roll)) {
                        return true;
                    }
                }

                posY -= component.getHeightPos();

                if (posY < 0) {
                    break;
                }
            }
        }

        return false;
    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        super.update(mouseHelper, posX, posY, dx, dy);

        if (hovered) {
            posY += percent * maxAmount;
            for (ComponentInteractable component : interactsList) {
                component.update(mouseHelper, posX, posY, dx, dy);
                posY -= component.getHeightPos();

                if (posY < 0) {
                    break;
                }
            }
        }

        scrollHovered = posX > this.posX + width - 8 && posX < this.posX + width;
    }

    @Override
    public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
        List<ScreenRenderer.RenderInfo> renderInfo = new ArrayList<>();
        ScreenRenderer.glDrawQuad(this.posX + posX + width - 8, this.posY + posY + 4 + percent * maxAmount, 6, scrollSize, COLOR_G90);

        int offsetY = (int) (this.posY + percent * maxAmount);
        for (ComponentInteractable component : interactsList) {
            component.draw(this.posX + posX, this.posY + offsetY + posY, this.posX, this.posY, this.width, this.height);
            offsetY += component.getHeight();
        }

        super.draw(posX, posY, limitX, limitY, limitWidth, limitHeight);
    }

    protected void drawContains() {

    }
}
