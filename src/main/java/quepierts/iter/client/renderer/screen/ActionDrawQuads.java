package quepierts.iter.client.renderer.screen;

import java.util.ArrayList;
import java.util.List;

public class ActionDrawQuads implements GLAction {
    private final List<QuadInfo> cache = new ArrayList<>();

    @Override
    public void execute() {
        for (QuadInfo quadInfo : cache) {
            ScreenRenderer.draw(quadInfo);
        }
    }

    public void addQuad(float posX, float posY, float width, float height, int color, int texture) {
        cache.add(new QuadInfo(posX, posY, width, height, color, texture));
    }

    public void setUV(float x1, float y1, float x2, float y2) {
        cache.get(cache.size() - 1).setUV(x1, y1, x2, y2);
    }

}
