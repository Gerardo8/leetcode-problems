package com.leetcodeproblems.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("3485. Longest Common Prefix of K Strings After Removal")
class LongestCommonPrefixOfKStringsAfterRemovalTest {

    private final LongestCommonPrefixOfKStringsAfterRemoval solution =
            new LongestCommonPrefixOfKStringsAfterRemoval();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new String[] {"jump", "run", "run", "jump", "run"}, 2, new int[] {3, 4, 4, 3, 4}),
                arguments(new String[] {"dog", "racer", "car"}, 2, new int[] {0, 0, 0}),
                arguments(new String[] {"abc"}, 1, new int[] {0}),
                arguments(new String[] {"abc", "abd"}, 1, new int[] {3, 3}),
                arguments(new String[] {"aa", "aa", "ab"}, 3, new int[] {0, 0, 0}),
                arguments(new String[] {"abcd", "abce", "abx", "abcf"}, 2, new int[] {3, 3, 3, 3}));
    }

    @ParameterizedTest(name = "[{index}] k={1}")
    @MethodSource("examples")
    void solvesExamples(String[] words, int k, int[] expected) {
        assertArrayEquals(expected, solution.longestCommonPrefix(words, k));
    }

    @Test
    void matchesBruteForce() {
        Random random = new Random(3485);
        for (int round = 0; round < 400; round++) {
            int n = 1 + random.nextInt(8);
            String[] words = new String[n];
            for (int i = 0; i < n; i++) {
                StringBuilder word = new StringBuilder();
                int length = 1 + random.nextInt(4);
                for (int c = 0; c < length; c++) {
                    word.append((char) ('a' + random.nextInt(2)));
                }
                words[i] = word.toString();
            }
            int k = 1 + random.nextInt(n);
            assertArrayEquals(bruteForce(words, k), solution.longestCommonPrefix(words, k),
                    Arrays.toString(words) + " k=" + k);
        }
    }

    /// For each removal, tries every prefix of every remaining word and counts its holders.
    private static int[] bruteForce(String[] words, int k) {
        int[] answer = new int[words.length];
        for (int removed = 0; removed < words.length; removed++) {
            List<String> remaining = new ArrayList<>(Arrays.asList(words));
            remaining.remove(removed);
            int best = 0;
            for (String word : remaining) {
                for (int length = 1; length <= word.length(); length++) {
                    String prefix = word.substring(0, length);
                    long holders = remaining.stream().filter(w -> w.startsWith(prefix)).count();
                    if (holders >= k) {
                        best = Math.max(best, length);
                    }
                }
            }
            answer[removed] = best;
        }
        return answer;
    }
}
