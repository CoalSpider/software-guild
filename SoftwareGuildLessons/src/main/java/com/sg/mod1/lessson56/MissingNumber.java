/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod1.lessson56;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author Ben Norman
 */
public class MissingNumber {

    static int binarySearch(int[] data, int target) {
        int low = 0;
        int high = data.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;

            if (data[mid] < target) {
                low = mid + 1;
            } else if (data[mid] > target) {
                high = mid - 1;
            } else {
                return mid;
            }

        }

        return -low;
    }

    static int recursiveBinarySearch(int[] data, int target) {
        return binarySearch(data, target, 0, data.length - 1);
    }

    static int binarySearch(int[] data, int target, int low, int high) {
        if (high >= low) {
            int mid = high + low >>> 1;

            if (data[mid] > target) {
                return binarySearch(data, target, low, mid - 1);
            } else if (data[mid] < target) {
                return binarySearch(data, target, mid + 1, high);
            } else {
                return mid;
            }

        }
        return -low;
    }

    static void printNumsInFirstNotInSecond(int[] one, int[] two) {
        // find number in first that is not in second
        for (int i : one) {
            //int indexOf = Arrays.binarySearch(two, i);
            int indexOf = binarySearch(two, i);
            //int indexOf = recursiveBinarySearch(two, i);
            if (indexOf < 0) {
                System.out.println(i + " is not in second array");
            }
        }
    }

    public static void main(String[] args) {
        int[] one = new int[]{1, 2, 3, 4, 5, 6};
        int[] two = new int[]{1, 2, 4, 5, 6, 7};
        int[] three = new int[]{2, 3, 4, 6, 8};
        int[] four = new int[]{1, 3, 4, 6, 8};

        printNumsInFirstNotInSecond(one, two);
        System.out.println("");
        printNumsInFirstNotInSecond(three, four);

        for (int i : one) {
            boolean contains = false;
            for (int j : two) {
                if (i == j) {
                    contains = true;
                    break;
                }
            }
            if (contains == false) {
                System.out.println(i + " is not in second array");
            }
        }
        String[] data = new String[20];
        String[] clone = GenericTest.cloneArray(data);
    }
}

class GenericTest{
    static <T> T[] cloneArray(T[] data){
        T[] clone = (T[])Array.newInstance(data.getClass(), data.length);
        System.arraycopy(data, 0, clone, 0, data.length);
        return clone;
    }
}
