package quepierts.iter.client.gui.component;

import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.util.font.FontUtils;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.util.Time;
import quepierts.iter.util.math.Color;
import org.lwjgl.glfw.GLFW;

public class CTextInput extends ComponentInteractable {
    private final boolean[] key = new boolean[3];
    private final float size;

    protected String text = "";
    protected int index = 0;

    public CTextInput(int posX, int posY, int width, float size) {
        super(posX, posY, width, (int) (20 * size + 1));

        this.size = size;
    }

    public CTextInput(int posX, int posY, int width, float size, String def) {
        super(posX, posY, width, (int) (20 * size + 1));

        this.text = def;
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.index = text.length();
    }

    @Override
    public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
        enable = hovered;
        return false;
    }

    @Override
    public void release(MouseHelper mouseHelper, double posX, double posY, int button) {}

    @Override
    public boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll) {
        return false;
    }

    @Override
    public void update() {
        if (key[0]) {
            if (text.length() > 0 && index > 0) {
                if (index == text.length()) {
                    text = text.substring(0, text.length() - 1);
                } else {
                    String part1 = text.substring(0, index - 1);
                    String part2 = text.substring(index);
                    text = part1 + part2;
                }

                --index;
            }
        } else if (key[1]) {
            if (index > 0) {
                --index;
            }
        } else if (key[2]) {
            if (text.length() > index) {
                ++index;
            }
        }
    }

    @Override
    public void charInput(char c) {
        if (enable) {
            if (text.length() == 0) {
                text = text + c;
            } else {
                String part1 = text.substring(0, index);
                String part2 = text.substring(index);
                text = part1 + c + part2;
            }
            ++index;
        }
    }

    @Override
    public void keyInput(int key, boolean pressed) {
        if (enable) {
            switch (key) {
                case GLFW.GLFW_KEY_BACKSPACE:
                    this.key[0] = pressed;
                    break;
                case GLFW.GLFW_KEY_LEFT:
                    this.key[1] = pressed;
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                    this.key[2] = pressed;
                    break;
            }
        }
    }

    @Override
    public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
        int length = FontUtils.getStringLength(text.substring(0, index), size);

        ScreenRenderer.glDrawFramedQuad(posX + this.posX, posY + this.posY, width, height, COLOR_G90, COLOR_G40);
        FontUtils.drawString(text, posX + this.posX + 1, posY + this.posY, Color.WHITE, size);
        if (Math.sin(Time.getCurrentTime() * 10) > 0 && enable) {
            ScreenRenderer.glDrawQuad(posX + this.posX + length, posY + this.posY + 2, size, 16 * size + 1, Color.WHITE);
        }
    }
}
