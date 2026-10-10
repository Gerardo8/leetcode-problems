package com.leetcodeproblems.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("2514. Count Anagrams")
class CountAnagramsTest {

    private static final BigInteger MOD = BigInteger.valueOf(1_000_000_007);

    private final CountAnagrams solution = new CountAnagrams();

    @ParameterizedTest(name = "countAnagrams(\"{0}\") = {1}")
    @CsvSource({
            "too hot,  18",
            "aa,        1",
            "abc,       6",
            "aab,       3",
            "ab ab ab,  8",
            "z,         1"
    })
    void solvesExamples(String s, int expected) {
        assertEquals(expected, solution.countAnagrams(s));
    }

    @Test
    void usesModularArithmeticForLongWords() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        BigInteger factorial = BigInteger.ONE;
        for (int i = 2; i <= 26; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        assertEquals(factorial.mod(MOD).intValue(), solution.countAnagrams(alphabet));
    }

    @Test
    void matchesPermutationEnumeration() {
        Random random = new Random(2514);
        for (int round = 0; round < 100; round++) {
            int words = 1 + random.nextInt(3);
            StringBuilder s = new StringBuilder();
            long expected = 1;
            for (int w = 0; w < words; w++) {
                StringBuilder word = new StringBuilder();
                int length = 1 + random.nextInt(5);
                for (int i = 0; i < length; i++) {
                    word.append((char) ('a' + random.nextInt(3)));
                }
                expected *= distinctPermutations(word.toString());
                if (w > 0) {
                    s.append(' ');
                }
                s.append(word);
            }
            assertEquals(expected, solution.countAnagrams(s.toString()), s.toString());
        }
    }

    private static int distinctPermutations(String word) {
        Set<String> seen = new HashSet<>();
        permute(word.toCharArray(), 0, seen);
        return seen.size();
    }

    private static void permute(char[] chars, int index, Set<String> seen) {
        if (index == chars.length) {
            seen.add(new String(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            permute(chars, index + 1, seen);
            swap(chars, index, i);
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }
}
