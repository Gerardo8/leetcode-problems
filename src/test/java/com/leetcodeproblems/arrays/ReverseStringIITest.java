package com.leetcodeproblems.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("541. Reverse String II")
class ReverseStringIITest {

    private final ReverseStringII solution = new ReverseStringII();

    @ParameterizedTest(name = "reverseStr(\"{0}\", {1}) = \"{2}\"")
    @CsvSource({
            "abcdefg,  2, bacdfeg",
            "abcd,     2, bacd",
            "a,        5, a",
            "abc,      5, cba",
            "abcdefgh, 3, cbadefhg",
            "abcdef,   1, abcdef"
    })
    void reversesFirstKOfEvery2K(String s, int k, String expected) {
        assertEquals(expected, solution.reverseStr(s, k));
    }
}
