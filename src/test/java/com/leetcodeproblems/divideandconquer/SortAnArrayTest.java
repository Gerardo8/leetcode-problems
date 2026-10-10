package com.leetcodeproblems.divideandconquer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("912. Sort an Array")
class SortAnArrayTest {

    private final SortAnArray solution = new SortAnArray();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {5, 2, 3, 1}, new int[] {1, 2, 3, 5}),
                arguments(new int[] {5, 1, 1, 2, 0, 0}, new int[] {0, 0, 1, 1, 2, 5}),
                arguments(new int[] {}, new int[] {}),
                arguments(new int[] {7}, new int[] {7}),
                arguments(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void sortsAscending(int[] nums, int[] expected) {
        assertArrayEquals(expected, solution.sortArray(nums));
    }

    @Test
    void matchesLibrarySortAndLeavesInputUntouched() {
        Random random = new Random(912);
        for (int round = 0; round < 200; round++) {
            int[] nums = random.ints(random.nextInt(500), -50_000, 50_001).toArray();
            int[] copy = nums.clone();
            int[] expected = nums.clone();
            Arrays.sort(expected);
            assertArrayEquals(expected, solution.sortArray(nums));
            assertArrayEquals(copy, nums);
        }
    }

    @Test
    void handlesAdversarialShapes() {
        int n = 100_000;
        int[] ascending = IntStream.range(0, n).toArray();
        int[] descending = IntStream.range(0, n).map(i -> n - i).toArray();
        int[] constant = new int[n];
        assertArrayEquals(ascending, solution.sortArray(ascending));
        assertArrayEquals(IntStream.rangeClosed(1, n).toArray(), solution.sortArray(descending));
        assertArrayEquals(constant, solution.sortArray(constant));
    }
}
