package com.leetcodeproblems.hashing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("49. Group Anagrams")
class GroupAnagramsTest {

    private final GroupAnagrams solution = new GroupAnagrams();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"},
                        List.of(List.of("eat", "tea", "ate"), List.of("tan", "nat"), List.of("bat"))),
                arguments(new String[] {""}, List.of(List.of(""))),
                arguments(new String[] {"a"}, List.of(List.of("a"))),
                arguments(new String[] {"ab", "ba", "abc", "", "cab", ""},
                        List.of(List.of("ab", "ba"), List.of("abc", "cab"), List.of("", ""))),
                arguments(new String[] {"aab", "abb"}, List.of(List.of("aab"), List.of("abb"))));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void groupsAnagramsInOrderOfFirstAppearance(String[] strs, List<List<String>> expected) {
        assertEquals(expected, solution.groupAnagrams(strs));
    }
}
