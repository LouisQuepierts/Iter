package quepierts.iter.util.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadTree<T extends Quad> implements ITree<T, Quad> {
    private static final int MAX_OBJECT_AMOUNT = 10;
    private static final int MAX_DEEP = 5;
    
    private final int deep;
    private final Quad bound;
    
    private final List<QuadTree<T>> branches;
    private final Set<T> objects;

    public QuadTree(Quad bound) {
        this.deep = 0;
        this.bound = bound;
        
        this.branches = new ArrayList<>(4);
        this.objects = new HashSet<>();
    }
    
    private QuadTree(Quad bound, int deep) {
        this.deep = deep;
        this.bound = bound;

        this.branches = new ArrayList<>(4);
        this.objects = new HashSet<>();
    }

    @Override
    public boolean insert(T t) {
        if (!bound.intersects(t)) {
            return false;
        }
        
        if (branches.isEmpty()) {
            objects.add(t);
            if (objects.size() > MAX_OBJECT_AMOUNT && deep < MAX_DEEP) {
                split();
            }
        } else {
            int index = getIndex(t);
            if (index != -1) {
                branches.get(index).insert(t);
            }
        }
        
        return true;
    }

    public int size() {
        return objects.size();
    }

    @Override
    public Set<T> retrieve(Set<T> set, Quad t) {
        if (!branches.isEmpty()) {
            int index = getIndex(t);

            if (index != -1) {
                branches.get(index).retrieve(set, t);
            }
        }

        set.addAll(objects);
        return set;
    }

    @Override
    public Set<T> getAll() {
        return objects;
    }

    @Override
    public void clear() {
        
    }
    
    private void split() {
        int widthHalf = bound.width / 2;
        int heightHalf = bound.height / 2;

        int x_mid = bound.posX + widthHalf;
        int y_mid = bound.posY + heightHalf;

        branches.add(new QuadTree<>(
                new Quad(
                        bound.posX,
                        bound.posY,
                        widthHalf,
                        heightHalf),
                deep + 1));

        branches.add(new QuadTree<>(
                new Quad(
                        x_mid,
                        bound.posY,
                        widthHalf,
                        heightHalf),
                deep + 1));

        branches.add(new QuadTree<>(
                new Quad(
                        bound.posX,
                        y_mid,
                        widthHalf,
                        heightHalf),
                deep + 1));

        branches.add(new QuadTree<>(
                new Quad(
                        x_mid,
                        y_mid,
                        widthHalf,
                        heightHalf),
                deep + 1));

        for (T object : objects) {
            insert(object);
        }
    }

    private int getIndex(Quad t) {
        int index = -1;

        int midVertical = bound.posX + (bound.width / 2);
        int midHorizontal = bound.posY + (bound.height / 2);

        boolean upper = (t.posY < midHorizontal && t.posY + t.height < midHorizontal);
        boolean lower = (t.posY > midHorizontal);

        if (t.posX > midVertical) {
            if (upper) {
                index = 0;
            } else if(lower) {
                index = 2;
            }
        } else if (t.posX < midVertical && t.posX + t.width < midVertical) {
            if (upper) {
                index = 1;
            } else if (lower) {
                index = 3;
            }
        }

        return index;
    }
}
