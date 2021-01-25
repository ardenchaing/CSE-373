package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    int size;

    // You may add extra fields or helper methods though!

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
        this.size = 0;
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (java.util.Objects.equals(this.entries[i].getKey(), key)) {
                    return this.entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V originalValue = null;
        for (int i = 0; i < this.size; i++) {
                if (java.util.Objects.equals(this.entries[i].getKey(), key)) {
                    originalValue = this.entries[i].getValue();
                    this.entries[i].setValue(value);
                    return originalValue;
                }
        }
        if (this.size == this.entries.length) {
            this.entries = resize();
        }
        this.entries[size] = new SimpleEntry<>(key, value);
        this.size++;
        return originalValue;
    }

    private SimpleEntry<K, V>[] resize() {
        ArrayMap<K, V> newArrayMap = new ArrayMap<>(this.entries.length * 2);
        newArrayMap.size = this.size;
        for (int i = 0; i < this.size; i++) {
            newArrayMap.entries[i] = this.entries[i];
        }
        return newArrayMap.entries;
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (java.util.Objects.equals(this.entries[i].getKey(), key)) {
                V toReturn = entries[i].getValue();
                if (this.size == 1) {
                    this.entries[i] = null;
                } else {
                    this.entries[i] = this.entries[size - 1];
                }
                this.size--;
                return toReturn;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        ArrayMap<K, V> newArrayMap = new ArrayMap<>(this.entries.length);
        this.entries = newArrayMap.entries;
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (java.util.Objects.equals(this.entries[i].getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries, this.size);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int index;
        private int size;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int size) {
            this.entries = entries;
            index = 0;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            SimpleEntry<K, V> copy = entries[index];
            index++;
            return new SimpleEntry<>(copy.getKey(), copy.getValue());
        }
    }
}
