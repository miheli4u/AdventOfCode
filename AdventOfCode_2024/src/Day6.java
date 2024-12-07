import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day6 {


    /**
     * A method to simulate and predict the guard's patrol route and checks the amount of positions he has before leaving the map
     * @param filePath the filepath to the textfile of the given puzzle-input
     * @return returns the amount of distinct positions the guard visits before leaving the map
     */
    public static int calcDistinctPositions (String filePath) {

        int distinctPositions = 0;

        String line;
        ArrayList<String> rows = new ArrayList<>();
        int lineCounter = 0;

        // create a variable-holder for position and rotation for 'position-history'
        record Coord(int x, int y) {};
        Coord startCoordDirection = new Coord(-1, -1);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // read the given textfile and construct the 'map'
            while ((line = br.readLine()) != null) {
                rows.add(line);

                // detect and initialize the guard's starting position
                if (line.contains("^")) {
                    startCoordDirection = new Coord(line.indexOf('^'), lineCounter);
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialize the guard's position and rotation
        int[] guard = new int[]{startCoordDirection.x, startCoordDirection.y, 0};

        // initialize positionHistory and add the starting position of the guard
        HashSet<Coord> positionHistory = new HashSet<>();
        positionHistory.add(new Coord(guard[0], guard[1]));

        // simulate his patrol route until he walks out of the map (catch it with IndexOutOfBounceException)
        while (true) {
            try {
                switch (guard[2]) {
                    case 0 -> {
                        // if he is looking upwards and faces an obstruction: turn 90° right, else take a step upwards
                        if (rows.get(guard[1] - 1).charAt(guard[0]) == '#') {
                            guard[2] = 1;
                        } else {
                            guard[1]--;
                        }
                    }
                    case 1 -> {
                        // if he is looking right and faces an obstruction: turn 90° right, else take a step right
                        if (rows.get(guard[1]).charAt(guard[0] + 1) == '#') {
                            guard[2] = 2;
                        } else {
                            guard[0]++;
                        }
                    }
                    case 2 -> {
                        // if he is looking downwards and faces an obstruction: turn 90° right, else take a step downwards
                        if (rows.get(guard[1] + 1).charAt(guard[0]) == '#') {
                            guard[2] = 3;
                        } else {
                            guard[1]++;
                        }
                    }
                    case 3 -> {
                        // if he is looking left and faces an obstruction: turn 90° right, else take a step left
                        if (rows.get(guard[1]).charAt(guard[0] - 1) == '#') {
                            guard[2] = 0;
                        } else {
                            guard[0]--;
                        }
                    }
                }

                // add the new position and rotation to the position-history
                positionHistory.add(new Coord(guard[0], guard[1]));
            }
            // when the guard leaves the map, break the loop (IndexOutOfBounceException)
            catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        // position-history only saves every position only once
        return positionHistory.size();
    }

    /**
     * A method to simulate and predict the guard's movement and check if he gets kept in a loop by the new obstruction
     * @param filePath the filepath to the textfile of the given puzzle-input
     * @return returns the amount of possible places for a new obstruction to imprison the guard in a loop
     */
    public static int calcDistinctLoopingPositions (String filePath) {

        int distinctPositions = 0;

        String line;
        ArrayList<String> rows = new ArrayList<>();
        int lineCounter = 0;

        // create a variable-holder for position and rotation for 'position-history'
        record CoordDirections(int x, int y, int rotation) {};
        CoordDirections startCoordDirection = new CoordDirections(-1,-1,-1);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // read the given textfile and construct the 'map'
            while ((line = br.readLine()) != null) {
                rows.add(line);

                // detect and initialize the guard's starting position
                if (line.contains("^")) {
                    startCoordDirection = new CoordDirections(line.indexOf('^'),lineCounter, 0);
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // for every coord on the 'map': simulate if the guard would get stuck in a loop and count the possibilities
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            for (int colIndex = 0; colIndex < rows.get(0).length(); colIndex++) {

                // only simulate this cell if there isn't an obstruction already
                if (rows.get(rowIndex).charAt(colIndex) == '.') {

                    // backup the original row and place the new obstruction temporarily
                    String removedRow = rows.remove(rowIndex);
                    rows.add(rowIndex,removedRow.substring(0,colIndex) + "#" + removedRow.substring(colIndex + 1));

                    // initialize the guard at the starting position and reset his position-history
                    int[] guard = new int[] {startCoordDirection.x, startCoordDirection.y, startCoordDirection.rotation};
                    HashSet<CoordDirections> positionHistory = new HashSet<>();
                    positionHistory.add(new CoordDirections(guard[0], guard[1], guard[2]));

                    // the guard's movement will be simulated until he walks out of the map or a loop is detected
                    while (true) {
                        try {
                            switch (guard[2]) {
                                    case 0 -> {
                                        // if he is looking upwards and faces an obstruction: turn 90° right, else take a step upwards
                                        if (rows.get(guard[1] - 1).charAt(guard[0]) == '#') {
                                            guard[2] = 1;
                                        } else {
                                            guard[1]--;
                                        }
                                    }
                                    case 1 -> {
                                        // if he is looking right and faces an obstruction: turn 90° right, else take a step right
                                        if (rows.get(guard[1]).charAt(guard[0] + 1) == '#') {
                                            guard[2] = 2;
                                        } else {
                                            guard[0]++;
                                        }
                                    }
                                    case 2 -> {
                                        // if he is looking downwards and faces an obstruction: turn 90° right, else take a step downwards
                                        if (rows.get(guard[1] + 1).charAt(guard[0]) == '#') {
                                            guard[2] = 3;
                                        } else {
                                            guard[1]++;
                                        }
                                    }
                                    case 3 -> {
                                        // if he is looking left and faces an obstruction: turn 90° right, else take a step left
                                        if (rows.get(guard[1]).charAt(guard[0] - 1) == '#') {
                                            guard[2] = 0;
                                        } else {
                                            guard[0]--;
                                        }
                                    }
                                }
                            // if the guard once had the same position and rotation: increment the loop counter and go to the next cell
                            if (positionHistory.contains(new CoordDirections(guard[0], guard[1], guard[2]))) {
                                distinctPositions++;
                                break;
                            }

                            // add the new position and rotation to the position-history
                            positionHistory.add(new CoordDirections(guard[0], guard[1], guard[2]));
                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    // remove the simulated obstacle and reestablish the original 'map'
                    rows.remove(rowIndex);
                    rows.add(rowIndex, removedRow);
                }
            }
        }
        return distinctPositions;
    }
}
