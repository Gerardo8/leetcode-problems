package com.leetcodeproblems.matrix;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayDeque;
import java.util.Deque;

/// Solution to LeetCode **200. Number of Islands**.
///
/// ## Problem
/// Given a grid of `'1'` (land) and `'0'` (water), count the islands: maximal groups of
/// land cells connected horizontally or vertically.
///
/// ## Approach — connected components with BFS flood fill
/// Scan the grid. Every time an unvisited land cell is found, a new island starts:
/// increment the counter and flood-fill (BFS) the whole island, marking its cells as
/// visited so they are never counted again.
///
/// Implementation notes: the BFS is iterative to avoid stack overflows on large islands,
/// and a `visited` matrix is used instead of overwriting the caller's grid.
/// The loop `while (queue.poll() instanceof Cell(int row, int col))` uses a record pattern
/// (Java 21) to dequeue and destructure in one step; it ends when `poll()` returns `null`.
///
/// ## Complexity
/// - Time: `O(rows · cols)` — each cell is enqueued at most once.
/// - Space: `O(rows · cols)` for `visited` and the queue.
@LeetCodeProblem(
        number = 200,
        title = "Number of Islands",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_5,
        pattern = "Connected components (BFS flood fill)",
        slug = "number-of-islands")
public final class NumberOfIslands {

    private static final char LAND = '1';
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /// A grid coordinate.
    private record Cell(int row, int col) { }

    /// Creates a new, stateless solver.
    public NumberOfIslands() {
        // stateless
    }

    /// Counts the islands of `grid`.
    ///
    /// @param grid matrix of `'1'` (land) and `'0'` (water); not modified
    /// @return the number of islands
    public int numIslands(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int islands = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == LAND && !visited[row][col]) {
                    islands++;
                    floodFill(grid, visited, new Cell(row, col));
                }
            }
        }
        return islands;
    }

    /// Marks every land cell connected to `start` as visited (iterative BFS).
    private static void floodFill(char[][] grid, boolean[][] visited, Cell start) {
        int rows = grid.length;
        int cols = grid[0].length;
        Deque<Cell> queue = new ArrayDeque<>();
        visited[start.row()][start.col()] = true;
        queue.offer(start);

        while (queue.poll() instanceof Cell(int row, int col)) {
            for (int[] direction : DIRECTIONS) {
                int nextRow = row + direction[0];
                int nextCol = col + direction[1];
                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols
                        && grid[nextRow][nextCol] == LAND && !visited[nextRow][nextCol]) {
                    visited[nextRow][nextCol] = true;
                    queue.offer(new Cell(nextRow, nextCol));
                }
            }
        }
    }
}
