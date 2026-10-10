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

@DisplayName("189. Rotate Array")
class RotateArrayTest {

    private final RotateArray solution = new RotateArray();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 2, 3, 4, 5, 6, 7}, 3, new int[] {5, 6, 7, 1, 2, 3, 4}),
                arguments(new int[] {-1, -100, 3, 99}, 2, new int[] {3, 99, -1, -100}),
                arguments(new int[] {1, 2, 3}, 0, new int[] {1, 2, 3}),
                arguments(new int[] {1, 2, 3}, 3, new int[] {1, 2, 3}),
                arguments(new int[] {1, 2, 3}, 10, new int[] {3, 1, 2}),
                arguments(new int[] {42}, 7, new int[] {42}));
    }

    @ParameterizedTest(name = "[{index}] k={1}")
    @MethodSource("examples")
    void rotatesToTheRight(int[] nums, int k, int[] expected) {
        solution.rotate(nums, k);
        assertArrayEquals(expected, nums);
    }

    @Test
    void matchesNaiveRotation() {
        Random random = new Random(189);
        for (int round = 0; round < 300; round++) {
            int n = 1 + random.nextInt(20);
            int k = random.nextInt(60);
            int[] nums = random.ints(n, -50, 50).toArray();

            int[] expected = new int[n];
            for (int i = 0; i < n; i++) {
                expected[(i + k) % n] = nums[i];
            }
            solution.rotate(nums, k);
            assertArrayEquals(expected, nums);
        }
    }
}
