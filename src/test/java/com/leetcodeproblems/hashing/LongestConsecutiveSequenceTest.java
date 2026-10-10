package com.leetcodeproblems.hashing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("128. Longest Consecutive Sequence")
class LongestConsecutiveSequenceTest {

    private final LongestConsecutiveSequence solution = new LongestConsecutiveSequence();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {100, 4, 200, 1, 3, 2}, 4),
                arguments(new int[] {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, 9),
                arguments(new int[] {1, 0, 1, 2}, 3),
                arguments(new int[] {}, 0),
                arguments(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}, 1),
                arguments(new int[] {Integer.MAX_VALUE - 1, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE + 1}, 2));
    }

    @ParameterizedTest(name = "[{index}] longest={1}")
    @MethodSource("examples")
    void findsLongestRun(int[] nums, int expected) {
        assertEquals(expected, solution.longestConsecutive(nums));
    }

    @Test
    void matchesSortingBasedCount() {
        Random random = new Random(128);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(random.nextInt(40), -30, 30).toArray();
            int[] sorted = Arrays.stream(nums).distinct().sorted().toArray();
            int expected = sorted.length == 0 ? 0 : 1;
            int run = 1;
            for (int i = 1; i < sorted.length; i++) {
                run = sorted[i] == sorted[i - 1] + 1 ? run + 1 : 1;
                expected = Math.max(expected, run);
            }
            assertEquals(expected, solution.longestConsecutive(nums));
        }
    }
}
