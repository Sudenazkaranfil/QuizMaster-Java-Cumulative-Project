package com.quizmaster.util;

import java.util.Objects;

/**
 * Generic Pair class for holding two related objects.
 * Demonstrates advanced Generics usage with multiple type parameters.
 * Useful for quiz results, question-answer pairs, etc.
 * @param <K> the type of the first element (key)
 * @param <V> the type of the second element (value)
 */
public class Pair<K, V> {
    
    private K key;
    private V value;

    public Pair() {
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }
    
    public K getKey() {
        return key;
    }
    
    public void setKey(K key) {
        this.key = key;
    }
    
    public V getValue() {
        return value;
    }
    
    public void setValue(V value) {
        this.value = value;
    }

    public Pair<V, K> swap() {
        return new Pair<>(value, key);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && 
               Objects.equals(value, pair.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
    
    @Override
    public String toString() {
        return "Pair{" + key + " = " + value + '}';
    }
}
