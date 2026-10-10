package com.leetcodeproblems.slidingwindow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("188. Best Time to Buy and Sell Stock IV")
class BestTimeToBuyAndSellStockIVTest {

    private final BestTimeToBuyAndSellStockIV solution = new BestTimeToBuyAndSellStockIV();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(2, new int[] {2, 4, 1}, 2),
                arguments(2, new int[] {3, 2, 6, 5, 0, 3}, 7),
                arguments(0, new int[] {1, 5}, 0),
                arguments(1, new int[] {7, 6, 4, 3, 1}, 0),
                arguments(100, new int[] {1, 2, 3, 0, 2, 5}, 7),
                arguments(1, new int[] {5}, 0));
    }

    @ParameterizedTest(name = "[{index}] k={0}, profit={2}")
    @MethodSource("examples")
    void computesMaximumProfit(int k, int[] prices, int expected) {
        assertEquals(expected, solution.maxProfit(k, prices));
    }

    @Test
    void matchesExhaustiveSearch() {
        Random random = new Random(188);
        for (int round = 0; round < 300; round++) {
            int[] prices = random.ints(random.nextInt(12), 0, 20).toArray();
            int k = random.nextInt(5);
            assertEquals(bruteForce(prices, k), solution.maxProfit(k, prices),
                    Arrays.toString(prices) + " k=" + k);
        }
    }

    /// Explores every decision (wait, buy, sell) day by day.
    private static int bruteForce(int[] prices, int k) {
        return explore(prices, 0, k, false);
    }

    private static int explore(int[] prices, int day, int remaining, boolean holding) {
        if (day == prices.length) {
            return 0;
        }
        int best = explore(prices, day + 1, remaining, holding);
        if (holding) {
            best = Math.max(best, prices[day] + explore(prices, day + 1, remaining - 1, false));
        } else if (remaining > 0) {
            best = Math.max(best, -prices[day] + explore(prices, day + 1, remaining, true));
        }
        return best;
    }
}
