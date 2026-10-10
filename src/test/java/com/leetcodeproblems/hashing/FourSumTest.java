package com.leetcodeproblems.hashing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("18. 4Sum")
class FourSumTest {

    private final FourSum solution = new FourSum();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 0, -1, 0, -2, 2}, 0,
                        List.of(List.of(-2, -1, 1, 2), List.of(-2, 0, 0, 2), List.of(-1, 0, 0, 1))),
                arguments(new int[] {2, 2, 2, 2, 2}, 8, List.of(List.of(2, 2, 2, 2))),
                arguments(new int[] {1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000}, -294_967_296,
                        List.of()),
                arguments(new int[] {0, 0, 0, 0}, 0, List.of(List.of(0, 0, 0, 0))),
                arguments(new int[] {1, 2, 3}, 6, List.of()));
    }

    @ParameterizedTest(name = "[{index}] target={1}")
    @MethodSource("examples")
    void findsUniqueQuadruplets(int[] nums, int target, List<List<Integer>> expected) {
        int[] copy = nums.clone();
        assertEquals(expected, solution.fourSum(nums, target));
        assertArrayEquals(copy, nums);
    }

    @Test
    void matchesBruteForce() {
        Random random = new Random(18);
        for (int round = 0; round < 200; round++) {
            int[] nums = random.ints(random.nextInt(12), -6, 7).toArray();
            int target = random.nextInt(13) - 6;
            Set<List<Integer>> expected = new TreeSet<>(FourSumTest::compareLists);
            for (int a = 0; a < nums.length; a++) {
                for (int b = a + 1; b < nums.length; b++) {
                    for (int c = b + 1; c < nums.length; c++) {
                        for (int d = c + 1; d < nums.length; d++) {
                            if ((long) nums[a] + nums[b] + nums[c] + nums[d] == target) {
                                int[] quad = {nums[a], nums[b], nums[c], nums[d]};
                                Arrays.sort(quad);
                                expected.add(Arrays.stream(quad).boxed().toList());
                            }
                        }
                    }
                }
            }
            assertEquals(List.copyOf(expected), solution.fourSum(nums, target), Arrays.toString(nums));
        }
    }

    private static int compareLists(List<Integer> left, List<Integer> right) {
        for (int i = 0; i < left.size(); i++) {
            int comparison = Integer.compare(left.get(i), right.get(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }
}
