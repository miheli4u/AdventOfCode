import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day11 {

    private static String filePath = "res/PuzzleInputDay11.txt";
    private static final boolean isExample = false;

    public static void main(String[] args) throws IOException {

        if (isExample) {
            filePath = "res/ExampleInputDay11.txt";
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String puzzleInput = br.readLine();

        System.out.println(part2(puzzleInput));
    }

    public static long part2(String puzzleInput) {

        Map<Long, Long> stones = new HashMap<>();
        for (String num : puzzleInput.split(" ")) {
            long stone = Integer.parseInt(num);
            stones.put(stone, stones.getOrDefault(stone, 0L) + 1);
        }

        for (int i = 0; i < 75; i++) {
            Map<Long, Long> newStones = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stones.entrySet()) {
                long stone = entry.getKey();
                long count = entry.getValue();

                for (Long child : mutate(stone)) {
                    newStones.put(child, newStones.getOrDefault(child, 0L) + count);
                }
            }
            stones = newStones;
        }

        long total = 0;
        for (long count : stones.values()) {
            total += count;
        }
        return total;
    }

    private static List<Long> mutate(long stone) {
        List<Long> results = new ArrayList<>();

        if (stone == 0) {
            results.add(1L);
            return results;
        }

        String digits = String.valueOf(stone);
        int length = digits.length();
        int half = length / 2;

        if (length % 2 == 0) {
            results.add(Long.parseLong(digits.substring(0, half)));
            results.add(Long.parseLong(digits.substring(half)));
        } else {
            results.add(stone * 2024);
        }

        return results;
    }
}
