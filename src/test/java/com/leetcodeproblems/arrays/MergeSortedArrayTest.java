package com.leetcodeproblems.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("88. Merge Sorted Array")
class MergeSortedArrayTest {

    private final MergeSortedArray solution = new MergeSortedArray();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 2, 3, 0, 0, 0}, 3, new int[] {2, 5, 6}, 3, new int[] {1, 2, 2, 3, 5, 6}),
                arguments(new int[] {1}, 1, new int[] {}, 0, new int[] {1}),
                arguments(new int[] {0}, 0, new int[] {1}, 1, new int[] {1}),
                arguments(new int[] {4, 5, 6, 0, 0, 0}, 3, new int[] {1, 2, 3}, 3, new int[] {1, 2, 3, 4, 5, 6}),
                arguments(new int[] {-1, 0, 0, 3, 3, 3, 0, 0, 0}, 6, new int[] {1, 2, 2}, 3,
                        new int[] {-1, 0, 0, 1, 2, 2, 3, 3, 3}));
    }

    @ParameterizedTest(name = "[{index}] m={1}, n={3}")
    @MethodSource("examples")
    void mergesIntoFirstArray(int[] nums1, int m, int[] nums2, int n, int[] expected) {
        solution.merge(nums1, m, nums2, n);
        assertArrayEquals(expected, nums1);
    }

    @Test
    void matchesSortingOnRandomInputs() {
        Random random = new Random(88);
        for (int round = 0; round < 500; round++) {
            int m = random.nextInt(10);
            int n = random.nextInt(10);
            int[] first = IntStream.generate(() -> random.nextInt(21) - 10).limit(m).sorted().toArray();
            int[] second = IntStream.generate(() -> random.nextInt(21) - 10).limit(n).sorted().toArray();

            int[] nums1 = Arrays.copyOf(first, m + n);
            solution.merge(nums1, m, second.clone(), n);

            int[] expected = IntStream.concat(Arrays.stream(first), Arrays.stream(second)).sorted().toArray();
            assertArrayEquals(expected, nums1);
        }
    }
}
