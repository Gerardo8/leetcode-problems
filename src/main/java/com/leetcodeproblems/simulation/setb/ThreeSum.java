package com.leetcodeproblems.simulation.setb;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// Solution to LeetCode **15. 3Sum**.
///
/// ## Problem
/// Return all **unique** triplets `[a, b, c]` from distinct indices of `nums` such that
/// `a + b + c == 0`.
///
/// ## Approach — sort, fix one value, two-pointer the rest
/// 1. Sort a copy of `nums`.
/// 2. Fix `sorted[i]` as the smallest value of the triplet. Skip it if it equals the
///    previous fixed value (it would only repeat triplets), and stop once it is positive
///    (three positive numbers cannot sum to zero).
/// 3. Search `(i, n)` with two pointers for a pair summing to `−sorted[i]`; after a hit,
///    skip equal neighbours on both sides so each triplet appears once.
///
/// ## Complexity
/// - Time: `O(n²)`.
/// - Space: `O(n)` for the sorted copy (output excluded).
@LeetCodeProblem(
        number = 15,
        title = "3Sum",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_9_SET_B,
        pattern = "Sorting + two pointers",
        slug = "3sum")
public final class ThreeSum {

    /// Creates a new, stateless solver.
    public ThreeSum() {
        // stateless
    }

    /// Finds all unique triplets that sum to zero.
    ///
    /// @param nums the input values; not modified
    /// @return unique triplets, each sorted ascending, in lexicographic order
    public List<List<Integer>> threeSum(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        List<List<Integer>> triplets = new ArrayList<>();

        for (int i = 0; i < n - 2 && sorted[i] <= 0; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }
            int low = i + 1;
            int high = n - 1;
            while (low < high) {
                int sum = sorted[i] + sorted[low] + sorted[high];
                if (sum == 0) {
                    triplets.add(List.of(sorted[i], sorted[low], sorted[high]));
                    while (low < high && sorted[low] == sorted[low + 1]) {
                        low++;
                    }
                    while (low < high && sorted[high] == sorted[high - 1]) {
                        high--;
                    }
                    low++;
                    high--;
                } else if (sum < 0) {
                    low++;
                } else {
                    high--;
                }
            }
        }
        return triplets;
    }
}
