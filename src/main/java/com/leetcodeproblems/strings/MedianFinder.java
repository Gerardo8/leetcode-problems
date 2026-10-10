package com.leetcodeproblems.strings;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/// Solution to LeetCode **295. Find Median from Data Stream**.
///
/// The class keeps LeetCode's required name, `MedianFinder`, so it can be pasted into the
/// online judge unchanged.
///
/// ## Problem
/// Design a structure that receives integers one at a time (`addNum`) and can return the
/// median of everything seen so far (`findMedian`) at any moment.
///
/// ## Approach — two heaps
/// Split the numbers into two halves:
/// - `lower`: a **max-heap** with the smaller half;
/// - `upper`: a **min-heap** with the larger half.
///
/// Invariants: every value in `lower` is `<=` every value in `upper`, and
/// `lower.size()` is either `upper.size()` or `upper.size() + 1`.
/// The median is then `lower.peek()` (odd count) or the average of both tops (even count).
///
/// To insert, push into `lower`, move `lower`'s maximum to `upper` (this restores the
/// ordering invariant) and, if `upper` became larger, move its minimum back.
///
/// ## Complexity
/// - `addNum`: `O(log n)`.
/// - `findMedian`: `O(1)`.
/// - Space: `O(n)`.
@LeetCodeProblem(
        number = 295,
        title = "Find Median from Data Stream",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_2,
        pattern = "Two heaps (max-heap + min-heap)",
        slug = "find-median-from-data-stream")
public final class MedianFinder {

    /// Smaller half of the numbers; its head is the largest of them.
    private final PriorityQueue<Integer> lower = new PriorityQueue<>(Comparator.reverseOrder());

    /// Larger half of the numbers; its head is the smallest of them.
    private final PriorityQueue<Integer> upper = new PriorityQueue<>();

    /// Creates an empty median finder.
    public MedianFinder() {
        // both heaps start empty
    }

    /// Adds a number to the data structure.
    ///
    /// @param num the number to add
    public void addNum(int num) {
        lower.offer(num);
        upper.offer(lower.poll());
        if (upper.size() > lower.size()) {
            lower.offer(upper.poll());
        }
    }

    /// Returns the median of all numbers added so far.
    ///
    /// @return the median; the mean of the two middle values when the count is even
    /// @throws NoSuchElementException if no number has been added yet
    public double findMedian() {
        if (lower.isEmpty()) {
            throw new NoSuchElementException("No numbers have been added yet");
        }
        if (lower.size() > upper.size()) {
            return lower.peek();
        }
        return ((long) lower.peek() + upper.peek()) / 2.0;
    }
}
