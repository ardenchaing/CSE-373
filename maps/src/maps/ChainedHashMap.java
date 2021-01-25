
package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {

    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;
    double resizingFactor;
    int chainSize;
    int hashMapSize;
    int size;
    int hashCode;
    AbstractIterableMap<K, V> currentChain;
    Iterator<Map.Entry<K, V>> iter;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.resizingFactor = resizingLoadFactorThreshold;
        this.hashMapSize = initialChainCount;
        this.chainSize = chainInitialCapacity;
        this.chains = this.createArrayOfChains(this.hashMapSize);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        if (java.util.Objects.equals(null, key)) {
            hashCode = 0;
        } else {
            hashCode = java.lang.Math.abs(key.hashCode());
        }
        if (java.util.Objects.equals(null, this.chains[hashCode % this.hashMapSize])) {
            return null;
        } else {
            return this.chains[hashCode % this.hashMapSize].get(key);
        }
    }

    private void resize() {
        Entry<K, V> newEntry;
        AbstractIterableMap<K, V>[] mapforResize = this.chains.clone();
        this.hashMapSize = hashMapSize * 2;
        this.chains = this.createArrayOfChains(hashMapSize);
        this.size = 0;
        for (int i = 0; i < hashMapSize / 2; i++) {
            if (!java.util.Objects.equals(mapforResize[i], null)) {
                currentChain = mapforResize[i];
                iter = currentChain.entrySet().iterator();
                while (iter.hasNext()) {
                    newEntry = iter.next();
                    put(newEntry.getKey(), newEntry.getValue());
                    //newKey = (K) currentChain.keySet().toArray()[j];
                    //tempValue = put(newKey, currentChain.get(currentChain.keySet().toArray()[j]));
                }
                /*
                for (int j = 0; j < chainSize; j++) {
                    if (java.util.Objects.equals(currentChain.keySet().toArray()[j], null)) {
                        break;
                    }
                    newKey = (K) currentChain.keySet().toArray()[j];
                    tempValue = put(newKey, currentChain.get(currentChain.keySet().toArray()[j]));
                }*/
            }
        }
    }

    @Override
    public V put(K key, V value) {
        if ((size / hashMapSize) >= resizingFactor) {
            resize();
        }
        if (java.util.Objects.equals(null, key)) {
            hashCode = 0;
        } else {
            hashCode = java.lang.Math.abs(key.hashCode());
        }
        V originalValue = null;
        if (java.util.Objects.equals(null, this.chains[hashCode % this.hashMapSize])) {
            currentChain = this.createChain(this.chainSize);
            currentChain.putIfAbsent(key, value);
            this.chains[hashCode % this.hashMapSize] = currentChain;
            this.size++;
            return originalValue;
        } else {
            currentChain = this.chains[hashCode % this.hashMapSize];
            originalValue = currentChain.get(key);
            if (!currentChain.containsKey(key)) {
                this.size++;
            }
            currentChain.put(key, value);
            return originalValue;
        }
    }

    @Override
    public V remove(Object key) {
        V originalValue = null;
        if (java.util.Objects.equals(null, key)) {
            hashCode = 0;
        } else {
            hashCode = java.lang.Math.abs(key.hashCode());
        }
        if (java.util.Objects.equals(null, this.chains[hashCode % this.hashMapSize])) {
            return originalValue;
        } else {
            currentChain = this.chains[hashCode % this.hashMapSize];
            if (currentChain.containsKey(key)) {
                originalValue = currentChain.get(key);
                currentChain.entrySet().remove(currentChain.iterator().next());
                currentChain.remove(key);
                if (currentChain.isEmpty()) {
                    this.chains[hashCode % this.hashMapSize] = null;
                    this.currentChain = null;
                }
                size--;
            }
            return originalValue;
        }
    }

    @Override
    public void clear() {
        AbstractIterableMap<K, V>[] mapforClear = createArrayOfChains(hashMapSize);
        this.chains = mapforClear;
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (java.util.Objects.equals(null, key)) {
            hashCode = 0;
        } else {
            hashCode = java.lang.Math.abs(key.hashCode());
        }
        if (java.util.Objects.equals(null, this.chains[hashCode % this.hashMapSize])) {
            return false;
        } else {
            currentChain = this.chains[hashCode % this.hashMapSize];
            return currentChain.containsKey(key);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains, this.hashMapSize);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private AbstractIterableMap<K, V> currentChain;
        private int chainArrayIndex;
        private int arraySize;
        Iterator<Map.Entry<K, V>> iter;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains, int arraySize) {
            this.chains = chains;
            this.currentChain = this.chains[0];
            this.chainArrayIndex = 0;
            this.arraySize = arraySize;
            this.iter = null;
        }

        @Override
        public boolean hasNext() {
            boolean toReturn = false;
            while (java.util.Objects.equals(currentChain, null)) {
                this.chainArrayIndex = chainArrayIndex + 1;
                if (this.chainArrayIndex < arraySize) {
                    this.currentChain = this.chains[this.chainArrayIndex];
                } else {
                    return toReturn;
                }
            }
            if (java.util.Objects.equals(iter, null)) {
                iter = currentChain.iterator();
            }
            toReturn = iter.hasNext();
            return toReturn;
        }

        @Override
        public Map.Entry<K, V> next() {
            Map.Entry<K, V> copy = null;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            copy = iter.next();
            if (java.util.Objects.equals(iter.hasNext(), false)) {
                this.chainArrayIndex = chainArrayIndex + 1;
                if (this.chainArrayIndex < arraySize) {
                    this.currentChain = this.chains[this.chainArrayIndex];
                }
                iter = null;
            }
            return copy;
        }

    }
}
