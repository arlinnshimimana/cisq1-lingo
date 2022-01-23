package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService trainerService;


    //aangepast voorbeeld van school slides
    @Test
    @DisplayName("starting a new game starts a new round")
    void startNewGame() {
        Progress progress = trainerService.startNewGame();

        assertEquals(GameStatus.PLAYING, progress.getGameStatus());
        assertEquals(0, progress.getScore());
        assertEquals(5, progress.getHint().length());
        assertEquals(0, progress.getFeedbacks().size());
    }

    //aangepast voorbeeld van school slide
    @Test
    @DisplayName("cannot start a new round when still playing")
    void cannotStartNewRound() {
        Progress currentProgress = trainerService.startNewGame();
        Long id = currentProgress.getId();
        assertThrows(
                StartNewRoundException.class,
                () -> trainerService.startNewRound(id)
        );
    }


}
