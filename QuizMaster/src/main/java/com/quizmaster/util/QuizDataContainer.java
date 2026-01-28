package com.quizmaster.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generic container class for managing quiz-related data.
 * Demonstrates Generics (Requirement 3) - provides type-safe operations.
 * @param <T> the type of data being managed
 */
public class QuizDataContainer<T> {
    
    private final List<T> items;
    public QuizDataContainer() {
        this.items = new ArrayList<>();
    }

    public QuizDataContainer(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    public boolean add(T item) {
        if (item == null) {
            return false;
        }
        return items.add(item);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public Optional<T> get(int index) {
        if (index >= 0 && index < items.size()) {
            return Optional.of(items.get(index));
        }
        return Optional.empty();
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public QuizDataContainer<T> filter(java.util.function.Predicate<T> predicate) {
        List<T> filtered = items.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new QuizDataContainer<>(filtered);
    }

    public <R> QuizDataContainer<R> map(Function<T, R> mapper) {
        List<R> mapped = items.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new QuizDataContainer<>(mapped);
    }

    public Optional<T> findFirst(java.util.function.Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .findFirst();
    }

    public long count(java.util.function.Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .count();
    }
    
    @Override
    public String toString() {
        return "QuizDataContainer{" +
                "size=" + items.size() +
                ", items=" + items +
                '}';
    }
}
