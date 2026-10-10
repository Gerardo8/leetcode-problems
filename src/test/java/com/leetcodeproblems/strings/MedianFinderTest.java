package com.leetcodeproblems.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("295. Find Median from Data Stream")
class MedianFinderTest {

    private static final double DELTA = 1e-9;

    @Test
    void solvesLeetCodeExample() {
        MedianFinder finder = new MedianFinder();
        finder.addNum(1);
        finder.addNum(2);
        assertEquals(1.5, finder.findMedian(), DELTA);
        finder.addNum(3);
        assertEquals(2.0, finder.findMedian(), DELTA);
    }

    @Test
    void handlesNegativeAndExtremeValuesWithoutOverflow() {
        MedianFinder finder = new MedianFinder();
        finder.addNum(Integer.MAX_VALUE);
        finder.addNum(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, finder.findMedian(), DELTA);
        finder.addNum(Integer.MIN_VALUE);
        finder.addNum(Integer.MIN_VALUE);
        assertEquals(-0.5, finder.findMedian(), DELTA);
    }

    @Test
    void rejectsMedianOfEmptyStream() {
        assertThrows(NoSuchElementException.class, () -> new MedianFinder().findMedian());
    }

    @Test
    void matchesSortedListAfterEveryInsertion() {
        Random random = new Random(295);
        MedianFinder finder = new MedianFinder();
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            int value = random.nextInt(2_001) - 1_000;
            finder.addNum(value);
            seen.add(value);
            Collections.sort(seen);
            int size = seen.size();
            double expected = size % 2 == 1
                    ? seen.get(size / 2)
                    : (seen.get(size / 2 - 1) + seen.get(size / 2)) / 2.0;
            assertEquals(expected, finder.findMedian(), DELTA);
        }
    }
}
