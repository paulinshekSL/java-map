package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class HashMapWithList<V, K> implements Map<K, V> {
  private List<MapEntry<K, V>> hashMap;

  public HashMapWithList() {
    this.hashMap = new ArrayList<>();
  }

  @Override
  public V get(K key) {
    return null;
  }

  @Override
  public void put(K key, V value) {
  }

  @Override
  public Set<K> getAllKeys() {
    return null;
  }

  @Override
  public boolean containsKey(K key) {
    return false;
  }

  private class MapEntry<A, B> {
    private A key;
    private B value;

    public MapEntry(A key, B value) {
      this.key = key;
      this.value = value;
    }

    public A getKey() {
      return key;
    }

    public B getValue() {
      return value;
    }
  }
}
