package com.leetcodeproblems.matrix;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **885. Spiral Matrix III**.
///
/// ## Problem
/// Start at `(rStart, cStart)` of a `rows × cols` grid facing east and walk in a clockwise
/// spiral. The walk may leave the grid and come back later. Return the coordinates of all
/// grid cells in the order they are visited.
///
/// ## Approach — simulate the spiral with leg lengths 1, 1, 2, 2, 3, 3, …
/// A square spiral is made of straight legs whose lengths follow the pattern
/// `1, 1, 2, 2, 3, 3, …` with a right turn after each leg (east → south → west → north).
/// Walk step by step, and record a position only when it lies inside the grid. Since the
/// spiral never visits the same point twice, we stop as soon as `rows · cols` cells have
/// been recorded.
///
/// ## Complexity
/// - Time: `O(max(rows, cols)²)` — the spiral must grow until it covers the grid.
/// - Space: `O(rows · cols)` for the output.
@LeetCodeProblem(
        number = 885,
        title = "Spiral Matrix III",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_5,
        pattern = "Simulation (spiral walk with growing legs)",
        slug = "spiral-matrix-iii")
public final class SpiralMatrixIII {

    /// The four headings of the walk, in clockwise order.
    private enum Heading {
        EAST(0, 1), SOUTH(1, 0), WEST(0, -1), NORTH(-1, 0);

        private final int rowStep;
        private final int colStep;

        Heading(int rowStep, int colStep) {
            this.rowStep = rowStep;
            this.colStep = colStep;
        }

        /// {@return the heading after a 90-degree clockwise turn}
        Heading turnRight() {
            return switch (this) {
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
                case NORTH -> EAST;
            };
        }
    }

    /// Creates a new, stateless solver.
    public SpiralMatrixIII() {
        // stateless
    }

    /// Lists the grid cells in the order a clockwise spiral starting at
    /// `(rStart, cStart)` visits them.
    ///
    /// @param rows   number of grid rows
    /// @param cols   number of grid columns
    /// @param rStart starting row, inside the grid
    /// @param cStart starting column, inside the grid
    /// @return `rows · cols` pairs `{row, col}` in visiting order
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int total = rows * cols;
        int[][] visited = new int[total][];
        int count = 0;

        int row = rStart;
        int col = cStart;
        visited[count++] = new int[] {row, col};
        Heading heading = Heading.EAST;

        for (int legLength = 1; count < total; legLength++) {
            for (int leg = 0; leg < 2; leg++) {
                for (int step = 0; step < legLength; step++) {
                    row += heading.rowStep;
                    col += heading.colStep;
                    if (row >= 0 && row < rows && col >= 0 && col < cols) {
                        visited[count++] = new int[] {row, col};
                    }
                }
                heading = heading.turnRight();
            }
        }
        return visited;
    }
}
