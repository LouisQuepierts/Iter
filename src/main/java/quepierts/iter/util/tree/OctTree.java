package quepierts.iter.util.tree;

import java.util.Set;

public class OctTree<T extends Area3f> implements ITree<T, Area3f> {

    @Override
    public boolean insert(T t) {
        return false;
    }

    @Override
    public Set<T> retrieve(Set<T> set, Area3f area3f) {
        return null;
    }

    @Override
    public Set<T> getAll() {
        return null;
    }

    @Override
    public void clear() {

    }
}
