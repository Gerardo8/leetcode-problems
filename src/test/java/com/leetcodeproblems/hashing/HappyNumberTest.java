package com.leetcodeproblems.hashing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("202. Happy Number")
class HappyNumberTest {

    private final HappyNumber solution = new HappyNumber();

    @ParameterizedTest(name = "isHappy({0}) = {1}")
    @CsvSource({
            "19,         true",
            "2,          false",
            "1,          true",
            "7,          true",
            "4,          false",
            "2147483647, false"
    })
    void bothStrategiesClassifyExamples(int n, boolean expected) {
        assertEquals(expected, solution.isHappy(n));
        assertEquals(expected, solution.isHappyConstantSpace(n));
    }

    @Test
    void findsTheKnownHappyNumbersUpTo100() {
        Set<Integer> expected = new TreeSet<>(Set.of(
                1, 7, 10, 13, 19, 23, 28, 31, 32, 44, 49, 68, 70, 79, 82, 86, 91, 94, 97, 100));
        Set<Integer> actual = new TreeSet<>();
        IntStream.rangeClosed(1, 100).filter(solution::isHappy).forEach(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void strategiesAgree() {
        for (int n = 1; n <= 20_000; n++) {
            assertEquals(solution.isHappy(n), solution.isHappyConstantSpace(n), "n=" + n);
        }
    }
}
