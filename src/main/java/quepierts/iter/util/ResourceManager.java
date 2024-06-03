package quepierts.iter.util;

import quepierts.iter.client.resource.ResourceID;

import java.util.*;

public abstract class ResourceManager<T> {
    private static final List<ResourceManager> managers = new ArrayList<>();

    public static void loadAll() {
        for (ResourceManager manager : managers) {
            manager.load();
        }
    }

    public static void clearAll() {
        for (ResourceManager manager : managers) {
            manager.cleanUp();
        }
    }
    //The list tells the game what should it load
    protected final List<ResourceID> loadList;

    //Loaded resources
    protected final Map<String, T> resources;
    protected T def;

    public ResourceManager() {
        loadList = new ArrayList<>();
        resources = new HashMap<>();

        managers.add(this);
    }

    public Set<String> list() {
        return resources.keySet();
    }

    //Add the thing should load during initialization
    public void register(ResourceID id) {
        loadList.add(id);
    }

    public void add(String id, T t) {
        resources.put(id, t);
    }

    //Get loaded resources by name
    public T get(String id) {
        return resources.get(id);
    }

    public T getDefault() {
        return def;
    }

    public final void init() {
        //Load resources
        load();

        //After finish loading, clear the list
        loadList.clear();
    }

    protected abstract void load();

    protected abstract void cleanUp();
}
