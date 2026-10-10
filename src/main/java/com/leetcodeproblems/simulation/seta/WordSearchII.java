package com.leetcodeproblems.simulation.seta;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.List;

/// Solution to LeetCode **212. Word Search II**.
///
/// ## Problem
/// Given a board of letters and a list of words, return every word that can be traced on
/// the board through horizontally or vertically adjacent cells, using each cell at most
/// once per word.
///
/// ## Approach — trie + backtracking DFS with pruning
/// Searching each word separately repeats a lot of work. Instead:
/// 1. Insert all words into a **trie**, so words sharing a prefix share a path.
/// 2. Start a DFS from every cell and walk the board and the trie **together**: a step is
///    taken only if the next letter is a child of the current trie node. Whole branches of
///    the board are skipped as soon as no word continues with the current prefix.
/// 3. When a node that ends a word is reached, record the word and clear the marker so it
///    is reported only once.
/// 4. **Prune** while backtracking: a trie node with no children and no word left is
///    removed from its parent, so later searches do not walk into exhausted branches.
///
/// A `visited` matrix (instead of overwriting board cells) keeps the caller's board intact.
///
/// ## Complexity
/// With `m · n` cells, `W` words of maximum length `L` (`L <= 10`):
/// - Time: `O(m · n · 3^L)` in the worst case (4 directions at the first step, then 3);
///   pruning makes it much faster in practice.
/// - Space: `O(W · L)` for the trie plus `O(L)` recursion depth.
@LeetCodeProblem(
        number = 212,
        title = "Word Search II",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_9_SET_A,
        pattern = "Trie + backtracking DFS with pruning",
        slug = "word-search-ii")
public final class WordSearchII {

    private static final int ALPHABET = 26;
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /// A trie node; `word` is non-null while a not-yet-found word ends here.
    private static final class TrieNode {
        private final TrieNode[] children = new TrieNode[ALPHABET];
        private int childCount;
        private String word;

        /// {@return the child for `letter`, creating it if needed}
        TrieNode childOrCreate(char letter) {
            int slot = letter - 'a';
            if (children[slot] == null) {
                children[slot] = new TrieNode();
                childCount++;
            }
            return children[slot];
        }

        /// {@return the child for `letter`, or `null`}
        TrieNode child(char letter) {
            return children[letter - 'a'];
        }

        /// Detaches the child for `letter`.
        void removeChild(char letter) {
            children[letter - 'a'] = null;
            childCount--;
        }

        /// {@return whether nothing more can be found below this node}
        boolean isExhausted() {
            return childCount == 0 && word == null;
        }
    }

    /// Creates a new, stateless solver.
    public WordSearchII() {
        // stateless
    }

    /// Finds every word of `words` that can be traced on `board`.
    ///
    /// @param board a non-empty grid of lowercase letters; not modified
    /// @param words distinct lowercase words
    /// @return the words found, in discovery order
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        List<String> found = new ArrayList<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (root.child(board[row][col]) != null) {
                    search(board, visited, row, col, root, found);
                }
            }
        }
        return found;
    }

    /// {@return a trie containing every word}
    private static TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                node = node.childOrCreate(word.charAt(i));
            }
            node.word = word;
        }
        return root;
    }

    /// Backtracking step: extends the prefix of `parent` with the letter at `(row, col)`.
    private static void search(char[][] board, boolean[][] visited, int row, int col,
                               TrieNode parent, List<String> found) {
        char letter = board[row][col];
        TrieNode node = parent.child(letter);
        if (node == null) {
            return;
        }
        if (node.word != null) {
            found.add(node.word);
            node.word = null;
        }

        visited[row][col] = true;
        for (int[] direction : DIRECTIONS) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (nextRow >= 0 && nextRow < board.length && nextCol >= 0 && nextCol < board[0].length
                    && !visited[nextRow][nextCol]) {
                search(board, visited, nextRow, nextCol, node, found);
            }
        }
        visited[row][col] = false;

        if (node.isExhausted()) {
            parent.removeChild(letter);
        }
    }
}
