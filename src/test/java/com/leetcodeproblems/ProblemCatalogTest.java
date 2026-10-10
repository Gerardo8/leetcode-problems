package com.leetcodeproblems;

import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.ProblemEntry;
import com.leetcodeproblems.catalog.StudyDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Study-plan catalogue")
class ProblemCatalogTest {

    private static final int PROBLEMS_IN_PLAN = 39;

    @Test
    void listsEveryProblemOfThePlanExactlyOnce() {
        List<ProblemEntry> entries = ProblemCatalog.entries();
        assertEquals(PROBLEMS_IN_PLAN, entries.size());

        Set<Integer> numbers = new HashSet<>();
        Set<Class<?>> classes = new HashSet<>();
        for (ProblemEntry entry : entries) {
            assertTrue(numbers.add(entry.number()), "duplicate problem #" + entry.number());
            assertTrue(classes.add(entry.solutionType()), "duplicate class " + entry.solutionType());
        }
    }

    @Test
    void groupsFourProblemsPerDayExceptTheSimulationSets() {
        Map<StudyDay, List<ProblemEntry>> byDay = ProblemCatalog.byDay();
        assertEquals(StudyDay.values().length, byDay.size());
        byDay.forEach((day, problems) -> assertEquals(day == StudyDay.DAY_9_SET_B ? 3 : 4, problems.size(),
                day.name()));
    }

    @Test
    void solutionsLiveInThePackageOfTheirStudyDay() {
        for (ProblemEntry entry : ProblemCatalog.entries()) {
            assertEquals(entry.day().packageName(), entry.solutionType().getPackageName(),
                    entry.solutionType().getSimpleName());
        }
    }

    @Test
    void solutionClassesAreFinalAndPublic() {
        for (ProblemEntry entry : ProblemCatalog.entries()) {
            int modifiers = entry.solutionType().getModifiers();
            assertTrue(Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers),
                    entry.solutionType().getSimpleName());
        }
    }

    @Test
    void entriesExposeConsistentMetadata() {
        ProblemEntry first = ProblemCatalog.entries().getFirst();
        assertEquals(88, first.number());
        assertEquals("Merge Sorted Array", first.title());
        assertEquals(Difficulty.EASY, first.difficulty());
        assertEquals("https://leetcode.com/problems/merge-sorted-array/", first.url());

        for (ProblemEntry entry : ProblemCatalog.entries()) {
            assertTrue(entry.url().startsWith(ProblemEntry.LEETCODE_PROBLEMS_URL) && entry.url().endsWith("/"));
            assertTrue(!entry.title().isBlank() && !entry.pattern().isBlank());
        }
        assertEquals(StudyDay.DAY_9_SET_B, ProblemCatalog.entries().getLast().day());
    }

    @Test
    void rejectsClassesWithoutMetadata() {
        assertThrows(IllegalArgumentException.class, () -> new ProblemEntry(String.class));
    }
}
