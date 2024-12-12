import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day12 {

    public static void main(String[] args) {

        // load the map from the textfile and store it in a two-dimensional string array
        String filePath = "res/ExampleInput3Day12.txt";

        String line;
        String[] plants;
        ArrayList<String[]> plantRows = new ArrayList<>();
        String[][] map;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                plants = line.split("");
                plantRows.add(plants);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        map = new String[plantRows.size()][];
        map = plantRows.toArray(map);

        System.out.println("calculateTotalPrice(map) = " + calculateTotalPrice(map));


    }

    public static int calculateTotalPrice(String[][] map) {

        int rows = map.length;
        int cols = map[0].length;

        boolean[][] visited = new boolean[rows][cols];

        int totalPrice = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                if (!visited[row][col]) {

                    String planttype = map[row][col];
                    Region region = floodFill(map, visited, row, col, planttype);
                    int area = region.area;
                    int perimeter = calculateSides(map, region.cells, planttype);
                    totalPrice += area * perimeter;

                }
            }
        }
        return totalPrice;
    }

    private static Region floodFill (String[][] map, boolean[][] visited, int r, int c, String planttype) {
        int rows = map.length;
        int cols = map[0].length;

        List<Integer[]> cells = new ArrayList<>();
        Stack<Integer[]> stack = new Stack<>();
        stack.push(new Integer[] {r, c});

        while (!stack.isEmpty()) {
            Integer[] current = stack.pop();
            int x = current[0], y = current[1];
            if (y < 0 || x < 0 || y >= cols || x >= rows || visited[x][y] || !map[x][y].equals(planttype)) {
                continue;
            }
            visited[x][y] = true;
            cells.add(new Integer[] {x, y});
            stack.push(new Integer[] {x, y + 1});
            stack.push(new Integer[] {x + 1, y});
            stack.push(new Integer[] {x, y - 1});
            stack.push(new Integer[] {x - 1, y});

        }

        return new Region(cells.size(), cells);
    }

    private static int calculatePerimeter(String[][] map, List<Integer[]> cells, String plantType){

        int rows = map.length;
        int cols = map[0].length;
        int perimeter = 0;

        for (Integer[] cell : cells) {
            int x = cell[0], y = cell[1];
            for (int[] direction : new int[][] {{-1,0}, {1,0},{0,-1},{0,1}}) {
                int nx = x + direction[0], ny = y + direction[1];
                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || !map[nx][ny].equals(plantType)) {
                    perimeter++;
                }
            }
        }

        return perimeter;
    }

    private static int calculateSides(String[][] map, List<Integer[]> cells, String plantType) {
        int rows = map.length;
        int cols = map[0].length;
        Set<String> edges = new HashSet<>();

        for (Integer[] cell : cells) {
            int x = cell[0], y = cell[1];
            for (int[] direction : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int nx = x + direction[0], ny = y + direction[1];
                String edge = x + "," + y + "-" + nx + "," + ny;
                String reverseEdge = nx + "," + ny + "-" + x + "," + y;

                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || !map[nx][ny].equals(plantType)) {
                    edges.add(edge);
                }else if (!edges.contains(reverseEdge)) {
                    edges.add(edge); // Avoid counting the same edge twice
                }
            }
        }

        return edges.size();
    }

    static class Region {
        int area;
        List<Integer[]> cells;

        Region (int area, List<Integer[]> cells) {
            this.area = area;
            this.cells = cells;
        }

    }
}