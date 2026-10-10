package com.leetcodeproblems.slidingwindow;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.LeetCodeProblem;
import com.leetcodeproblems.catalog.StudyDay;

import java.util.Arrays;

/// Solution to LeetCode **188. Best Time to Buy and Sell Stock IV**.
///
/// ## Problem
/// `prices[i]` is the price of a stock on day `i`. Complete **at most `k` transactions**
/// (a buy followed by a later sell; you may hold at most one share at a time) and return
/// the maximum profit.
///
/// ## Approach — state-machine dynamic programming over a rolling window of one day
/// Sweep the days once while keeping, for every transaction number `t` (`1..k`), the best
/// cash balance in two states:
/// - `buy[t]`  — after the `t`-th buy (currently holding a share);
/// - `sell[t]` — after the `t`-th sell (not holding).
///
/// Transitions for a price `p`:
/// - `buy[t]  = max(buy[t],  sell[t − 1] − p)` — keep holding, or buy today;
/// - `sell[t] = max(sell[t], buy[t] + p)` — keep waiting, or sell today.
///
/// Only the previous day's state is needed, so the window over the timeline has size one
/// and memory is `O(k)`. When `k >= n / 2` the limit can never bind, so the problem
/// degenerates to "unlimited transactions": sum every positive day-to-day increase.
///
/// ## Complexity
/// - Time: `O(n · k)`, or `O(n)` when `k >= n / 2`.
/// - Space: `O(k)`.
@LeetCodeProblem(
        number = 188,
        title = "Best Time to Buy and Sell Stock IV",
        difficulty = Difficulty.HARD,
        day = StudyDay.DAY_4,
        pattern = "State-machine dynamic programming",
        slug = "best-time-to-buy-and-sell-stock-iv")
public final class BestTimeToBuyAndSellStockIV {

    /// Creates a new, stateless solver.
    public BestTimeToBuyAndSellStockIV() {
        // stateless
    }

    /// Computes the maximum profit achievable with at most `k` transactions.
    ///
    /// @param k      maximum number of transactions, `k >= 0`
    /// @param prices daily prices
    /// @return the maximum profit (never negative)
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n < 2 || k == 0) {
            return 0;
        }
        if (k >= n / 2) {
            return unlimitedTransactionsProfit(prices);
        }

        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];
        Arrays.fill(buy, -prices[0]);

        for (int price : prices) {
            for (int t = 1; t <= k; t++) {
                buy[t] = Math.max(buy[t], sell[t - 1] - price);
                sell[t] = Math.max(sell[t], buy[t] + price);
            }
        }
        return sell[k];
    }

    /// Profit with no limit on transactions: collect every positive price increase.
    private static int unlimitedTransactionsProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            profit += Math.max(0, prices[i] - prices[i - 1]);
        }
        return profit;
    }
}
