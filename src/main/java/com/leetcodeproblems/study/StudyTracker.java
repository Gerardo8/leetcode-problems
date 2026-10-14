package com.leetcodeproblems.study;

import com.leetcodeproblems.ProblemCatalog;
import com.leetcodeproblems.catalog.Difficulty;
import com.leetcodeproblems.catalog.ProblemEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/// Study-tracker state for the repository: it keeps a CSV file in sync with the plan and
/// surfaces the next actions a human should work on.
public final class StudyTracker {

    public static final Path PROGRESS_FILE = Path.of("study-progress.csv");

    private final Map<Integer, StudyStatus> progress;

    public StudyTracker() {
        this(defaultProgress());
    }

    public StudyTracker(Map<Integer, StudyStatus> progress) {
        this.progress = new LinkedHashMap<>();
        for (ProblemEntry entry : ProblemCatalog.entries()) {
            StudyStatus status = progress.getOrDefault(entry.number(), StudyStatus.NOT_STARTED);
            this.progress.put(entry.number(), status);
        }
    }

    /// {@return the default status map used when no tracker file exists yet}
    public static Map<Integer, StudyStatus> defaultProgress() {
        return ProblemCatalog.entries().stream()
                .collect(Collectors.toMap(
                        ProblemEntry::number,
                        entry -> StudyStatus.NOT_STARTED,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
    }

    /// Loads the tracker state from `study-progress.csv` if it exists, otherwise uses defaults.
    public static StudyTracker loadFromFile() {
        Map<Integer, StudyStatus> loaded = defaultProgress();
        if (Files.notExists(PROGRESS_FILE)) {
            return new StudyTracker(loaded);
        }

        try (var lines = Files.lines(PROGRESS_FILE, StandardCharsets.UTF_8)) {
            var iterator = lines.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = parseCsvLine(line);
                if (parts.length < 4) {
                    continue;
                }
                int number = Integer.parseInt(parts[0].trim());
                StudyStatus status = StudyStatus.fromCode(parts[2].trim());
                loaded.put(number, status);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read " + PROGRESS_FILE, exception);
        }
        return new StudyTracker(loaded);
    }

    /// Saves the current tracker state to the CSV file in the project root.
    public void saveToFile() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("number,title,status,pattern");
        for (ProblemEntry entry : ProblemCatalog.entries()) {
            StudyStatus status = progress.getOrDefault(entry.number(), StudyStatus.NOT_STARTED);
            lines.add(csvRow(entry.number(), entry.title(), status.code(), entry.pattern()));
        }
        Files.write(PROGRESS_FILE, lines, StandardCharsets.UTF_8);
    }

    private static String csvRow(int number, String title, String status, String pattern) {
        return "%d,%s,%s,%s".formatted(
                number,
                csvEscape(title),
                csvEscape(status),
                csvEscape(pattern));
    }

    private static String[] parseCsvLine(String line) {
        List<String> cells = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                cells.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }
        cells.add(current.toString());
        return cells.toArray(String[]::new);
    }

    private static String csvEscape(String value) {
        String escaped = value == null ? "" : value;
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return '"' + escaped.replace("\"", "\"\"") + '"';
        }
        return escaped;
    }

    /// {@return the progress state of one problem}
    public StudyStatus statusFor(int problemNumber) {
        return progress.getOrDefault(problemNumber, StudyStatus.NOT_STARTED);
    }

    /// Sets the progress state for one problem.
    public void setStatus(int problemNumber, StudyStatus status) {
        progress.put(problemNumber, status);
    }

    /// {@return the number of problems in each study status}
    public Map<StudyStatus, Long> summary() {
        Map<StudyStatus, Long> summary = new EnumMap<>(StudyStatus.class);
        for (StudyStatus status : StudyStatus.values()) {
            summary.put(status, 0L);
        }
        ProblemCatalog.entries().stream()
                .collect(Collectors.groupingBy(
                        entry -> statusFor(entry.number()),
                        Collectors.counting()))
                .forEach(summary::put);
        return summary;
    }

    /// {@return the next problem(s) to practice, ordered by urgency and difficulty}
    public List<ProblemEntry> nextProblems(int limit) {
        if (limit <= 0) {
            return List.of();
        }
        return ProblemCatalog.entries().stream()
                .sorted(Comparator
                        .comparingInt((ProblemEntry entry) -> statusPriority(statusFor(entry.number())))
                        .thenComparingInt(entry -> entry.difficulty().ordinal())
                        .thenComparingInt(ProblemEntry::number))
                .limit(limit)
                .toList();
    }

    private static int statusPriority(StudyStatus status) {
        return switch (status) {
            case NOT_STARTED -> 0;
            case PRACTICING -> 1;
            case REVIEWED -> 2;
            case MASTERED -> 3;
        };
    }
}
