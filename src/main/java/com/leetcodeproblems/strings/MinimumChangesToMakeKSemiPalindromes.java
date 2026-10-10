package com.leetcodeproblems.strings;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;
import java.util.stream.IntStream;

/// Solution to LeetCode **2911. Minimum Changes to Make K Semi-palindromes**.
///
/// ## Problem
/// A string of length `len` is a **semi-palindrome** if there is a divisor `d` of `len`
/// with `1 <= d < len` such that, when the characters are grouped by their index modulo
/// `d`, every group reads the same forwards and backwards. (Consequently a single
/// character is never a semi-palindrome.)
///
/// Split `s` into exactly `k` non-empty substrings so that the total number of letter
/// changes needed to turn every substring into a semi-palindrome is minimal, and return
/// that minimum.
///
/// ## Approach — interval cost table + partition DP
/// 1. **Cost of one substring.** For `s[i..j]` try every proper divisor `d` of its length.
///    For a fixed `d`, each of the `d` groups is fixed independently: compare the group's
///    characters from both ends and count mismatches (each mismatch costs one change).
///    `cost[i][j]` is the minimum over all divisors.
/// 2. **Partition DP.** `dp[p][e]` = minimum changes to split the prefix `s[0..e)` into
///    `p` semi-palindromes:
///    `dp[p][e] = min over b of dp[p − 1][b] + cost[b][e − 1]`, where every part has at
///    least two characters. The answer is `dp[k][n]`.
///
/// ## Complexity
/// With `n = |s|` (at most 200):
/// - Time: `O(n² · σ(n))` for the cost table, where `σ(n)` is the cost of trying all
///   divisors, plus `O(k · n²)` for the DP.
/// - Space: `O(n² + k · n)`.
@LeetCodeProblem(
        number = 2911,
        title = "Minimum Changes to Make K Semi-palindromes",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_2,
        pattern = "Dynamic programming over partitions + precomputed interval costs",
        slug = "minimum-changes-to-make-k-semi-palindromes")
public final class MinimumChangesToMakeKSemiPalindromes {

    private static final int UNREACHABLE = Integer.MAX_VALUE / 2;
    private static final int MIN_PART_LENGTH = 2;

    /// Creates a new, stateless solver.
    public MinimumChangesToMakeKSemiPalindromes() {
        // stateless
    }

    /// Returns the minimum number of letter changes needed to split `s` into `k`
    /// semi-palindromes.
    ///
    /// @param s lowercase English letters, `2 <= s.length() <= 200`
    /// @param k number of parts, `1 <= k <= s.length() / 2`
    /// @return the minimum number of changes
    public int minimumChanges(String s, int k) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        int[][] cost = semiPalindromeCosts(chars);

        int[][] dp = new int[k + 1][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, UNREACHABLE);
        }
        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int end = MIN_PART_LENGTH * parts; end <= n; end++) {
                int best = UNREACHABLE;
                for (int start = MIN_PART_LENGTH * (parts - 1); start <= end - MIN_PART_LENGTH; start++) {
                    if (dp[parts - 1][start] != UNREACHABLE) {
                        best = Math.min(best, dp[parts - 1][start] + cost[start][end - 1]);
                    }
                }
                dp[parts][end] = best;
            }
        }
        return dp[k][n];
    }

    /// Builds `cost[i][j]`: the minimum changes that turn `chars[i..j]` into a
    /// semi-palindrome (only meaningful for `j > i`).
    private static int[][] semiPalindromeCosts(char[] chars) {
        int n = chars.length;
        int[][] divisors = properDivisorsUpTo(n);
        int[][] cost = new int[n][n];

        for (int start = 0; start < n; start++) {
            for (int end = start + 1; end < n; end++) {
                int length = end - start + 1;
                int best = Integer.MAX_VALUE;
                for (int d : divisors[length]) {
                    best = Math.min(best, changesForDivisor(chars, start, length, d));
                    if (best == 0) {
                        break;
                    }
                }
                cost[start][end] = best;
            }
        }
        return cost;
    }

    /// Counts the changes needed so that every residue group modulo `d` of
    /// `chars[start .. start + length)` becomes a palindrome.
    private static int changesForDivisor(char[] chars, int start, int length, int d) {
        int groupSize = length / d;
        int changes = 0;
        for (int residue = 0; residue < d; residue++) {
            for (int lo = 0, hi = groupSize - 1; lo < hi; lo++, hi--) {
                if (chars[start + residue + lo * d] != chars[start + residue + hi * d]) {
                    changes++;
                }
            }
        }
        return changes;
    }

    /// For every length `len` in `[0, n]` lists the divisors `d` with `1 <= d < len`.
    private static int[][] properDivisorsUpTo(int n) {
        return IntStream.rangeClosed(0, n)
                .mapToObj(len -> IntStream.range(1, len).filter(d -> len % d == 0).toArray())
                .toArray(int[][]::new);
    }
}
