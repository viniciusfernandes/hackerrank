package br.com.challanges.hackerrank.datastructure;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PLRCache<K, V extends Comparable<V>> {
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

  public PLRCache<K, V> put(K k, V v) {
    if (k == null || v == null) {
      throw new IllegalArgumentException("The pair key/value must not be null");
    }
    if (pairs.size() >= capacity) {
      var old = pairs.remove(0);
      cache.remove(old.key);
    }
    pairs.add(new Pair<>(k, v));
    cache.put(k, v);
    return this;
  }

  public V get(K k) {
    return cache.get(k);
  }

  public V pop() {
    if (pairs.isEmpty()) {
      return null;
    }
    var pair = pairs.remove(pairs.size() - 1);
    cache.remove(pair.key);
    return pair.value;
  }

  public V peek() {
    if (pairs.isEmpty()) {
      return null;
    }
    return pairs.get(pairs.size() - 1).value;
  }

  public List<V> filter(Predicate<V> filter) {
    return pairs.stream()
        .filter(pair -> filter.test(pair.value))
        .map(pair -> pair.value)
        .collect(Collectors.toList());
  }

  public PLRCache<K, V> remove(Predicate<V> rule) {
    pairs = pairs.stream()
        .filter(pair -> {
          if (rule.test(pair.value)) {
            cache.remove(pair.key);
            return false;
          }
          return true;
        })
        .collect(Collectors.toList());

    return this;
  }

  public PLRCache<K, V> sort(Comparator<V> comparator) {
    pairs.sort((p1, p2) -> comparator.compare(p1.value, p2.value));
    return this;
  }

  public PLRCache<K, V> sort() {
    return sort((v1, v2) -> Comparator.<V>naturalOrder().compare(v1, v2));
  }

  public List<V> values() {
    return pairs.stream().map(pair -> pair.value).collect(Collectors.toList());
  }

  public V get(int index) {
    return pairs.get(index).value;
  }

  public static void main(String[] args) {
    newCache()
        .filter(name -> name.contains("v"))
        .forEach(name -> System.out.println("satisfies filter: " + name));

    newCache()
        .remove(value -> value.contains("n") || value.length() <= 4)
        .values()
        .forEach(value -> System.out.println("not removed: " + value));

    newCache()
        .sort((name1, name2) -> {
          if (name1.length() > name2.length()) {
            return 1;
          } else if (name1.length() < name2.length()) {
            return -1;
          } else {
            return name1.compareTo(name2);
          }
        })
        .values()
        .forEach(value -> System.out.println("sorted: " + value));

    var cache = newCache();
    cache.put("w", "william")
        .sort()
        .values()
        .forEach(value -> System.out.println("added: " + value));
  }

  private static PLRCache<String, String> newCache() {
    var cache = new PLRCache<String, String>(4);
    cache.put("x", "xavier")
        .put("v", "vinicius")
        .put("a", "aida")
        .put("p", "pato");
    return cache;
  }

}
