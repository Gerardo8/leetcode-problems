package com.leetcodeproblems.divideandconquer;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **81. Search in Rotated Sorted Array II**.
///
/// ## Problem
/// A sorted array **with duplicates** was rotated at an unknown pivot
/// (e.g. `[0,1,2,4,4,4,5,6,6,7]` → `[4,5,6,6,7,0,1,2,4,4]`). Return whether `target`
/// is present.
///
/// ## Approach — binary search on the sorted half
/// For any `mid`, at least one of the halves `[low, mid]` or `[mid, high]` is sorted.
/// - If `nums[low] <= nums[mid]`, the left half is sorted: keep it iff `target` lies in
///   `[nums[low], nums[mid])`, otherwise search the right half.
/// - Otherwise the right half is sorted: keep it iff `target` lies in
///   `(nums[mid], nums[high]]`.
///
/// **Duplicates** add one ambiguous case: `nums[low] == nums[mid] == nums[high]` (e.g.
/// `[1,0,1,1,1]`) does not tell which half is sorted. Neither end can be the target
/// (it equals `nums[mid]`, already checked), so shrink both ends by one and retry.
///
/// ## Complexity
/// - Time: `O(log n)` on average, `O(n)` in the worst case (all duplicates).
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 81,
        title = "Search in Rotated Sorted Array II",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_8,
        pattern = "Modified binary search (find the sorted half)",
        slug = "search-in-rotated-sorted-array-ii")
public final class SearchInRotatedSortedArrayII {

    /// Creates a new, stateless solver.
    public SearchInRotatedSortedArrayII() {
        // stateless
    }

    /// Tells whether `target` occurs in the rotated array `nums`.
    ///
    /// @param nums   a rotated non-decreasing array (duplicates allowed)
    /// @param target the value to find
    /// @return `true` if `target` is present
    public boolean search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
                low++;
                high--;
            } else if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return false;
    }
}
