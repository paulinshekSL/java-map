package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

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
    LinkedList<MapEntry<K, V>> bucket = hashMap[key.hashCode()];

    if (bucket == null) {
      throw new KeyNotFoundException();
    }

    Iterator<MapEntry<K, V>> iterator = bucket.iterator();
    boolean found = false;
    if (!iterator.hasNext()) {
      throw new KeyNotFoundException();
    }
    MapEntry<K, V> mapEntry = iterator.next();

    while (iterator.hasNext() && !found) {
      mapEntry = iterator.next();
      found = mapEntry.key.equals(key);
    }
    if (!found) {
      throw new KeyNotFoundException();
    }
    return mapEntry.value;
  }

  @Override
  public void add(K key, V value) throws InvalidKeyException {
    if (key == null) {
      throw new NullPointerException();
    }

    hashMap[key.hashCode()].add(new MapEntry<>(key, value));
    size++;
  }

  @Override
  public Set<K> getAllKeys() {
    return null;
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

    hashMap[key.hashCode()].remove(tryFind.get());
    size--;
  }

  private Optional<MapEntry<K, V>> findMapEntryByKey(K key) {
    return hashMap[key.hashCode()].stream().filter(e -> e.key.equals(key)).findFirst();
  }

  private class MapEntry<L, R> {
    L key;
    R value;

    public MapEntry(L key, R value) {
      this.key = key;
      this.value = value;
    }
  }
}
