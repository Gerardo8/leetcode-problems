package com.leetcodeproblems.matrix;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayDeque;
import java.util.Deque;

/// Solution to LeetCode **1020. Number of Enclaves**.
///
/// ## Problem
/// In a binary grid, `1` is land and `0` is sea. A move goes to a 4-directionally adjacent
/// land cell, and walking off the grid counts as escaping. Return how many land cells
/// can **never** escape.
///
/// ## Approach — multi-source BFS from the border ("reverse thinking")
/// Instead of asking, for every land cell, whether it can reach the border, flood-fill
/// from the border inwards: every land cell reachable from a border land cell can escape.
/// The answer is the number of land cells left unmarked.
///
/// The BFS is iterative (no recursion), so large grids cannot overflow the call stack,
/// and a separate `reachable` matrix leaves the caller's grid untouched.
///
/// ## Complexity
/// - Time: `O(rows · cols)`.
/// - Space: `O(rows · cols)` for the marks and the queue.
@LeetCodeProblem(
        number = 1020,
        title = "Number of Enclaves",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_5,
        pattern = "Multi-source BFS flood fill from the border",
        slug = "number-of-enclaves")
public final class NumberOfEnclaves {

    private static final int LAND = 1;
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /// A grid coordinate.
    private record Cell(int row, int col) { }

    /// Creates a new, stateless solver.
    public NumberOfEnclaves() {
        // stateless
    }

    /// Counts the land cells from which the border cannot be reached.
    ///
    /// @param grid binary grid (`1` = land, `0` = sea); not modified
    /// @return the number of enclosed land cells
    public int numEnclaves(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] reachable = new boolean[rows][cols];
        Deque<Cell> queue = new ArrayDeque<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean onBorder = row == 0 || col == 0 || row == rows - 1 || col == cols - 1;
                if (onBorder && grid[row][col] == LAND) {
                    reachable[row][col] = true;
                    queue.offer(new Cell(row, col));
                }
            }
        }

        while (queue.poll() instanceof Cell(int row, int col)) {
            for (int[] direction : DIRECTIONS) {
                int nextRow = row + direction[0];
                int nextCol = col + direction[1];
                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols
                        && grid[nextRow][nextCol] == LAND && !reachable[nextRow][nextCol]) {
                    reachable[nextRow][nextCol] = true;
                    queue.offer(new Cell(nextRow, nextCol));
                }
            }
        }

        int enclosed = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == LAND && !reachable[row][col]) {
                    enclosed++;
                }
            }
        }
        return enclosed;
    }
}
