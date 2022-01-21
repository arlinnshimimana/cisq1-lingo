package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
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
    void startNewGame() throws Exception {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        GameRepository gameRepository = mock(GameRepository.class);

        TrainerService service = new TrainerService(gameRepository, wordService);

        Progress progress = service.startNewGame();
    }

}