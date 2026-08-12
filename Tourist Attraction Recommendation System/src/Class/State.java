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

    public static String formatStateName(State state) {
        switch (state) {
            case NEGERISEMBILAN:
                return "Negeri Sembilan";
            default:
                String lower = state.name().toLowerCase();
                return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
        }
    }
}