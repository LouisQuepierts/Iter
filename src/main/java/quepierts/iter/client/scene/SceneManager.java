package quepierts.iter.client.scene;

import quepierts.iter.client.EnumRenderType;
import quepierts.iter.client.object.Object;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private final List<Scene> sceneList;
    private int selectedIndex;

    public SceneManager() {
        this.sceneList = new ArrayList<>();
        this.selectedIndex = 0;
    }

    public void load() {
        if (this.sceneList.isEmpty()) {
            Scene scene = new Scene("basic");
            scene.addObject(EnumRenderType.NORMAL, new Object("scene"));
            this.sceneList.add(scene);
        }
    }

    public void createScene(String sceneID) {
        this.sceneList.add(new Scene(sceneID));
    }

    public boolean removeScene(String sceneID) {
        for (int i = 0; i < this.sceneList.size(); i++) {
            if (sceneList.get(i).equals(sceneID)) {
                sceneList.remove(i);
                return true;
            }
        }

        return false;
    }

    public void select(int index) {
        if (sceneList.size() + 1 < index) {
            this.selectedIndex = this.sceneList.size() - 1;
        } else this.selectedIndex = Math.max(index, 0);
    }

    public Scene getCurrentScene() {
        return this.sceneList.get(this.selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }
}
