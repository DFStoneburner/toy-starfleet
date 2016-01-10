package stone.starfleet;

import stone.starfleet.models.Cuboid;
import stone.starfleet.models.Step;
import stone.starfleet.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Contains the logic needed to execute on the command line.
 *
 * Created by danielstoneburner on 1/10/16.
 */
public class Run {
    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Field and Script files are both required arguments");
        }
        File field = new File(args[0]);
        if(!field.exists()) {
            throw new IllegalArgumentException("File file must exist");
        }
        File script = new File(args[1]);
        if(!script.exists()) {
            throw new IllegalArgumentException("Script file must exist");
        }

        String unparsedField = FileUtils.parseFile(field);
        Cuboid cuboid = new Cuboid(unparsedField);
        int stepCounter = 0;

        List<Step> steps = FileUtils.parseSteps(script);
        while(!cuboid.isOver() && stepCounter < steps.size()) {
            Step step = steps.get(stepCounter++);
            System.out.printf("Step %d%n%n", stepCounter);
            cuboid.printState(System.out);
            System.out.println();
            System.out.println(step);
            System.out.println();
            cuboid.processStep(step);
            cuboid.printState(System.out);
            System.out.println();
        }
        if(cuboid.isCleared())
            System.out.printf("pass (%d)%n", (stepCounter == steps.size() ? cuboid.getScore() : 1));
        else
            System.out.println("fail (0)");
    }

}
