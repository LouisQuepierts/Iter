package quepierts.iter.client.gui;

import quepierts.iter.client.Iter;
import quepierts.iter.client.gui.component.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui {
    protected static final Iter iter = Iter.getInstance();

    protected final List<Component> componentList;

    public Gui() {
        this.componentList = new ArrayList<>();
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    protected void add(Component component) {
        this.componentList.add(component);
    }

    public abstract void update();

    public abstract void updateByMouse(double posX, double posY, double dx, double dy);

    public abstract void draw();

    public abstract void cleanUp();
}
