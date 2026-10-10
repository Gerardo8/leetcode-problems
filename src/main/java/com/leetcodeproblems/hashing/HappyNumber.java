package com.leetcodeproblems.hashing;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.HashSet;
import java.util.Set;

/// Solution to LeetCode **202. Happy Number**.
///
/// ## Problem
/// Repeatedly replace a positive integer by the sum of the squares of its digits.
/// The number is *happy* if this process reaches `1`; otherwise it loops forever in a
/// cycle that does not contain `1`.
///
/// ## Approach — cycle detection
/// The sequence always ends in a cycle (for any `n` with `d` digits the next value is at
/// most `81 · d`, so values stay bounded). `n` is happy iff that cycle is `{1}`.
///
/// - [#isHappy(int)] stores every value in a `HashSet`; seeing a value twice means we are
///   looping (`O(log n)` extra space).
/// - [#isHappyConstantSpace(int)] uses **Floyd's tortoise and hare**: a slow pointer
///   advances one step and a fast pointer two steps; they must meet inside the cycle, and
///   the number is happy iff they meet at `1`.
///
/// ## Complexity
/// - Time: `O(log n)` per step; the number of steps before cycling is small and bounded.
/// - Space: `O(log n)` with the set, `O(1)` with Floyd's algorithm.
@LeetCodeProblem(
        number = 202,
        title = "Happy Number",
        difficulty = Difficulty.EASY,
        day = StudyDay.DAY_6,
        pattern = "Hash set cycle detection (and Floyd's tortoise and hare)",
        slug = "happy-number")
public final class HappyNumber {

    /// Creates a new, stateless solver.
    public HappyNumber() {
        // stateless
    }

    /// Decides whether `n` is happy, detecting cycles with a hash set.
    ///
    /// @param n a positive integer
    /// @return `true` if the digit-square process reaches `1`
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        int current = n;
        while (current != 1 && seen.add(current)) {
            current = sumOfSquaredDigits(current);
        }
        return current == 1;
    }

    /// Decides whether `n` is happy using Floyd's cycle detection and `O(1)` memory.
    ///
    /// @param n a positive integer
    /// @return `true` if the digit-square process reaches `1`
    public boolean isHappyConstantSpace(int n) {
        int slow = n;
        int fast = sumOfSquaredDigits(n);
        while (fast != 1 && slow != fast) {
            slow = sumOfSquaredDigits(slow);
            fast = sumOfSquaredDigits(sumOfSquaredDigits(fast));
        }
        return fast == 1;
    }

    /// {@return the sum of the squares of the decimal digits of `value`}
    private static int sumOfSquaredDigits(int value) {
        int sum = 0;
        for (int rest = value; rest > 0; rest /= 10) {
            int digit = rest % 10;
            sum += digit * digit;
        }
        return sum;
    }
}
