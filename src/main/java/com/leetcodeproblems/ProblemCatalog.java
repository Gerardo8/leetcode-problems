package com.leetcodeproblems;

import com.leetcodeproblems.arrays.MergeSortedArray;
import com.leetcodeproblems.arrays.ReverseStringII;
import com.leetcodeproblems.arrays.RotateArray;
import com.leetcodeproblems.arrays.RunningSumOf1dArray;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.ProblemEntry;
import com.leetcodeproblems.catalog.StudyDay;
import com.leetcodeproblems.divideandconquer.BinarySearch;
import com.leetcodeproblems.divideandconquer.MajorityChecker;
import com.leetcodeproblems.divideandconquer.SearchInRotatedSortedArrayII;
import com.leetcodeproblems.divideandconquer.SortAnArray;
import com.leetcodeproblems.greedy.GasStation;
import com.leetcodeproblems.greedy.InsertInterval;
import com.leetcodeproblems.greedy.JumpGameII;
import com.leetcodeproblems.greedy.NonOverlappingIntervals;
import com.leetcodeproblems.hashing.FourSum;
import com.leetcodeproblems.hashing.GroupAnagrams;
import com.leetcodeproblems.hashing.HappyNumber;
import com.leetcodeproblems.hashing.LongestConsecutiveSequence;
import com.leetcodeproblems.matrix.NumberOfEnclaves;
import com.leetcodeproblems.matrix.NumberOfIslands;
import com.leetcodeproblems.matrix.RotateImage;
import com.leetcodeproblems.matrix.SpiralMatrixIII;
import com.leetcodeproblems.simulation.seta.MaximumNumberOfNonOverlappingPalindromeSubstrings;
import com.leetcodeproblems.simulation.seta.ProductOfArrayExceptSelf;
import com.leetcodeproblems.simulation.seta.TextJustification;
import com.leetcodeproblems.simulation.seta.WordSearchII;
import com.leetcodeproblems.simulation.setb.DungeonGame;
import com.leetcodeproblems.simulation.setb.SudokuSolver;
import com.leetcodeproblems.simulation.setb.ThreeSum;
import com.leetcodeproblems.slidingwindow.BestTimeToBuyAndSellStockIV;
import com.leetcodeproblems.slidingwindow.MaximumSubarray;
import com.leetcodeproblems.slidingwindow.MinimumWindowSubstring;
import com.leetcodeproblems.slidingwindow.SubstringWithConcatenationOfAllWords;
import com.leetcodeproblems.strings.CountAnagrams;
import com.leetcodeproblems.strings.LongestCommonPrefixOfKStringsAfterRemoval;
import com.leetcodeproblems.strings.MedianFinder;
import com.leetcodeproblems.strings.MinimumChangesToMakeKSemiPalindromes;
import com.leetcodeproblems.twopointers.MoveZeroes;
import com.leetcodeproblems.twopointers.RemoveDuplicatesFromSortedArrayII;
import com.leetcodeproblems.twopointers.TrappingRainWater;
import com.leetcodeproblems.twopointers.TwoSumII;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/// Index of every solution in the study plan, in the order the plan lists them.
///
/// The catalogue is built from the [LeetCodeProblem] annotation of each class, so the
/// metadata lives next to the code it describes and cannot drift out of sync.
public final class ProblemCatalog {

    /// Solution classes in study-plan order.
    private static final List<Class<?>> SOLUTIONS = List.of(
            // Day 1 — Arrays (1D) foundations + implementation drills
            MergeSortedArray.class,
            RunningSumOf1dArray.class,
            RotateArray.class,
            ReverseStringII.class,
            // Day 2 — Strings + string manipulation
            MinimumChangesToMakeKSemiPalindromes.class,
            CountAnagrams.class,
            LongestCommonPrefixOfKStringsAfterRemoval.class,
            MedianFinder.class,
            // Day 3 — Two pointers
            TwoSumII.class,
            RemoveDuplicatesFromSortedArrayII.class,
            MoveZeroes.class,
            TrappingRainWater.class,
            // Day 4 — Sliding window
            BestTimeToBuyAndSellStockIV.class,
            MaximumSubarray.class,
            MinimumWindowSubstring.class,
            SubstringWithConcatenationOfAllWords.class,
            // Day 5 — Matrix & multidimensional arrays
            RotateImage.class,
            SpiralMatrixIII.class,
            NumberOfEnclaves.class,
            NumberOfIslands.class,
            // Day 6 — HashMaps & sets
            FourSum.class,
            GroupAnagrams.class,
            HappyNumber.class,
            LongestConsecutiveSequence.class,
            // Day 7 — Greedy strategies
            JumpGameII.class,
            GasStation.class,
            InsertInterval.class,
            NonOverlappingIntervals.class,
            // Day 8 — Divide & conquer + recursion
            BinarySearch.class,
            SearchInRotatedSortedArrayII.class,
            SortAnArray.class,
            MajorityChecker.class,
            // Day 9 — CodeSignal-style simulation, set A
            MaximumNumberOfNonOverlappingPalindromeSubstrings.class,
            ProductOfArrayExceptSelf.class,
            WordSearchII.class,
            TextJustification.class,
            // Day 9 — CodeSignal-style simulation, set B
            ThreeSum.class,
            SudokuSolver.class,
            DungeonGame.class);

    private static final List<ProblemEntry> ENTRIES = SOLUTIONS.stream()
            .map(ProblemEntry::new)
            .toList();

    private ProblemCatalog() {
        throw new AssertionError("No instances");
    }

    /// {@return every problem of the study plan, in plan order (immutable)}
    public static List<ProblemEntry> entries() {
        return ENTRIES;
    }

    /// {@return the problems grouped by study day, days in plan order (immutable)}
    public static Map<StudyDay, List<ProblemEntry>> byDay() {
        return Collections.unmodifiableMap(ENTRIES.stream()
                .collect(Collectors.groupingBy(
                        ProblemEntry::day,
                        () -> new EnumMap<>(StudyDay.class),
                        Collectors.toUnmodifiableList())));
    }
}
