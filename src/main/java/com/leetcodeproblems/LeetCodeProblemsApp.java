package com.leetcodeproblems;

import module java.base;

import com.leetcodeproblems.catalog.ProblemEntry;
import com.leetcodeproblems.catalog.StudyDay;

/// Command-line entry point that prints the study-plan index.
///
/// Run it with `java -jar target/leetcode-problems-1.0.0.jar` after `mvn package`.
///
/// This class showcases two Java 25 features:
/// - **Instance main methods** (JEP 512): `main` is a non-static method with no
///   parameters; the launcher instantiates the class and calls it.
/// - **Module import declarations** (JEP 511): `import module java.base;` imports every
///   package exported by `java.base` (`java.util`, `java.util.stream`, …) at once.
///
/// It also uses `IO.println`, the console helper added to `java.lang` in Java 25.
public final class LeetCodeProblemsApp {

    private static final String ROW_FORMAT = "  %-6s %-58s %-7s %s";

    /// Creates the application; required by the launcher to call the instance `main`.
    public LeetCodeProblemsApp() {
        // nothing to initialise
    }

    /// Prints every problem grouped by study day.
    void main() {
        Map<StudyDay, List<ProblemEntry>> plan = ProblemCatalog.byDay();
        IO.println("LeetCode Problems - %d solutions in Java %d".formatted(
                ProblemCatalog.entries().size(), Runtime.version().feature()));

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
    }
}
