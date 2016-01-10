package stone.starfleet.models;

import stone.starfleet.utils.Constants;
import stone.starfleet.utils.GridUtils;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Core logic and data representation of the starfleet simulation.
 *
 * The cuboid tracks the size by keeping the ship as the center mark
 * and moving everything else around it. This creates a natural model
 * for creating the center-based render.
 *
 * Created by danielstoneburner on 1/10/16.
 */
@SuppressWarnings("CanBeFinal")
public class Cuboid {
    public enum GridState {
        IN_PROGRESS,
        PASSED_MINE,
        CLEARED
    }

    final Set<Mine> mines;
    final Score score;
    GridState state;

    /**
     * Convenience constructor that simplifies setting up a Cuboid
     * @param gridRepresentation
     */
    public Cuboid(String gridRepresentation) {
        this(GridUtils.gridStringToArray(gridRepresentation));
    }

    /**
     * Constructor that populates the set of all mines
     * @param gridRepresentation
     */
    public Cuboid(char[][] gridRepresentation) {
        mines = new HashSet<>();
        for(int i = 0; i < gridRepresentation.length; i++) {
            for(int j = 0; j < gridRepresentation[i].length; j++) {
                if(gridRepresentation[i][j] == Constants.EMPTY_CELL) {
                    continue;
                }
                mines.add(new Mine(
                        j - gridRepresentation.length / 2,
                        i - gridRepresentation[i].length / 2,
                        Constants.getZOffsetValue(gridRepresentation[i][j])
                ));
            }
        }
        score = new Score(mines.size());
        state = GridState.IN_PROGRESS;
    }

    /**
     * Applies a step from the script and then updates if
     * the cuboid has been cleared or a mine has been passed
     * @param step
     */
    public void processStep(Step step) {
        // first fire a torpedo if there is one
        if(step.getTorpedo() != null) {
            processTorpedo(step.getTorpedo());
        }
        // then process a move if there is one
        if(step.getMove() != null) {
            processMove(step.getMove());
        }
        // process the constant 1km drop
        processDrop();
        // check if the grid is in an end state
        checkEnd();
    }

    /**
     * Process the move part of a step in a script
     * @param move
     */
    private void processMove(Move move) {
        score.incrementMoves();
        Offset offset = Constants.getMoveOffset(move.getDirection());
        for(Mine mine: mines) {
            mine.setOffsetX(mine.getOffsetX() + (offset.x));
            mine.setOffsetY(mine.getOffsetY() + (offset.y));
        }
    }

    /**
     * Process the torpedo firing of a step in a script
     * @param torpedo
     */
    private void processTorpedo(Torpedo torpedo) {
        score.incrementShots();
        Set<Offset> offsets = Constants.getTorpedoOffsets(torpedo.getPattern());
        for(Offset offset: offsets) {
            Iterator<Mine> mineIterator = mines.iterator();
            while(mineIterator.hasNext()) {
                Mine mine = mineIterator.next();
                // Since mines are stored by their offset we can tell if they are
                // currently in the way of a torpedo by comparing each one's offset
                // with the offsets of firing pattern
                if(mine.getOffsetX() == offset.x && mine.getOffsetY() == offset.y) {
                    mineIterator.remove();
                }
            }
        }
    }

    /**
     * Process a ship drop which changes the current offset of mines
     */
    private void processDrop() {
        for(Mine mine: mines) {
            mine.setOffsetZ( mine.getOffsetZ() - 1);
        }
    }

    /**
     * Checks if the last mine was destroyed or if a mine is now being passed
     */
    private void checkEnd() {
        if(mines.isEmpty()) {
            state = GridState.CLEARED;
            return;
        }
        for(Mine mine: mines) {
            if(mine.getOffsetZ() == 0) {
                state = GridState.PASSED_MINE;
                return;
            }
        }
    }

    /**
     * Returns the current score of the script
     * @return
     */
    public int getScore() {
        if(state == GridState.PASSED_MINE) {
            return 0;
        }
        return score.calculateScore();
    }

    public boolean isOver() {
        return state != GridState.IN_PROGRESS;
    }

    public boolean isCleared() {
        return state == GridState.CLEARED;
    }

    /**
     * Prints the cuboid out to any PrintStream passed in,
     * this enables redirection of the printing for testing
     * or future reuse
     * @param stream
     */
    public void printState(PrintStream stream) {
        char[][] grid = createGridRepresentation();
        for (char[] aGrid : grid) {
            for (char anAGrid : aGrid) {
                stream.print(anAGrid);
            }
            stream.println();
        }
    }

    /**
     * Produces a two-dimensional array representing the cuboid
     * by creating an array and then updating the array with
     * the locations of the remaining mines
     * @return
     */
    private char[][] createGridRepresentation() {
        int minX = 0, minY = 0, maxX = 0, maxY = 0;
        // determine the current bounds of the cuboid relative to the center
        for(Mine mine: mines) {
            if(mine.getOffsetY() < minY)
                minY = mine.getOffsetY();
            if(mine.getOffsetY() > maxY)
                maxY = mine.getOffsetY();
            if(mine.getOffsetX() < minX)
                minX = mine.getOffsetX();
            if(mine.getOffsetX() > maxX)
                maxX = mine.getOffsetX();
        }

        // infer the x and y bounds of the cuboid
        int width = 1 + 2 * Math.max(Math.abs(minX), Math.abs(maxX));
        int height = 1 + 2 * Math.max(Math.abs(minY), Math.abs(maxY));
        // make certain that the bounds are renderable with a center
        if(width % 2 == 0)
            width++;
        if(height % 2 == 0)
            height++;

        // fill the representation of the cuboid with the default empty value
        char[][] gridRepresentation = new char[height][width];
        for(int i = 0; i < gridRepresentation.length; i++) {
            for(int j = 0; j < gridRepresentation[i].length; j++) {
                gridRepresentation[i][j] = Constants.EMPTY_CELL;
            }
        }

        // update the cells with a mine occupying them with their representations
        for(Mine mine: mines) {
            gridRepresentation[mine.getOffsetY() + height / 2][mine.getOffsetX() + width / 2] =
                    Constants.getZOffsetRepresentation(mine.getOffsetZ());
        }
        return gridRepresentation;
    }
}
