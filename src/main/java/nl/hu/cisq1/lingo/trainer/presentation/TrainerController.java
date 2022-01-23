package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.domain.exception.GuessAttemptException;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;
    public TrainerController(TrainerService service) {
        this.service = service;
    }
    @PostMapping("/games")
    public Progress startNewGame() {
        return this.service.startNewGame();
    }

    @PostMapping("/games/{id}/guess/{attempt}")
    public Progress guess(@PathVariable long id,@PathVariable String attempt){
        try {
            return this.service.guess(attempt,id);
        }catch (GuessAttemptException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
    @PostMapping("/games/{id}/round")
    public Progress startNewRound(@PathVariable Long id) {
        try {
            return this.service.startNewRound(id);
        } catch (GameNotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (StartNewRoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
