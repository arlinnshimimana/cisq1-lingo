package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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
        assertThrows(WordLengthNotSupportedException.class,
                ()-> service.startNewGame());
    }

    @Test
    @DisplayName("get game by id")
    void getGameById(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game(0,GameStatus.OPEN);
        Round round = new Round("woord");
        Feedback feedback = new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));
        round.getFeedbackHistory().add(feedback);
        game.getMyRounds().add(round);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);
        assertEquals(0,service.startNewRound((long) -252132).getScore());
    }

    @Test
    @DisplayName("get game by id exception")
    void getGameByIdException(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        TrainerService service = new TrainerService(gameRepository, wordService);
        assertThrows(GameNotFound.class,
                ()-> service.startNewRound((long) -252132));
    }




}