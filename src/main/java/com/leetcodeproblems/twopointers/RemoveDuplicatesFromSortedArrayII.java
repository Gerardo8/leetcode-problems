package com.leetcodeproblems.twopointers;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **80. Remove Duplicates from Sorted Array II**.
///
/// ## Problem
/// `nums` is sorted. Remove duplicates **in place** so that each value appears at most
/// twice, keep the relative order, and return the new length `len`; only
/// `nums[0..len)` matters afterwards.
///
/// ## Approach — read/write (fast/slow) pointers
/// A `write` pointer marks the end of the kept prefix while the loop reads every value.
/// Since the array is sorted, a value may be kept iff it differs from the element two
/// positions behind the write pointer, `nums[write − 2]`: if they were equal, the kept
/// prefix would already contain two copies of it.
///
/// The same rule generalises to "at most `m` copies" by comparing with `nums[write − m]`.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 80,
        title = "Remove Duplicates from Sorted Array II",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_3,
        pattern = "Two pointers (read/write)",
        slug = "remove-duplicates-from-sorted-array-ii")
public final class RemoveDuplicatesFromSortedArrayII {

    /// Maximum number of copies of each value that may be kept.
    private static final int MAX_COPIES = 2;

    /// Creates a new, stateless solver.
    public RemoveDuplicatesFromSortedArrayII() {
        // stateless
    }

    /// Keeps at most two copies of every value at the front of `nums`.
    ///
    /// @param nums values sorted in non-decreasing order; rearranged in place
    /// @return the length of the deduplicated prefix
    public int removeDuplicates(int[] nums) {
        int write = 0;
        for (int value : nums) {
            if (write < MAX_COPIES || value != nums[write - MAX_COPIES]) {
                nums[write++] = value;
            }
        }
        return write;
    }
}
