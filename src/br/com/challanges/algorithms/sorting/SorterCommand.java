package br.com.challanges.algorithms.sorting;

public interface SorterCommand<T extends Comparable<T>> {
    void sort(T[] array);
}
