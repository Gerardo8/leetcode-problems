package com.leetcodeproblems.greedy;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **134. Gas Station**.
///
/// ## Problem
/// `n` stations lie on a circular route. Station `i` provides `gas[i]` units and driving to
/// station `i + 1` costs `cost[i]`. Starting with an empty tank, return the index of the
/// station from which you can drive around the whole circle once, or `-1` if impossible.
/// When a solution exists it is unique.
///
/// ## Approach — single greedy pass
/// Two facts make one pass enough:
/// 1. If the **total** gas is smaller than the total cost, no start works.
/// 2. If starting at `s` the tank first becomes negative when leaving station `i`, then no
///    station between `s` and `i` can be a valid start either (each of them would arrive at
///    `i` with *less* fuel than `s` did). So the next candidate is `i + 1`.
///
/// Keep the running `tank` since the current candidate and the overall `total`; reset the
/// candidate whenever `tank` drops below zero.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 134,
        title = "Gas Station",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_7,
        pattern = "Greedy (reset the start on deficit)",
        slug = "gas-station")
public final class GasStation {

    /// Creates a new, stateless solver.
    public GasStation() {
        // stateless
    }

    /// Finds the starting station that allows a full lap.
    ///
    /// @param gas  fuel available at each station
    /// @param cost fuel needed to reach the next station
    /// @return the starting index, or `-1` if the lap is impossible
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int tank = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int balance = gas[i] - cost[i];
            total += balance;
            tank += balance;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }
}
