package com.leetcodeproblems.slidingwindow;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **76. Minimum Window Substring**.
///
/// ## Problem
/// Return the shortest substring of `s` that contains every character of `t`, including
/// duplicates. Return `""` when no such window exists.
///
/// ## Approach — variable-size sliding window with a "missing" counter
/// `need[c]` stores how many more copies of `c` the window still needs (it becomes
/// negative for surplus characters) and `missing` is the total number still needed.
///
/// 1. **Expand:** move `right` and consume `s[right]`; if it was still needed,
///    decrement `missing`.
/// 2. **Shrink:** while `missing == 0` the window is valid — record it if it is the
///    shortest so far, then release `s[left]` and move `left` forwards. If the released
///    character was required, the window becomes invalid again.
///
/// Each index enters and leaves the window at most once.
///
/// ## Complexity
/// - Time: `O(|s| + |t|)`.
/// - Space: `O(1)` — a fixed table of 128 ASCII counters.
@LeetCodeProblem(
        number = 76,
        title = "Minimum Window Substring",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_4,
        pattern = "Variable-size sliding window + frequency table",
        slug = "minimum-window-substring")
public final class MinimumWindowSubstring {

    private static final int ASCII = 128;

    /// Creates a new, stateless solver.
    public MinimumWindowSubstring() {
        // stateless
    }

    /// Finds the minimum window of `s` that contains all characters of `t`.
    ///
    /// @param s the text to search (ASCII letters)
    /// @param t the characters that must be covered (ASCII letters)
    /// @return the shortest covering substring, or `""` if none exists
    public String minWindow(String s, String t) {
        if (t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        int[] need = new int[ASCII];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        int missing = t.length();
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        for (int left = 0, right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)]-- > 0) {
                missing--;
            }
            while (missing == 0) {
                if (right - left + 1 < bestLength) {
                    bestLength = right - left + 1;
                    bestStart = left;
                }
                if (++need[s.charAt(left++)] > 0) {
                    missing++;
                }
            }
        }
        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }
}
