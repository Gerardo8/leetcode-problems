package com.leetcodeproblems.twopointers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("80. Remove Duplicates from Sorted Array II")
class RemoveDuplicatesFromSortedArrayIITest {

    private final RemoveDuplicatesFromSortedArrayII solution = new RemoveDuplicatesFromSortedArrayII();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 1, 1, 2, 2, 3}, new int[] {1, 1, 2, 2, 3}),
                arguments(new int[] {0, 0, 1, 1, 1, 1, 2, 3, 3}, new int[] {0, 0, 1, 1, 2, 3, 3}),
                arguments(new int[] {1}, new int[] {1}),
                arguments(new int[] {2, 2}, new int[] {2, 2}),
                arguments(new int[] {5, 5, 5, 5, 5}, new int[] {5, 5}));
    }

    @ParameterizedTest(name = "[{index}]")
    @MethodSource("examples")
    void keepsAtMostTwoCopies(int[] nums, int[] expectedPrefix) {
        int length = solution.removeDuplicates(nums);
        assertEquals(expectedPrefix.length, length);
        assertArrayEquals(expectedPrefix, Arrays.copyOf(nums, length));
    }

    @Test
    void matchesStraightforwardFilter() {
        Random random = new Random(80);
        for (int round = 0; round < 300; round++) {
            int[] nums = random.ints(random.nextInt(30), -5, 5).sorted().toArray();
            List<Integer> expected = new ArrayList<>();
            for (int value : nums) {
                int size = expected.size();
                if (size < 2 || expected.get(size - 2) != value) {
                    expected.add(value);
                }
            }
            int length = solution.removeDuplicates(nums);
            assertArrayEquals(expected.stream().mapToInt(Integer::intValue).toArray(), Arrays.copyOf(nums, length));
        }
    }
}
