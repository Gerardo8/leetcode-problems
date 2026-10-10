package com.leetcodeproblems.slidingwindow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("53. Maximum Subarray")
class MaximumSubarrayTest {

    private final MaximumSubarray solution = new MaximumSubarray();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4}, 6),
                arguments(new int[] {1}, 1),
                arguments(new int[] {5, 4, -1, 7, 8}, 23),
                arguments(new int[] {-3, -1, -2}, -1));
    }

    @ParameterizedTest(name = "[{index}] best={1}")
    @MethodSource("examples")
    void findsLargestSum(int[] nums, int expected) {
        assertEquals(expected, solution.maxSubArray(nums));
    }

    @Test
    void matchesAllSubarrays() {
        Random random = new Random(53);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(1 + random.nextInt(30), -20, 20).toArray();
            int expected = Integer.MIN_VALUE;
            for (int from = 0; from < nums.length; from++) {
                int sum = 0;
                for (int to = from; to < nums.length; to++) {
                    sum += nums[to];
                    expected = Math.max(expected, sum);
                }
            }
            assertEquals(expected, solution.maxSubArray(nums));
        }
    }
}
