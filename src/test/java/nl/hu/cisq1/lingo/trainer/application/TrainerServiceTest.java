package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.domain.exception.GuessAttemptException;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("start new game")
    void startNewGame(){
//        WordService wordService = mock(WordService.class);
//        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");
//

//        //Progress progress
//        //when(game.showProgress())
//        GameRepository gameRepository = mock(GameRepository.class);
//
//
//        TrainerService service = new TrainerService(gameRepository, wordService);
//
//        Progress progress = service.startNewGame();

        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        game.setId(1L);
        Round round = new Round(wordService.provideRandomWord(5));
//        Feedback feedback = new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));
//        round.getFeedbackHistory().add(feedback);
        game.getMyRounds().add(round);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.save(any())).thenReturn(game);
        TrainerService service = new TrainerService(gameRepository, wordService);

        Progress progress = service.startNewGame();

        verify(gameRepository).save(any(Game.class));
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

        Game game = new Game();
        game.setId((long) 1);
        Round round = new Round(wordService.provideRandomWord(5));
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

    @Test
    @DisplayName("start new round exception: gamestatus is playing")
    void startNewRoundException(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        Round round = new Round(wordService.provideRandomWord(5));
        Feedback feedback = new Feedback("waard", List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.CORRECT));
        round.getFeedbackHistory().add(feedback);
        game.getMyRounds().add(round);
        game.setGameStatus(GameStatus.PLAYING);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);
        assertThrows(StartNewRoundException.class,
                ()-> service.startNewRound((long) -252132));
    }

    @Test
    @DisplayName("guess is made")
    void guessIsMade(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        game.setId((long) 1);
        Round round = new Round(wordService.provideRandomWord(5));
        game.getMyRounds().add(round);
        game.setGameStatus(GameStatus.PLAYING);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);
        Progress progress = service.guess("woord",(long) -1);
        assertEquals(GameStatus.OPEN,progress.getGameStatus());
        assertFalse(progress.getHint().contains("."));
    }

    @ParameterizedTest
    @DisplayName("guess is made with wrong gamestatus")
    @MethodSource("gameStatusExamples")
    void guessIsMadeNotPlaying(GameStatus gameStatus){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        game.setId((long) 1);
        Round round = new Round(wordService.provideRandomWord(5));
        game.getMyRounds().add(round);
        game.setGameStatus(gameStatus);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);
        Progress progress = service.guess("woord",(long) -212);
        assertNotEquals(GameStatus.PLAYING, progress.getGameStatus());
    }
    static Stream<Arguments> gameStatusExamples(){
        return Stream.of(
                Arguments.of(GameStatus.OPEN),
                Arguments.of(GameStatus.CLOSED)
        );
    }

    @Test
    @DisplayName("guess is made, lost game")
    void guessIsMadeLost(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        game.setId((long) 1);

        Round round = new Round(wordService.provideRandomWord(5));
        round.setAttempts(4);
        game.getMyRounds().add(round);
        game.setGameStatus(GameStatus.PLAYING);
        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);

        assertEquals(GameStatus.CLOSED, service.guess("waard",(long) -212).getGameStatus());
    }

    @Test
    @DisplayName("guess is made exception, guess attempts exeeeded")
    void guessIsMadeException(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(anyInt())).thenReturn("woord");

        Game game = new Game();
        game.setId((1L));
        Round round = new Round(wordService.provideRandomWord(5));
        round.setAttempts(5);
        game.getMyRounds().add(round);
        game.setGameStatus(GameStatus.PLAYING);

        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        TrainerService service = new TrainerService(gameRepository, wordService);
        assertThrows(GuessAttemptException.class,
                ()-> service.guess("waard",1L));
    }












}