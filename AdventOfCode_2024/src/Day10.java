import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Day10 {

    /**
     * A method to calculate the sum of mountains you can reach from each trailhead of a map from a given textfile
     * @param filePath filepath to a given textfile with a topographic map
     * @param countTrails true if the amount of trails to reach a mountain should be included into the calculation
     * @return returns the sum of mountains you can reach from each trailhead from the given topographic map
     */
    public static long calcSumOfTrailheadScores (String filePath, boolean countTrails) {

        // initialize the sums
        long trailheadSum = 0;
        long hikingTrailsSum = 0;

        // read the file and load the map into a two-dimensional integer array
        String line;
        ArrayList<int[]> arrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                int[] lineArr = new int[line.length()];

                for (int lineIndex = 0; lineIndex < line.length(); lineIndex++) {
                    lineArr[lineIndex] = Character.getNumericValue(line.charAt(lineIndex));
                };
                arrayList.add(lineArr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] topoMap = new int[arrayList.size()][] ;
        for (int listIndex = 0; listIndex < arrayList.size(); listIndex++) {
            topoMap[listIndex] = arrayList.get(listIndex);
        }

        // find the next trailhead in the map
        for (int row = 0; row < topoMap.length; row++) {
            for (int col = 0; col < topoMap[row].length; col++) {

                // calculate the reachable mountains and the distinct trails and add them to the sums
                if (topoMap[row][col] == 0) {
                    TrailHead trailhead = new TrailHead(row, col, topoMap);
                    trailhead.calcScore(row,col);
                    trailheadSum += trailhead.mountains.size();
                    hikingTrailsSum += trailhead.hikingtrails.size();
                }
            }
        }
        if (countTrails) {
            return hikingTrailsSum;
        } else {
            return trailheadSum;
        }

    }
}

// A class to
class TrailHead {

    int[][] topoMap;
    int row;
    int col;

    record MountainCoords (int row, int col) {};
    HashSet<MountainCoords> mountains;
    HashSet<int[]> hikingtrails;

    TrailHead (int row, int col, int[][] topoMap) {
        this.topoMap = topoMap;
        this.mountains = new HashSet<>();
        this.hikingtrails = new HashSet<>();
    }

    // recursive method to backtrack every possible way and save reachable mountains and the amount of different trails
    public void calcScore(int row, int col) {

        // if we arrived at top of the mountain: return while adding the mountain and the trail to the set
        if (this.topoMap[row][col] == 9) {
            this.mountains.add(new MountainCoords(row, col));
            this.hikingtrails.add(new int[] {row, col});
            return;
        } else {

            // try all four directions and catch (continue) if we look outside of the map
            try {
                if (topoMap[row][col + 1] - topoMap[row][col] == 1) {
                    calcScore(row, col + 1);
                }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
                if (topoMap[row + 1][col] - topoMap[row][col] == 1) {
                    calcScore(row + 1, col);
                }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
                if (topoMap[row][col - 1] - topoMap[row][col] == 1) {
                    calcScore(row , col - 1);
                }
            } catch (IndexOutOfBoundsException e) {
            }
            try {
                if (topoMap[row - 1][col] - topoMap[row][col] == 1) {
                    calcScore(row - 1, col);
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }
}
