package com.leetcodeproblems.greedy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("57. Insert Interval")
class InsertIntervalTest {

    private final InsertInterval solution = new InsertInterval();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[][] {{1, 3}, {6, 9}}, new int[] {2, 5}, new int[][] {{1, 5}, {6, 9}}),
                arguments(new int[][] {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[] {4, 8},
                        new int[][] {{1, 2}, {3, 10}, {12, 16}}),
                arguments(new int[][] {}, new int[] {5, 7}, new int[][] {{5, 7}}),
                arguments(new int[][] {{1, 5}}, new int[] {6, 8}, new int[][] {{1, 5}, {6, 8}}),
                arguments(new int[][] {{3, 5}}, new int[] {1, 2}, new int[][] {{1, 2}, {3, 5}}),
                arguments(new int[][] {{1, 5}}, new int[] {5, 7}, new int[][] {{1, 7}}),
                arguments(new int[][] {{2, 3}, {4, 5}}, new int[] {1, 10}, new int[][] {{1, 10}}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void insertsAndMerges(int[][] intervals, int[] newInterval, int[][] expected) {
        int[][] copy = Arrays.stream(intervals).map(int[]::clone).toArray(int[][]::new);
        assertArrayEquals(expected, solution.insert(intervals, newInterval));
        assertArrayEquals(copy, intervals);
    }
}
