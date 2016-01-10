package stone.starfleet.models;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class Step {
    public static Step parseStep(String unparsedStep) {
        Step step = new Step();

        for(String piece: unparsedStep.toUpperCase().split(" ")) {
            if(Move.Direction.isValidDirection(piece)) {
                step.setMove(new Move(Move.Direction.valueOf(piece)));
            } else if(Torpedo.Pattern.isValidPattern(piece)) {
                step.setTorpedo(new Torpedo(Torpedo.Pattern.valueOf(piece)));
            }
        }

        return step;
    }

    private Move move;
    private Torpedo torpedo;

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Torpedo getTorpedo() {
        return torpedo;
    }

    public void setTorpedo(Torpedo torpedo) {
        this.torpedo = torpedo;
    }

    @Override
    public String toString() {
        StringBuilder step = new StringBuilder();
        if(torpedo != null)
            step.append(torpedo.getPattern().toString()).append(" ");
        if(move != null)
            step.append(move.getDirection().toString());
        return step.toString().toLowerCase();
    }
}
