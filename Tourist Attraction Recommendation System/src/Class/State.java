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
    SARAWAK,
    KUALALUMPUR,
    PUTRAJAYA;

    private static String normaliseStateInput(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+","".toUpperCase());
    }
    
    public static State findState(String input) {
        String normalisedInput = normaliseStateInput(input);

        for (State s : State.values()) {
            String normalisedStateName = normaliseStateInput(s.name());
            String normalisedFormattedStateName = normaliseStateInput(formatStateName(s));
            if (normalisedStateName.equalsIgnoreCase(normalisedInput) || normalisedFormattedStateName.equalsIgnoreCase(normalisedInput)) {
                return s;
            }
        }
        return null;
    }

    public static String formatStateName(State state) {
        switch (state) {
            case NEGERISEMBILAN:
                return "Negeri Sembilan";
            case KUALALUMPUR:
                return "Kuala Lumpur";
            default:
                String lower = state.name().toLowerCase();
                return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
        }
    }
}