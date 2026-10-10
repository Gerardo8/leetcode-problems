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

@DisplayName("200. Number of Islands")
class NumberOfIslandsTest {

    private final NumberOfIslands solution = new NumberOfIslands();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(grid("11110", "11010", "11000", "00000"), 1),
                arguments(grid("11000", "11000", "00100", "00011"), 3),
                arguments(grid("000", "000"), 0),
                arguments(grid("101", "010", "101"), 5),
                arguments(grid("111", "101", "111"), 1));
    }

    @ParameterizedTest(name = "[{index}] islands={1}")
    @MethodSource("examples")
    void countsIslands(char[][] grid, int expected) {
        char[][] copy = Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new);
        assertEquals(expected, solution.numIslands(grid));
        assertArrayEquals(copy, grid);
    }

    @Test
    void handlesOneHugeIslandWithoutStackOverflow() {
        int size = 300;
        char[][] grid = new char[size][size];
        for (char[] row : grid) {
            Arrays.fill(row, '1');
        }
        assertEquals(1, solution.numIslands(grid));
    }

    private static char[][] grid(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
