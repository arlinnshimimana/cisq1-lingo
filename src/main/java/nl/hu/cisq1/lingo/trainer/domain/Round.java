package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.words.domain.exception.GuessAttemptException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
public class Round {
    @Id
    @GeneratedValue
    @Getter @Setter private Long id;

    @Getter @Setter private String laatsteHint;
    @Getter @Setter private String wordToGuess;
    @Getter @Setter private int attempts = 0;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    @Getter @Setter private final List<Feedback> feedbackHistory = new ArrayList<>();

    public Round(){}

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        makeFirstHint(wordToGuess);
    }

    public void guess(String attempt) throws GuessAttemptException {
        List<Mark> marks = new ArrayList<>();
        Feedback feedback;
        if(attempts < 5) {
            if (attempt.length() == wordToGuess.length()) {
                for (var i = 0; i < wordToGuess.length(); i++) {
                    if (attempt.charAt(i) == wordToGuess.charAt(i)) {
                        marks.add(Mark.CORRECT);
                    } else if (wordToGuess.contains(String.valueOf(attempt.charAt(i)))) {
                        marks.add(Mark.PRESENT);
                    } else marks.add(Mark.ABSENT);
                }
            } else {
                for (var i = 0; i < attempt.length(); i++) {
                    marks.add(Mark.INVALID);
                }
            }
            attempts++;

            feedback = new Feedback(attempt,marks);
            feedbackHistory.add(feedback);
        }
        else

        throw new GuessAttemptException();
    }

    void addFeedback(Feedback feedback){
        this.feedbackHistory.add(feedback);
    }

    public Feedback getLatestFeedback(){
        return feedbackHistory.get(feedbackHistory.size() -1);
    }
    private void makeFirstHint(String wordToGuess){
        StringBuilder hint = new StringBuilder(wordToGuess);
        for(var i = 1; i < wordToGuess.length();i++){
            hint.setCharAt(i, '.');
        }
        this.laatsteHint = String.valueOf(hint);
    }

    public String giveHint(){
        Feedback feedback = feedbackHistory.get(feedbackHistory.size() - 1);
        return feedback.giveHint(laatsteHint);
    }

}
