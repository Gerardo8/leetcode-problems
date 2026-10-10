package com.leetcodeproblems.twopointers;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **283. Move Zeroes**.
///
/// ## Problem
/// Move every `0` of `nums` to the end while keeping the relative order of the non-zero
/// values. The operation must be done in place.
///
/// ## Approach — read/write pointers with swaps
/// `write` is the position where the next non-zero value belongs. Scan with `read`; when
/// a non-zero value is found, swap it into `write` and advance `write`. Everything
/// between `write` and `read` is always zero, so the swap simply moves a zero backwards.
/// Values are written only when `read != write`, which keeps the number of writes minimal.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 283,
        title = "Move Zeroes",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_3,
        pattern = "Two pointers (read/write, stable partition)",
        slug = "move-zeroes")
public final class MoveZeroes {

    /// Creates a new, stateless solver.
    public MoveZeroes() {
        // stateless
    }

    /// Moves all zeros of `nums` to its end, preserving the order of the other values.
    ///
    /// @param nums the array to rearrange in place
    public void moveZeroes(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != 0) {
                if (read != write) {
                    nums[write] = nums[read];
                    nums[read] = 0;
                }
                write++;
            }
        }
    }
}
