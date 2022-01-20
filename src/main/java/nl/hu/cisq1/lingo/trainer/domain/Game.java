package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Game {
    @Getter @Setter private Integer score = 0;
    @Getter @Setter private GameStatus gameStatus;
    @Getter @Setter private final List<Round> myRounds = new ArrayList<>();

    public Game(Integer score, GameStatus gameStatus){
        this.score = score;
        this.gameStatus = gameStatus;
    }

    public boolean isPlayerEliminated(){
        return this.gameStatus.equals(GameStatus.Closed);
    }

    public boolean isPlaying(){
        return this.gameStatus.equals(GameStatus.Playing);
    }

    public Progress showProgress(){
        Round round = getCurrentRound();
        Progress progress = new Progress(score,round.getLaatsteHint(),round.getFeedbackHistory());
        return progress;
    }

    public Round getCurrentRound(){
        return myRounds.get(myRounds.size() - 1);
    }


    public Integer provideNexWordLength() {
        if(getCurrentRound().getWordToGuess().length() < 7) {
            Integer nextWordLenght = getCurrentRound().getWordToGuess().length() + 1;
            return nextWordLenght;
        }
        else if(getCurrentRound().getWordToGuess().length() == 7) {
            return 5;
        }

         throw new WordLengthNotSupportedException(getCurrentRound().getWordToGuess().length());

    }

    public void startNewRound(String wordToGuess) throws Exception {
        if(this.gameStatus.equals(GameStatus.Open)){
            this.myRounds.add(new Round(wordToGuess));
            setGameStatus(GameStatus.Playing);
        }
        else
        throw new Exception("cannot start new round if player is eliminated or current round is playing. gamestatus: "+this.gameStatus.name());
    }
}
