package item37;

public enum BadPhase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

        private static final Transition[][] TRANSITIONS = {
                {null, MELT, SUBLIME},
                {FREEZE, null, BOIL},
                {DEPOSIT, CONDENSE, null},
        };

        public static Transition from(BadPhase from, BadPhase to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }
}
