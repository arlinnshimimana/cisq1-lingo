package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    @DisplayName("Valid guess is made")
    void guessValid(){
        Round round = new Round("waard");
        round.guess("waren");

        assertEquals(1,round.getAttempts());
        assertFalse(round.getFeedbackHistory().isEmpty());
        assertTrue(round.getLatestFeedback().getMark().contains(Mark.PRESENT));
    }
    @Test
    @DisplayName("guess is not valid")
    void guessNotValid(){
        Round round = new Round("waard");
        round.guess("ademen");

        assertEquals(1,round.getAttempts());
        assertFalse(round.getFeedbackHistory().isEmpty());
        assertTrue(round.getLatestFeedback().getMark().contains(Mark.INVALID));
    }

    @ParameterizedTest
    @DisplayName("gives correct hints")
    @MethodSource("giveHintExamples")
    void giveHint(String wordToguess, Feedback feedback, String expectedHint) throws Exception {
        Round round = new Round(wordToguess);
        round.addFeedback(feedback);
        round.giveHint();
        assertEquals(expectedHint, round.giveHint());
    }

    static Stream<Arguments> giveHintExamples() {
        return Stream.of(
                Arguments.of("woord",new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT)),"w..rd"),
                Arguments.of("woord",new Feedback("woerd",List.of(Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT)),"wo.rd"),
                Arguments.of("woord",new Feedback("woord",List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)),"woord")

        );
    }

}
