package com.leetcodeproblems.simulation.setb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("15. 3Sum")
class ThreeSumTest {

    private static final Comparator<List<Integer>> LEXICOGRAPHIC = Comparator
            .comparing((List<Integer> triplet) -> triplet.get(0))
            .thenComparing(triplet -> triplet.get(1))
            .thenComparing(triplet -> triplet.get(2));

    private final ThreeSum solution = new ThreeSum();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {-1, 0, 1, 2, -1, -4}, List.of(List.of(-1, -1, 2), List.of(-1, 0, 1))),
                arguments(new int[] {0, 1, 1}, List.of()),
                arguments(new int[] {0, 0, 0}, List.of(List.of(0, 0, 0))),
                arguments(new int[] {0, 0, 0, 0}, List.of(List.of(0, 0, 0))),
                arguments(new int[] {-2, 0, 1, 1, 2}, List.of(List.of(-2, 0, 2), List.of(-2, 1, 1))));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void findsUniqueZeroSumTriplets(int[] nums, List<List<Integer>> expected) {
        assertEquals(expected, solution.threeSum(nums));
    }

    @Test
    void matchesBruteForce() {
        Random random = new Random(15);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(3 + random.nextInt(15), -6, 7).toArray();
            Set<List<Integer>> expected = new TreeSet<>(LEXICOGRAPHIC);
            for (int a = 0; a < nums.length; a++) {
                for (int b = a + 1; b < nums.length; b++) {
                    for (int c = b + 1; c < nums.length; c++) {
                        if (nums[a] + nums[b] + nums[c] == 0) {
                            int[] triplet = {nums[a], nums[b], nums[c]};
                            Arrays.sort(triplet);
                            expected.add(Arrays.stream(triplet).boxed().toList());
                        }
                    }
                }
            }
            assertEquals(List.copyOf(expected), solution.threeSum(nums), Arrays.toString(nums));
        }
    }
}
