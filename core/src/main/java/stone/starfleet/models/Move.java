package stone.starfleet.models;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class Move {
    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public static boolean isValidDirection(String value) {
            try {
                valueOf(value);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }

    public Move(Direction direction) {
        this.direction = direction;
    }

    private final Direction direction;

    public Direction getDirection() {
        return direction;
    }
}
