package quepierts.iter.development;

import quepierts.iter.client.Iter;
import quepierts.iter.client.gui.component.button.Button;
import quepierts.iter.client.gui.component.window.CWindowVertical;
import quepierts.iter.client.gui.screen.Screen;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.scene.Scene;
import quepierts.iter.client.scene.SceneManager;
import quepierts.iter.client.util.input.MouseHelper;

import java.util.List;

public class WindowSceneManager extends CWindowVertical {
    private ButtonAdd buttonAdd;
    private SceneManager sceneManager;
    private WindowAddScene windowAddScene;
    public WindowSceneManager(SceneManager sceneManager, Screen screen) {
        super(0, 0, 200, Iter.getHeight(), screen);

        windowAddScene = new WindowAddScene(sceneManager, screen);

        movable = false;
        closable = false;
        this.sceneManager = sceneManager;

        this.topHeight = 58;

        init();
    }

    private void init() {
        List<Scene> sceneList = sceneManager.getSceneList();
        for (int i = 0; i < sceneList.size(); i++) {
            add(new ButtonScene(sceneManager, i, width - 20));
        }

        buttonAdd = new ButtonAdd(this, screen);

    }

    @Override
    protected void drawHead() {
        ScreenRenderer.glDrawFramedQuad(posX, posY, width, 32, COLOR_G90, COLOR_G40);
        buttonAdd.draw(posX, posY);
    }

    @Override
    protected boolean clickHead(MouseHelper mouseHelper, double posX, double posY, int button) {
        boolean flag = buttonAdd.click(mouseHelper, posX, posY, button);
        return super.clickHead(mouseHelper, posX, posY, button) || flag;
    }

    @Override
    public void update(MouseHelper mouseHelper, double posX, double posY, double dx, double dy) {
        buttonAdd.update(mouseHelper, posX, posY, dx, dy);
        super.update(mouseHelper, posX, posY, dx, dy);
    }

    private static class ButtonAdd extends Button {
        private final int textureAdd = Iter.instance.textureManager.get("gui/add").getId();
        private WindowSceneManager parent;
        private Screen screen;
        public ButtonAdd(WindowSceneManager parent, Screen screen) {
            super(0, 32, parent.width, 24);

            this.screen = screen;
            this.parent = parent;
        }

        @Override
        public boolean click(MouseHelper mouseHelper, double posX, double posY, int button) {
            if (hovered && enable) {
                WindowAddScene windowAddScene = parent.windowAddScene;
                if (!windowAddScene.isEnable()) {
                    screen.addWindow(windowAddScene);
                    windowAddScene.setEnable(true);
                    this.enable = false;

                    return true;
                }
            }

            return false;
        }

        @Override
        public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
            if (this.hovered && enable) {
                ScreenRenderer.glDrawQuad(posX, posY + this.posY, width, height, COLOR_G100);
            }

            ScreenRenderer.glDrawQuadTexture(posX + width / 2 - 16, posY + this.posY, 24, 24, textureAdd);
        }
    }

}
