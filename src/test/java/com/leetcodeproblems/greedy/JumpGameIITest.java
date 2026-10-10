package com.leetcodeproblems.greedy;

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

@DisplayName("45. Jump Game II")
class JumpGameIITest {

    private final JumpGameII solution = new JumpGameII();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {2, 3, 1, 1, 4}, 2),
                arguments(new int[] {2, 3, 0, 1, 4}, 2),
                arguments(new int[] {0}, 0),
                arguments(new int[] {1, 2}, 1),
                arguments(new int[] {1, 1, 1, 1}, 3),
                arguments(new int[] {10, 0, 0, 0}, 1));
    }

    @ParameterizedTest(name = "[{index}] jumps={1}")
    @MethodSource("examples")
    void findsMinimumJumps(int[] nums, int expected) {
        assertEquals(expected, solution.jump(nums));
    }

    @Test
    void matchesDynamicProgramming() {
        Random random = new Random(45);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(1 + random.nextInt(20), 1, 5).toArray();
            int[] best = new int[nums.length];
            Arrays.fill(best, Integer.MAX_VALUE);
            best[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int step = 1; step <= nums[i] && i + step < nums.length; step++) {
                    best[i + step] = Math.min(best[i + step], best[i] + 1);
                }
            }
            assertEquals(best[nums.length - 1], solution.jump(nums), Arrays.toString(nums));
        }
    }
}
