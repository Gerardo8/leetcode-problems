package com.leetcodeproblems.strings;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.stream.IntStream;

/// Solution to LeetCode **3485. Longest Common Prefix of K Strings After Removal**.
///
/// ## Problem
/// For every index `i` of `words`, remove `words[i]` and report the length of the longest
/// common prefix shared by **some** `k` of the remaining strings (taken at distinct
/// indices). If fewer than `k` strings remain, the answer is `0`.
///
/// ## Approach — sorting + window LCPs + sliding-window maximum
/// **Key fact 1.** After sorting, all strings sharing a prefix are contiguous. Hence the
/// best `k` strings can always be taken as `k` *consecutive* sorted strings, and the LCP of
/// a sorted window equals the LCP of its first and last strings.
///
/// So with `sorted` the sorted words:
/// - `window[j] = lcp(sorted[j], sorted[j + k − 1])` — every `k`-window;
/// - `widened[j] = lcp(sorted[j], sorted[j + k])` — every `(k + 1)`-window.
///
/// **Key fact 2.** Removing the string at sorted position `p` leaves two kinds of
/// `k`-windows in the new order:
/// 1. old windows that do not contain `p` (entirely left or right of it) → prefix
///    maximum of `window` up to `p − k` and suffix maximum from `p + 1`;
/// 2. windows that "jump over" `p`: old indices `j .. j + k` without `p`, with
///    `p − k < j < p`. Their LCP is `widened[j]` because `p` is neither first nor last.
///    As `p` increases this range slides by one, so a **monotonic deque** yields its
///    maximum in amortised `O(1)`.
///
/// ## Complexity
/// With `n` words of total length `L`:
/// - Time: `O(n log n · c + L)` where `c` is the cost of one string comparison while
///   sorting; computing all LCPs is `O(L)` because `lcp(sorted[j], x) <= |sorted[j]|`.
/// - Space: `O(n)`.
@LeetCodeProblem(
        number = 3485,
        title = "Longest Common Prefix of K Strings After Removal",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_2,
        pattern = "Sorting + prefix/suffix maxima + monotonic deque",
        slug = "longest-common-prefix-of-k-strings-after-removal")
public final class LongestCommonPrefixOfKStringsAfterRemoval {

    /// Creates a new, stateless solver.
    public LongestCommonPrefixOfKStringsAfterRemoval() {
        // stateless
    }

    /// Computes, for every index, the longest common prefix of `k` strings after removing
    /// the string at that index.
    ///
    /// @param words the input strings (lowercase English letters)
    /// @param k     how many strings must share the prefix, `1 <= k <= words.length`
    /// @return `answer[i]` = best prefix length once `words[i]` is removed
    public int[] longestCommonPrefix(String[] words, int k) {
        int n = words.length;
        int[] answer = new int[n];
        if (n - 1 < k) {
            return answer;
        }

        int[] order = IntStream.range(0, n)
                .boxed()
                .sorted(Comparator.comparing(index -> words[index]))
                .mapToInt(Integer::intValue)
                .toArray();
        String[] sorted = new String[n];
        for (int position = 0; position < n; position++) {
            sorted[position] = words[order[position]];
        }

        int windowCount = n - k + 1;
        int[] window = new int[windowCount];
        for (int j = 0; j < windowCount; j++) {
            window[j] = lcp(sorted[j], sorted[j + k - 1]);
        }
        int widenedCount = n - k;
        int[] widened = new int[widenedCount];
        for (int j = 0; j < widenedCount; j++) {
            widened[j] = lcp(sorted[j], sorted[j + k]);
        }

        int[] prefixMax = new int[windowCount];
        int[] suffixMax = new int[windowCount + 1];
        for (int j = 0; j < windowCount; j++) {
            prefixMax[j] = Math.max(window[j], j > 0 ? prefixMax[j - 1] : 0);
        }
        for (int j = windowCount - 1; j >= 0; j--) {
            suffixMax[j] = Math.max(window[j], suffixMax[j + 1]);
        }

        Deque<Integer> candidates = new ArrayDeque<>();
        int nextWidened = 0;
        for (int p = 0; p < n; p++) {
            int best = 0;
            if (p - k >= 0) {
                best = Math.max(best, prefixMax[p - k]);
            }
            if (p + 1 < windowCount) {
                best = Math.max(best, suffixMax[p + 1]);
            }

            int high = Math.min(p - 1, widenedCount - 1);
            while (nextWidened <= high) {
                while (!candidates.isEmpty() && widened[candidates.peekLast()] <= widened[nextWidened]) {
                    candidates.pollLast();
                }
                candidates.offerLast(nextWidened++);
            }
            int low = p - k + 1;
            while (!candidates.isEmpty() && candidates.peekFirst() < low) {
                candidates.pollFirst();
            }
            if (!candidates.isEmpty()) {
                best = Math.max(best, widened[candidates.peekFirst()]);
            }

            answer[order[p]] = best;
        }
        return answer;
    }

    /// {@return the length of the longest common prefix of `a` and `b`}
    private static int lcp(String a, String b) {
        int limit = Math.min(a.length(), b.length());
        int i = 0;
        while (i < limit && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return i;
    }
}
