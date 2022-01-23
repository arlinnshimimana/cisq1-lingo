package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.domain.exception.GuessAttemptException;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TrainerControllerTest {

    private  TrainerService trainerService;

    private TrainerController trainerController;

    @BeforeEach
    public void before(){
        trainerService = mock(TrainerService.class);
        trainerController = new TrainerController(trainerService);
    }

    @Test
    void startNewGame() {
        Progress progress = mock(Progress.class);
        when(trainerService.startNewGame()).thenReturn(progress);

        Progress actual = trainerController.startNewGame();

        assertEquals(progress, actual);
    }

    @Test
    void guess() {
        Progress progress = mock(Progress.class);
        when(trainerService.guess("woord",1l)).thenReturn(progress);

        Progress actual = trainerController.guess(1l, "woord");

        assertEquals(progress, actual);
    }

    @Test
    void guessWhenException() {
        when(trainerService.guess("woord",1l)).thenThrow(GuessAttemptException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> trainerController.guess(1l, "woord"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void startNewRound() {
        Progress progress = mock(Progress.class);
        when(trainerService.startNewRound(1l)).thenReturn(progress);

        Progress actual = trainerController.startNewRound(1l);

        assertEquals(progress, actual);
    }

    @Test
    void startNewRoundWhenGameNotFoundException() {
        when(trainerService.startNewRound(1l)).thenThrow(GameNotFound.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> trainerController.startNewRound(1l));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void startNewRoundWhenStartNewRoundException() {
        when(trainerService.startNewRound(1l)).thenThrow(StartNewRoundException.class);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> trainerController.startNewRound(1l));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}