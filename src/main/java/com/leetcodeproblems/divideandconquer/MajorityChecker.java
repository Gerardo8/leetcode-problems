package com.leetcodeproblems.divideandconquer;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// Solution to LeetCode **1157. Online Majority Element In Subarray**.
///
/// The class keeps LeetCode's required name, `MajorityChecker`, so it can be pasted into
/// the online judge unchanged.
///
/// ## Problem
/// Build a structure over `arr` that answers many queries `query(left, right, threshold)`:
/// return the element that occurs **at least `threshold` times** in `arr[left..right]`, or
/// `-1` if none does. It is guaranteed that `2 · threshold > right − left + 1`, i.e. the
/// answer, if any, is a strict majority of the range.
///
/// ## Approach — segment tree of Boyer–Moore votes + position lists
/// **Boyer–Moore voting is mergeable.** Summarise a range by a pair
/// `(candidate, count)`. Two summaries combine like two armies: equal candidates add their
/// counts; different candidates cancel each other and the larger survives with the
/// difference. If a range has a strict majority element, the merged candidate of any
/// split of that range is that element. This associative merge is exactly what a
/// **segment tree** needs:
///
/// 1. **Build (divide and conquer):** each node stores the merged vote of its two
///    children; leaves store `(arr[i], 1)`.
/// 2. **Query:** merge the `O(log n)` nodes that cover `[left, right]` to obtain the
///    only possible majority candidate.
/// 3. **Verify:** the candidate might not reach `threshold`, so count its real
///    occurrences with two binary searches in the sorted list of its positions.
///
/// ## Complexity
/// - Construction: `O(n)` time, `O(n)` space.
/// - `query`: `O(log n)` time.
@LeetCodeProblem(
        number = 1157,
        title = "Online Majority Element In Subarray",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_8,
        pattern = "Segment tree + Boyer-Moore voting + binary search",
        slug = "online-majority-element-in-subarray")
public final class MajorityChecker {

    /// A Boyer–Moore summary of a range: the surviving candidate and its net count.
    ///
    /// @param candidate the only element that can be the range's majority
    /// @param count     how many unmatched votes the candidate keeps
    private record Vote(int candidate, int count) {

        /// Summary of an empty range.
        static final Vote EMPTY = new Vote(0, 0);

        /// {@return the summary of the concatenation of this range and `other`}
        Vote merge(Vote other) {
            if (candidate == other.candidate) {
                return new Vote(candidate, count + other.count);
            }
            return count >= other.count
                    ? new Vote(candidate, count - other.count)
                    : new Vote(other.candidate, other.count - count);
        }
    }

    private final int size;
    private final Vote[] tree;
    private final Map<Integer, int[]> positions;

    /// Builds the checker over `arr`.
    ///
    /// @param arr the values (`1 <= arr.length`); the array is not retained
    public MajorityChecker(int[] arr) {
        this.size = arr.length;
        this.tree = new Vote[4 * Math.max(1, size)];
        if (size > 0) {
            build(arr, 1, 0, size - 1);
        }
        this.positions = positionsByValue(arr);
    }

    /// Returns the element occurring at least `threshold` times in `arr[left..right]`.
    ///
    /// @param left      start of the range (inclusive)
    /// @param right     end of the range (inclusive)
    /// @param threshold required number of occurrences, with `2 · threshold > right − left + 1`
    /// @return the majority element, or `-1` if no element reaches `threshold`
    public int query(int left, int right, int threshold) {
        int candidate = query(1, 0, size - 1, left, right).candidate();
        return occurrences(candidate, left, right) >= threshold ? candidate : -1;
    }

    /// Recursively fills node `node`, which covers `arr[low..high]`.
    private void build(int[] arr, int node, int low, int high) {
        if (low == high) {
            tree[node] = new Vote(arr[low], 1);
            return;
        }
        int mid = (low + high) >>> 1;
        build(arr, 2 * node, low, mid);
        build(arr, 2 * node + 1, mid + 1, high);
        tree[node] = tree[2 * node].merge(tree[2 * node + 1]);
    }

    /// Merges the votes of the nodes covering `[left, right]` inside node `node`'s range.
    private Vote query(int node, int low, int high, int left, int right) {
        if (right < low || high < left) {
            return Vote.EMPTY;
        }
        if (left <= low && high <= right) {
            return tree[node];
        }
        int mid = (low + high) >>> 1;
        return query(2 * node, low, mid, left, right)
                .merge(query(2 * node + 1, mid + 1, high, left, right));
    }

    /// {@return how many times `value` occurs in `arr[left..right]`}
    private int occurrences(int value, int left, int right) {
        int[] where = positions.get(value);
        if (where == null) {
            return 0;
        }
        return insertionPoint(where, right + 1) - insertionPoint(where, left);
    }

    /// {@return the number of elements of the ascending, duplicate-free `sorted` that are smaller than `key`}
    private static int insertionPoint(int[] sorted, int key) {
        int index = Arrays.binarySearch(sorted, key);
        return index >= 0 ? index : -index - 1;
    }

    /// {@return for every distinct value, the ascending array of indices where it occurs}
    private static Map<Integer, int[]> positionsByValue(int[] arr) {
        return IntStream.range(0, arr.length)
                .boxed()
                .collect(Collectors.groupingBy(
                        index -> arr[index],
                        Collectors.collectingAndThen(Collectors.toList(), MajorityChecker::toIntArray)));
    }

    /// {@return the list's values as a primitive array}
    private static int[] toIntArray(List<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).toArray();
    }
}
