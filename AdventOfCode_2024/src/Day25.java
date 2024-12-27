import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day25 {

    private static String filePath = "res/PuzzleInputDay25.txt";
    private static final boolean isExample = false;

    public static void main(String[] args) {

        if (isExample) {
            filePath = "res/ExampleInputDay25.txt";
        }

        String line;
        List<String> schematic = new ArrayList<>();
        List<int[]> keys = new ArrayList<>();
        List<int[]> locks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                if (line.equals("")) {
                    addSchematic(keys, locks, schematic);
                    schematic = new ArrayList<>();
                } else {
                    schematic.add(line);
                }
            }
            addSchematic(keys, locks, schematic);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int sum = 0;

        for (int[] lock : locks) {
            for (int[] key : keys) {
                if (tryLockWithKey(lock, key)) {
                    sum++;
                }
            }
        }
        System.out.println("sum = " + sum);
    }

    public static void addSchematic (List<int[]> keys, List<int[]> locks, List<String> schematic) {

        // if its a lock: calculate the pin heights and add it to the locks
        if (schematic.get(0).equals("#####")) {
            locks.add(calculateLockPinHeights(schematic));
        } else if (schematic.get(0).equals(".....")) {
            keys.add(calculateKeyPinHeights(schematic));
        }
    }

    public static int[] calculateLockPinHeights(List<String> schematic) {

        int[] lockPinHeights = new int[5];
        int heightCounter = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < schematic.size(); j++) {
                if (j == schematic.size()) {
                    lockPinHeights[i] = heightCounter;
                    heightCounter = 0;
                } else if (schematic.get(j).charAt(i) == '.') {
                    lockPinHeights[i] = heightCounter;
                    heightCounter = 0;
                    break;
                } else if (schematic.get(j).charAt(i) == '#') {
                    heightCounter++;
                }
            }
        }
        return lockPinHeights;
    }

    public static int[] calculateKeyPinHeights(List<String> schematic) {

        int[] lockPinHeights = new int[5];
        int heightCounter = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = schematic.size() - 2; j >= 0; j--) {
                if (j == 0) {
                    lockPinHeights[i] = heightCounter;
                    heightCounter = 0;
                } else if (schematic.get(j).charAt(i) == '.') {
                    lockPinHeights[i] = heightCounter;
                    heightCounter = 0;
                    break;
                } else if (schematic.get(j).charAt(i) == '#') {
                    heightCounter++;
                }
            }
        }
        return lockPinHeights;
    }

    public static boolean tryLockWithKey (int[] lock, int[] key) {

        for (int i = 0; i < lock.length; i++) {
            if (lock[i] + key[i] > 5) return false;
        }
        return true;
    }
}
