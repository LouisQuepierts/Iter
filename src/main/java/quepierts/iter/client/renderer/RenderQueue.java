package quepierts.iter.client.renderer;

import quepierts.iter.client.resource.model.Model;
import quepierts.iter.client.resource.model.Mesh;
import quepierts.iter.client.object.Renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderQueue {
    private static final Map<Mesh, List<Renderable>> queue = new HashMap<>();

    public RenderQueue() {
    }

    public Map<Mesh, List<Renderable>> getQueue() {
        return queue;
    }

    public void addToQueue(Renderable renderable) {
        Model model = renderable.getModel();
        List<Renderable> renderList;

        for (Mesh modelData : model.getModelDataList()) {
            renderList = new ArrayList<>();
            if (queue.containsKey(modelData)) {
                renderList = queue.get(modelData);
            }

            renderList.add(renderable);
            queue.put(modelData, renderList);
        }

    }

    public void clear() {
        queue.clear();
    }

}
