package com.leetcodeproblems.twopointers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("42. Trapping Rain Water")
class TrappingRainWaterTest {

    private final TrappingRainWater solution = new TrappingRainWater();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 6),
                arguments(new int[] {4, 2, 0, 3, 2, 5}, 9),
                arguments(new int[] {5}, 0),
                arguments(new int[] {1, 2, 3, 4}, 0),
                arguments(new int[] {3, 0, 3}, 3));
    }

    @ParameterizedTest(name = "[{index}] water={1}")
    @MethodSource("examples")
    void computesTrappedWater(int[] height, int expected) {
        assertEquals(expected, solution.trap(height));
    }

    @Test
    void matchesQuadraticDefinition() {
        Random random = new Random(42);
        for (int round = 0; round < 300; round++) {
            int[] height = random.ints(1 + random.nextInt(25), 0, 10).toArray();
            int expected = 0;
            for (int i = 0; i < height.length; i++) {
                int leftMax = 0;
                int rightMax = 0;
                for (int j = 0; j <= i; j++) {
                    leftMax = Math.max(leftMax, height[j]);
                }
                for (int j = i; j < height.length; j++) {
                    rightMax = Math.max(rightMax, height[j]);
                }
                expected += Math.min(leftMax, rightMax) - height[i];
            }
            assertEquals(expected, solution.trap(height));
        }
    }
}
