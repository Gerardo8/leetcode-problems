package com.leetcodeproblems.arrays;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **541. Reverse String II**.
///
/// ## Problem
/// Given a string `s` and an integer `k`, reverse the first `k` characters of every block
/// of `2k` characters, counting from the start of the string.
/// If fewer than `k` characters remain, reverse all of them; if between `k` and `2k`
/// remain, reverse the first `k` and leave the rest unchanged.
///
/// ## Approach — fixed-stride two pointers
/// Jump through the character array with a stride of `2k`. At each block start `start`,
/// reverse the range `[start, min(start + k, n) − 1]` with two pointers that walk
/// towards each other. Using `min` handles the short final block without special cases.
///
/// ## Complexity
/// - Time: `O(n)`.
/// - Space: `O(n)` for the mutable `char[]` copy (Java strings are immutable).
@LeetCodeProblem(
        number = 541,
        title = "Reverse String II",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_1,
        pattern = "Two pointers with a fixed stride",
        slug = "reverse-string-ii")
public final class ReverseStringII {

    /// Creates a new, stateless solver.
    public ReverseStringII() {
        // stateless
    }

    /// Reverses the first `k` characters of every `2k`-character block of `s`.
    ///
    /// @param s the input string
    /// @param k the block size, `k >= 1`
    /// @return the transformed string
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int start = 0; start < chars.length; start += 2 * k) {
            int end = Math.min(start + k, chars.length) - 1;
            reverse(chars, start, end);
        }
        return new String(chars);
    }

    /// Reverses `chars[from..to]` (both ends inclusive) in place.
    private static void reverse(char[] chars, int from, int to) {
        while (from < to) {
            char tmp = chars[from];
            chars[from++] = chars[to];
            chars[to--] = tmp;
        }
    }
}
