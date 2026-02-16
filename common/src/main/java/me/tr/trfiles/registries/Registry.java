package me.tr.trfiles.registries;

import java.util.*;
import java.util.stream.Stream;

public class Registry<K, V> {
    private final Map<K, V> registry = new HashMap<>();


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

    public Set<Map.Entry<K, V>> entries() {
        return getRegistry().entrySet();
    }

    public Set<K> keys() {
        return getRegistry().keySet();
    }

    public Collection<V> values() {
        return getRegistry().values();
    }

    public Stream<Map.Entry<K, V>> streamEntries() {
        return entries().stream();
    }

    public Stream<K> streamKeys() {
        return keys().stream();
    }

    public Stream<V> streamValues() {
        return values().stream();
    }

    protected Map<K, V> getRegistry() {
        return registry;
    }

}