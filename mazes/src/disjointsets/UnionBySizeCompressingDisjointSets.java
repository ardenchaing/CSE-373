package disjointsets;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A quick-union-by-size data structure with path compression.
 *
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;

    private final HashMap<T, Integer> ids;
    private int size;

    public UnionBySizeCompressingDisjointSets() {
        this.ids = new HashMap<>();
        this.size = 0;
        this.pointers = new ArrayList<>();
    }

    @Override
    public void makeSet(T item) {
        this.ids.put(item, this.size);
        this.pointers.add(this.size, -1);
        this.size++;
    }

    @Override
    public int findSet(T item) {
        Integer index = this.ids.get(item);
        if (index == null) {
            throw new IllegalArgumentException(item + " is not in any set.");
        }
        while (pointers.get(index) >= 0) {
            index = pointers.get(index);
        }
        Integer compression = this.ids.get(item);
        Integer temp = compression;
        while (compression != index) {
            pointers.set(compression, index);
            compression = pointers.get(temp);
            temp = compression;
        }
        return index;
    }

    @Override
    public boolean union(T item1, T item2) {
        int id1 = findSet(item1);
        int id2 = findSet(item2);

        if (id1 == id2) {
            return false;
        }

        if (pointers.get(id1) < pointers.get(id2)) {
            pointers.set(id1, pointers.get(id1) + pointers.get(id2));
            pointers.set(id2, id1);
        } else if (pointers.get(id1) > pointers.get(id2)) {
            pointers.set(id2, pointers.get(id1) + pointers.get(id2));
            pointers.set(id1, id2);
        } else if (id1 < id2) {
            pointers.set(id1, pointers.get(id1) + pointers.get(id2));
            pointers.set(id2, id1);
        } else {
            pointers.set(id2, pointers.get(id1) + pointers.get(id2));
            pointers.set(id1, id2);
        }

        return true;
    }
}
