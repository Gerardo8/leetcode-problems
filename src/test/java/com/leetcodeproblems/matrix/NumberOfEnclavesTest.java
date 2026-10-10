package com.leetcodeproblems.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("1020. Number of Enclaves")
class NumberOfEnclavesTest {

    private final NumberOfEnclaves solution = new NumberOfEnclaves();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[][] {{0, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}}, 3),
                arguments(new int[][] {{0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}}, 0),
                arguments(new int[][] {{1}}, 0),
                arguments(new int[][] {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, 1),
                arguments(new int[][] {
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 0, 0},
                        {0, 1, 0, 1, 1},
                        {0, 0, 1, 1, 0},
                        {0, 0, 0, 0, 0}}, 3));
    }

    @ParameterizedTest(name = "[{index}] enclaves={1}")
    @MethodSource("examples")
    void countsLandThatCannotEscape(int[][] grid, int expected) {
        int[][] copy = Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
        assertEquals(expected, solution.numEnclaves(grid));
        assertArrayEquals(copy, grid);
    }

    @Test
    void handlesLargeGridIteratively() {
        int size = 500;
        int[][] grid = new int[size][size];
        for (int row = 1; row < size - 1; row++) {
            Arrays.fill(grid[row], 1, size - 1, 1);
        }
        assertEquals((size - 2) * (size - 2), solution.numEnclaves(grid));
    }
}
