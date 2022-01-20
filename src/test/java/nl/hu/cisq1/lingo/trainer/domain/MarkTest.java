package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
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
                Arguments.of(Mark.Correct,"Correct"),
                Arguments.of(Mark.Absent, "Absent"),
                Arguments.of(Mark.Invalid,"Invalid"),
                Arguments.of(Mark.Present, "Present"),
                Arguments.of(Mark.None,null )
        );
    }
}