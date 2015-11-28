package net.coderodde.util;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
 
/**
 * This class implements the binary minimum heap (a priority queue type).
 *
 * @author  Rodion "rodde" Efremov
 * @version 1.618 (11.12.2013)
 * @param <E> the actual element type.
 */
public class BinaryHeap<E> {
    private static final float LOAD_FACTOR = 1.05f;
    private static final int DEFAULT_CAPACITY = 1024;
 
    public int size() {
        return size;
    }
 
    public boolean isEmpty() {
        return size == 0;
    }
 
    private static class HeapNode<E> {
        E      element;
        double priority;
        int    index;
 
        HeapNode(E element, double priority) {
            this.element = element;
            this.priority = priority;
        }
    }
 
    /**
     * The amount of elements in this heap.
     */
    private int size;
 
    /**
     * The actual storage array.
     */
    private HeapNode<E>[] nodeArray;
 
    /**
     * Maps each element to the heap node holding it.
     */
    private Map<E, HeapNode<E>> map;
 
    /**
     * Constructs a binary heap with initial capacity <code>capacity</code>.
     *
     * @param capacity the initial capacity of this heap.
     */
    public BinaryHeap(int capacity) {
        capacity = checkCapacity(capacity);
        this.nodeArray = new HeapNode[capacity];
        this.map = new HashMap<>(capacity, LOAD_FACTOR);
    }
 
    /**
     * Construct a binary heap.
     */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }
 
    /**
     * Adds an element if not already present.
     *
     * @param e        the element to insert.
     * @param priority the priority of the element.
     */
    public void add(E e, double priority) {
        if (map.containsKey(e)) {
            return;
        }
 
        if (size == nodeArray.length) {
            extendArray();
        }
 
        int index = size;
        HeapNode<E> node = new HeapNode<>(e, priority);
 
        // Sift up the node containing the input element until min-heap property
        // is restored.
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            HeapNode<E> p = nodeArray[parent];
 
            if (priority >= p.priority) {
                break;
            }
 
            nodeArray[index] = p;
            p.index = index;
            index = parent;
        }
 
        nodeArray[index] = node;
        node.index = index;
        map.put(e, node);
        ++size;
    }
 
    public void decreasePriority(E e, double newPriority) {
        HeapNode<E> node = map.get(e);
 
        if (node == null || node.priority <= newPriority) {
            return;
        }
 
        node.priority = newPriority;
        int index = node.index;
        int parentIndex = (index - 1) >> 1;
 
        for (;;) {
            if (parentIndex >= 0
                    && nodeArray[parentIndex].priority > node.priority) {
                nodeArray[index] = nodeArray[parentIndex];
                nodeArray[index].index = index;
                index = parentIndex;
                parentIndex = (index - 1) >> 1;
            } else {
                nodeArray[index] = node;
                node.index = index;
                return;
            }
        }
    }
 
    public E min() {
        if (size == 0) {
            throw new NoSuchElementException("Reading from an empty heap.");
        }
 
        return nodeArray[0].element;
    }
 
    public E extractMinimum() {
        if (size == 0) {
            throw new NoSuchElementException("Extracting from an empty queue.");
        }
 
        E element = nodeArray[0].element;
        map.remove(element);
        HeapNode<E> node = nodeArray[--size];
        nodeArray[size] = null;
 
        int nodeIndex = 0;
        int leftChildIndex = 1;
        int rightChildIndex = 2;
 
        for (;;) {
            int minChildIndex;
 
            if (leftChildIndex < size) {
                minChildIndex = leftChildIndex;
            } else {
                nodeArray[nodeIndex] = node;
                node.index = nodeIndex;
                return element;
            }
 
            if (rightChildIndex < size
                    && nodeArray[leftChildIndex].priority > 
                       nodeArray[rightChildIndex].priority) {
                minChildIndex = rightChildIndex;
            }
 
            if (node.priority > nodeArray[minChildIndex].priority) {
                nodeArray[nodeIndex] = nodeArray[minChildIndex];
                nodeArray[nodeIndex].index = nodeIndex;
 
                nodeIndex = minChildIndex;
                leftChildIndex = (nodeIndex << 1) + 1;
                rightChildIndex = leftChildIndex + 1;
            } else {
                nodeArray[nodeIndex] = node;
                node.index = nodeIndex;
                return element;
            }
        }
    }
 
    public void clear() {
        size = 0;
        map.clear();
    }
 
    public boolean contains(E element) {
        return map.containsKey(element);
    }
 
    public Double getPriority(E element) {
        if (map.containsKey(element) == false) {
            return null;
        }
 
        return map.get(element).priority;
    }
 
    private int checkCapacity(int capacity) {
        return capacity < 16 ? 16 : capacity;
    }
 
    private void extendArray() {
        int capacity = (size * 3) / 2;
        HeapNode[] array = new HeapNode[capacity];
        System.arraycopy(nodeArray, 0, array, 0, size);
        nodeArray = array;
    }
}