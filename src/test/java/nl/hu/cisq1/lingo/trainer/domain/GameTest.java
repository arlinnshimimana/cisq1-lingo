package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    @BeforeEach
    void setUp(){
        this.game = new Game();
        game.setId((long) 3);
        Round round = new Round("woord");
        Feedback feedback = new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));
        round.getFeedbackHistory().add(feedback);
        game.getMyRounds().add(round);
        Round secondround = new Round("waarde");
        Feedback secondfeedback = new Feedback("woedde", List.of(Mark.CORRECT,Mark.ABSENT,Mark.PRESENT,Mark.PRESENT,Mark.CORRECT,Mark.CORRECT));
        secondround.getFeedbackHistory().add(secondfeedback);
        game.getMyRounds().add(secondround);
    }
    @Test
    @DisplayName("player is eliminated")
    void playerIsEliminated(){
        game.setGameStatus(GameStatus.CLOSED);
        assertTrue(game.isPlayerEliminated());
    }

    @Test
    @DisplayName("player is not eliminated")
    void playerIsNotEliminated(){
        assertFalse(game.isPlayerEliminated());
    }

    @Test
    @DisplayName("player is playing")
    void playerIsPlaying(){
        game.setGameStatus(GameStatus.PLAYING);
        assertTrue(game.isPlaying());
    }

    @Test
    @DisplayName("player is not playing")
    void playerIsNotPlaying(){
        assertFalse(game.isPlaying());
    }

    @Test
    @DisplayName("progress is showing")
    void progresIsShowing(){
        Progress progress = game.showProgress();
        assertEquals(game.getScore(),progress.getScore());
        assertEquals(game.getCurrentRound().getFeedbackHistory(),progress.getFeedbacks());
    }

    @Test
    @DisplayName("get next word length")
    void getNextWordLengthe(){
        Integer expectedNextWordLengthe = game.getCurrentRound().getWordToGuess().length() + 1;
        assertEquals(expectedNextWordLengthe,game.provideNexWordLength());
    }
    @Test
    @DisplayName("get next word length at end of cycle")
    void getNextWordLengthEndOfCycle(){
        game.getCurrentRound().setWordToGuess("woorden");
        Integer expectedNextWordLengthe = game.getCurrentRound().getWordToGuess().length() + -2;
        assertEquals(expectedNextWordLengthe,game.provideNexWordLength());
    }

    @Test
    @DisplayName("get next word length exception")
    void getNextWordLengtheException(){
        game.getCurrentRound().setWordToGuess("advertenties");
        Integer expectedNextWordLengthe = game.getCurrentRound().getWordToGuess().length() + 1;
        assertThrows(
                WordLengthNotSupportedException.class,
                () -> game.provideNexWordLength());
    }

    @Test
    @DisplayName("start new round")
    void startNewRound() throws Exception {
        System.out.println(game.getGameStatus());
        game.startNewRound("waard");
        assertEquals(GameStatus.PLAYING,game.getGameStatus());
    }

    @Test
    @DisplayName("start new round exception")
    void startNewRoundException() throws Exception {
        game.setGameStatus(GameStatus.PLAYING);
        assertThrows(Exception.class,
                () ->game.startNewRound("waard"));
    }




}