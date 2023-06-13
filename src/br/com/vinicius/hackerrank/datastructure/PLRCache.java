package br.com.vinicius.hackerrank.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PLRCache<K, V> {
  private class Pair<K, V> {
    final K key;
    final V value;

    public Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private Map<K, V> cache;
  private int capacity;

  private List<Pair<K, V>> pairs;

  public PLRCache(int capacity) {
    this.capacity = capacity;
    this.cache = new HashMap<>();
    this.pairs = new ArrayList<>();
  }

  public void put(K k, V v) {
    if (k == null || v == null) {
      return;
    }
    if (pairs.size() >= capacity) {
      var old = pairs.get(0);
      cache.remove(old.key);
      pairs.set(0, new Pair<>(k, v));
    } else {
      pairs.add(new Pair<>(k, v));
    }
    cache.put(k, v);
  }

  public V get(K k) {
    return cache.get(k);
  }

  public V pop() {
    if (pairs.isEmpty()) {
      return null;
    }
    var pair = pairs.get(pairs.size() - 1);
    cache.remove(pair.key);
    pairs.remove(pairs.size() - 1);
    return pair.value;
  }

  public V peek() {
    if (pairs.isEmpty()) {
      return null;
    }
    return pairs.get(pairs.size() - 1).value;
  }
}
