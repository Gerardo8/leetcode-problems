package com.leetcodeproblems.simulation.seta;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **2472. Maximum Number of Non-overlapping Palindrome Substrings**.
///
/// ## Problem
/// Choose as many non-overlapping substrings of `s` as possible such that each one is a
/// palindrome of length **at least `k`**. Return the maximum count.
///
/// ## Approach — greedy "earliest end first" over short palindromes
/// Two observations turn this into interval scheduling:
/// 1. **Only lengths `k` and `k + 1` matter.** Any palindrome of length `>= k` contains,
///    around the same centre, a palindrome of length `k` or `k + 1` (same parity). That
///    shorter one ends earlier, so it is never a worse choice.
/// 2. **Earliest end wins.** As in classic activity selection, picking the palindrome that
///    ends first leaves the most room for the rest.
///
/// Scan `start` from left to right: if `s[start .. start + k)` is a palindrome take it
/// (it has the earliest possible end); else if `s[start .. start + k]` is one, take that;
/// otherwise advance `start` by one. After taking a palindrome, continue right after it.
///
/// ## Complexity
/// - Time: `O(n · k)` — each start checks at most two candidates of length `<= k + 1`.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 2472,
        title = "Maximum Number of Non-overlapping Palindrome Substrings",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_9_SET_A,
        pattern = "Greedy interval scheduling over palindromes of length k and k + 1",
        slug = "maximum-number-of-non-overlapping-palindrome-substrings")
public final class MaximumNumberOfNonOverlappingPalindromeSubstrings {

    /// Creates a new, stateless solver.
    public MaximumNumberOfNonOverlappingPalindromeSubstrings() {
        // stateless
    }

    /// Returns the maximum number of non-overlapping palindromic substrings of length
    /// at least `k`.
    ///
    /// @param s the input string
    /// @param k minimum palindrome length, `k >= 1`
    /// @return the maximum number of substrings that can be selected
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        int count = 0;
        int start = 0;
        while (start + k <= n) {
            if (isPalindrome(s, start, start + k - 1)) {
                count++;
                start += k;
            } else if (start + k < n && isPalindrome(s, start, start + k)) {
                count++;
                start += k + 1;
            } else {
                start++;
            }
        }
        return count;
    }

    /// {@return whether `s[from..to]` (inclusive) is a palindrome}
    private static boolean isPalindrome(String s, int from, int to) {
        for (int left = from, right = to; left < right; left++, right--) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
        }
        return true;
    }
}
