package com.leetcodeproblems.catalog;

import java.util.Objects;

/// One line of the study-plan index: a solution class together with its metadata.
///
/// @param solutionType the class that solves the problem
/// @param metadata     the [LeetCodeProblem] annotation found on `solutionType`
public record ProblemEntry(Class<?> solutionType, LeetCodeProblem metadata) {

    /// Base URL of every LeetCode problem page.
    public static final String LEETCODE_PROBLEMS_URL = "https://leetcode.com/problems/";

    /// Canonical constructor that rejects `null` components.
    ///
    /// @param solutionType the class that solves the problem
    /// @param metadata     the metadata of the problem
    public ProblemEntry {
        Objects.requireNonNull(solutionType, "solutionType");
        Objects.requireNonNull(metadata, "metadata");
    }

    /// Creates an entry by reading the [LeetCodeProblem] annotation of a solution class.
    ///
    /// The validation runs *before* delegating to the canonical constructor, which is only
    /// legal since Java 25 thanks to flexible constructor bodies (JEP 513).
    ///
    /// @param solutionType a class annotated with [LeetCodeProblem]
    /// @throws NullPointerException     if `solutionType` is `null`
    /// @throws IllegalArgumentException if the class is not annotated
    public ProblemEntry(Class<?> solutionType) {
        Objects.requireNonNull(solutionType, "solutionType");
        LeetCodeProblem annotation = solutionType.getAnnotation(LeetCodeProblem.class);
        if (annotation == null) {
            throw new IllegalArgumentException(
                    solutionType.getName() + " is not annotated with @" + LeetCodeProblem.class.getSimpleName());
        }
        this(solutionType, annotation);
    }

    /// {@return the LeetCode problem number}
    public int number() {
        return metadata.number();
    }

    /// {@return the official problem title}
    public String title() {
        return metadata.title();
    }

    /// {@return the LeetCode difficulty}
    public Difficulty difficulty() {
        return metadata.difficulty();
    }

    /// {@return the study day in which the problem appears}
    public StudyDay day() {
        return metadata.day();
    }

    /// {@return the main algorithm or pattern used by the solution}
    public String pattern() {
        return metadata.pattern();
    }

    /// {@return the full URL of the problem statement on LeetCode}
    public String url() {
        return LEETCODE_PROBLEMS_URL + metadata.slug() + "/";
    }
}
