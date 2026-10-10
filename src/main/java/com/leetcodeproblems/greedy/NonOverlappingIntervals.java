package com.leetcodeproblems.greedy;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;
import java.util.Comparator;

/// Solution to LeetCode **435. Non-overlapping Intervals**.
///
/// ## Problem
/// Return the minimum number of intervals to remove so that the remaining ones do not
/// overlap. Intervals that only touch (`[1,2]` and `[2,3]`) do not overlap.
///
/// ## Approach — activity selection: keep the interval that ends first
/// Removing the fewest intervals is the same as keeping the most. Classic interval
/// scheduling says: sort by **end**, then greedily keep every interval that starts at or
/// after the end of the last kept one. Choosing the earliest end always leaves the most
/// room for the rest (an exchange argument shows any optimal schedule can be rewritten to
/// start with that choice). The answer is `n − kept`.
///
/// ## Complexity
/// - Time: `O(n log n)` for sorting.
/// - Space: `O(n)` for the sorted copy of the references.
@LeetCodeProblem(
        number = 435,
        title = "Non-overlapping Intervals",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_7,
        pattern = "Greedy interval scheduling (earliest end first)",
        slug = "non-overlapping-intervals")
public final class NonOverlappingIntervals {

    /// Creates a new, stateless solver.
    public NonOverlappingIntervals() {
        // stateless
    }

    /// Returns the minimum number of intervals to remove to eliminate all overlaps.
    ///
    /// @param intervals `[start, end]` intervals with `start < end`; the array is not reordered
    /// @return the minimum number of removals
    public int eraseOverlapIntervals(int[][] intervals) {
        int[][] byEnd = intervals.clone();
        Arrays.sort(byEnd, Comparator.comparingInt(interval -> interval[1]));

        int kept = 0;
        long lastEnd = Long.MIN_VALUE;
        for (int[] interval : byEnd) {
            if (interval[0] >= lastEnd) {
                kept++;
                lastEnd = interval[1];
            }
        }
        return intervals.length - kept;
    }
}
