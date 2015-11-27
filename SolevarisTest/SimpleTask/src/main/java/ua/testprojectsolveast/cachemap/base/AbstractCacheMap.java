package ua.testprojectsolveast.cachemap.base;

import javafx.util.Pair;
import ua.testprojectsolveast.cachemap.containters.base.Container;

/**
 * Created by Anton on 03.11.2015.
 *
 * Not thread safe
 */
public abstract class AbstractCacheMap<KeyType, ValueType> implements CacheMap<KeyType, ValueType> {

    private Long timeToLive = 0L;
    private Container<KeyType, Pair<ValueType, Long>> container = null;

    /**
     * Must return an instance of a class inherited from the container
     */

    protected abstract Container<KeyType, Pair<ValueType, Long>> newContainer();

    protected Container<KeyType, Pair<ValueType, Long>> getContainer() {
        if (container == null) {
            container = newContainer();
        }
        return container;
    }

    protected Long getCurrentTime() {
        return System.currentTimeMillis();
    }

    protected boolean checkTimeToDie(Long saveTime) {
        return getTimeToLive() != 0L && getCurrentTime() - saveTime >= getTimeToLive();
    }

    private boolean checkTimeToDie(KeyType key) {
        return checkTimeToDie(getContainer().get(key).getValue());
    }

    private boolean checkTimeToDie(Pair<ValueType, Long> pair) {
        return checkTimeToDie(pair.getValue());
    }

    @Override
    public void setTimeToLive(long timeToLive) {
        if (timeToLive < 0L) throw new IllegalArgumentException("Time to live must be more then 0 or equals him.");
        this.timeToLive = timeToLive;
        clearExpired();
    }

    @Override
    public long getTimeToLive() {
        return timeToLive;
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        ValueType oldValue = get(key);
        getContainer().put(key, new Pair<>(value, getCurrentTime()));
        return oldValue;
    }

    @Override
    public void clearExpired() {
        KeyType[] keySet = getContainer().keyArray();
        for (KeyType key : keySet) {
            if (checkTimeToDie(key)) {
                getContainer().remove(key);
            }
        }
    }

    @Override
    public void clear() {
        getContainer().clear();
    }

    @Override
    public boolean containsKey(Object key) {
        if (getContainer().containsKey(key)) {
            if (checkTimeToDie((KeyType) key)) {
                getContainer().remove(key);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        KeyType[] keySet = getContainer().keyArray();
        for (KeyType key : keySet) {
            Pair<ValueType, Long> pair = getContainer().get(key);
            if (pair.getKey() == null) {
                if (value == null) return true;
            } else if (pair.getKey().equals(value)) {
                if (checkTimeToDie(pair)) {
                    getContainer().remove(key);
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ValueType get(Object key) {
        if (getContainer().containsKey(key)) {
            Pair<ValueType, Long> pair = getContainer().get(key);
            if (!checkTimeToDie(pair)) {
                return pair.getKey();
            }
            getContainer().remove(key);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        clearExpired();
        return getContainer().isEmpty();
    }

    @Override
    public ValueType remove(Object key) {
        if ((key != null) && (getContainer().containsKey(key))) {
            Pair<ValueType, Long> pair = getContainer().get(key);
            getContainer().remove(key);
            return checkTimeToDie(pair) ? null : pair.getKey();
        }
        return null;
    }

    @Override
    public int size() {
        clearExpired();
        return getContainer().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCacheMap)) return false;

        AbstractCacheMap<?, ?> that = (AbstractCacheMap<?, ?>) o;
        clearExpired();
        that.clearExpired();
        if (getContainer() != null ? !getContainer().equals(that.getContainer()) : that.getContainer() != null)
            return false;
        return getTimeToLive() == that.getTimeToLive();
    }

    @Override
    public int hashCode() {
        clearExpired();
        int result = getContainer() != null ? getContainer().hashCode() : 0;
        result = 31 * result + Long.valueOf(getTimeToLive()).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AbstractCacheMap{" +
                ", timeToLive=" + timeToLive +
                "map=" + getContainer() +
                '}';
    }

}
