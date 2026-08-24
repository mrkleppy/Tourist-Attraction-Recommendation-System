package Class;

public enum State {

    // Attributes
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

    // Normalise the state input to remove whitespace and convert to uppercase
    private static String normaliseStateInput(String input) {
        return input == null ? "" : input.trim().replaceAll("\\s+","".toUpperCase());
    }
    
    // Find the corresponding State enum value based on the input string, ignoring case and whitespace
    public static State findState(String input) {
        String normalisedInput = normaliseStateInput(input);

        // Check against both the enum name and the formatted state name
        for (State s : State.values()) {
            String normalisedStateName = normaliseStateInput(s.name()); // normalise the enum state name
            String normalisedFormattedStateName = normaliseStateInput(formatStateName(s)); // normalise the formatted state name
            if (normalisedStateName.equalsIgnoreCase(normalisedInput) || normalisedFormattedStateName.equalsIgnoreCase(normalisedInput)) {
                return s; // return the matching State enum value
            }
        }

        // If no match is found, return null
        return null;
    }

    // Format the state name for display purposes, handling special cases like "Negeri Sembilan" and "Kuala Lumpur"
    public static String formatStateName(State state) {
        switch (state) {
            case NEGERISEMBILAN:
                return "Negeri Sembilan";
            case KUALALUMPUR:
                return "Kuala Lumpur";
            default:
                String lower = state.name().toLowerCase(); // Convert the enum name to lowercase
                return Character.toUpperCase(lower.charAt(0)) + lower.substring(1); // Capitalize the first letter and return the formatted name
        }
    }
}