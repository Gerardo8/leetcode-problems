package com.leetcodeproblems.twopointers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("283. Move Zeroes")
class MoveZeroesTest {

    private final MoveZeroes solution = new MoveZeroes();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {0, 1, 0, 3, 12}, new int[] {1, 3, 12, 0, 0}),
                arguments(new int[] {0}, new int[] {0}),
                arguments(new int[] {1, 2, 3}, new int[] {1, 2, 3}),
                arguments(new int[] {0, 0, 1}, new int[] {1, 0, 0}),
                arguments(new int[] {4, 0, -2, 0, 0, 7}, new int[] {4, -2, 7, 0, 0, 0}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void movesZeroesToTheEndKeepingOrder(int[] nums, int[] expected) {
        solution.moveZeroes(nums);
        assertArrayEquals(expected, nums);
    }
}
