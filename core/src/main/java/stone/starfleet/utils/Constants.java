package stone.starfleet.utils;

import stone.starfleet.models.Move;
import stone.starfleet.models.Offset;
import stone.starfleet.models.Torpedo;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains data that could later on be configured or modified
 *
 * Created by danielstoneburner on 1/10/16.
 */
public class Constants {
    public static final char EMPTY_CELL = '.';
    public static final char MISSED_MINE = '*';

    public static char getZOffsetRepresentation(int zOffset) {
        if(zOffset <= 0) {
            return MISSED_MINE;
        } else if(zOffset <= 26) {
            return (char) ('a' - 1 + zOffset);
        } else if(zOffset <= 52) {
            return (char) ('A' - 27 + zOffset);
        }
        throw new IllegalArgumentException("Offset must be less than 52");
    }

    public static int getZOffsetValue(char zOffset) {
        if(!Character.isAlphabetic(zOffset)) {
            throw new IllegalArgumentException("Offset must be an alphabetic character");
        }
        return (Character.isLowerCase(zOffset) ? zOffset - 'a' + 1 : zOffset - 'A' + 27);
    }

    public static Offset getMoveOffset(Move.Direction Direction) {
        switch (Direction) {
            case NORTH:
                return new Offset(0, 1);
            case SOUTH:
                return new Offset(0, -1);
            case EAST:
                return new Offset(-1, 0);
            case WEST:
                return new Offset(1, 0);
        }
        throw new IllegalArgumentException("Illegal Direction provided");
    }

    public static Set<Offset> getTorpedoOffsets(Torpedo.Pattern pattern) {
        Set<Offset> offsets = new HashSet<>();
        switch (pattern) {
            case ALPHA:
                offsets.add(new Offset(1, 1));
                offsets.add(new Offset(-1, 1));
                offsets.add(new Offset(1, -1));
                offsets.add(new Offset(-1, -1));
                return offsets;
            case BETA:
                offsets.add(new Offset(1, 0));
                offsets.add(new Offset(-1, 0));
                offsets.add(new Offset(0, 1));
                offsets.add(new Offset(0, -1));
                return offsets;
            case GAMMA:
                offsets.add(new Offset(1, 0));
                offsets.add(new Offset(-1, 0));
                offsets.add(new Offset(0, 0));
                return offsets;
            case DELTA:
                offsets.add(new Offset(0, 1));
                offsets.add(new Offset(0, -1));
                offsets.add(new Offset(0, 0));
                return offsets;
        }
        throw new IllegalArgumentException("Illegal firing pattern provided");
    }
}
