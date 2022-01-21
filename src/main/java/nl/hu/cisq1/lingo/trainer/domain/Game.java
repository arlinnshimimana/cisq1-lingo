package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.words.domain.exception.StartNewRoundException;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
public class Game {
    @Id
    @GeneratedValue
    @Getter @Setter private Long id;

    @Getter @Setter private Integer score = 0;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private GameStatus gameStatus = GameStatus.OPEN;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    @Getter @Setter private final List<Round> myRounds = new ArrayList<>();

    public Game(){}

    public Game(Integer score, GameStatus gameStatus){
        this.score = score;
        this.gameStatus = gameStatus;
    }

    public boolean isPlayerEliminated(){
        return this.gameStatus.equals(GameStatus.CLOSED);
    }

    public boolean isPlaying(){
        return this.gameStatus.equals(GameStatus.PLAYING);
    }

    public Progress showProgress(){
        return new Progress(this.score,getCurrentRound().getLaatsteHint(),getCurrentRound().getFeedbackHistory());
    }

    public Round getCurrentRound(){
        return myRounds.get(myRounds.size() - 1);
    }


    public Integer provideNexWordLength() {
        if(getCurrentRound().getWordToGuess().length() < 7) {
            return getCurrentRound().getWordToGuess().length() + 1;
        }
        else if(getCurrentRound().getWordToGuess().length() == 7) {
            return 5;
        }

         throw new WordLengthNotSupportedException(getCurrentRound().getWordToGuess().length());

    }

    public void startNewRound(String wordToGuess){
        if (wordToGuess.length() < 5 || wordToGuess.length() > 7) {
            throw new WordLengthNotSupportedException(wordToGuess.length());
        }
        else if (this.gameStatus.equals(GameStatus.OPEN)) {
            this.myRounds.add(new Round(wordToGuess));
            setGameStatus(GameStatus.PLAYING);
        }
        else {
            throw new StartNewRoundException(gameStatus);
        }
    }
}
