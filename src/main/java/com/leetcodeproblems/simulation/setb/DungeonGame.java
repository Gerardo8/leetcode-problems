package com.leetcodeproblems.simulation.setb;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;

/// Solution to LeetCode **174. Dungeon Game**.
///
/// ## Problem
/// A knight starts in the top-left room of a dungeon and must reach the princess in the
/// bottom-right room, moving only **right** or **down**. Each room changes his health by
/// `dungeon[r][c]` (negative = demon, positive = potion). If his health ever drops to `0`
/// or below he dies. Return the minimum initial health that guarantees a rescue.
///
/// ## Approach — dynamic programming backwards from the princess
/// Going forwards does not work: the best path depends on both the minimum health along
/// the way *and* the final health, which do not combine nicely. Going **backwards** does:
///
/// `need[r][c]` = minimum health required when **entering** room `(r, c)`:
///
/// `need[r][c] = max(1, min(need[r + 1][c], need[r][c + 1]) − dungeon[r][c])`
///
/// i.e. pick the cheaper of the two next rooms, subtract what this room gives, and never
/// go below 1 (you must be alive). Only the row below is needed, so the table is a single
/// array: before it is overwritten, `need[c]` still holds the value of the row below and
/// `need[c + 1]` already holds the value of the room to the right. A virtual cell below the
/// princess holding `1` seeds the recurrence.
///
/// ## Complexity
/// - Time: `O(rows · cols)`.
/// - Space: `O(cols)`.
@LeetCodeProblem(
        number = 174,
        title = "Dungeon Game",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_9_SET_B,
        pattern = "Dynamic programming in reverse (bottom-up grid DP)",
        slug = "dungeon-game")
public final class DungeonGame {

    /// Creates a new, stateless solver.
    public DungeonGame() {
        // stateless
    }

    /// Returns the knight's minimum initial health.
    ///
    /// @param dungeon health changes per room; at least one row and one column
    /// @return the minimum initial health, always `>= 1`
    public int calculateMinimumHP(int[][] dungeon) {
        int rows = dungeon.length;
        int cols = dungeon[0].length;
        int[] need = new int[cols + 1];
        Arrays.fill(need, Integer.MAX_VALUE);
        need[cols - 1] = 1;

        for (int row = rows - 1; row >= 0; row--) {
            for (int col = cols - 1; col >= 0; col--) {
                int cheapestNext = Math.min(need[col], need[col + 1]);
                need[col] = Math.max(1, cheapestNext - dungeon[row][col]);
            }
        }
        return need[0];
    }
}
