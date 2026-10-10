package com.leetcodeproblems.slidingwindow;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Solution to LeetCode **30. Substring with Concatenation of All Words**.
///
/// ## Problem
/// All strings in `words` have the same length. A *concatenated string* contains every
/// word of `words` exactly once, in any order. Return the starting indices of all
/// concatenated substrings inside `s`.
///
/// ## Approach — word-sized sliding windows, one per offset
/// Let `w` be the word length. Any match starts at an index whose remainder modulo `w`
/// is in `[0, w)`, so run `w` independent passes; pass `offset` reads `s` in chunks of
/// `w` characters starting at `offset`.
///
/// Within a pass keep a window `[left, right)` of whole words and a multiset `seen`:
/// - a chunk that is not a word clears the window (nothing crossing it can match);
/// - adding a word that is now over-represented shrinks the window from the left until
///   the count is legal again;
/// - when the window holds exactly `words.length` words it is a match: record `left`,
///   then drop the leftmost word to keep sliding.
///
/// ## Complexity
/// With `n = |s|`, `w` the word length and `m` the number of words:
/// - Time: `O(n · w)` — each pass touches every chunk at most twice and every chunk
///   costs `O(w)` to extract and hash.
/// - Space: `O(m · w)` for the two maps.
@LeetCodeProblem(
        number = 30,
        title = "Substring with Concatenation of All Words",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_4,
        pattern = "Sliding window over fixed-size chunks + hash map counts",
        slug = "substring-with-concatenation-of-all-words")
public final class SubstringWithConcatenationOfAllWords {

    /// Creates a new, stateless solver.
    public SubstringWithConcatenationOfAllWords() {
        // stateless
    }

    /// Finds every index of `s` where a concatenation of all `words` starts.
    ///
    /// @param s     the text to search
    /// @param words non-empty array of words that all share the same length
    /// @return the starting indices in ascending order
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> starts = new ArrayList<>();
        int wordLength = words[0].length();
        int wordCount = words.length;
        if ((long) wordLength * wordCount > s.length()) {
            return starts;
        }

        Map<String, Integer> required = new HashMap<>();
        for (String word : words) {
            required.merge(word, 1, Integer::sum);
        }

        for (int offset = 0; offset < wordLength; offset++) {
            Map<String, Integer> seen = new HashMap<>();
            int left = offset;
            int wordsInWindow = 0;

            for (int right = offset; right + wordLength <= s.length(); right += wordLength) {
                String word = s.substring(right, right + wordLength);
                if (!(required.get(word) instanceof Integer allowed)) {
                    seen.clear();
                    wordsInWindow = 0;
                    left = right + wordLength;
                    continue;
                }

                seen.merge(word, 1, Integer::sum);
                wordsInWindow++;
                while (seen.get(word) > allowed) {
                    seen.merge(s.substring(left, left + wordLength), -1, Integer::sum);
                    wordsInWindow--;
                    left += wordLength;
                }

                if (wordsInWindow == wordCount) {
                    starts.add(left);
                    seen.merge(s.substring(left, left + wordLength), -1, Integer::sum);
                    wordsInWindow--;
                    left += wordLength;
                }
            }
        }
        starts.sort(Comparator.naturalOrder());
        return starts;
    }
}
