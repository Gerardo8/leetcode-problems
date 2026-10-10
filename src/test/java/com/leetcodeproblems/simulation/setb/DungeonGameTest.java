package com.leetcodeproblems.simulation.setb;

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

@DisplayName("174. Dungeon Game")
class DungeonGameTest {

    private final DungeonGame solution = new DungeonGame();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[][] {{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}, 7),
                arguments(new int[][] {{0}}, 1),
                arguments(new int[][] {{100}}, 1),
                arguments(new int[][] {{-5}}, 6),
                arguments(new int[][] {{1, -3, 3}, {0, -2, 0}, {-3, -3, -3}}, 3),
                arguments(new int[][] {{0, 0, 0}, {-1, 0, 0}, {2, 0, -2}}, 2));
    }

    @ParameterizedTest(name = "[{index}] health={1}")
    @MethodSource("examples")
    void computesMinimumInitialHealth(int[][] dungeon, int expected) {
        assertEquals(expected, solution.calculateMinimumHP(dungeon));
    }

    @Test
    void matchesPathEnumeration() {
        Random random = new Random(174);
        for (int round = 0; round < 300; round++) {
            int rows = 1 + random.nextInt(5);
            int cols = 1 + random.nextInt(5);
            int[][] dungeon = new int[rows][];
            for (int row = 0; row < rows; row++) {
                dungeon[row] = random.ints(cols, -10, 8).toArray();
            }
            assertEquals(bestOverAllPaths(dungeon, 0, 0, 0, 0), solution.calculateMinimumHP(dungeon),
                    Arrays.deepToString(dungeon));
        }
    }

    /// Walks every right/down path; the health needed by a path is `1 − (lowest prefix sum)`.
    private static int bestOverAllPaths(int[][] dungeon, int row, int col, int sum, int lowestPrefix) {
        int newSum = sum + dungeon[row][col];
        int newLowest = Math.min(lowestPrefix, newSum);
        boolean lastRow = row == dungeon.length - 1;
        boolean lastCol = col == dungeon[0].length - 1;
        if (lastRow && lastCol) {
            return Math.max(1, 1 - newLowest);
        }
        int best = Integer.MAX_VALUE;
        if (!lastRow) {
            best = Math.min(best, bestOverAllPaths(dungeon, row + 1, col, newSum, newLowest));
        }
        if (!lastCol) {
            best = Math.min(best, bestOverAllPaths(dungeon, row, col + 1, newSum, newLowest));
        }
        return best;
    }
}
