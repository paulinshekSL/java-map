package main;

import java.util.Set;

/*
 * Map of key-value pairs
 */
public interface Map<K, V> {
  V get(K key);
  void add(K key, V value);
  Set<K> getAllKeys();
  boolean containsKey(K key);
  void delete(K key);
}
