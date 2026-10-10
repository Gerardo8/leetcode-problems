package com.leetcodeproblems.twopointers;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **42. Trapping Rain Water**.
///
/// ## Problem
/// `height[i]` is the height of a bar of width 1. Compute how many units of rain water
/// are trapped between the bars after it rains.
///
/// ## Approach — two pointers with running maxima
/// The water above bar `i` is `min(maxLeft(i), maxRight(i)) − height[i]`.
/// Move two pointers inwards and always process the side with the **lower** bar:
/// if `height[left] < height[right]`, the right side already contains a bar at least as
/// tall as anything on the left, so the water at `left` is limited only by `leftMax`.
/// The symmetric argument applies to the right side.
///
/// ## Complexity
/// - Time: `O(n)` — one pass.
/// - Space: `O(1)` — no prefix/suffix arrays are needed.
@LeetCodeProblem(
        number = 42,
        title = "Trapping Rain Water",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_3,
        pattern = "Two pointers with running maxima",
        slug = "trapping-rain-water")
public final class TrappingRainWater {

    /// Creates a new, stateless solver.
    public TrappingRainWater() {
        // stateless
    }

    /// Computes the total amount of trapped rain water.
    ///
    /// @param height non-negative bar heights
    /// @return units of trapped water
    public int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }
}
