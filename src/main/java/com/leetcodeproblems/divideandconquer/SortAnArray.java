package com.leetcodeproblems.divideandconquer;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **912. Sort an Array**.
///
/// ## Problem
/// Sort an integer array in ascending order **without built-in sort functions**, in
/// `O(n log n)` time and with the smallest possible extra space.
///
/// ## Approach — top-down merge sort
/// Divide the range in two halves, sort each half recursively (conquer) and merge the two
/// sorted halves (combine). Unlike quicksort, merge sort is `O(n log n)` in the **worst**
/// case, so adversarial inputs (already sorted, all equal, …) cannot degrade it.
///
/// Practical refinements used here:
/// - a single auxiliary buffer is allocated once and reused by every merge;
/// - ranges shorter than [#INSERTION_SORT_THRESHOLD] are insertion-sorted, which is faster
///   on tiny inputs and cuts the recursion tree's leaves;
/// - if the two halves are already in order (`a[mid] <= a[mid + 1]`) the merge is skipped,
///   making sorted inputs run in linear time.
///
/// The input array is not modified; a sorted copy is returned.
///
/// ## Complexity
/// - Time: `O(n log n)` in every case.
/// - Space: `O(n)` buffer + `O(log n)` recursion stack.
@LeetCodeProblem(
        number = 912,
        title = "Sort an Array",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_8,
        pattern = "Merge sort (divide and conquer)",
        slug = "sort-an-array")
public final class SortAnArray {

    /// Ranges with fewer elements than this are sorted with insertion sort.
    public static final int INSERTION_SORT_THRESHOLD = 16;

    /// Creates a new, stateless solver.
    public SortAnArray() {
        // stateless
    }

    /// Returns a sorted copy of `nums`.
    ///
    /// @param nums the values to sort; not modified
    /// @return a new array with the same values in ascending order
    public int[] sortArray(int[] nums) {
        int[] sorted = nums.clone();
        mergeSort(sorted, new int[sorted.length], 0, sorted.length - 1);
        return sorted;
    }

    /// Sorts `values[low..high]` (inclusive) recursively.
    private static void mergeSort(int[] values, int[] buffer, int low, int high) {
        if (high - low + 1 < INSERTION_SORT_THRESHOLD) {
            insertionSort(values, low, high);
            return;
        }
        int mid = (low + high) >>> 1;
        mergeSort(values, buffer, low, mid);
        mergeSort(values, buffer, mid + 1, high);
        if (values[mid] <= values[mid + 1]) {
            return;
        }
        merge(values, buffer, low, mid, high);
    }

    /// Merges the sorted runs `values[low..mid]` and `values[mid+1..high]`.
    private static void merge(int[] values, int[] buffer, int low, int mid, int high) {
        System.arraycopy(values, low, buffer, low, high - low + 1);
        int left = low;
        int right = mid + 1;
        for (int write = low; write <= high; write++) {
            if (left > mid) {
                values[write] = buffer[right++];
            } else if (right > high) {
                values[write] = buffer[left++];
            } else if (buffer[right] < buffer[left]) {
                values[write] = buffer[right++];
            } else {
                values[write] = buffer[left++];
            }
        }
    }

    /// Insertion sort of `values[low..high]` (inclusive); stable and fast for short ranges.
    private static void insertionSort(int[] values, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int current = values[i];
            int j = i - 1;
            while (j >= low && values[j] > current) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = current;
        }
    }
}
