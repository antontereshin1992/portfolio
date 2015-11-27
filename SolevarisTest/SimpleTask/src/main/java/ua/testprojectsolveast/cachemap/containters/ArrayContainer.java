package ua.testprojectsolveast.cachemap.containters;

import javafx.util.Pair;
import ua.testprojectsolveast.cachemap.containters.base.AbstractContainer;

import java.util.Arrays;

/**
 * Created by Anton on 03.11.2015.
 *
 * Not thread safe
 */
public class ArrayContainer<K, V> extends AbstractContainer<K, V> {

    private final int DEFAULT_CAPACITY = 10;

    private Pair[] items = new Pair[DEFAULT_CAPACITY];
    private Integer count = 0;


    private Integer capacity = DEFAULT_CAPACITY;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Optimize memory
     */
    public void optimizeArray() {
        Pair[] tmp = new Pair[size() + getCapacity()];
        int k = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) tmp[k++] = items[i];
        }
        items = tmp;
    }


    @Override
    public Integer size() {
        return count;
    }

    @Override
    public V put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key must be not null!");
        int indexNull = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                if (indexNull < 0) indexNull = i;
            } else if (items[i].getKey().equals(key)) {
                V oldValue = (V) items[i].getValue();
                items[i] = new Pair(key, value);
                count++;
                return oldValue;
            }
        }
        if (indexNull < 0) {
            Pair[] tmp = new Pair[items.length];
            System.arraycopy(items, 0, tmp, 0, items.length);
            items = new Pair[items.length + getCapacity()];
            System.arraycopy(tmp, 0, items, 0, tmp.length);
            items[tmp.length] = new Pair(key, value);
            count++;
        } else {
            items[indexNull] = new Pair(key, value);
            count++;
        }
        return null;
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < items.length; i++) {
            if ((items[i] != null) && (items[i].getKey().equals(key))) return (V) items[i].getValue();
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        for (int i = items.length - 1; i >= 0; i--) {
            if ((items[i] != null) && items[i].getKey().equals(key)) {
                V oldValue = (V) items[i].getValue();
                items[i] = null;
                count--;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = items.length - 1; i >= 0; i--) {
            if (items[i] != null) remove(items[i].getKey());
        }
    }

    @Override
    public Boolean containsValue(V value) {
        for (int i = 0; i < items.length; i++) {
            if ((items[i] != null) && (items[i].getValue().equals(value))) return true;
        }
        return false;
    }

    @Override
    public Boolean containsKey(Object key) {
        for (int i = 0; i < items.length; i++) {
            if ((items[i] != null) && (items[i].getKey().equals(key))) return true;
        }
        return false;
    }


    @Override
    public K[] keyArray() {
        Object[] result = new Object[size()];
        int k = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) result[k++] = items[i].getKey();
        }
        return (K[]) result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayContainer)) return false;

        ArrayContainer<?, ?> that = (ArrayContainer<?, ?>) o;
        optimizeArray();
        that.optimizeArray();
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(items, that.items)) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return !(getCapacity() != null ? !getCapacity().equals(that.getCapacity()) : that.getCapacity() != null);

    }

    @Override
    public int hashCode() {
        optimizeArray();
        int result = items != null ? Arrays.hashCode(items) : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (getCapacity() != null ? getCapacity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ArrayContainer{" +
                "capacity=" + capacity +
                ", count=" + count +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}

