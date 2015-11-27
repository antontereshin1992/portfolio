package ua.testprojectsolveast.cachemap.containters.base;

/**
 * Created by Anton on 03.11.2015.
 */
public interface Container<K, V> {

    V put(K key, V value);

    V get(Object key);

    V remove(Object key);

    void clear();

    Integer size();

    Boolean isEmpty();

    Boolean containsKey(Object key);

    Boolean containsValue(V value);

    K[] keyArray();


}
