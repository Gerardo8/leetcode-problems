package com.leetcodeproblems.slidingwindow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("76. Minimum Window Substring")
class MinimumWindowSubstringTest {

    private final MinimumWindowSubstring solution = new MinimumWindowSubstring();

    @ParameterizedTest(name = "minWindow(\"{0}\", \"{1}\") = \"{2}\"")
    @CsvSource({
            "ADOBECODEBANC, ABC, BANC",
            "a,             a,   a",
            "a,             aa,  ''",
            "ab,            b,   b",
            "aAbBcC,        Ac,  AbBc",
            "abc,           d,   ''",
            "bba,           ab,  ba"
    })
    void findsShortestWindow(String s, String t, String expected) {
        assertEquals(expected, solution.minWindow(s, t));
    }

    @Test
    void matchesExhaustiveSearch() {
        Random random = new Random(76);
        for (int round = 0; round < 400; round++) {
            String s = randomString(random, 1 + random.nextInt(12));
            String t = randomString(random, 1 + random.nextInt(4));
            String result = solution.minWindow(s, t);

            int shortest = Integer.MAX_VALUE;
            for (int from = 0; from < s.length(); from++) {
                for (int to = from + 1; to <= s.length(); to++) {
                    if (covers(s.substring(from, to), t)) {
                        shortest = Math.min(shortest, to - from);
                    }
                }
            }
            if (shortest == Integer.MAX_VALUE) {
                assertEquals("", result, s + " / " + t);
            } else {
                assertEquals(shortest, result.length(), s + " / " + t);
                assertTrue(s.contains(result) && covers(result, t), s + " / " + t);
            }
        }
    }

    private static String randomString(Random random, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append((char) ('a' + random.nextInt(3)));
        }
        return text.toString();
    }

    private static boolean covers(String window, String t) {
        int[] counts = new int[128];
        window.chars().forEach(c -> counts[c]++);
        for (char c : t.toCharArray()) {
            if (--counts[c] < 0) {
                return false;
            }
        }
        return true;
    }
}
