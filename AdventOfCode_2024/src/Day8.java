import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day8 {

    // a variable holder for the coords of the antinodes
    private record antinodeCoords(int x, int y) {} ;

    /**
     * A method to calculate the positions of antinodes of a given map with antennas
     * @param filePath the path to the textfile with the given puzzle-input
     * @param includeResonantHarmonics true if resonant harmonics should be included in the calculation
     * @return returns the amount of distinct antinode positions on the given map
     */
    public static int calcDistinctAntinodePositions (String filePath, boolean includeResonantHarmonics) {

        // read the file and save the map as a list of strings
        String line;
        ArrayList<String> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                rows.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        int mapSizeX = rows.get(0).length();
        int mapSizeY = rows.size();

        HashSet<antinodeCoords> antinodePlaces = new HashSet<>();

        // check every coord on the given map for an antenna
        for (int rowIndex = 0; rowIndex < mapSizeY; rowIndex++) {
            for (int colIndex = 0; colIndex < mapSizeX; colIndex++) {

                char currCell = rows.get(rowIndex).charAt(colIndex);

                if (currCell != '.') {

                    boolean isCurrCell = true;

                    // check the rest of the map for antennas of the same frequencies
                    for (int nextCellRowIndex = rowIndex; nextCellRowIndex < mapSizeY; nextCellRowIndex++) {

                        int startingCol = 0;

                        if (isCurrCell) {
                            startingCol = colIndex;
                        }

                        for (int nextCellColIndex = startingCol; nextCellColIndex < mapSizeX; nextCellColIndex++) {

                            // skip to check the next not the current cell
                            if (isCurrCell) {
                                isCurrCell = false;
                                continue;
                            }

                            char cellToCheck = rows.get(nextCellRowIndex).charAt(nextCellColIndex);

                            // if the current cell is an antenna of the same frequency: count it's antinodes
                            if (currCell == cellToCheck) {
                                countAntinodes(colIndex, rowIndex, nextCellColIndex, nextCellRowIndex, mapSizeX, mapSizeY, antinodePlaces, includeResonantHarmonics);
                            }
                        }
                    }
                }
            }
        }
        // the size of the hashset determines the amount of unique antinode places
        return antinodePlaces.size();
    }

    /**
     * A method to count antinodes
     * @param firstAntennaX xCoord of the first antenna
     * @param firstAntennaY yCoord of the first antenna
     * @param secondAntennaX xCoord of the second antenna
     * @param secondAntennaY yCoord of the second antenna
     * @param mapSizeX horizontal size of the map
     * @param mapSizeY vertical size of the map
     * @param antinodePlaces set of coords of the antinode places
     * @param includeResonantHarmonics true if resonant harmonics should be included in the calculations
     */
    private static void countAntinodes (int firstAntennaX, int firstAntennaY,
                                      int secondAntennaX, int secondAntennaY,
                                      int mapSizeX, int mapSizeY, HashSet<antinodeCoords> antinodePlaces, boolean includeResonantHarmonics) {

        // calculate the distances in both directions
        int deltaX = secondAntennaX - firstAntennaX;
        int deltaY = secondAntennaY - firstAntennaY;

        int antinodeX;
        int antinodeY;

        // calculate the whole ray of antinodes if resonant harmonics are included in the calculation
        if (includeResonantHarmonics) {

            int antinodeCounter = 0;

            while (true) {

                // adapt the coords of the next antinode
                antinodeX = firstAntennaX - (deltaX * antinodeCounter);
                antinodeY = firstAntennaY - (deltaY * antinodeCounter);

                // if the ray of antinodes is already outside the map: break the loop
                if (!addAntinodeIfOnMap(antinodeX, antinodeY, mapSizeX, mapSizeY, antinodePlaces)) {
                    break;
                }
                antinodeCounter++;
            }

            antinodeCounter = 0;

            // calculate the ray of coords to the other direction
            while (true) {

                antinodeX = secondAntennaX + (deltaX * antinodeCounter);
                antinodeY = secondAntennaY + (deltaY * antinodeCounter);

                if (!addAntinodeIfOnMap(antinodeX, antinodeY, mapSizeX, mapSizeY, antinodePlaces)) {
                    break;
                }
                antinodeCounter++;
            }
        } else {

            // if resonant harmonics aren't included: adapt and add the coords of only the two antinodes outside the antennas
            antinodeX = firstAntennaX - deltaX;
            antinodeY = firstAntennaY - deltaY;

            addAntinodeIfOnMap(antinodeX, antinodeY, mapSizeX, mapSizeY, antinodePlaces);

            antinodeX = secondAntennaX + deltaX;
            antinodeY = secondAntennaY + deltaY;

            addAntinodeIfOnMap(antinodeX, antinodeY, mapSizeX, mapSizeY, antinodePlaces);
        }

        return;
    }

    // a method to add the antinode coords to the set if it's on the map
    private static boolean addAntinodeIfOnMap (int antinodeX, int antinodeY, int mapSizeX, int mapSizeY, HashSet<antinodeCoords> antinodePlaces) {

        // check if the coords of the antinode are on the map
        if (antinodeX > -1 && antinodeX < mapSizeX && antinodeY > -1 && antinodeY < mapSizeY) {

            // add the coords to the set of places
            antinodePlaces.add(new antinodeCoords(antinodeX, antinodeY));
            return true;

        } else {
            return false;
        }
    }
}
