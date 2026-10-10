package com.leetcodeproblems.hashing;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// Solution to LeetCode **18. 4Sum**.
///
/// ## Problem
/// Return all **unique** quadruplets `[a, b, c, d]` of values taken from distinct indices
/// of `nums` such that `a + b + c + d == target`.
///
/// ## Approach — sort, fix two values, two-pointer the rest
/// A hash-set solution has to de-duplicate quadruplets after the fact (e.g. by hashing
/// sorted 4-tuples). Sorting first gives de-duplication for free and lets the inner
/// search use two pointers:
///
/// 1. sort a copy of `nums`;
/// 2. fix `nums[i]` and then `nums[j]` (`j > i`), skipping values equal to the previous
///    one at the same level so no quadruplet is produced twice;
/// 3. look for the remaining pair with two pointers moving inwards from both ends of
///    `(j, n)`, again skipping duplicates after a hit;
/// 4. prune: if the smallest possible sum at this level already exceeds `target`, stop;
///    if the largest possible sum is still below it, try the next value.
///
/// Sums are computed with `long` arithmetic because four values up to `10^9` overflow
/// `int`.
///
/// ## Complexity
/// - Time: `O(n³)`.
/// - Space: `O(n)` for the sorted copy (output excluded).
@LeetCodeProblem(
        number = 18,
        title = "4Sum",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_6,
        pattern = "Sorting + k-Sum reduction to two pointers",
        slug = "4sum")
public final class FourSum {

    /// Creates a new, stateless solver.
    public FourSum() {
        // stateless
    }

    /// Finds all unique quadruplets that add up to `target`.
    ///
    /// @param nums   the input values; not modified
    /// @param target the required sum
    /// @return unique quadruplets, each sorted ascending, in lexicographic order
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        List<List<Integer>> quadruplets = new ArrayList<>();

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }
            if ((long) sorted[i] + sorted[i + 1] + sorted[i + 2] + sorted[i + 3] > target) {
                break;
            }
            if ((long) sorted[i] + sorted[n - 3] + sorted[n - 2] + sorted[n - 1] < target) {
                continue;
            }

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && sorted[j] == sorted[j - 1]) {
                    continue;
                }
                if ((long) sorted[i] + sorted[j] + sorted[j + 1] + sorted[j + 2] > target) {
                    break;
                }
                if ((long) sorted[i] + sorted[j] + sorted[n - 2] + sorted[n - 1] < target) {
                    continue;
                }

                long remaining = (long) target - sorted[i] - sorted[j];
                int low = j + 1;
                int high = n - 1;
                while (low < high) {
                    long pairSum = (long) sorted[low] + sorted[high];
                    if (pairSum == remaining) {
                        quadruplets.add(List.of(sorted[i], sorted[j], sorted[low], sorted[high]));
                        while (low < high && sorted[low] == sorted[low + 1]) {
                            low++;
                        }
                        while (low < high && sorted[high] == sorted[high - 1]) {
                            high--;
                        }
                        low++;
                        high--;
                    } else if (pairSum < remaining) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
        }
        return quadruplets;
    }
}
