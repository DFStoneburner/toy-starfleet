package stone.starfleet.models;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class Torpedo {
    public enum Pattern {
        ALPHA,
        BETA,
        GAMMA,
        DELTA;

        public static boolean isValidPattern(String value) {
            try {
                valueOf(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    private final Pattern pattern;

    public Torpedo(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
