package com.leetcodeproblems.strings;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;

/// Solution to LeetCode **2514. Count Anagrams**.
///
/// ## Problem
/// `s` contains one or more words separated by single spaces. A string `t` is an anagram
/// of `s` if, for every position `i`, the `i`-th word of `t` is a permutation of the
/// `i`-th word of `s`. Return the number of **distinct** anagrams of `s` modulo
/// `10^9 + 7`.
///
/// ## Approach — combinatorics (multinomial coefficients) with modular inverses
/// Words are independent, so the answer is the product of the answers per word.
/// The number of distinct permutations of a word of length `L` whose letters appear
/// `c₁, c₂, …, c₂₆` times is the multinomial coefficient
/// `L! / (c₁! · c₂! · … · c₂₆!)`.
///
/// Division is not directly available modulo a prime, so we multiply by modular
/// inverses instead. Because `10^9 + 7` is prime, Fermat's little theorem gives
/// `x⁻¹ ≡ x^(p−2) (mod p)`. Factorials and inverse factorials up to `|s|` are
/// precomputed once (one fast exponentiation, then a backwards recurrence).
///
/// ## Complexity
/// - Time: `O(n + 26 · w + log p)` for `n = |s|` characters and `w` words.
/// - Space: `O(n)` for the factorial tables.
@LeetCodeProblem(
        number = 2514,
        title = "Count Anagrams",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_2,
        pattern = "Combinatorics: multinomial coefficients with modular inverses",
        slug = "count-anagrams")
public final class CountAnagrams {

    private static final int MOD = 1_000_000_007;
    private static final int ALPHABET = 26;

    /// Creates a new, stateless solver.
    public CountAnagrams() {
        // stateless
    }

    /// Counts the distinct anagrams of `s`, modulo `10^9 + 7`.
    ///
    /// @param s lowercase words separated by single spaces
    /// @return the number of distinct anagrams modulo `10^9 + 7`
    public int countAnagrams(String s) {
        int n = s.length();
        long[] factorial = new long[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i % MOD;
        }
        long[] inverseFactorial = new long[n + 1];
        inverseFactorial[n] = modPow(factorial[n], MOD - 2);
        for (int i = n; i > 0; i--) {
            inverseFactorial[i - 1] = inverseFactorial[i] * i % MOD;
        }

        long result = 1;
        int[] frequency = new int[ALPHABET];
        int wordLength = 0;
        for (int i = 0; i <= n; i++) {
            if (i == n || s.charAt(i) == ' ') {
                result = result * factorial[wordLength] % MOD;
                for (int count : frequency) {
                    result = result * inverseFactorial[count] % MOD;
                }
                Arrays.fill(frequency, 0);
                wordLength = 0;
            } else {
                frequency[s.charAt(i) - 'a']++;
                wordLength++;
            }
        }
        return (int) result;
    }

    /// Computes `base^exponent mod MOD` by binary exponentiation.
    private static long modPow(long base, long exponent) {
        long result = 1;
        long power = base % MOD;
        for (long e = exponent; e > 0; e >>= 1) {
            if ((e & 1) == 1) {
                result = result * power % MOD;
            }
            power = power * power % MOD;
        }
        return result;
    }
}
