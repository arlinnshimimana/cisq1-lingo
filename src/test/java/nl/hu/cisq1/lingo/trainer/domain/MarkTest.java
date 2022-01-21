package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MarkTest {

    @ParameterizedTest
    @DisplayName("get String value of Marks")
    @MethodSource("MarksExamples")
    void getString(Mark markEnum, String stringMark){
       assertEquals(stringMark,Mark.getString(markEnum));
    }

    static Stream<Arguments> MarksExamples() {
        return Stream.of(
                Arguments.of(Mark.CORRECT,"Correct"),
                Arguments.of(Mark.ABSENT, "Absent"),
                Arguments.of(Mark.INVALID,"Invalid"),
                Arguments.of(Mark.PRESENT, "Present"),
                Arguments.of(Mark.NONE,null )
        );
    }
}