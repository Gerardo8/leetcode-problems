package com.leetcodeproblems.arrays;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;
import java.util.stream.Gatherers;

/// Solution to LeetCode **1480. Running Sum of 1d Array**.
///
/// ## Problem
/// Given an array `nums`, return `runningSum` where
/// `runningSum[i] = nums[0] + nums[1] + … + nums[i]`.
///
/// ## Approach — prefix sum
/// Keep an accumulator `sum`. Walking left to right, add the current value to `sum`
/// and store it: each output cell reuses the work done for the previous one, so there is
/// no need to re-add the whole prefix every time.
///
/// [#runningSumWithGatherer(int\[\])] shows the same idea expressed declaratively with a
/// **stream gatherer** (`Gatherers.scan`, final since Java 24), which emits every
/// intermediate value of a fold.
///
/// ## Complexity
/// - Time: `O(n)`
/// - Space: `O(1)` besides the output array.
@LeetCodeProblem(
        number = 1480,
        title = "Running Sum of 1d Array",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_1,
        pattern = "Prefix sum",
        slug = "running-sum-of-1d-array")
public final class RunningSumOf1dArray {

    /// Creates a new, stateless solver.
    public RunningSumOf1dArray() {
        // stateless
    }

    /// Computes the running (prefix) sums of `nums` with a single loop.
    ///
    /// The input array is not modified.
    ///
    /// @param nums the input values
    /// @return a new array whose cell `i` holds the sum of `nums[0..i]`
    public int[] runningSum(int[] nums) {
        int[] prefix = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            prefix[i] = sum;
        }
        return prefix;
    }

    /// Computes the running sums with the Stream API and `Gatherers.scan` (JEP 485).
    ///
    /// Functionally identical to [#runningSum(int\[\])]; it exists to showcase stream
    /// gatherers, a feature finalised in Java 24.
    ///
    /// @param nums the input values
    /// @return a new array whose cell `i` holds the sum of `nums[0..i]`
    public int[] runningSumWithGatherer(int[] nums) {
        return Arrays.stream(nums)
                .boxed()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
