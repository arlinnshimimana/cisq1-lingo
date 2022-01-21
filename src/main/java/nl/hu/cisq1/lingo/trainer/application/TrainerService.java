package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.exception.GameNotFound;
import org.springframework.stereotype.Service;


@Service
public class TrainerService {
    private GameRepository gameRepository;
    private WordService wordService;
    public TrainerService(GameRepository gameRepository, WordService wordService){
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }
    public Progress startNewGame() throws Exception {
        String wordToGuess = this.wordService.provideRandomWord(5);
        Game game = new Game();
        game.startNewRound(wordToGuess);
        this.gameRepository.save(game);
        return game.showProgress();
    }
    public Progress startNewRound(Long gameId) throws Exception {
        Game game = getGameById(gameId);
        Integer nextLength = game.provideNexWordLength();
        String wordToGuess = this.wordService.provideRandomWord(nextLength);
        game.startNewRound(wordToGuess);
        this.gameRepository.save(game);
        return game.showProgress();
    }
    private Game getGameById(Long gameId) {
        return this.gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFound(gameId));
    }
}
