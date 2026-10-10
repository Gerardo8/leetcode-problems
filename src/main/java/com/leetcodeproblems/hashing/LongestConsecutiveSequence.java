package com.leetcodeproblems.hashing;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.HashSet;
import java.util.Set;

/// Solution to LeetCode **128. Longest Consecutive Sequence**.
///
/// ## Problem
/// Given an unsorted array, return the length of the longest run of consecutive integers
/// (`x, x + 1, x + 2, …`) that can be formed from its values, in `O(n)` time.
///
/// ## Approach — hash set, counting only from the start of each run
/// Put every value in a `HashSet`. A value `x` **starts** a run iff `x − 1` is absent;
/// only from such starts do we walk `x + 1, x + 2, …` while the set contains them.
/// Because each run is walked exactly once (from its unique start), the total work over
/// all walks is `O(n)`, even though there is a loop inside a loop.
///
/// Bounds are checked explicitly so the method stays correct at `Integer.MIN_VALUE` and
/// `Integer.MAX_VALUE`.
///
/// ## Complexity
/// - Time: `O(n)` expected.
/// - Space: `O(n)`.
@LeetCodeProblem(
        number = 128,
        title = "Longest Consecutive Sequence",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_6,
        pattern = "Hash set + run starts",
        slug = "longest-consecutive-sequence")
public final class LongestConsecutiveSequence {

    /// Creates a new, stateless solver.
    public LongestConsecutiveSequence() {
        // stateless
    }

    /// Returns the length of the longest consecutive-values run in `nums`.
    ///
    /// @param nums the input values (duplicates allowed)
    /// @return the length of the longest run, `0` for an empty array
    public int longestConsecutive(int[] nums) {
        Set<Integer> values = HashSet.newHashSet(nums.length);
        for (int value : nums) {
            values.add(value);
        }

        int longest = 0;
        for (int start : values) {
            if (start != Integer.MIN_VALUE && values.contains(start - 1)) {
                continue;
            }
            int end = start;
            while (end != Integer.MAX_VALUE && values.contains(end + 1)) {
                end++;
            }
            longest = Math.max(longest, end - start + 1);
        }
        return longest;
    }
}
