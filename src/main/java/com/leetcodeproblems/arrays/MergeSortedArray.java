package com.leetcodeproblems.arrays;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **88. Merge Sorted Array**.
///
/// ## Problem
/// `nums1` has length `m + n`: its first `m` cells hold a sorted sequence and the last `n`
/// cells are placeholders (zeros). `nums2` holds `n` sorted values. Merge `nums2` into
/// `nums1` **in place** so that `nums1` ends up fully sorted.
///
/// ## Approach — two pointers, merging from the back
/// Filling `nums1` from the front would overwrite values that have not been read yet.
/// Filling it from the **back** is safe because the tail of `nums1` is free space:
///
/// 1. `i` points at the last real value of `nums1`, `j` at the last value of `nums2`
///    and `write` at the last cell of `nums1`.
/// 2. Copy the larger of `nums1[i]` and `nums2[j]` into `nums1[write]` and move that
///    pointer (and `write`) one step to the left.
/// 3. Stop when `nums2` is exhausted; whatever is left of `nums1` is already in place.
///
/// ## Complexity
/// - Time: `O(m + n)` — every element is written once.
/// - Space: `O(1)` — the merge is done in place.
@LeetCodeProblem(
        number = 88,
        title = "Merge Sorted Array",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_1,
        pattern = "Two pointers (merge from the back)",
        slug = "merge-sorted-array")
public final class MergeSortedArray {

    /// Creates a new, stateless solver.
    public MergeSortedArray() {
        // stateless
    }

    /// Merges `nums2` into `nums1` in non-decreasing order.
    ///
    /// @param nums1 array of length `m + n` whose first `m` values are sorted; receives the result
    /// @param m     number of meaningful values at the start of `nums1`
    /// @param nums2 sorted array with `n` values
    /// @param n     number of values in `nums2`
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int write = m + n - 1;

        while (j >= 0) {
            nums1[write--] = (i >= 0 && nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
        }
    }
}
