package com.leetcodeproblems.divideandconquer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("81. Search in Rotated Sorted Array II")
class SearchInRotatedSortedArrayIITest {

    private final SearchInRotatedSortedArrayII solution = new SearchInRotatedSortedArrayII();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {2, 5, 6, 0, 0, 1, 2}, 0, true),
                arguments(new int[] {2, 5, 6, 0, 0, 1, 2}, 3, false),
                arguments(new int[] {1, 0, 1, 1, 1}, 0, true),
                arguments(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 13, true),
                arguments(new int[] {1}, 0, false),
                arguments(new int[] {3, 1}, 1, true));
    }

    @ParameterizedTest(name = "[{index}] target={1} -> {2}")
    @MethodSource("examples")
    void detectsTarget(int[] nums, int target, boolean expected) {
        assertEquals(expected, solution.search(nums, target));
    }

    @Test
    void agreesWithLinearScanOnRotatedArraysWithDuplicates() {
        Random random = new Random(81);
        for (int round = 0; round < 500; round++) {
            int[] sorted = random.ints(1 + random.nextInt(15), 0, 6).sorted().toArray();
            int pivot = random.nextInt(sorted.length);
            int[] rotated = new int[sorted.length];
            for (int i = 0; i < sorted.length; i++) {
                rotated[i] = sorted[(i + pivot) % sorted.length];
            }
            for (int target = -1; target <= 6; target++) {
                int value = target;
                boolean expected = Arrays.stream(rotated).anyMatch(x -> x == value);
                assertEquals(expected, solution.search(rotated, target), Arrays.toString(rotated) + " t=" + target);
            }
        }
    }
}
