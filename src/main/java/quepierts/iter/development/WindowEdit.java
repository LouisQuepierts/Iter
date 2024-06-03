package quepierts.iter.development;

import quepierts.iter.client.Iter;
import quepierts.iter.client.gui.component.CDragBar;
import quepierts.iter.client.gui.component.CTextInput;
import quepierts.iter.client.gui.component.ComponentInteractable;
import quepierts.iter.client.gui.component.window.CWindowVertical;
import quepierts.iter.client.gui.screen.Screen;
import org.joml.Vector3f;

public class WindowEdit extends CWindowVertical {
    public WindowEdit(Screen screen) {
        super(0, 0, 400, 200, screen);

        Vector3f backgroundColor = Iter.getInstance().sceneManager.getCurrentScene().backgroundColor;

        add(new CDragBar(0, 0, 300, 1, 0).bindMethod(var -> backgroundColor.x = (float) var));
        add(new CDragBar(0, 0, 300, 1, 0).bindMethod(var -> backgroundColor.y = (float) var));
        add(new CDragBar(0, 0, 300, 1, 0).bindMethod(var -> backgroundColor.z = (float) var));
        add(new CTextInput(0, 0, 300, 2));
    }

    @Override
    protected void drawHead() {
        super.drawHead();
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
