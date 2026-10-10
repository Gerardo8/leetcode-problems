package com.leetcodeproblems.divideandconquer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("1157. Online Majority Element In Subarray")
class MajorityCheckerTest {

    @Test
    void solvesLeetCodeExample() {
        MajorityChecker checker = new MajorityChecker(new int[] {1, 1, 2, 2, 1, 1});
        assertEquals(1, checker.query(0, 5, 4));
        assertEquals(-1, checker.query(0, 3, 3));
        assertEquals(2, checker.query(2, 3, 2));
    }

    @Test
    void handlesSingleElementArray() {
        MajorityChecker checker = new MajorityChecker(new int[] {7});
        assertEquals(7, checker.query(0, 0, 1));
    }

    @Test
    void matchesBruteForceCounting() {
        Random random = new Random(1157);
        for (int round = 0; round < 50; round++) {
            int[] arr = random.ints(1 + random.nextInt(60), 1, 4).toArray();
            MajorityChecker checker = new MajorityChecker(arr);
            for (int query = 0; query < 200; query++) {
                int left = random.nextInt(arr.length);
                int right = left + random.nextInt(arr.length - left);
                int length = right - left + 1;
                int threshold = length / 2 + 1 + random.nextInt(length - length / 2);

                assertEquals(bruteForce(arr, left, right, threshold), checker.query(left, right, threshold));
            }
        }
    }

    private static int bruteForce(int[] arr, int left, int right, int threshold) {
        for (int i = left; i <= right; i++) {
            int count = 0;
            for (int j = left; j <= right; j++) {
                if (arr[j] == arr[i]) {
                    count++;
                }
            }
            if (count >= threshold) {
                return arr[i];
            }
        }
        return -1;
    }
}
