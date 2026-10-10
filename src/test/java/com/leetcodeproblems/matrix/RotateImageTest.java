package com.leetcodeproblems.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("48. Rotate Image")
class RotateImageTest {

    private final RotateImage solution = new RotateImage();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(
                        new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                        new int[][] {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}}),
                arguments(
                        new int[][] {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}},
                        new int[][] {{15, 13, 2, 5}, {14, 3, 4, 1}, {12, 6, 8, 9}, {16, 7, 10, 11}}),
                arguments(new int[][] {{1}}, new int[][] {{1}}),
                arguments(new int[][] {{1, 2}, {3, 4}}, new int[][] {{3, 1}, {4, 2}}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void rotatesClockwise(int[][] matrix, int[][] expected) {
        solution.rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void matchesIndexFormulaAndFourRotationsAreIdentity() {
        Random random = new Random(48);
        for (int n = 1; n <= 9; n++) {
            int[][] original = new int[n][];
            for (int row = 0; row < n; row++) {
                original[row] = random.ints(n, -100, 100).toArray();
            }
            int[][] matrix = Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);

            solution.rotate(matrix);
            int[][] expected = new int[n][n];
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    expected[col][n - 1 - row] = original[row][col];
                }
            }
            assertArrayEquals(expected, matrix);

            solution.rotate(matrix);
            solution.rotate(matrix);
            solution.rotate(matrix);
            assertArrayEquals(original, matrix);
        }
    }
}
