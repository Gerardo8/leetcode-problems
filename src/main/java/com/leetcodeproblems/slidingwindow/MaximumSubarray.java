package com.leetcodeproblems.slidingwindow;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **53. Maximum Subarray**.
///
/// ## Problem
/// Find the contiguous, non-empty subarray of `nums` with the largest sum and return
/// that sum.
///
/// ## Approach — Kadane's algorithm (a variable-size window)
/// Think of a window that always ends at the current index. Its best sum is either the
/// current value alone (start a new window) or the current value appended to the best
/// window ending one step earlier:
///
/// `current = max(nums[i], current + nums[i])`
///
/// In other words, whenever the running window sum becomes negative it can only hurt
/// what follows, so the window's left edge jumps to the current index. The answer is the
/// best `current` ever seen.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 53,
        title = "Maximum Subarray",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_4,
        pattern = "Kadane's algorithm (dynamic window)",
        slug = "maximum-subarray")
public final class MaximumSubarray {

    /// Creates a new, stateless solver.
    public MaximumSubarray() {
        // stateless
    }

    /// Returns the largest sum of a non-empty contiguous subarray.
    ///
    /// @param nums the input values, `nums.length >= 1`
    /// @return the maximum subarray sum
    public int maxSubArray(int[] nums) {
        int best = nums[0];
        int current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }
}
