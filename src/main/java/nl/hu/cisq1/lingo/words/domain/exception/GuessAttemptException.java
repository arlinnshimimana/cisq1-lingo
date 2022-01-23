package nl.hu.cisq1.lingo.words.domain.exception;

public class GuessAttemptException extends RuntimeException {
    public GuessAttemptException(){super("guess attempts exceeded");}
}
