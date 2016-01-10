package stone.starfleet.utils;

import stone.starfleet.models.Move;

/**
 * Created by danielstoneburner on 1/10/16.
 */
public class GridUtils {
    public static char[][] gridStringToArray(String gridString) {
        String[] rows = gridString.split("\n");

        char[][] grid = new char[rows[0].length()][rows.length];
        for(int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for(int j = 0; j < row.length(); j++) {
                grid[i][j] = row.charAt(j);
            }
        }

        return grid;
    }
}
