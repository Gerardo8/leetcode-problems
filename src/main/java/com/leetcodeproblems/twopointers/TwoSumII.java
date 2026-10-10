package com.leetcodeproblems.twopointers;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **167. Two Sum II - Input Array Is Sorted**.
///
/// ## Problem
/// `numbers` is sorted in non-decreasing order. Find the two (different) positions whose
/// values add up to `target` and return them **1-indexed**. Exactly one solution exists
/// and only constant extra space may be used.
///
/// ## Approach — opposite-direction two pointers
/// Start with `left` at the smallest value and `right` at the largest.
/// - If the sum is too small, the only way to grow it is to advance `left`.
/// - If the sum is too large, the only way to shrink it is to move `right` back.
///
/// Each step discards one value that provably cannot be part of the answer, so the
/// pointers meet the solution in at most `n − 1` steps.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 167,
        title = "Two Sum II - Input Array Is Sorted",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_3,
        pattern = "Two pointers (opposite ends)",
        slug = "two-sum-ii-input-array-is-sorted")
public final class TwoSumII {

    /// Creates a new, stateless solver.
    public TwoSumII() {
        // stateless
    }

    /// Finds the 1-indexed positions of the two numbers that add up to `target`.
    ///
    /// @param numbers values sorted in non-decreasing order
    /// @param target  the required sum
    /// @return `{index1, index2}` with `1 <= index1 < index2 <= numbers.length`
    /// @throws IllegalArgumentException if no pair adds up to `target`
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            long sum = (long) numbers[left] + numbers[right];
            if (sum == target) {
                return new int[] {left + 1, right + 1};
            }
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        throw new IllegalArgumentException("No two numbers add up to " + target);
    }
}
