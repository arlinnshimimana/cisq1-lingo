package nl.hu.cisq1.lingo.trainer.domain;

public enum Mark {
    Correct,
    Absent,
    Present,
    Invalid, None;

    public static String getString(Mark feedback) {
        switch (feedback){
            case Correct:
                return "Correct";
            case Absent:
                return "Absent";
            case Present:
                return "Present";
            case Invalid:
                return "Invalid";
        }
        return null;
    }

}
