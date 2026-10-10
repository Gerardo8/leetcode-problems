package com.leetcodeproblems.greedy;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **45. Jump Game II**.
///
/// ## Problem
/// From index `i` you may jump forward up to `nums[i]` positions. Starting at index `0`,
/// return the minimum number of jumps needed to reach the last index (always possible).
///
/// ## Approach — greedy, level-by-level BFS without a queue
/// Group indices by the number of jumps needed to reach them. Indices reachable with
/// `j` jumps form a contiguous range `[start, currentEnd]`. While scanning that range we
/// track `farthest`, the furthest index reachable with one more jump. When the scan
/// reaches `currentEnd`, we must jump: increment `jumps` and the next range ends at
/// `farthest`. This is exactly BFS on an implicit graph, done in one linear pass.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 45,
        title = "Jump Game II",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_7,
        pattern = "Greedy range expansion (implicit BFS)",
        slug = "jump-game-ii")
public final class JumpGameII {

    /// Creates a new, stateless solver.
    public JumpGameII() {
        // stateless
    }

    /// Returns the minimum number of jumps from the first to the last index.
    ///
    /// @param nums maximum jump length from each index; the last index is reachable
    /// @return the minimum number of jumps
    public int jump(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }
        return jumps;
    }
}
