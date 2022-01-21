package nl.hu.cisq1.lingo.trainer.domain;

public enum Mark {
    CORRECT,
    ABSENT,
    PRESENT,
    INVALID, NONE;

    public static String getString(Mark feedback) {
        switch (feedback){
            case CORRECT:
                return "Correct";
            case ABSENT:
                return "Absent";
            case PRESENT:
                return "Present";
            case INVALID:
                return "Invalid";
            default:
                return null;
        }
    }

}
