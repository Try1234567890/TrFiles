package me.tr.trfiles.registries;

import java.util.Map;
import java.util.Optional;

public abstract class Registry<K, V> {

    public boolean register(K key, V value) {
        if (getRegistry().containsKey(key))
            return false;
        getRegistry().put(key, value);
        return true;
    }

    public V registerAndReturn(K key, V value) {
        if (getRegistry().containsKey(key))
            return null;
        return getRegistry().put(key, value);
    }

    public Optional<V> registerAndReturnOpt(K key, V value) {
        if (getRegistry().containsKey(key))
            return Optional.empty();
        return Optional.ofNullable(getRegistry().put(key, value));
    }

    public boolean unregister(K key) {
        if (!getRegistry().containsKey(key))
            return false;
        getRegistry().remove(key);
        return true;
    }

    public V unregisterAndReturn(K key) {
        return getRegistry().remove(key);
    }

    public Optional<V> unregisterAndReturnOpt(K key) {
        return Optional.ofNullable(getRegistry().remove(key));
    }

    public boolean modify(K key, K newKey, V newValue) {
        unregister(key);
        return register(newKey, newValue);
    }

    public V modifyAndReturn(K key, K newKey, V newValue) {
        unregister(key);
        return registerAndReturn(newKey, newValue);
    }


    public Optional<V> modifyAndReturnOpt(K key, K newKey, V newValue) {
        unregister(key);
        return Optional.ofNullable(registerAndReturn(newKey, newValue));
    }

    public V retrieve(K key) {
        return getRegistry().get(key);
    }

    public Optional<V> retrieveOpt(K key) {
        return Optional.ofNullable(getRegistry().get(key));
    }


    protected abstract Map<K, V> getRegistry();

}