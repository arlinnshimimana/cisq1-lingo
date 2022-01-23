package nl.hu.cisq1.lingo.trainer.domain;

import jdk.jfr.StackTrace;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@ToString
public class Progress {
    @Getter @Setter private long id;
    @Getter @Setter private Integer score;
    @Getter @Setter private String hint;
    @Getter @Setter private List<Feedback> feedbacks;
    @Getter @Setter private GameStatus gameStatus;

    public Progress(Integer score, String hint, List<Feedback> feedbacks, GameStatus gameStatus, long id) {
        this.score = score;
        this.hint = hint;
        this.feedbacks = feedbacks;
        this.gameStatus = gameStatus;
        this.id = id;
    }
    public Progress(Integer score, String hint, List<Feedback> feedbacks, GameStatus gameStatus) {
        this.score = score;
        this.hint = hint;
        this.feedbacks = feedbacks;
        this.gameStatus = gameStatus;
    }
}
