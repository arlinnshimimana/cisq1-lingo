package nl.hu.cisq1.lingo.trainer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
public class Feedback {
  @Getter @Setter private final String attempt;
  @Getter @Setter private final List<Mark> mark;

   // public Feedback(String attempt, List<Mark> marks){}
    public Feedback(String attempt, List<Mark> mark){
        this.attempt = attempt;
        this.mark = mark;
    }

    public boolean isWordGuessed(){
        for(Mark m: this.mark){
            if(m != Mark.Correct){
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

    public String giveHint(String previusHint) throws Exception {
        //String nextHint = previusHint;
        StringBuilder nextHint = new StringBuilder(previusHint);
        if(!mark.contains(Mark.Invalid)) {
            for (var i = 0; i < attempt.length(); i++) {
                if (Mark.getString(mark.get(i)).equals("Correct")) {
                    nextHint.setCharAt(i, attempt.charAt(i));
                }
            }
        }
        return String.valueOf(nextHint);
    }

}
