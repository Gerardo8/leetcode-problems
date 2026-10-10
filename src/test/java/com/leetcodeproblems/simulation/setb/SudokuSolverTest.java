package com.leetcodeproblems.simulation.setb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("37. Sudoku Solver")
class SudokuSolverTest {

    private final SudokuSolver solution = new SudokuSolver();

    @Test
    void solvesLeetCodeExample() {
        char[][] board = board(
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79");
        char[][] expected = board(
                "534678912",
                "672195348",
                "198342567",
                "859761423",
                "426853791",
                "713924856",
                "961537284",
                "287419635",
                "345286179");

        solution.solveSudoku(board);
        assertArrayEquals(expected, board);
    }

    @Test
    void solvesAVeryHardPuzzleAndKeepsTheGivens() {
        char[][] puzzle = board(
                "8........",
                "..36.....",
                ".7..9.2..",
                ".5...7...",
                "....457..",
                "...1...3.",
                "..1....68",
                "..85...1.",
                ".9....4..");
        char[][] board = Arrays.stream(puzzle).map(char[]::clone).toArray(char[][]::new);

        solution.solveSudoku(board);

        assertValidSolution(board);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[row][col] != '.') {
                    assertEquals(puzzle[row][col], board[row][col]);
                }
            }
        }
    }

    @Test
    void rejectsUnsolvablePuzzle() {
        char[][] board = board(
                "12345678.",
                "........9",
                ".........",
                ".........",
                ".........",
                ".........",
                ".........",
                ".........",
                ".........");
        assertThrows(IllegalArgumentException.class, () -> solution.solveSudoku(board));
    }

    private static void assertValidSolution(char[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] inRow = new boolean[10];
            boolean[] inCol = new boolean[10];
            boolean[] inBox = new boolean[10];
            for (int j = 0; j < 9; j++) {
                assertTrue(markOnce(inRow, board[i][j]), "row " + i);
                assertTrue(markOnce(inCol, board[j][i]), "column " + i);
                assertTrue(markOnce(inBox, board[3 * (i / 3) + j / 3][3 * (i % 3) + j % 3]), "box " + i);
            }
        }
    }

    private static boolean markOnce(boolean[] seen, char cell) {
        if (cell < '1' || cell > '9' || seen[cell - '0']) {
            return false;
        }
        seen[cell - '0'] = true;
        return true;
    }

    private static char[][] board(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
