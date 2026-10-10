package com.leetcodeproblems.arrays;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **189. Rotate Array**.
///
/// ## Problem
/// Rotate the array `nums` to the right by `k` steps, in place.
/// Example: `[1,2,3,4,5,6,7]`, `k = 3` → `[5,6,7,1,2,3,4]`.
///
/// ## Approach — triple reversal
/// A right rotation by `k` moves the last `k` elements to the front while keeping the
/// relative order inside both blocks. Three in-place reversals achieve exactly that:
///
/// 1. reverse the whole array: `[7,6,5,4,3,2,1]`
/// 2. reverse the first `k` elements: `[5,6,7,4,3,2,1]`
/// 3. reverse the remaining `n − k` elements: `[5,6,7,1,2,3,4]`
///
/// Because rotating by `n` is a no-op, only `k mod n` steps are performed.
///
/// ## Complexity
/// - Time: `O(n)` — every element is swapped at most twice.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 189,
        title = "Rotate Array",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_1,
        pattern = "In-place reversal",
        slug = "rotate-array")
public final class RotateArray {

    /// Creates a new, stateless solver.
    public RotateArray() {
        // stateless
    }

    /// Rotates `nums` to the right by `k` positions, in place.
    ///
    /// @param nums the array to rotate
    /// @param k    number of steps, `k >= 0`
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return;
        }
        int shift = k % n;
        if (shift == 0) {
            return;
        }
        reverse(nums, 0, n - 1);
        reverse(nums, 0, shift - 1);
        reverse(nums, shift, n - 1);
    }

    /// Reverses `values[from..to]` (both ends inclusive) in place.
    private static void reverse(int[] values, int from, int to) {
        while (from < to) {
            int tmp = values[from];
            values[from++] = values[to];
            values[to--] = tmp;
        }
    }
}
