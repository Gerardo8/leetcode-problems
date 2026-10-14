package com.leetcodeproblems;

import module java.base;

import com.leetcodeproblems.catalog.ProblemEntry;
import com.leetcodeproblems.catalog.StudyDay;
import com.leetcodeproblems.study.PatternGuide;
import com.leetcodeproblems.study.StudyStatus;
import com.leetcodeproblems.study.StudyTracker;

/// Command-line entry point that prints the study-plan index and the learning dashboard.
///
/// Run it with `java -jar target/leetcode-problems-1.0.0.jar` after `mvn package`.
public final class LeetCodeProblemsApp {

    private static final String ROW_FORMAT = "  %-6s %-58s %-7s %s";

    public LeetCodeProblemsApp() {
        // nothing to initialise
    }

    /// Prints the plan, the tracker summary and a quick interview reference.
    void main() {
        StudyTracker tracker = StudyTracker.loadFromFile();
        Map<StudyDay, List<ProblemEntry>> plan = ProblemCatalog.byDay();

        IO.println("LeetCode Problems - %d solutions in Java %d".formatted(
                ProblemCatalog.entries().size(), Runtime.version().feature()));

        IO.println("");
        IO.println("Study dashboard");
        var summary = tracker.summary();
        IO.println("  Not started: %d | Practicing: %d | Reviewed: %d | Mastered: %d".formatted(
                summary.getOrDefault(StudyStatus.NOT_STARTED, 0L),
                summary.getOrDefault(StudyStatus.PRACTICING, 0L),
                summary.getOrDefault(StudyStatus.REVIEWED, 0L),
                summary.getOrDefault(StudyStatus.MASTERED, 0L)));
        IO.println("  Next problems to practice:");
        for (ProblemEntry problem : tracker.nextProblems(5)) {
            IO.println("    #%d %-52s [%s]".formatted(
                    problem.number(), problem.title(), tracker.statusFor(problem.number()).label()));
        }

        plan.forEach((day, problems) -> {
            IO.println("");
            IO.println("DAY %d: %s".formatted(day.number(), day.title().toUpperCase(Locale.ROOT)));
            for (ProblemEntry problem : problems) {
                IO.println(ROW_FORMAT.formatted(
                        "#" + problem.number(),
                        problem.title(),
                        problem.difficulty().label(),
                        problem.solutionType().getSimpleName()));
            }
        });

        Map<String, Long> byDifficulty = ProblemCatalog.entries().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.difficulty().label(), TreeMap::new, Collectors.counting()));
        IO.println("");
        IO.println("By difficulty: " + byDifficulty);

        IO.println("");
        IO.println("Pattern quick-reference");
        for (PatternGuide guide : PatternGuide.quickReference()) {
            IO.println("  %-18s - %s".formatted(guide.name(), guide.whenToUse()));
            IO.println("    Core idea: " + guide.coreIdea());
            IO.println("    Watch out: " + guide.commonMistakes().getFirst());
        }

        IO.println("");
        IO.println("Interview workflow");
        IO.println("  1. Read the statement and define the invariant.");
        IO.println("  2. Write a brute-force solution and identify the bottleneck.");
        IO.println("  3. Optimise with the right pattern and explain the trade-offs.");
        IO.println("  4. Review edge cases, correctness and complexity before moving on.");
        IO.println("  5. Update study-progress.csv after each practice round.");
    }
}
