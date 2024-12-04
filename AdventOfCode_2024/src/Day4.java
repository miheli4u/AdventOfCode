import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day4 {

    // finds amount of 'XMAS' appearances horizontally, vertically, diagonally and everything backwards
    public static int countXMASinFile (String filePath) {

        int appearanceCounter = 0;

        // read the file and add every line to a list
        String line;
        ArrayList<String> stringList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                stringList.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        int rows = stringList.size();
        int cols = stringList.get(0).length();
        boolean spaceToTop;
        boolean spaceToRight;
        boolean spaceToBottom;
        String stringToCheck;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                spaceToTop = (row > 2);
                spaceToRight = (col < cols - 3);
                spaceToBottom = (row < rows - 3);

                // check diagonally downwards from the cell
                if (spaceToBottom && spaceToRight) {

                    stringToCheck = "";
                    for (int i = 0; i < 4; i++) {
                        stringToCheck += stringList.get(row + i).charAt(col + i);
                    }
                    if (stringToCheck.equals("XMAS") || stringToCheck.equals("SAMX")) {
                        appearanceCounter++;
                    }
                }

                // check horizontally from the cell
                if (spaceToRight) {

                    stringToCheck = "";
                    for (int i = 0; i < 4; i++) {
                        stringToCheck += stringList.get(row).charAt(col + i);
                    }
                    if (stringToCheck.equals("XMAS") || stringToCheck.equals("SAMX")) {
                        appearanceCounter++;
                    }
                }

                // check vertically from the cell
                if (spaceToBottom) {

                    stringToCheck = "";
                    for (int i = 0; i < 4; i++) {
                        stringToCheck += stringList.get(row + i).charAt(col);
                    }
                    if (stringToCheck.equals("XMAS") || stringToCheck.equals("SAMX")) {
                        appearanceCounter++;
                    }
                }

                // check diagonally upwards from the cell
                if (spaceToTop && spaceToRight) {

                    stringToCheck = "";
                    for (int i = 0; i < 4; i++) {
                        stringToCheck += stringList.get(row - i).charAt(col + i);
                    }
                    if (stringToCheck.equals("XMAS") || stringToCheck.equals("SAMX")) {
                        appearanceCounter++;
                    }
                }
            }
        }
        return appearanceCounter;
    }

    // finds amount of x-shaped 'MAS' appearances horizontally, vertically, diagonally and everything backwards
    public static int countXShapedMASinFile (String filePath) {

        int appearanceCounter = 0;

        // read the file and add every line to a list
        String line;
        ArrayList<String> stringList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                stringList.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // for every cell which has space to the bottom and right corner:
        // check the diagonals in a 3 x 3 area starting from the cell as the top left corner
        int rows = stringList.size();
        int cols = stringList.get(0).length();
        String diagUpString;
        String diagDownString;

        for (int row = 0; row < rows - 2 ; row++) {
            for (int col = 0; col < cols - 2; col++) {

                diagUpString = "";
                diagDownString = "";

                for (int i = 0; i < 3; i++) {
                    diagUpString += stringList.get(row + i).charAt(col + i);
                    diagDownString += stringList.get(row + 2 - i).charAt(col + i);
                }
                if ((diagUpString.equals("MAS") || diagUpString.equals("SAM")) && (diagDownString.equals("MAS") || diagDownString.equals("SAM"))) {
                    appearanceCounter++;
                }
            }
        }
        return appearanceCounter;
    }
}
