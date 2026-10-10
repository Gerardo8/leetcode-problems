package com.leetcodeproblems.divideandconquer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("704. Binary Search")
class BinarySearchTest {

    private final BinarySearch solution = new BinarySearch();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {-1, 0, 3, 5, 9, 12}, 9, 4),
                arguments(new int[] {-1, 0, 3, 5, 9, 12}, 2, -1),
                arguments(new int[] {5}, 5, 0),
                arguments(new int[] {5}, -5, -1),
                arguments(new int[] {1, 3}, 3, 1),
                arguments(new int[] {1, 3}, 4, -1));
    }

    @ParameterizedTest(name = "[{index}] target={1}")
    @MethodSource("examples")
    void iterativeSearch(int[] nums, int target, int expected) {
        assertEquals(expected, solution.search(nums, target));
    }

    @ParameterizedTest(name = "[{index}] target={1}")
    @MethodSource("examples")
    void recursiveSearch(int[] nums, int target, int expected) {
        assertEquals(expected, solution.searchRecursively(nums, target));
    }

    @Test
    void findsEveryElementAndRejectsGaps() {
        Random random = new Random(704);
        for (int round = 0; round < 100; round++) {
            int[] nums = random.ints(-1_000, 1_000).distinct().limit(1 + random.nextInt(60)).sorted().toArray();
            for (int i = 0; i < nums.length; i++) {
                assertEquals(i, solution.search(nums, nums[i]));
                assertEquals(i, solution.searchRecursively(nums, nums[i]));
            }
            int absent = nums[nums.length - 1] + 1;
            assertEquals(-1, solution.search(nums, absent));
            assertEquals(-1, solution.searchRecursively(nums, absent));
        }
    }
}
