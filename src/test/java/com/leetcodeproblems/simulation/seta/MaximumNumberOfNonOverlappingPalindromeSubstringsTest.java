package com.leetcodeproblems.simulation.seta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2472. Maximum Number of Non-overlapping Palindrome Substrings")
class MaximumNumberOfNonOverlappingPalindromeSubstringsTest {

    private final MaximumNumberOfNonOverlappingPalindromeSubstrings solution =
            new MaximumNumberOfNonOverlappingPalindromeSubstrings();

    @ParameterizedTest(name = "maxPalindromes(\"{0}\", {1}) = {2}")
    @CsvSource({
            "abaccdbbd, 3, 2",
            "adbcda,    2, 0",
            "aaaa,      1, 4",
            "a,         1, 1",
            "abba,      4, 1",
            "abcba,     4, 1"
    })
    void solvesExamples(String s, int k, int expected) {
        assertEquals(expected, solution.maxPalindromes(s, k));
    }

    @Test
    void matchesQuadraticDynamicProgramming() {
        Random random = new Random(2472);
        for (int round = 0; round < 500; round++) {
            int n = 1 + random.nextInt(14);
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < n; i++) {
                text.append((char) ('a' + random.nextInt(2)));
            }
            int k = 1 + random.nextInt(n);
            String s = text.toString();
            assertEquals(bruteForce(s, k), solution.maxPalindromes(s, k), s + " k=" + k);
        }
    }

    /// `best[i]` = answer for the prefix of length `i`, trying every palindrome ending there.
    private static int bruteForce(String s, int k) {
        int n = s.length();
        int[] best = new int[n + 1];
        for (int end = 1; end <= n; end++) {
            best[end] = best[end - 1];
            for (int start = 0; start + k <= end; start++) {
                String candidate = s.substring(start, end);
                if (candidate.contentEquals(new StringBuilder(candidate).reverse())) {
                    best[end] = Math.max(best[end], best[start] + 1);
                }
            }
        }
        return best[n];
    }
}
