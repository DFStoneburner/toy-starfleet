package stone.starfleet.utils;

import stone.starfleet.models.Step;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class FileUtils {
    public static String parseFile(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()) {
                builder.append(sc.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static List<Step> parseSteps(File file) {
        List<Step> steps = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                steps.add(Step.parseStep(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return steps;
    }
}
