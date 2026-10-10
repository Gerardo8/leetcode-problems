package com.leetcodeproblems.hashing;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/// Solution to LeetCode **49. Group Anagrams**.
///
/// ## Problem
/// Group the strings of `strs` so that anagrams (same letters, possibly in a different
/// order) end up in the same group.
///
/// ## Approach — hash map keyed by a letter-count signature
/// Two words are anagrams iff they have the same count of every letter. Encode the 26
/// counters as a 26-character string (counter `i` stored as the `char` with code `i`;
/// counts never exceed 100, so they fit) and use it as the key of a hash map from
/// signature to group.
///
/// Building the signature is `O(L)`, faster than sorting the letters (`O(L log L)`).
/// A `LinkedHashMap` keeps the groups in order of first appearance, which makes the
/// output deterministic. `computeIfAbsent(key, _ -> new ArrayList<>())` uses an unnamed
/// lambda parameter (`_`, Java 22).
///
/// ## Complexity
/// With `n` strings of maximum length `L`:
/// - Time: `O(n · L)`.
/// - Space: `O(n · L)`.
@LeetCodeProblem(
        number = 49,
        title = "Group Anagrams",
        difficulty = Difficulty.MEDIUM,
        day = StudyDay.DAY_6,
        pattern = "Hash map with a canonical key (letter counts)",
        slug = "group-anagrams")
public final class GroupAnagrams {

    private static final int ALPHABET = 26;

    /// Creates a new, stateless solver.
    public GroupAnagrams() {
        // stateless
    }

    /// Groups anagrams together.
    ///
    /// @param strs lowercase English words
    /// @return the groups, in order of first appearance; words keep their input order
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : strs) {
            groups.computeIfAbsent(signature(word), _ -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    /// {@return a key that is equal for two words iff they are anagrams}
    private static String signature(String word) {
        char[] counts = new char[ALPHABET];
        for (int i = 0; i < word.length(); i++) {
            counts[word.charAt(i) - 'a']++;
        }
        return new String(counts);
    }
}
