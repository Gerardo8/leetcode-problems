package com.leetcodeproblems.divideandconquer;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **704. Binary Search**.
///
/// ## Problem
/// Given a sorted array of distinct integers and a `target`, return the index of `target`
/// or `-1` if it is absent, in `O(log n)` time.
///
/// ## Approach — halve the search space on every comparison
/// Keep the invariant "if `target` is present, it lies in `[low, high]`". Compare with the
/// middle element: equal → found; smaller → discard the left half; larger → discard the
/// right half. The midpoint is computed as `(low + high) >>> 1`, which cannot overflow
/// even when `low + high` exceeds `Integer.MAX_VALUE`.
///
/// [#search(int\[\], int)] is the iterative version (preferred in production: no call
/// stack growth); [#searchRecursively(int\[\], int)] expresses the same divide-and-conquer
/// recurrence `T(n) = T(n/2) + O(1)` directly.
///
/// ## Complexity
/// - Time: `O(log n)`.
/// - Space: `O(1)` iterative, `O(log n)` recursive (call stack).
@LeetCodeProblem(
        number = 704,
        title = "Binary Search",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_8,
        pattern = "Binary search (divide and conquer)",
        slug = "binary-search")
public final class BinarySearch {

    /// Creates a new, stateless solver.
    public BinarySearch() {
        // stateless
    }

    /// Searches `target` iteratively.
    ///
    /// @param nums   distinct values sorted ascending
    /// @param target the value to find
    /// @return the index of `target`, or `-1` if it is not present
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /// Searches `target` recursively.
    ///
    /// @param nums   distinct values sorted ascending
    /// @param target the value to find
    /// @return the index of `target`, or `-1` if it is not present
    public int searchRecursively(int[] nums, int target) {
        return search(nums, target, 0, nums.length - 1);
    }

    /// Recursive step: searches `target` inside `nums[low..high]`.
    private static int search(int[] nums, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) >>> 1;
        if (nums[mid] == target) {
            return mid;
        }
        return nums[mid] < target
                ? search(nums, target, mid + 1, high)
                : search(nums, target, low, mid - 1);
    }
}
