package ua.testprojectsolveast.cachemap;

import javafx.util.Pair;
import ua.testprojectsolveast.cachemap.base.AbstractCacheMap;
import ua.testprojectsolveast.cachemap.containters.ArrayContainer;
import ua.testprojectsolveast.cachemap.containters.base.Container;

/**
 * Created by Anton on 03.11.2015.
 */
public class ArrayCacheMap<KeyType, ValueType> extends AbstractCacheMap<KeyType, ValueType> {

    @Override
    protected Container<KeyType, Pair<ValueType, Long>> newContainer() {

        return new ArrayContainer<>();
    }
}
