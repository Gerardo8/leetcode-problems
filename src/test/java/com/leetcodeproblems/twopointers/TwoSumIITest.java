package com.leetcodeproblems.twopointers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("167. Two Sum II - Input Array Is Sorted")
class TwoSumIITest {

    private final TwoSumII solution = new TwoSumII();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {2, 7, 11, 15}, 9, new int[] {1, 2}),
                arguments(new int[] {2, 3, 4}, 6, new int[] {1, 3}),
                arguments(new int[] {-1, 0}, -1, new int[] {1, 2}),
                arguments(new int[] {1, 2, 3, 4, 4, 9, 56, 90}, 8, new int[] {4, 5}),
                arguments(new int[] {-5, -3, 0, 2, 8}, 5, new int[] {2, 5}));
    }

    @ParameterizedTest(name = "[{index}] target={1}")
    @MethodSource("examples")
    void findsOneIndexedPair(int[] numbers, int target, int[] expected) {
        assertArrayEquals(expected, solution.twoSum(numbers, target));
    }

    @Test
    void rejectsInputWithoutSolution() {
        assertThrows(IllegalArgumentException.class, () -> solution.twoSum(new int[] {1, 2, 3}, 100));
    }
}
