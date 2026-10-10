package com.leetcodeproblems.greedy;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.List;

/// Solution to LeetCode **57. Insert Interval**.
///
/// ## Problem
/// `intervals` is sorted by start and non-overlapping. Insert `newInterval` and merge
/// wherever necessary so the result is still sorted and non-overlapping.
///
/// ## Approach — one linear sweep in three phases
/// Because the input is already sorted and disjoint, each interval falls into one of
/// three consecutive groups relative to `newInterval = [start, end]`:
/// 1. intervals that end **before** `start` → copied unchanged;
/// 2. intervals that **overlap** `[start, end]` (their start is `<= end`) → absorbed by
///    widening `start = min(...)` and `end = max(...)`; then the merged interval is added;
/// 3. intervals that start **after** `end` → copied unchanged.
///
/// The output contains copies of the input intervals, so it never aliases the caller's
/// arrays.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(n)` for the output.
@LeetCodeProblem(
        number = 57,
        title = "Insert Interval",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_7,
        pattern = "Interval sweep (before / overlap / after)",
        slug = "insert-interval")
public final class InsertInterval {

    /// Creates a new, stateless solver.
    public InsertInterval() {
        // stateless
    }

    /// Inserts `newInterval` into `intervals`, merging overlaps.
    ///
    /// @param intervals   sorted, pairwise disjoint `[start, end]` intervals; not modified
    /// @param newInterval the `[start, end]` interval to insert
    /// @return the sorted, merged intervals
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> merged = new ArrayList<>(intervals.length + 1);
        int start = newInterval[0];
        int end = newInterval[1];
        int i = 0;
        int n = intervals.length;

        while (i < n && intervals[i][1] < start) {
            merged.add(intervals[i++].clone());
        }
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        merged.add(new int[] {start, end});
        while (i < n) {
            merged.add(intervals[i++].clone());
        }
        return merged.toArray(int[][]::new);
    }
}
