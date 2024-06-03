package quepierts.iter.development;

import quepierts.iter.client.Iter;
import quepierts.iter.client.gui.component.CTextInput;
import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.gui.component.button.Button;
import quepierts.iter.client.gui.component.window.CWindow;
import quepierts.iter.client.gui.screen.Screen;
import quepierts.iter.client.scene.SceneManager;
import quepierts.iter.client.util.input.MouseHelper;

public class WindowAddScene extends CWindow {
    private SceneManager sceneManager;
    private CTextInput textInput = new CTextInput(0, 20, 240, 1f, "New Scene");

    public WindowAddScene(SceneManager sceneManager, Screen screen) {
        super(Iter.getWidth() / 2 - 120, Iter.getHeight() / 2 - 140, 240, 200, screen);

        this.sceneManager = sceneManager;
        this.add(textInput);
        Button createScene = new Button(0, 70, 240, 32, "Confirm", 1f);
        createScene.bindMethod(this::addScene);
        this.add(createScene);

        visible = true;
        enable = false;
    }

    private void addScene(Object o) {
        sceneManager.createScene(textInput.getText());
        textInput.setText("");
        this.setEnable(false);
        screen.tryRemoveWindow(this);
    }

    @Override
    protected void drawHead() {

        super.drawHead();
    }

    @Override
    protected boolean clickComponents(MouseHelper mouseHelper, double posX, double posY, int button) {

        return super.clickComponents(mouseHelper, posX, posY, button);
    }

    @Override
    protected boolean clickHead(MouseHelper mouseHelper, double posX, double posY, int button) {
        if (buttonClose.intersects((int) posX - this.posX - width, (int) posY - this.posY) && closable) {
            shouldClose = true;
            enable = false;
            return true;
        }

        return false;
    }

    @Override
    public void charInput(char c) {
        for (ComponentInteractable componentInteractable : interactsList) {
            componentInteractable.charInput(c);
        }
    }

    @Override
    public void keyInput(int key, boolean pressed) {
        for (ComponentInteractable componentInteractable : interactsList) {
            componentInteractable.keyInput(key, pressed);
        }
    }
}
