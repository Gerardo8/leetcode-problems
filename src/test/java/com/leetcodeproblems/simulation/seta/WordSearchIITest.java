package com.leetcodeproblems.simulation.seta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("212. Word Search II")
class WordSearchIITest {

    private final WordSearchII solution = new WordSearchII();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(board("oaan", "etae", "ihkr", "iflv"),
                        new String[] {"oath", "pea", "eat", "rain"}, Set.of("eat", "oath")),
                arguments(board("ab", "cd"), new String[] {"abcb"}, Set.of()),
                arguments(board("ab", "cd"), new String[] {"a", "ab", "abd", "abdc", "abdca", "acdb"},
                        Set.of("a", "ab", "abd", "abdc", "acdb")),
                arguments(board("a"), new String[] {"a", "aa"}, Set.of("a")),
                arguments(board("aaa", "aaa"), new String[] {"aaaaaa", "aaaaaaa"}, Set.of("aaaaaa")));
    }

    @ParameterizedTest(name = "[{index}] found={2}")
    @MethodSource("examples")
    void findsEveryTraceableWordOnce(char[][] board, String[] words, Set<String> expected) {
        char[][] copy = Arrays.stream(board).map(char[]::clone).toArray(char[][]::new);
        List<String> found = solution.findWords(board, words);

        assertEquals(new TreeSet<>(expected), new TreeSet<>(found));
        assertEquals(expected.size(), found.size());
        assertArrayEquals(copy, board);
    }

    @Test
    void handlesManyWordsSharingPrefixes() {
        char[][] board = board("abcd", "efgh", "ijkl", "mnop");
        String[] words = {"abcd", "abcdhgfe", "aeim", "afkp", "ponm", "abfe", "abfg", "mnopl"};
        Set<String> found = new TreeSet<>(solution.findWords(board, words));
        assertEquals(new TreeSet<>(Set.of("abcd", "abcdhgfe", "aeim", "ponm", "abfe", "abfg", "mnopl")), found);
    }

    private static char[][] board(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
