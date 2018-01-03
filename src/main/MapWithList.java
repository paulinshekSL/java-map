package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class MapWithList<V, K> implements Map<K, V> {
  private List<MapEntry<K, V>> mapEntries;

  public MapWithList() {
    this.mapEntries = new ArrayList<>();
  }

  @Override
  public V get(K key) {
    Optional<MapEntry<K, V>> foundEntry = mapEntries.stream()
      .filter(e -> e.key.equals(key))
      .findFirst();

    if (!foundEntry.isPresent()) {
      throw new KeyNotFoundException();
    }

    return foundEntry.get().value;
  }

  @Override
  public void add(K key, V value) {
  }

  @Override
  public Set<K> getAllKeys() {
    return null;
  }

  @Override
  public boolean containsKey(K key) {
    return false;
  }

  @Override
  public void delete(K key) {

  }

  private class MapEntry<A, B> {
    private A key;
    private B value;

    public MapEntry(A key, B value) {
      this.key = key;
      this.value = value;
    }
  }
}
