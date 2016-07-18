/**
 *
 */
package com.flatironschool.javacs;

import java.util.*;

/**
 * Provides sorting algorithms.
 */
public class ListSorter<T> {

    /**
     * Sorts a list using a Comparator object.
     *
     * @param list
     * @param comparator
     * @return
     */
    public void insertionSort(List<T> list, Comparator<T> comparator) {

        for (int i = 1; i < list.size(); i++) {
            T elt_i = list.get(i);
            int j = i;
            while (j > 0) {
                T elt_j = list.get(j - 1);
                if (comparator.compare(elt_i, elt_j) >= 0) {
                    break;
                }
                list.set(j, elt_j);
                j--;
            }
            list.set(j, elt_i);
        }
    }

    /**
     * Sorts a list using a Comparator object.
     *
     * @param list
     * @param comparator
     * @return
     */
    public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
        List<T> sorted = mergeSort(list, comparator);
        list.clear();
        list.addAll(sorted);
    }

    /**
     * Sorts a list using a Comparator object.
     * <p/>
     * Returns a list that might be new.
     *
     * @param list
     * @param comparator
     * @return
     */
    public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        if (list.size() <= 1) {
            return list;
        } else {
            List<T> list1 = list.subList(0, list.size() / 2);
            List<T> list2 = list.subList(list.size() / 2, list.size());
            List<T> list1Sorted = mergeSort(list1, comparator);
            List<T> list2Sorted = mergeSort(list2, comparator);
            int list1Index = 0;
            int list2Index = 0;
            List<T> toReturn = new ArrayList<>();
            while (list1Index < list1Sorted.size()) {
                //there are still unprocessed elements of list 2
                if (list2Index < list2Sorted.size()) {
                    T elem1 = list1Sorted.get(list1Index);
                    T elem2 = list2Sorted.get(list2Index);
                    int result = comparator.compare(elem1, elem2);
                    if (result < 0) {
                        toReturn.add(elem1);
                        list1Index++;
                    } else if (result == 0) {
                        toReturn.add(elem1);
                        toReturn.add(elem2);
                        list1Index++;
                        list2Index++;
                    } else {
                        toReturn.add(elem2);
                        list2Index++;
                    }
                } else {
                    toReturn.add(list1Sorted.get(list1Index++));
                }
            }
            while (list2Index < list2Sorted.size()) {
                toReturn.add(list2Sorted.get(list2Index++));
            }
            return toReturn;
        }
    }

    /**
     * Sorts a list using a Comparator object.
     *
     * @param list
     * @param comparator
     * @return
     */
    public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> priorityQueue = new PriorityQueue<>(list.size(), comparator);
        for (T elem : list) {
            priorityQueue.add(elem);
        }
        list.clear();
        while (!priorityQueue.isEmpty()) {
            list.add(priorityQueue.poll());
        }
    }


    /**
     * Returns the largest `k` elements in `list` in ascending order.
     *
     * @param k
     * @param list
     * @param comparator
     * @return
     */
    public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> priorityQueue = new PriorityQueue<>(k, comparator);
        for (T elem : list) {
            if (priorityQueue.size() < k) {
                priorityQueue.add(elem);
            } else {
                T minSoFar = priorityQueue.peek();
                int compResult = comparator.compare(elem, minSoFar);
                if (compResult <= 0) {
                    continue;
                } else {
                    priorityQueue.poll();
                    priorityQueue.add(elem);
                }
            }
        }
        List<T> toReturn = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            toReturn.add(priorityQueue.poll());
        }
        return toReturn;
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer n, Integer m) {
                return n.compareTo(m);
            }
        };

        ListSorter<Integer> sorter = new ListSorter<Integer>();
        sorter.insertionSort(list, comparator);
        System.out.println(list);

        list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
        sorter.mergeSortInPlace(list, comparator);
        System.out.println(list);

        list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
        sorter.heapSort(list, comparator);
        System.out.println(list);

        list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
        List<Integer> queue = sorter.topK(4, list, comparator);
        System.out.println(queue);
    }
}
