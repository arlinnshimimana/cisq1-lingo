package nl.hu.cisq1.lingo.trainer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@EqualsAndHashCode
@Entity
public class Feedback {
    @Id
    @GeneratedValue
    @Getter @Setter private Long id;
    @Getter @Setter private String attempt;

    @ElementCollection
    @Getter @Setter private List<Mark> mark;



    public Feedback(){}
    public Feedback(String attempt, List<Mark> mark){
        this.attempt = attempt;
        this.mark = mark;
    }

    public boolean isWordGuessed(){
        for(Mark m: this.mark){
            if(m != Mark.CORRECT){
                return false;
            }
        }
        return true;
    }

    public boolean guessIsValid() {
        if(5 <= attempt.length() && attempt.length() <= 7){
            return true;
        }
        return false;
    }

    public String giveHint(String previusHint) {
        StringBuilder nextHint = new StringBuilder(previusHint);
        if(!mark.contains(Mark.INVALID)) {
            for (var i = 0; i < attempt.length(); i++) {
                if (Mark.getString(mark.get(i)).equals("Correct")) {
                    nextHint.setCharAt(i, attempt.charAt(i));
                }
            }
        }
        return String.valueOf(nextHint);
    }

}
