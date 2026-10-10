package com.leetcodeproblems.simulation.seta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("238. Product of Array Except Self")
class ProductOfArrayExceptSelfTest {

    private final ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 2, 3, 4}, new int[] {24, 12, 8, 6}),
                arguments(new int[] {-1, 1, 0, -3, 3}, new int[] {0, 0, 9, 0, 0}),
                arguments(new int[] {0, 0}, new int[] {0, 0}),
                arguments(new int[] {2, 3}, new int[] {3, 2}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void multipliesEverythingElse(int[] nums, int[] expected) {
        assertArrayEquals(expected, solution.productExceptSelf(nums));
    }

    @Test
    void matchesQuadraticDefinition() {
        Random random = new Random(238);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(2 + random.nextInt(10), -3, 4).toArray();
            int[] expected = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                int product = 1;
                for (int j = 0; j < nums.length; j++) {
                    if (j != i) {
                        product *= nums[j];
                    }
                }
                expected[i] = product;
            }
            assertArrayEquals(expected, solution.productExceptSelf(nums));
        }
    }
}
