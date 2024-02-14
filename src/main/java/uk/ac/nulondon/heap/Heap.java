package uk.ac.nulondon.heap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Heap<T extends Comparable<T>> {
    private final List<T> heap = new ArrayList<>();
    private int heapSize = 0;

    public Heap() {

    }

    public Heap(Collection<? extends T> initial) {
        heap.addAll(initial);
        heapSize = heap.size();
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private void swap(int i, int j) {
        T buf = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, buf);
    }

    /**
     * Verify if this structure is a valid heap
     */
    public boolean isValidHeap() {
        for (int i = 0; i < heapSize; i++) {
            if (leftChild(i) < heapSize
                    && heap.get(leftChild(i)).compareTo(heap.get(i)) > 0)
                return false;
            if (rightChild(i) < heapSize
                    && heap.get(rightChild(i)).compareTo(heap.get(i)) > 0)
                return false;
        }
        return true;
    }

    /**
     * Build the max-heap for the provided data.
     */
    public void maxHeapify() {
        //Start with the parent of the last element, proceed to the top
        for (int i = parent(heapSize - 1); i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void maxHeapify(int i) {
        int l = leftChild(i);
        int r = rightChild(i);
        //Compare left, right and current and find out the largest element
        int largest = i;
        if (l < heapSize && (heap.get(l).compareTo(heap.get(i)) > 0))
            largest = l;
        if (r < heapSize && (heap.get(r).compareTo(heap.get(largest)) > 0))
            largest = r;
        //Swap current with the largest (if needed)
        if (largest != i) {
            swap(i, largest);
            //Fix heap for the children of largest, as there may be larger values below
            maxHeapify(largest);
        }

    }

    public void add(T element) {
        heap.add(element);
        heapSize++;
        if (heapSize > 1) {
            maxHeapify();
        }
    }

    public T poll() {
        if (heap.isEmpty()) return null;
        T result = heap.getFirst();
        //Bring the last node to the top
        heap.set(0, heap.getLast());
        //Trim the heap
        heap.removeLast();
        heapSize--;
        //Recursively "sift" the elements
        maxHeapify(0);
        return result;
    }

    public List<T> heapSort() {
        maxHeapify();
        while (heapSize > 1) {
            heapSize--;
            swap(0, heapSize);
            maxHeapify(0);
        }
        return List.copyOf(heap);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

}
