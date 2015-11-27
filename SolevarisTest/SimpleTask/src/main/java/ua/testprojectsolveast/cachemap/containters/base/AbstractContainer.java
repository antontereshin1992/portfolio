package ua.testprojectsolveast.cachemap.containters.base;

/**
 * Created by Anton on 03.11.2015.
 */
public abstract class AbstractContainer<K, V> implements Container<K, V> {

    @Override
    public Boolean isEmpty() {
        return size() == 0;
    }

}
