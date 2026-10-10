package com.leetcodeproblems.slidingwindow;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("30. Substring with Concatenation of All Words")
class SubstringWithConcatenationOfAllWordsTest {

    private final SubstringWithConcatenationOfAllWords solution = new SubstringWithConcatenationOfAllWords();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments("barfoothefoobarman", new String[] {"foo", "bar"}, List.of(0, 9)),
                arguments("wordgoodgoodgoodbestword", new String[] {"word", "good", "best", "word"}, List.of()),
                arguments("barfoofoobarthefoobarman", new String[] {"bar", "foo", "the"}, List.of(6, 9, 12)),
                arguments("aaaaaa", new String[] {"aa", "aa"}, List.of(0, 1, 2)),
                arguments("ab", new String[] {"abc"}, List.of()));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("examples")
    void findsAllConcatenations(String s, String[] words, List<Integer> expected) {
        assertEquals(expected, solution.findSubstring(s, words));
    }

    @Test
    void matchesBruteForce() {
        Random random = new Random(30);
        for (int round = 0; round < 400; round++) {
            int wordLength = 1 + random.nextInt(3);
            String[] words = new String[1 + random.nextInt(3)];
            for (int i = 0; i < words.length; i++) {
                words[i] = randomString(random, wordLength);
            }
            String s = randomString(random, random.nextInt(16));
            assertEquals(bruteForce(s, words), solution.findSubstring(s, words),
                    s + " " + Arrays.toString(words));
        }
    }

    private static List<Integer> bruteForce(String s, String[] words) {
        int wordLength = words[0].length();
        int total = wordLength * words.length;
        String[] expected = words.clone();
        Arrays.sort(expected);
        List<Integer> starts = new ArrayList<>();
        for (int start = 0; start + total <= s.length(); start++) {
            String[] pieces = new String[words.length];
            for (int i = 0; i < words.length; i++) {
                int from = start + i * wordLength;
                pieces[i] = s.substring(from, from + wordLength);
            }
            Arrays.sort(pieces);
            if (Arrays.equals(expected, pieces)) {
                starts.add(start);
            }
        }
        return starts;
    }

    private static String randomString(Random random, int length) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append((char) ('a' + random.nextInt(2)));
        }
        return text.toString();
    }
}
