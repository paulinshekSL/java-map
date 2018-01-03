package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class MapWithList<V, K> implements Map<K, V> {
  private List<MapEntry<K, V>> mapEntries;

  public MapWithList() {
    this.mapEntries = new ArrayList<>();
  }

  @Override
  public V get(K key) {
    if (key == null) {
      throw new NullPointerException();
    }

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
    if (key == null) {
      throw new NullPointerException();
    }
    if (findIndexFromKey(key) >= 0) {
      throw new InvalidKeyException(); // duplicate key
    }
    mapEntries.add(new MapEntry<>(key, value));
  }

  @Override
  public Set<K> getAllKeys() {
    return mapEntries.stream().map(e -> e.key).collect(Collectors.toSet());
  }

  @Override
  public boolean containsKey(K key) {
    if (key == null) throw new NullPointerException();
    return mapEntries.stream().anyMatch(e -> e.key.equals(key));
  }

  @Override
  public void delete(K key) {
    if (key == null) throw new NullPointerException();

    // find index
    int foundIndex = findIndexFromKey(key);
    if (foundIndex == -1) {
      throw new KeyNotFoundException();
    }

    mapEntries.remove(foundIndex);
  }

  public int findIndexFromKey(K key) {
    int i = -1;
    boolean found = false;
    Iterator<MapEntry<K, V>> iterator = mapEntries.iterator();
    while (!found && iterator.hasNext()) {
      i++;
      found = iterator.next().key.equals(key);
    }
    if (!found) {
      return -1;
    }
    return i;
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
