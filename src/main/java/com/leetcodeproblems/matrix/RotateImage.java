package com.leetcodeproblems.matrix;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

/// Solution to LeetCode **48. Rotate Image**.
///
/// ## Problem
/// Rotate an `n × n` matrix by 90 degrees clockwise, **in place** (no second matrix).
///
/// ## Approach — transpose, then mirror each row
/// A clockwise rotation sends cell `(r, c)` to `(c, n − 1 − r)`. That mapping is the
/// composition of two simple in-place operations:
/// 1. **transpose**: `(r, c) → (c, r)` — swap across the main diagonal;
/// 2. **horizontal mirror**: `(r, c) → (r, n − 1 − c)` — reverse every row.
///
/// ## Complexity
/// - Time: `O(n²)` — each cell is moved a constant number of times.
/// - Space: `O(1)`.
@LeetCodeProblem(
        number = 48,
        title = "Rotate Image",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_5,
        pattern = "Matrix transformation (transpose + reverse)",
        slug = "rotate-image")
public final class RotateImage {

    /// Creates a new, stateless solver.
    public RotateImage() {
        // stateless
    }

    /// Rotates the square `matrix` 90 degrees clockwise, in place.
    ///
    /// @param matrix an `n × n` matrix
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int tmp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = tmp;
            }
        }
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    /// Reverses `values` in place.
    private static void reverse(int[] values) {
        for (int left = 0, right = values.length - 1; left < right; left++, right--) {
            int tmp = values[left];
            values[left] = values[right];
            values[right] = tmp;
        }
    }
}
