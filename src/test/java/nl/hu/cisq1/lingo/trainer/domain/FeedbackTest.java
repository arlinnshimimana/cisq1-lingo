package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {


    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertTrue(feedback.isWordGuessed());
    }
    @Test
    @DisplayName("word is not guessed if all latters are not correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.ABSENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
        assertFalse(feedback.isWordGuessed());
    }

    @ParameterizedTest
    @DisplayName("word guessed is valid")
    @MethodSource("guessExamples")
    void guessIsValid(Feedback feedback, boolean valid){
        assertEquals(valid,feedback.guessIsValid());
    }

    static Stream<Arguments> guessExamples() {
        return Stream.of(
                Arguments.of(new Feedback("waard", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT)), true),
                Arguments.of(new Feedback("was", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID)), false),
                Arguments.of(new Feedback("bewijzen", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID)), false)

        );
    }


    @ParameterizedTest
    @DisplayName("hint are given based on previous hints and feedback")
    @MethodSource("provideHintExamples")
    void giveHints(String previusHint, String guess,List<Mark> marks, String nextHint) throws Exception {
        Feedback feedback = new Feedback(guess, marks);
        assertEquals(nextHint,feedback.giveHint(previusHint));


    }

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("w....","woorden", List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID),"w...."),
                Arguments.of("w....","waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT),"w..rd"),
                Arguments.of("w..rd","woerd",List.of(Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT),"wo.rd"),
                Arguments.of("wo.rd", "woord",List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),"woord")

        );
    }

}