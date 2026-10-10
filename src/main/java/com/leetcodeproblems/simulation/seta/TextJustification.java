package com.leetcodeproblems.simulation.seta;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.List;

/// Solution to LeetCode **68. Text Justification**.
///
/// ## Problem
/// Format `words` into lines of exactly `maxWidth` characters:
/// - pack as many words as possible into each line (greedy);
/// - distribute extra spaces between words as evenly as possible; when they do not divide
///   evenly, the **left** gaps get one more space than the right ones;
/// - the **last line**, and any line with a single word, is left-justified and padded with
///   trailing spaces.
///
/// ## Approach — greedy line packing + arithmetic space distribution
/// 1. **Pack:** starting at word `from`, keep adding words while
///    `letters + nextWord + (wordsInLine) <= maxWidth` (one mandatory space per gap).
/// 2. **Distribute:** with `spaces = maxWidth − letters` and `gaps = wordsInLine − 1`,
///    every gap gets `spaces / gaps` spaces and the first `spaces % gaps` gaps get one extra.
/// 3. **Special lines:** the last line and single-word lines are left-justified.
///
/// Padding uses `StringBuilder.repeat` (added in Java 21).
///
/// ## Complexity
/// - Time: `O(n · maxWidth)` — every output character is written once.
/// - Space: `O(maxWidth)` per line being built.
@LeetCodeProblem(
        number = 68,
        title = "Text Justification",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_9_SET_A,
        pattern = "Greedy packing + string simulation",
        slug = "text-justification")
public final class TextJustification {

    private static final char SPACE = ' ';

    /// Creates a new, stateless solver.
    public TextJustification() {
        // stateless
    }

    /// Fully justifies `words` into lines of width `maxWidth`.
    ///
    /// @param words    words with `1 <= word.length() <= maxWidth`
    /// @param maxWidth the width of every output line
    /// @return the justified lines
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int from = 0;
        while (from < words.length) {
            int to = from;
            int letters = 0;
            while (to < words.length && letters + words[to].length() + (to - from) <= maxWidth) {
                letters += words[to].length();
                to++;
            }

            boolean lastLine = to == words.length;
            boolean singleWord = to - from == 1;
            lines.add(lastLine || singleWord
                    ? leftJustify(words, from, to, maxWidth)
                    : fullyJustify(words, from, to, letters, maxWidth));
            from = to;
        }
        return lines;
    }

    /// Joins `words[from..to)` with single spaces and pads the line on the right.
    private static String leftJustify(String[] words, int from, int to, int maxWidth) {
        StringBuilder line = new StringBuilder(maxWidth);
        for (int i = from; i < to; i++) {
            if (i > from) {
                line.append(SPACE);
            }
            line.append(words[i]);
        }
        line.repeat(SPACE, maxWidth - line.length());
        return line.toString();
    }

    /// Spreads the spaces of a line holding `words[from..to)` (at least two words).
    private static String fullyJustify(String[] words, int from, int to, int letters, int maxWidth) {
        int gaps = to - from - 1;
        int spaces = maxWidth - letters;
        int evenShare = spaces / gaps;
        int extra = spaces % gaps;

        StringBuilder line = new StringBuilder(maxWidth);
        for (int i = from; i < to; i++) {
            line.append(words[i]);
            int gap = i - from;
            if (gap < gaps) {
                line.repeat(SPACE, evenShare + (gap < extra ? 1 : 0));
            }
        }
        return line.toString();
    }
}
