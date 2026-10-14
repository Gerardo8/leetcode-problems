package com.leetcodeproblems.study;

import com.leetcodeproblems.ProblemCatalog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Study tracker")
class StudyTrackerTest {

    @Test
    void defaultsProblemsToNotStarted() {
        StudyTracker tracker = new StudyTracker();
        assertEquals(StudyStatus.NOT_STARTED, tracker.statusFor(88));
        assertEquals(StudyStatus.NOT_STARTED, tracker.statusFor(200));
    }

    @Test
    void ordersTheNextPracticeTargetsByUrgency() {
        StudyTracker tracker = new StudyTracker();
        for (var entry : ProblemCatalog.entries()) {
            tracker.setStatus(entry.number(), StudyStatus.MASTERED);
        }
        tracker.setStatus(1480, StudyStatus.NOT_STARTED);
        tracker.setStatus(189, StudyStatus.PRACTICING);
        tracker.setStatus(541, StudyStatus.REVIEWED);

        var targets = tracker.nextProblems(4);

        assertEquals(StudyStatus.NOT_STARTED, tracker.statusFor(targets.getFirst().number()));
        assertEquals(StudyStatus.PRACTICING, tracker.statusFor(targets.get(1).number()));
        assertEquals(StudyStatus.REVIEWED, tracker.statusFor(targets.get(2).number()));
        assertEquals(StudyStatus.MASTERED, tracker.statusFor(targets.get(3).number()));
    }

    @Test
    void exposesQuickReferencePatterns() {
        assertTrue(PatternGuide.quickReference().size() >= 6);
        assertTrue(PatternGuide.quickReference().stream()
                .anyMatch(pattern -> pattern.name().equalsIgnoreCase("Two Pointers")));
        assertTrue(PatternGuide.quickReference().stream()
                .anyMatch(pattern -> pattern.name().equalsIgnoreCase("Dynamic Programming")));
    }
}
