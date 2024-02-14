package uk.ac.nulondon.heap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class HeapTest {
    static List<Arguments> isValidMaxHeap() {
        return List.of(
                Arguments.of(List.of(3, 1, 2), true),
                Arguments.of(List.of(3, 2, 1), true),
                Arguments.of(List.of(2, 3, 1), false),
                Arguments.of(List.of(10, 5, 9, 3, 2, 4), true),
                Arguments.of(List.of(10, 5, 9, 3, 6 /*misplaced!*/, 4), false));
    }

    @DisplayName("Valid and invalid heaps")
    @MethodSource
    @ParameterizedTest
    void isValidMaxHeap(List<Integer> data, boolean expected) {
        Heap<Integer> heap = new Heap<>(data);
        assertThat(heap.isValidHeap()).isEqualTo(expected);
    }


    static List<List<Integer>> maxHeapify() {
        return List.of(
                List.of(1, 2, 3, 4, 5, 6),
                List.of(6, 5, 4, 3, 2, 1),
                //This example requires fixing of child nodes (recursive call to maxHeapify)
                List.of(3, 4, 7, 10, 5),
                List.of(1),
                List.of());
    }

    @DisplayName("Heapify produces a valid heap for any input")
    @MethodSource
    @ParameterizedTest
    void maxHeapify(List<Integer> data) {
        Heap<Integer> heap = new Heap<>(data);
        heap.maxHeapify();
        assertThat(heap.isValidHeap()).isTrue();
    }

    static List<List<Integer>> add() {
        return List.of(
                List.of(3, 4, 9, 5, 2, 10, 7),
                List.of(1, 2, 3, 4),
                List.of(4, 3, 2, 1));
    }

    @MethodSource
    @DisplayName("Add produces a valid heap")
    @ParameterizedTest
    void add(List<Integer> data) {
        Heap<Integer> heap = new Heap<>();
        for (Integer value : data) {
            heap.add(value);
        }
        Assertions.assertThat(heap.isValidHeap()).isTrue();
    }


    static List<List<Integer>> randomLists() {
        //Produce 5 shuffled lists of a random length
        List<List<Integer>> samples = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int len = ThreadLocalRandom.current().nextInt(2, 10);
            List<Integer> sample = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                sample.add(j);
            }
            Collections.shuffle(sample);
            samples.add(sample);
        }
        return samples;
    }

    @MethodSource("randomLists")
    @DisplayName("Remove always returns values in order")
    @ParameterizedTest
    void poll(List<Integer> data) {
        List<Integer> result = new ArrayList<>();

        Heap<Integer> heap = new Heap<>(data);
        heap.maxHeapify();
        Integer element = heap.poll();
        while (element != null) {
            result.addFirst(element);
            element = heap.poll();
        }

        List<Integer> sortedData = data.stream().sorted().toList();
        Assertions.assertThat(result).isEqualTo(sortedData);
    }

    @MethodSource("randomLists")
    @DisplayName("Remove always returns values in order")
    @ParameterizedTest
    void heapSort(List<Integer> data) {
        Heap<Integer> heap = new Heap<>(data);
        List<Integer> result = heap.heapSort();
        List<Integer> sortedData = data.stream().sorted().toList();
        Assertions.assertThat(result).isEqualTo(sortedData);
    }
}