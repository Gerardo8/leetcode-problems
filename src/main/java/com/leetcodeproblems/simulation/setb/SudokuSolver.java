package com.leetcodeproblems.simulation.setb;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **37. Sudoku Solver**.
///
/// ## Problem
/// Fill the empty cells (`'.'`) of a 9 × 9 board so that every row, every column and
/// every 3 × 3 box contains the digits `1`–`9` exactly once. The puzzle has exactly one
/// solution; the board must be modified in place.
///
/// ## Approach — backtracking with bitmasks and the "most constrained cell" heuristic
/// - **Bitmasks:** `rows[r]`, `cols[c]` and `boxes[b]` are 9-bit sets of the digits already
///   used. The candidates of a cell are `~(rows[r] | cols[c] | boxes[b]) & 0x1FF`, computed
///   in constant time, and `Integer.bitCount` gives how many there are.
/// - **MRV heuristic (minimum remaining values):** always branch on the empty cell with the
///   fewest candidates. Cells with a single candidate are filled without real branching,
///   and a cell with zero candidates triggers an immediate backtrack. This shrinks the
///   search tree dramatically compared with filling cells left to right.
/// - **Backtracking:** try each candidate, recurse, and undo the move if it leads to a
///   dead end.
///
/// ## Complexity
/// - Time: exponential in the number of empty cells in the worst case (`O(9^e)`), but the
///   heuristic solves typical puzzles in microseconds to milliseconds.
/// - Space: `O(e)` recursion depth, `e <= 81`.
@LeetCodeProblem(
        number = 37,
        title = "Sudoku Solver",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_9_SET_B,
        pattern = "Backtracking + bitmasks + MRV heuristic",
        slug = "sudoku-solver")
public final class SudokuSolver {

    private static final int SIZE = 9;
    private static final int BOX = 3;
    private static final int ALL_DIGITS = (1 << SIZE) - 1;
    private static final char EMPTY = '.';
    private static final int NO_CELL = -1;

    /// Creates a new, stateless solver.
    public SudokuSolver() {
        // stateless; all search state lives in a per-call Search object
    }

    /// Solves `board` in place.
    ///
    /// @param board a 9 × 9 grid of digits `'1'`–`'9'` and `'.'` for empty cells
    /// @throws IllegalArgumentException if the puzzle has no solution
    public void solveSudoku(char[][] board) {
        Search search = new Search(board);
        if (!search.solve()) {
            throw new IllegalArgumentException("The Sudoku puzzle has no solution");
        }
    }

    /// Mutable state of one solving run.
    private static final class Search {
        private final char[][] board;
        private final int[] rows = new int[SIZE];
        private final int[] cols = new int[SIZE];
        private final int[] boxes = new int[SIZE];

        Search(char[][] board) {
            this.board = board;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (board[row][col] != EMPTY) {
                        toggle(row, col, board[row][col] - '1');
                    }
                }
            }
        }

        /// {@return `true` once every cell is filled consistently}
        boolean solve() {
            int cell = mostConstrainedEmptyCell();
            if (cell == NO_CELL) {
                return true;
            }
            int row = cell / SIZE;
            int col = cell % SIZE;
            for (int options = candidates(row, col); options != 0; options &= options - 1) {
                int digit = Integer.numberOfTrailingZeros(options);
                place(row, col, digit);
                if (solve()) {
                    return true;
                }
                clear(row, col, digit);
            }
            return false;
        }

        /// {@return the empty cell (as `row * 9 + col`) with the fewest candidates, or `NO_CELL` if the board is full}
        private int mostConstrainedEmptyCell() {
            int best = NO_CELL;
            int bestCount = Integer.MAX_VALUE;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (board[row][col] != EMPTY) {
                        continue;
                    }
                    int count = Integer.bitCount(candidates(row, col));
                    if (count < bestCount) {
                        best = row * SIZE + col;
                        bestCount = count;
                        if (count <= 1) {
                            return best;
                        }
                    }
                }
            }
            return best;
        }

        /// {@return the bitmask of digits that may still go into `(row, col)`}
        private int candidates(int row, int col) {
            return ~(rows[row] | cols[col] | boxes[boxIndex(row, col)]) & ALL_DIGITS;
        }

        private void place(int row, int col, int digit) {
            board[row][col] = (char) ('1' + digit);
            toggle(row, col, digit);
        }

        private void clear(int row, int col, int digit) {
            board[row][col] = EMPTY;
            toggle(row, col, digit);
        }

        /// Flips the bit of `digit` in the row, column and box of `(row, col)`.
        private void toggle(int row, int col, int digit) {
            int bit = 1 << digit;
            rows[row] ^= bit;
            cols[col] ^= bit;
            boxes[boxIndex(row, col)] ^= bit;
        }

        private static int boxIndex(int row, int col) {
            return (row / BOX) * BOX + col / BOX;
        }
    }
}
