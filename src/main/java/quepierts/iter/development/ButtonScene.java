package quepierts.iter.development;

import quepierts.iter.client.gui.component.button.Button;
import quepierts.iter.client.renderer.screen.ScreenRenderer;
import quepierts.iter.client.scene.Scene;
import quepierts.iter.client.scene.SceneManager;
import quepierts.iter.client.util.input.MouseHelper;
import quepierts.iter.client.util.font.FontUtils;
import quepierts.iter.util.math.Color;

public class ButtonScene extends Button {
    private final SceneManager sceneManager;
    private final Scene scene;
    private final int sceneIndex;
    public ButtonScene(SceneManager sceneManager, int sceneIndex, int width) {
        super(0, 0, width, 80);

        this.sceneManager = sceneManager;
        this.sceneIndex = sceneIndex;

        this.scene = sceneManager.getSceneList().get(sceneIndex);
    }

    @Override
    protected boolean doubleClick(MouseHelper mouseHelper, double posX, double posY, int button) {
        sceneManager.select(sceneIndex);
        return true;
    }

    @Override
    public void draw(float posX, float posY, int limitX, int limitY, int limitWidth, int limitHeight) {
        if (hovered || sceneManager.getSelectedIndex() == sceneIndex) {
            ScreenRenderer.glDrawQuad(this.posX + posX, this.posY + posY, width, 24, COLOR_G100);
        }

        FontUtils.drawString(scene.getSceneID(), this.posX + posX + 2, this.posY + posY, Color.WHITE, 1f);
    }
}
