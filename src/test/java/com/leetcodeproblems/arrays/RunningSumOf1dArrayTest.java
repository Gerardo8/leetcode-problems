package com.leetcodeproblems.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("1480. Running Sum of 1d Array")
class RunningSumOf1dArrayTest {

    private final RunningSumOf1dArray solution = new RunningSumOf1dArray();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 2, 3, 4}, new int[] {1, 3, 6, 10}),
                arguments(new int[] {1, 1, 1, 1, 1}, new int[] {1, 2, 3, 4, 5}),
                arguments(new int[] {3, 1, 2, 10, 1}, new int[] {3, 4, 6, 16, 17}),
                arguments(new int[] {-5}, new int[] {-5}),
                arguments(new int[] {}, new int[] {}));
    }

    @ParameterizedTest(name = "[{index}] loop")
    @MethodSource("examples")
    void computesPrefixSums(int[] nums, int[] expected) {
        assertArrayEquals(expected, solution.runningSum(nums));
    }

    @ParameterizedTest(name = "[{index}] gatherer")
    @MethodSource("examples")
    void gathererVersionComputesPrefixSums(int[] nums, int[] expected) {
        assertArrayEquals(expected, solution.runningSumWithGatherer(nums));
    }

    @Test
    void doesNotModifyInputAndBothVersionsAgree() {
        Random random = new Random(1480);
        for (int round = 0; round < 200; round++) {
            int[] nums = random.ints(random.nextInt(50), -1_000_000, 1_000_000).toArray();
            int[] copy = nums.clone();
            assertArrayEquals(solution.runningSum(nums), solution.runningSumWithGatherer(nums));
            assertArrayEquals(copy, nums);
        }
    }
}
