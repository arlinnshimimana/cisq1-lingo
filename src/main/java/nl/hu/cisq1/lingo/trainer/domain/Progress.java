package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Progress {
    @Getter @Setter private Integer score;
    @Getter @Setter private String hint;
    @Getter @Setter private List<Feedback> feedbacks;

    public Progress(Integer score, String hint, List<Feedback> feedbacks) {
        this.score = score;
        this.hint = hint;
        this.feedbacks = feedbacks;
    }
}
