package stone.starfleet.models;

/**
 * Created by danielstoneburner on 1/10/16.
 */
@SuppressWarnings("ALL")
public class Score {
    private final int startingMines;
    private int shotsFired;
    private int moves;

    public Score(int startingMines) {
        this.startingMines = startingMines;
    }

    public int getMoves() {
        return moves;
    }

    /**
     * Adds a move and makes certain the number stays valid
     */
    public void incrementMoves() {
        moves++;
        if(moves > 3 * startingMines) {
            moves--;
        }
    }

    public int getShotsFired() {
        return shotsFired;
    }

    /**
     * Adds a shot fired and makes certain the number stays valid
     */
    public void incrementShots() {
        shotsFired++;
        if(shotsFired > 5 * startingMines) {
            shotsFired--;
        }
    }

    public int getStartingMines() {
        return startingMines;
    }

    /**
     * Computes the score based on the current recorded actions and the points
     * @return
     */
    public int calculateScore() {
        return (10 * startingMines) - (5 * shotsFired) - (2 * moves);
    }
}
