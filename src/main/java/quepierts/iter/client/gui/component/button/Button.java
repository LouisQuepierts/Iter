package quepierts.iter.client.gui.component.button;

import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.font.FontUtils;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.Time;
import quepierts.iter.util.math.Color;

public class Button extends ComponentInteractable {
    private double lastClickTime;
    private String text;
    private float textScale = 1;

    public Button(int posX, int posY, int width, int height, String text, float textScale) {
        super(posX, posY, width, height);

        this.text = text;
        this.textScale = textScale;
    }

    public Button(int posX, int posY, int width, int height, int r, int g, int b, int a) {
        super(posX, posY, width, height, r, g, b, a);
    }

    public Button(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    @Override
    public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
        if (hovered) {
            double currentTime = Time.getCurrentTime();

            if (currentTime - lastClickTime < 1) {
                if (doubleClick(mouseHelper, posX, posY, button)) {
                    return true;
                }
            }

            lastClickTime = currentTime;
            if (consumer != null) {
                consumer.accept(mouseHelper);
                return true;
            }
        }

        return false;
    }

    protected boolean doubleClick(MouseHelper mouseHelper, double posX, double posY, int button) {
        return false;
    }

    @Override
    public void release(MouseHelper mouseHelper, double posX, double posY, int button) {

    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        super.update(mouseHelper, posX, posY, dx, dy);
    }

    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        return false;
    }

    @Override
    public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
        ScreenRenderer.glDrawQuad(posX + this.posX, posY + this.posY, width, height, (hovered ? COLOR_G100 : COLOR_G90));
        if (text != null && !text.isEmpty()) {
            FontUtils.drawStringCenter(text, posX + this.posX + width / 2, posY + this.posY, Color.WHITE, textScale);
        }
    }
}
