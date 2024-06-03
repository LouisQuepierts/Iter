package quepierts.iter.client.gui.component;

import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.client.util.font.FontUtils;
import quepierts.iter.util.math.Color;
import quepierts.iter.util.math.MathHelper;

public class CDragBar extends ComponentInteractable {
    private static final int COLOR_BAR = Color.getColor(65, 65, 65, 255);
    private static final int COLOR_RAIL = Color.getColor(70, 70, 70, 255);

    private final float max;
    private final float min;
    private final float bar_length;

    private float percent = 0;

    private boolean dragging = false;

    public CDragBar(int posX, int posY, int width, float max, float min) {
        super(posX, posY, width, 24);

        this.max = max;
        this.min = min;

        this.bar_length = width - 24;
    }

    public float getValue() {
        float varPercent = (max - min) * percent;

        return min + varPercent;
    }

    public void setPercent(float value) {
        percent = MathHelper.limit(value, 1.0f, 0.0f);

        if (this.consumer != null) {
            consumer.accept(getValue());
        }
    }

    @Override
    public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
        if (hovered) {
            dragging = true;

            double absPosX = posX - this.posX - 8;
            setPercent((float) (absPosX / bar_length));

            return true;
        }

        return false;
    }

    @Override
    public void release(MouseHelper mouseHelper, double posX, double posY, int button) {
        dragging = false;
    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        super.update(mouseHelper, posX, posY, dx, dy);

        if (!hovered) {
            dragging = false;
        }

        if (dragging) {
            double absPosX = posX - this.posX - 8;
            setPercent((float) (absPosX / bar_length));
        }
    }

    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        if (hovered && !dragging) {
            setPercent((float) (percent - roll / 100));

            return true;
        }

        return false;
    }

    @Override
    public void draw(float posX, float posY,
                                                int limitX, int limitY, int limitWidth, int limitHeight) {
        ScreenRenderer.glDrawQuad(this.posX + posX, this.posY + posY + 1, width, height - 1, COLOR_BAR);

        int width = this.width - 16;
        ScreenRenderer.glDrawQuad(this.posX + posX + 8, this.posY + posY + 10, width, height - 20, COLOR_RAIL);
        ScreenRenderer.glDrawQuad(this.posX + posX + 8 + bar_length * percent, this.posY + posY + 2, 8, height - 4, hovered ? COLOR_G100 : COLOR_G90);

        FontUtils.drawString(String.format("%.2f", getValue()), this.posX + posX + this.width + 8, this.posY + posY, Color.WHITE, 1f);
    }
}
