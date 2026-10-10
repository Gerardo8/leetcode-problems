package com.leetcodeproblems.greedy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("435. Non-overlapping Intervals")
class NonOverlappingIntervalsTest {

    private final NonOverlappingIntervals solution = new NonOverlappingIntervals();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[][] {{1, 2}, {2, 3}, {3, 4}, {1, 3}}, 1),
                arguments(new int[][] {{1, 2}, {1, 2}, {1, 2}}, 2),
                arguments(new int[][] {{1, 2}, {2, 3}}, 0),
                arguments(new int[][] {{1, 100}, {11, 22}, {1, 11}, {2, 12}}, 2),
                arguments(new int[][] {{-50000, 50000}}, 0));
    }

    @ParameterizedTest(name = "[{index}] removals={1}")
    @MethodSource("examples")
    void countsMinimumRemovals(int[][] intervals, int expected) {
        int[][] original = intervals.clone();
        assertEquals(expected, solution.eraseOverlapIntervals(intervals));
        assertArrayEquals(original, intervals);
    }

    @Test
    void matchesExhaustiveSubsetSearch() {
        Random random = new Random(435);
        for (int round = 0; round < 200; round++) {
            int n = 1 + random.nextInt(10);
            int[][] intervals = new int[n][];
            for (int i = 0; i < n; i++) {
                int start = random.nextInt(10);
                intervals[i] = new int[] {start, start + 1 + random.nextInt(4)};
            }
            int bestKept = 0;
            for (int mask = 0; mask < (1 << n); mask++) {
                if (isDisjoint(intervals, mask)) {
                    bestKept = Math.max(bestKept, Integer.bitCount(mask));
                }
            }
            assertEquals(n - bestKept, solution.eraseOverlapIntervals(intervals));
        }
    }

    private static boolean isDisjoint(int[][] intervals, int mask) {
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                boolean both = (mask >> i & 1) == 1 && (mask >> j & 1) == 1;
                if (both && intervals[i][0] < intervals[j][1] && intervals[j][0] < intervals[i][1]) {
                    return false;
                }
            }
        }
        return true;
    }
}
