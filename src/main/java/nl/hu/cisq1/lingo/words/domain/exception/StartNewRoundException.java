package nl.hu.cisq1.lingo.words.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class StartNewRoundException extends RuntimeException{
    public StartNewRoundException(GameStatus gameStatus){
        super("cannot start new round if player is eliminated or current round is playing. gamestatus: "+ gameStatus.name());}
}
