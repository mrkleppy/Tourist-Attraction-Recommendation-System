package Class;

public enum State {
    PERLIS,
    KEDAH,
    PENANG,
    PERAK,
    SELANGOR,
    NEGERISEMBILAN,
    MELAKA,
    KELANTAN,
    TERENGGANU,
    PAHANG,
    JOHOR,
    SABAH,
    SARAWAK;
    
    public String calculateTotalCity() {
        return this.toString();
    }
    
    public static State findState(String input) {
        for (State s : State.values()) {
            if (s.name().equalsIgnoreCase(input) || s.toString().equalsIgnoreCase(input)) {
                return s;
            }
        }
        return null;
    }
}