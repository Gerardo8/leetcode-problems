package com.leetcodeproblems.study;

import java.util.Locale;

/// A lightweight learning state that turns the repository into a working interview-prep tracker.
public enum StudyStatus {

    /// The problem is in the study plan but has not been attempted yet.
    NOT_STARTED("not_started", "Not started"),

    /// The problem has been solved once, but the explanation is not yet stable.
    PRACTICING("practicing", "Practicing"),

    /// The solution has been reviewed and the explanation is understood well enough to teach.
    REVIEWED("reviewed", "Reviewed"),

    /// The technique is internalised and can be recalled quickly under interview pressure.
    MASTERED("mastered", "Mastered");

    private final String code;
    private final String label;

    StudyStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    /// {@return the machine-readable value used in the CSV tracker}
    public String code() {
        return code;
    }

    /// {@return a readable label for the CLI dashboard}
    public String label() {
        return label;
    }

    /// Parses the status written in the CSV tracker.
    public static StudyStatus fromCode(String raw) {
        String code = raw == null ? "" : raw.trim();
        String normalized = code.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", "");

        for (StudyStatus status : values()) {
            String candidate = status.code.toLowerCase(Locale.ROOT)
                    .replaceAll("[^a-z0-9]+", "_")
                    .replaceAll("^_+|_+$", "");
            String nameCandidate = status.name().toLowerCase(Locale.ROOT)
                    .replaceAll("[^a-z0-9]+", "_")
                    .replaceAll("^_+|_+$", "");
            if (normalized.equals(candidate) || normalized.equals(nameCandidate)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown study status: " + raw);
    }
}
