package com.leetcodeproblems.greedy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("134. Gas Station")
class GasStationTest {

    private final GasStation solution = new GasStation();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new int[] {1, 2, 3, 4, 5}, new int[] {3, 4, 5, 1, 2}, 3),
                arguments(new int[] {2, 3, 4}, new int[] {3, 4, 3}, -1),
                arguments(new int[] {5}, new int[] {4}, 0),
                arguments(new int[] {3, 1, 1}, new int[] {1, 2, 2}, 0),
                arguments(new int[] {5, 1, 2, 3, 4}, new int[] {4, 4, 1, 5, 1}, 4));
    }

    @ParameterizedTest(name = "[{index}] start={2}")
    @MethodSource("examples")
    void findsStartingStation(int[] gas, int[] cost, int expected) {
        assertEquals(expected, solution.canCompleteCircuit(gas, cost));
    }

    @Test
    void agreesWithSimulation() {
        Random random = new Random(134);
        for (int round = 0; round < 500; round++) {
            int n = 1 + random.nextInt(8);
            int[] gas = random.ints(n, 0, 6).toArray();
            int[] cost = random.ints(n, 0, 6).toArray();

            int answer = solution.canCompleteCircuit(gas, cost);
            boolean anyValid = false;
            for (int start = 0; start < n; start++) {
                anyValid |= completesLap(gas, cost, start);
            }
            if (anyValid) {
                assertTrue(answer >= 0 && completesLap(gas, cost, answer));
            } else {
                assertEquals(-1, answer);
            }
        }
    }

    private static boolean completesLap(int[] gas, int[] cost, int start) {
        int tank = 0;
        for (int step = 0; step < gas.length; step++) {
            int station = (start + step) % gas.length;
            tank += gas[station] - cost[station];
            if (tank < 0) {
                return false;
            }
        }
        return true;
    }
}
