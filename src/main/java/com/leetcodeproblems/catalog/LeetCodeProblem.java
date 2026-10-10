package com.leetcodeproblems.catalog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// Metadata attached to every solution class.
///
/// The annotation is retained at run time so that
/// [com.leetcodeproblems.ProblemCatalog] can build the
/// study-plan index by reflection, and it is [Documented] so the metadata also shows up
/// in the generated Javadoc of each solution.
///
/// Example:
///
/// ```java
/// @LeetCodeProblem(
///         number = 88,
///         title = "Merge Sorted Array",
///         difficulty = Difficulty.EASY,
///         day = StudyDay.DAY_1,
///         pattern = "Two pointers (merge from the back)",
///         slug = "merge-sorted-array")
/// public final class MergeSortedArray { ... }
/// ```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LeetCodeProblem {

    /// {@return the LeetCode problem number}
    int number();

    /// {@return the official problem title}
    String title();

    /// {@return the LeetCode difficulty}
    Difficulty difficulty();

    /// {@return the study day in which the problem appears}
    StudyDay day();

    /// {@return the main algorithm or pattern used by the solution}
    String pattern();

    /// {@return the URL slug, e.g. `merge-sorted-array` for `https://leetcode.com/problems/merge-sorted-array/`}
    String slug();
}
