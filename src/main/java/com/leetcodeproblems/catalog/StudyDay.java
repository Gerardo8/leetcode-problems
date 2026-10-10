package com.leetcodeproblems.catalog;

/// The days of the study plan. Each day focuses on one family of techniques and maps
/// to exactly one Java package below `com.leetcodeproblems`.
public enum StudyDay {

    /// Day 1 — arrays (1D) foundations and implementation drills.
    DAY_1(1, "Arrays (1D) Foundations + Implementation Drills", "arrays"),

    /// Day 2 — strings and string manipulation.
    DAY_2(2, "Strings + String Manipulation", "strings"),

    /// Day 3 — the two-pointers technique.
    DAY_3(3, "Two Pointers", "twopointers"),

    /// Day 4 — sliding windows (and the closely related running-state DP).
    DAY_4(4, "Sliding Window", "slidingwindow"),

    /// Day 5 — matrices and multidimensional arrays.
    DAY_5(5, "Matrix & Multidimensional Arrays", "matrix"),

    /// Day 6 — hash maps and hash sets.
    DAY_6(6, "HashMaps & Sets", "hashing"),

    /// Day 7 — greedy strategies.
    DAY_7(7, "Greedy Strategies", "greedy"),

    /// Day 8 — divide and conquer plus recursion.
    DAY_8(8, "Divide & Conquer + Recursion", "divideandconquer"),

    /// Day 9 — full CodeSignal-style simulation, set A.
    DAY_9_SET_A(9, "Full CodeSignal-Style Simulation - Set A", "simulation.seta"),

    /// Day 9 — full CodeSignal-style simulation, set B.
    DAY_9_SET_B(9, "Full CodeSignal-Style Simulation - Set B", "simulation.setb");

    /// Root package that contains one sub-package per study day.
    public static final String ROOT_PACKAGE = "com.leetcodeproblems";

    private final int number;
    private final String title;
    private final String subPackage;

    StudyDay(int number, String title, String subPackage) {
        this.number = number;
        this.title = title;
        this.subPackage = subPackage;
    }

    /// {@return the day number (1–9); both Day 9 sets share number 9}
    public int number() {
        return number;
    }

    /// {@return the title of the day, as written in the study plan}
    public String title() {
        return title;
    }

    /// {@return the fully qualified package that holds this day's solutions}
    public String packageName() {
        return ROOT_PACKAGE + "." + subPackage;
    }
}
