package quepierts.iter.util.tree;

import java.util.Set;

public interface ITree<T, E> {
    boolean insert(T t);

    Set<T> retrieve(Set<T> set, E e);

    Set<T> getAll();

    void clear();
}
