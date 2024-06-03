package quepierts.iter.client.gui.component;

import quepierts.iter.client.util.input.MouseHelper;

import java.util.function.Consumer;

public abstract class ComponentInteractable extends Component {
    protected boolean hovered = false;
    protected boolean enable = true;
    protected Consumer<Object> consumer;

    public ComponentInteractable(int posX, int posY, int width, int height, int r, int g, int b, int a) {
        super(posX, posY, width, height, r, g, b, a);
    }

    public ComponentInteractable(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        this.hovered = (posX > this.posX && posX < this.posX + this.width && posY > this.posY && posY < this.posY + height);
    }

    public ComponentInteractable bindMethod(Consumer<Object> consumer) {
        this.consumer = consumer;

        return this;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public abstract boolean click(MouseHelper mouseHelper, double posX, double posY, int button);

    public abstract void release(MouseHelper mouseHelper, double posX, double posY, int button);

    public abstract boolean scroll(MouseHelper mouseHelper, double posX, double posY, double roll);

    public void charInput(char c) {}

    public void keyInput(int key, boolean pressed) {}

    public void update() {}
}
