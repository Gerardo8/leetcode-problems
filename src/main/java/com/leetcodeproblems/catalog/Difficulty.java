package com.leetcodeproblems.catalog;

/// Difficulty levels assigned by LeetCode to its problems.
public enum Difficulty {

    /// Introductory problems that usually need a single, well-known idea.
    EASY("Easy"),

    /// Problems that combine several ideas or need a non-obvious observation.
    MEDIUM("Medium"),

    /// Problems that need advanced techniques, careful proofs or tight optimisations.
    HARD("Hard");

    private final String label;

    Difficulty(String label) {
        this.label = label;
    }

    /// {@return the human-readable label, exactly as LeetCode shows it}
    public String label() {
        return label;
    }
}
