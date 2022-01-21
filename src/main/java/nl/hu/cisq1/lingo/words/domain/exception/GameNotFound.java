package nl.hu.cisq1.lingo.words.domain.exception;

public class GameNotFound extends RuntimeException{
    public GameNotFound(Long id){super("game with id: "+id+" , could not be found");}
}
