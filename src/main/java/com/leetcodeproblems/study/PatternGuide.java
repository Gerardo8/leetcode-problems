package com.leetcodeproblems.study;

import java.util.List;

/// Quick-reference notes for the patterns that keep recurring in technical interviews.
public record PatternGuide(String name, String whenToUse, String coreIdea, List<String> commonMistakes) {

    public static List<PatternGuide> quickReference() {
        return List.of(
                new PatternGuide(
                        "Two Pointers",
                        "Sorted arrays, reverse operations, in-place movement and read/write cursor problems.",
                        "Reduce the search space by moving pointers in a controlled direction and preserving invariants.",
                        List.of(
                                "Forgetting that the left and right pointers must be consistent with the remaining state.",
                                "Reading past the end of the array after an in-place mutation.",
                                "Using the wrong traversal direction for a sorted or reversed input.")),
                new PatternGuide(
                        "Sliding Window",
                        "Subarrays or substrings with a constrained sum, size or frequency requirement.",
                        "Expand the right edge, contract the left edge, and keep the window valid while tracking counts.",
                        List.of(
                                "Shrinking too early before the current window is actually valid.",
                                "Failing to update the state when the window changes size.",
                                "Using O(n²) logic instead of amortised O(n) updates.")),
                new PatternGuide(
                        "Hash Map / Set",
                        "Frequency counting, duplicate detection, grouping, set membership or lookup by canonical key.",
                        "Normalise the input into a key and store the state or count needed for the next step.",
                        List.of(
                                "Using a list scan when a hash table gives O(1) lookups.",
                                "Mutating a key while computing the hash value.",
                                "Ignoring edge cases like empty input or duplicate values.")),
                new PatternGuide(
                        "Greedy",
                        "Interval scheduling, resource allocation, local decisions that work globally.",
                        "Choose the locally best step that preserves the possibility of reaching a valid final answer.",
                        List.of(
                                "Believing every local choice is safe without checking the invariant.",
                                "Not sorting before applying interval or range logic.",
                                "Confusing a greedy heuristic with a proof that it is optimal.")),
                new PatternGuide(
                        "Binary Search",
                        "Monotonic search spaces, sorted ranges and answer ranges that can be checked cheap.",
                        "Track the feasible boundary and keep the search invariant true across each midpoint.",
                        List.of(
                                "Using the wrong midpoint formula or forgetting the inclusive/exclusive boundary.",
                                "Not proving that the answer space is monotone.",
                                "Searching the wrong half after a comparison.")),
                new PatternGuide(
                        "Divide & Conquer",
                        "Sorting, recursive decomposition and problems where partial answers can be merged.",
                        "Split the problem into independent halves, solve them recursively and combine the partial results.",
                        List.of(
                                "Not defining a correct merge rule.",
                                "Recursing without reducing the problem size.",
                                "Forgetting to handle the base case and overlap conditions.")),
                new PatternGuide(
                        "BFS / DFS",
                        "Grid traversal, connected components, graph exploration and shortest path in unweighted graphs.",
                        "Explore neighbours while tracking visited states and a queue or stack for order.",
                        List.of(
                                "Revisiting a state without a visited set.",
                                "Mirroring the wrong direction in matrix traversal.",
                                "Forgetting the boundary and repeated-state conditions.")),
                new PatternGuide(
                        "Dynamic Programming",
                        "Repetitive subproblems with overlapping states and a well-defined transition.",
                        "Cache the answer to each subproblem and derive larger states from smaller ones.",
                        List.of(
                                "Not identifying the exact state that captures all relevant history.",
                                "Forgetting the base case or transition direction.",
                                "Choosing a state that is too large or too small to be useful.")),
                new PatternGuide(
                        "Backtracking",
                        "Constraint satisfaction, combinatorial search and candidate generation under pruning.",
                        "Try a candidate, recurse, then undo the change so the search tree remains valid.",
                        List.of(
                                "Not restoring the state after a recursive attempt.",
                                "Searching too many branches without pruning.",
                                "Recording the same arrangement more than once."))
        );
    }
}
