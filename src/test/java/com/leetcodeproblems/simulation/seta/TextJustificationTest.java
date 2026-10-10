package com.leetcodeproblems.simulation.seta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("68. Text Justification")
class TextJustificationTest {

    private final TextJustification solution = new TextJustification();

    static Stream<Arguments> examples() {
        return Stream.of(
                arguments(new String[] {"This", "is", "an", "example", "of", "text", "justification."}, 16,
                        List.of(
                                "This    is    an",
                                "example  of text",
                                "justification.  ")),
                arguments(new String[] {"What", "must", "be", "acknowledgment", "shall", "be"}, 16,
                        List.of(
                                "What   must   be",
                                "acknowledgment  ",
                                "shall be        ")),
                arguments(new String[] {"Science", "is", "what", "we", "understand", "well", "enough", "to",
                                "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"}, 20,
                        List.of(
                                "Science  is  what we",
                                "understand      well",
                                "enough to explain to",
                                "a  computer.  Art is",
                                "everything  else  we",
                                "do                  ")),
                arguments(new String[] {"a"}, 1, List.of("a")),
                arguments(new String[] {"a", "b", "c", "d", "e"}, 3, List.of("a b", "c d", "e  ")));
    }

    @ParameterizedTest(name = "[{index}] width={1}")
    @MethodSource("examples")
    void justifiesText(String[] words, int maxWidth, List<String> expected) {
        assertEquals(expected, solution.fullJustify(words, maxWidth));
    }

    @Test
    void everyLineHasExactlyMaxWidthCharacters() {
        String[] words = "the quick brown fox jumps over the lazy dog and keeps running far away".split(" ");
        for (int width = 7; width <= 30; width++) {
            for (String line : solution.fullJustify(words, width)) {
                assertEquals(width, line.length(), "width=" + width + " line='" + line + "'");
            }
        }
    }
}
