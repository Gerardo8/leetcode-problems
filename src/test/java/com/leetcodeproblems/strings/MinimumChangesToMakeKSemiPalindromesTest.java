package com.leetcodeproblems.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2911. Minimum Changes to Make K Semi-palindromes")
class MinimumChangesToMakeKSemiPalindromesTest {

    private static final String ALPHABET = "abc";

    private final MinimumChangesToMakeKSemiPalindromes solution = new MinimumChangesToMakeKSemiPalindromes();

    @ParameterizedTest(name = "minimumChanges(\"{0}\", {1}) = {2}")
    @CsvSource({
            "abcac,  2, 1",
            "abcdef, 2, 2",
            "aabbaa, 3, 0",
            "ab,     1, 1",
            "abab,   1, 0",
            "abcdba, 1, 1"
    })
    void solvesExamples(String s, int k, int expected) {
        assertEquals(expected, solution.minimumChanges(s, k));
    }

    @Test
    void matchesExhaustiveSearchOnSmallStrings() {
        Random random = new Random(2911);
        for (int round = 0; round < 150; round++) {
            int n = 2 + random.nextInt(6);
            int k = 1 + random.nextInt(n / 2);
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < n; i++) {
                s.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            assertEquals(bruteForce(s.toString(), k), solution.minimumChanges(s.toString(), k), s + " k=" + k);
        }
    }

    /// Tries every partition and, for each part, every target string over the alphabet.
    private static int bruteForce(String s, int k) {
        return bestPartition(s, 0, k);
    }

    private static int bestPartition(String s, int from, int parts) {
        if (parts == 0) {
            return from == s.length() ? 0 : Integer.MAX_VALUE / 2;
        }
        int best = Integer.MAX_VALUE / 2;
        for (int to = from + 2; to <= s.length(); to++) {
            best = Math.min(best, cheapestSemiPalindrome(s.substring(from, to)) + bestPartition(s, to, parts - 1));
        }
        return best;
    }

    private static int cheapestSemiPalindrome(String part) {
        int best = Integer.MAX_VALUE;
        int total = (int) Math.pow(ALPHABET.length(), part.length());
        for (int code = 0; code < total; code++) {
            StringBuilder candidate = new StringBuilder();
            for (int i = 0, c = code; i < part.length(); i++, c /= ALPHABET.length()) {
                candidate.append(ALPHABET.charAt(c % ALPHABET.length()));
            }
            if (isSemiPalindrome(candidate.toString())) {
                int changes = 0;
                for (int i = 0; i < part.length(); i++) {
                    if (part.charAt(i) != candidate.charAt(i)) {
                        changes++;
                    }
                }
                best = Math.min(best, changes);
            }
        }
        return best;
    }

    private static boolean isSemiPalindrome(String t) {
        int len = t.length();
        for (int d = 1; d < len; d++) {
            if (len % d != 0) {
                continue;
            }
            boolean ok = true;
            for (int residue = 0; residue < d && ok; residue++) {
                StringBuilder group = new StringBuilder();
                for (int i = residue; i < len; i += d) {
                    group.append(t.charAt(i));
                }
                ok = group.toString().contentEquals(new StringBuilder(group).reverse());
            }
            if (ok) {
                return true;
            }
        }
        return false;
    }
}
