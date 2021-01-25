package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * @see ExtrinsicMinPQ
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    HashMap map;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        map = new HashMap();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> itemForSwap = items.get(a);
        items.set(a, items.get(b));
        items.set(b, itemForSwap);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new PriorityNode<>(item, priority));
        int index = items.size() - 1;
        map.put(item, index);
        while (index > 1) {
            if (items.get(index / 2).getPriority() > items.get(index).getPriority()) {
                map.put(items.get(index).getItem(), index / 2);
                map.put(items.get(index / 2).getItem(), index);
                this.swap(index, index / 2);
                index = index / 2;
            } else {
                break;
            }
        }
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (items.size() < 2) {
            throw new java.util.NoSuchElementException();
        }
        return items.get(1).getItem();
    }

    @SuppressWarnings({"checkstyle:NeedBraces", "checkstyle:EmptyBlock", "checkstyle:WhitespaceAround"})
    @Override
    public T removeMin() {
        if (items.size() < 2) {
            throw new java.util.NoSuchElementException();
        }
        T itemRemoved = items.get(1).getItem();
        int index = 1;
        items.set(1, items.get(items.size() - 1));
        items.remove(items.size() - 1);
        while (index * 2 < items.size()) {
            if (index * 2 + 1 < items.size()) {
                if (items.get(index * 2).getPriority() < items.get(index).getPriority()
                    || items.get(index * 2 + 1).getPriority() < items.get(index).getPriority()) {
                    if (items.get(index * 2).getPriority() < items.get(index * 2 + 1).getPriority()) {
                        map.put(items.get(index).getItem(), index * 2);
                        map.put(items.get(index * 2).getItem(), index);
                        this.swap(index, index * 2);
                        index = index * 2;
                    } else {
                        map.put(items.get(index).getItem(), index * 2 + 1);
                        map.put(items.get(index * 2 + 1).getItem(), index);
                        this.swap(index, index * 2 + 1);
                        index = index * 2 + 1;
                    }
                } else {
                    break;
                }
            } else {
                if (items.get(index * 2).getPriority() < items.get(index).getPriority()) {
                    map.put(items.get(index).getItem(), index * 2);
                    map.put(items.get(index * 2).getItem(), index);
                    this.swap(index, index * 2);
                    index = index * 2;
                } else {
                    break;
                }
            }
        }
        map.remove(itemRemoved);
        return itemRemoved;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!map.containsKey(item)) {
            throw new java.util.NoSuchElementException();
        } else {
            int index = (Integer) map.get(item);
            items.set(index, new PriorityNode<>(item, priority));
            if (index > 1 && items.get(index / 2).getPriority() > priority) {
                while (index > 1) {
                    if (items.get(index / 2).getPriority() > priority) {
                        map.put(items.get(index).getItem(), index / 2);
                        map.put(items.get(index / 2).getItem(), index);
                        this.swap(index, index / 2);
                        index = index / 2;
                    } else {
                        break;
                    }
                }
            } else {
                while (index * 2 < items.size()) {
                    if (index * 2 + 1 < items.size()) {
                        if ((items.get(index * 2).getPriority() < items.get(index).getPriority()
                            || items.get(index * 2 + 1).getPriority() < items.get(index).getPriority())
                            && items.get(index * 2).getPriority() < items.get(index * 2 + 1).getPriority()) {
                            map.put(items.get(index).getItem(), index * 2);
                            map.put(items.get(index * 2).getItem(), index);
                            this.swap(index, index * 2);
                            index = index * 2;
                        } else if (items.get(index * 2 + 1).getPriority() < items.get(index).getPriority()
                            && items.get(index * 2).getPriority() > items.get(index * 2 + 1).getPriority()) {
                            map.put(items.get(index).getItem(), index * 2 + 1);
                            map.put(items.get(index * 2 + 1).getItem(), index);
                            this.swap(index, index * 2 + 1);
                            index = index * 2 + 1;
                        } else {
                            break;
                        }
                    } else {
                        if (items.get(index * 2).getPriority() < items.get(index).getPriority()) {
                            map.put(items.get(index).getItem(), index * 2);
                            map.put(items.get(index * 2).getItem(), index);
                            this.swap(index, index * 2);
                            index = index * 2;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        return items.size() - 1;
    }
}
