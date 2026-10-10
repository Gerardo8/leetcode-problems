package com.leetcodeproblems.simulation.seta;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **238. Product of Array Except Self**.
///
/// ## Problem
/// Return `answer` where `answer[i]` is the product of all elements of `nums` except
/// `nums[i]`, in `O(n)` time and **without using division**.
///
/// ## Approach — prefix products × suffix products
/// `answer[i] = (nums[0] · … · nums[i − 1]) · (nums[i + 1] · … · nums[n − 1])`.
/// 1. Left-to-right pass: store the prefix product *before* `i` in `answer[i]`.
/// 2. Right-to-left pass: keep a running `suffix` product *after* `i` and multiply it in.
///
/// No division is involved, so zeros in the input need no special handling.
///
/// ## Complexity
/// - Time: `O(n)` — two passes.
/// - Space: `O(1)` besides the output array.
@LeetCodeProblem(
        number = 238,
        title = "Product of Array Except Self",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_9_SET_A,
        pattern = "Prefix and suffix products",
        slug = "product-of-array-except-self")
public final class ProductOfArrayExceptSelf {

    /// Creates a new, stateless solver.
    public ProductOfArrayExceptSelf() {
        // stateless
    }

    /// Computes the product of every element except the one at each index.
    ///
    /// @param nums the input values, `nums.length >= 2`
    /// @return a new array of products
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            answer[i] = prefix;
            prefix *= nums[i];
        }
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }
        return answer;
    }
}
