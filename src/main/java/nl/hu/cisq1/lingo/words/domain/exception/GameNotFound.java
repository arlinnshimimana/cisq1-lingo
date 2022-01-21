package nl.hu.cisq1.lingo.words.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.Game;

public class GameNotFound extends RuntimeException{
    public GameNotFound(Long id){super("game with id: "+id+" , could not be found");}
}
