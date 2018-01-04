package main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ChainedHashMap<K, V> implements Map<K, V> {
  public static final int INIT_CAPACITY = 10;
  public static final double CAPACITY_MULTIPLIER = 1.5;
  /*
   * Trigger resize when number of elements reaches currentCapacity * TRIGGER_RESIZE
   * Note that TRIGGER_RESIZE can be more than 1- this just means that there will definitely
   * be buckets that contain more than one element
   */
  public static final double TRIGGER_RESIZE = 0.7;

  private LinkedList<MapEntry<K, V>>[] hashMap;
  private int size;

  private int currentCapacity;

  public ChainedHashMap() {
    size = 0;
    hashMap = (LinkedList<MapEntry<K, V>>[]) new LinkedList[INIT_CAPACITY];
    currentCapacity = INIT_CAPACITY;
  }

  @Override
  public V get(K key) {
    Optional<MapEntry<K, V>> optionalMapEntry = findMapEntryByKey(key);
    if (!optionalMapEntry.isPresent()) {
      throw new KeyNotFoundException();
    }
    return optionalMapEntry.get().value;
  }

  @Override
  public void add(K key, V value) throws InvalidKeyException {
    if (key == null) {
      throw new NullPointerException();
    }

    if (findMapEntryByKey(key).isPresent()) {
      throw new InvalidKeyException();
    }

    resizeIfNeeded();

    LinkedList<MapEntry<K, V>> bucket = getOrInitialiseBucket(key);
    bucket.add(new MapEntry<>(key, value));
    size++;
  }

  @Override
  public Set<K> getAllKeys() {
    Set<K> keySet = new HashSet<>();
    for (LinkedList<MapEntry<K, V>> bucket : hashMap) {
      if (bucket != null) {
        keySet.addAll(bucket.stream().map(e -> e.key).collect(Collectors.toSet()));
      }
    }
    return keySet;
  }

  @Override
  public boolean containsKey(K key) {
    return findMapEntryByKey(key).isPresent();
  }

  @Override
  public void delete(K key) {
    Optional<MapEntry<K, V>> tryFind = findMapEntryByKey(key);
    if (!tryFind.isPresent()) {
      throw new KeyNotFoundException();
    }

    hashMap[calculateHash(key)].remove(tryFind.get());
    size--;
  }

  @Override
  public int size() {
    return size;
  }

  private LinkedList<MapEntry<K, V>> getOrInitialiseBucket(K key) {
    LinkedList<MapEntry<K, V>> bucket = hashMap[calculateHash(key)];
    if (bucket == null) {
      bucket = new LinkedList<>();
      hashMap[calculateHash(key)] = bucket;
    }
    return bucket;
  }

  private int calculateHash(K key) {
    return Math.abs(key.hashCode()) % currentCapacity;
  }

  private Optional<MapEntry<K, V>> findMapEntryByKey(K key) {
    LinkedList<MapEntry<K, V>> bucket = hashMap[calculateHash(key)];
    if (bucket == null) {
      return Optional.empty();
    }
    return bucket.stream().filter(e -> e.key.equals(key)).findFirst();
  }

  private void resizeIfNeeded() {
    if (size < TRIGGER_RESIZE * currentCapacity) {
      // resize not needed so do nothing
      return;
    }

    // do the resizing
    currentCapacity = (int) (currentCapacity * CAPACITY_MULTIPLIER);
    LinkedList<MapEntry<K, V>>[] newHashMap = (LinkedList<MapEntry<K, V>>[]) new LinkedList[currentCapacity];
    // rehash all existing
    for (LinkedList<MapEntry<K, V>> bucket : hashMap) {
      if (bucket != null) {
        for (MapEntry<K, V> oldEntry : bucket) {
          int newHash = calculateHash(oldEntry.key);
          if (newHashMap[newHash] == null) {
            newHashMap[newHash] = new LinkedList<>();
          }
          newHashMap[newHash].add(new MapEntry<>(oldEntry.key, oldEntry.value));
        }
      }
    }
    hashMap = newHashMap;
  }

  private class MapEntry<L, R> {
    L key;
    R value;

    public MapEntry(L key, R value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MapEntry<?, ?> mapEntry = (MapEntry<?, ?>) o;
      return Objects.equals(key, mapEntry.key) &&
        Objects.equals(value, mapEntry.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, value);
    }
  }
}
