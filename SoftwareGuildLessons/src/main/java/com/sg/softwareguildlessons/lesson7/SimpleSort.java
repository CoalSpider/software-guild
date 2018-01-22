/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.softwareguildlessons.lesson7;

import java.util.Arrays;

/**
 *
 * @author Ben Norman
 */
public class SimpleSort {
    
    public static void main(String[] args) {
        int[] firstHalf = {3, 7, 9, 10, 16, 19, 20, 34, 55, 67, 88, 99};
        int[] secondHalf = {1, 4, 8, 11, 15, 18, 21, 44, 54, 79, 89, 100};
        
        int[] wholeNumbers = new int[firstHalf.length + secondHalf.length];//new int[24];

        // Sorting code should go here!
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < wholeNumbers.length) {
            // abusing post increment operator
            wholeNumbers[i++] = firstHalf[j++];
            wholeNumbers[i++] = secondHalf[k++];
        }
        System.out.println(Arrays.toString(wholeNumbers));

        //   SortingUtils.insertionSort(wholeNumbers);
        //   SortingUtils.unknownSort(wholeNumbers);
        //   SortingUtils.bubbleSort(wholeNumbers);
        //   Arrays.sort(wholeNumbers);
        SortingUtils.SelectionSort(wholeNumbers);
        
        System.out.println(Arrays.toString(wholeNumbers));
    }
}

class SortingUtils {

    /*
    i ← 1
while i < length(A)
    j ← i
    while j > 0 and A[j-1] > A[j]
        swap A[j] and A[j-1]
        j ← j - 1
    end while
    i ← i + 1
end while
     */
    /**
     * this method modifies the given array
     */
    public final static int[] insertionSort(int[] array) {
        int i = 1;
        while (i < array.length) {
            int j = i;
            while (j > 0 && array[j - 1] > array[j]) {
                int tmp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = tmp;
                j--;
            }
            i++;
        }
        return array;
    }

    // bubble sort variant?
    public final static void unknownSort(int[] array) {
        boolean needsSorting = true;
        while (needsSorting) {
            needsSorting = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    needsSorting = true;
                }
            }
        }
    }
    
    public final static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }
    
    public final static void SelectionSort(int[] array) {
        // TODO
    }

    /**
     * algorithm quicksort(A, lo, hi) is if lo < hi then p := partition(A, lo,
     * hi) quicksort(A, lo, p - 1 ) quicksort(A, p + 1, hi)
     *
     * algorithm partition(A, lo, hi) is pivot := A[hi] i := lo - 1 for j := lo
     * to hi - 1 do if A[j] < pivot then i := i + 1 swap A[i] with A[j] if A[hi]
     * < A[i + 1] then swap A[i + 1] with A[hi] return i + 1*
     */
    public final static void quicksortLomuto(int[] data, int low, int high) {
        if (low < high) {
            int pivot = lomutoPartition(data, low, high);
            quicksortLomuto(data, low, pivot - 1);
            quicksortLomuto(data, pivot + 1, high);
        }
        
    }
    
    private static int lomutoPartition(int[] data, int low, int high) {
        int pivot = data[high];
        int i = low - 1;
        for (int j = low; j < high - 1; j++) {
            if (data[j] < pivot) {
                i = i + 1;
                swap(data, i, j);
            }
        }
        if (data[high] < data[i + 1]) {
            swap(data, i + 1, high);
        }
        return i + 1;
    }
    
    private static void swap(int[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }
}
// diff type multi demensional
// generic array type, multiple types in array

class stuff {
// anything can go in here, everything is a object

    Object[] objects = new Object[]{
        "My String",
        10,
        1.0,};
}
