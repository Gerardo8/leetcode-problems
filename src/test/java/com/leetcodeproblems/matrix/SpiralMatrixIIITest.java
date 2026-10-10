package com.leetcodeproblems.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("885. Spiral Matrix III")
class SpiralMatrixIIITest {

    private final SpiralMatrixIII solution = new SpiralMatrixIII();

    @Test
    void walksASingleRow() {
        assertArrayEquals(
                new int[][] {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
                solution.spiralMatrixIII(1, 4, 0, 0));
    }

    @Test
    void solvesLeetCodeExampleTwo() {
        int[][] expected = {
                {1, 4}, {1, 5}, {2, 5}, {2, 4}, {2, 3}, {1, 3}, {0, 3}, {0, 4}, {0, 5}, {3, 5},
                {3, 4}, {3, 3}, {3, 2}, {2, 2}, {1, 2}, {0, 2}, {4, 5}, {4, 4}, {4, 3}, {4, 2},
                {4, 1}, {3, 1}, {2, 1}, {1, 1}, {0, 1}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0}};
        assertArrayEquals(expected, solution.spiralMatrixIII(5, 6, 1, 4));
    }

    @Test
    void visitsEveryCellExactlyOnceFromAnyStart() {
        for (int rows = 1; rows <= 6; rows++) {
            for (int cols = 1; cols <= 6; cols++) {
                for (int startRow = 0; startRow < rows; startRow++) {
                    for (int startCol = 0; startCol < cols; startCol++) {
                        int[][] path = solution.spiralMatrixIII(rows, cols, startRow, startCol);
                        assertEquals(rows * cols, path.length);
                        assertArrayEquals(new int[] {startRow, startCol}, path[0]);

                        Set<List<Integer>> distinct = new HashSet<>();
                        for (int[] cell : path) {
                            assertTrue(cell[0] >= 0 && cell[0] < rows && cell[1] >= 0 && cell[1] < cols);
                            distinct.add(List.of(cell[0], cell[1]));
                        }
                        assertEquals(rows * cols, distinct.size());
                    }
                }
            }
        }
    }
}
