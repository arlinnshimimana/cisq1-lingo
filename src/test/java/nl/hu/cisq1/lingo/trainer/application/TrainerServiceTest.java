package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerServiceTest {

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("start new game")
    void startNewGame(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        GameRepository gameRepository = mock(GameRepository.class);

        TrainerService service = new TrainerService(gameRepository, wordService);

        Progress progress = service.startNewGame();
        assertEquals(0, progress.getScore());
        assertEquals("w....", progress.getHint());
    }

    @Test
    @DisplayName("start new game exception word too short")
    void startNewgameExceptionShort(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("was");

        GameRepository gameRepository = mock(GameRepository.class);

        TrainerService service = new TrainerService(gameRepository, wordService);
        //assertTrue(progress.getFeedbacks().isEmpty());
        assertThrows(WordLengthNotSupportedException.class,
                ()-> service.startNewGame());
    }

    @Test
    @DisplayName("start new game exception word too long")
    void startNewgameExceptionLong(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("advertentie");

        GameRepository gameRepository = mock(GameRepository.class);

        TrainerService service = new TrainerService(gameRepository, wordService);
        //assertTrue(progress.getFeedbacks().isEmpty());
        assertThrows(WordLengthNotSupportedException.class,
                ()-> service.startNewGame());
    }




}