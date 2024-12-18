import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day18 {
    // PII
    private static int simulationDistance = 1;
    private static int gridSize = 71;

    // DEBUG
    private static String filePath = "res/PuzzleInputDay18.txt";
    // change this to false to go for the real puzzle input
    private static final boolean isExample = false;

    public static void main(String[] args) {

        //DEBUG
        if (isExample) {
            filePath = "res/ExampleInputDay18.txt";
            gridSize = 7;
            simulationDistance = 12;
        }

        while (true) {
            // create a grid with only dots in it
            char[][] grid = makeEmptyGrid(gridSize);
            grid = simulateMemoryCorruption(grid, simulationDistance, filePath);

            //DEBUG
            for (char[] row : grid) {
                for (char cell : row) {
                    System.out.print(cell);
                }
                System.out.print("\n");
            }

            List<Node> path = findPath(grid, 0, 0, gridSize - 1, gridSize - 1);

            if (!path.isEmpty()) {
                System.out.println("NodeAnzahl OHNE START ALSO NUR DIE STEPS: "+ (path.size() - 1));
                System.out.println("Path found:");
                for (Node node : path) {
                    System.out.printf("[%d, %d] -> ", node.row, node.col);
                }
                System.out.println("Goal \n");

                System.out.println("Grid with path:\n");
                for (Node node : path) {
                    grid[node.row][node.col] = 'O';
                }
                for (char[] row : grid) {
                    for (char cell : row) {
                        System.out.print(cell);
                    }
                    System.out.print("\n");
                }
            } else {
                System.out.println("No path found.");
            }

            if (path.isEmpty()) {
                break;
            }
            simulationDistance++;
        }

        System.out.println("simulationDistance = " + simulationDistance);

    }

    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Rechts, Unten, Links, Oben

    public static List<Node> findPath (char[][] grid, int startRow, int startCol, int endRow, int endCol) {

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        Node start = new Node (startRow, startCol);
        start.gCost = 0;
        openSet.add(start);

        Node end = new Node (endRow, endCol);

        while (!openSet.isEmpty()) {

            Node currentNode = openSet.poll();

            if (currentNode.equals(end)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode);

            for (int[] direction : DIRECTIONS) {
                int newRow = currentNode.row + direction[0];
                int newCol = currentNode.col + direction[1];

                if (!isValid(grid, newRow, newCol)) continue;

                Node neighbor = new Node (newRow, newCol);

                if (closedSet.contains(neighbor)) continue;

                int tentativeGCost = currentNode.gCost + 1;

                if (openSet.contains(neighbor) && tentativeGCost > neighbor.gCost) continue;

                neighbor.gCost = tentativeGCost;
                neighbor.hCost = calculateManhattanDistance(neighbor, end);
                neighbor.fCost = neighbor.getFCost();
                neighbor.parent = currentNode;

                openSet.remove(neighbor); // Entferne, falls es existiert (wird später mit neuen Werten hinzugefügt)
                openSet.add(neighbor);
            }

        }
        return new ArrayList<>();
    }

    public static int calculateManhattanDistance(Node nodeA, Node target) {
        return Math.abs(nodeA.row - target.row) + Math.abs(nodeA.col - target.col);
    }

    public static boolean isValid (char[][] grid, int row, int col) {
        if (row > -1 && row < gridSize && col > -1 && col < gridSize) {
            return grid[row][col] == '.';
        } else {
            return false;
        }

    }

    public static List<Node> reconstructPath(Node currentNode) {
        List<Node> reversePath = new ArrayList<>();

        while (currentNode != null) {
            reversePath.add(currentNode);
            currentNode = currentNode.getParent();
        }

        Collections.reverse(reversePath);

        return reversePath;
    }

    static class Node implements Comparable<Node> {

        private int row, col;
        private int gCost, hCost, fCost;
        Node parent;

        Node (int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return this.row == node.row && this.col == node.col;
        }
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(getFCost(), other.getFCost());
        }

        public int getFCost() {
            return this.gCost + this.hCost;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }
    }

    public static char[][] simulateMemoryCorruption (char[][] grid, int simulationDistance, String filePath) {

        String line;
        int[] currentMemoryCell;
        int byteCounter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null && byteCounter < simulationDistance) {

                currentMemoryCell = getMemoryCoords(line);
                int currentRow = currentMemoryCell[1];
                int currentCol = currentMemoryCell[0];

                grid[currentRow][currentCol] = '#';

                byteCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return grid;
    }

    public static char[][] makeEmptyGrid (int gridSize) {
        char[][] grid = new char[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            Arrays.fill(grid[row], '.');
        }
        return grid;
    }

    public static int[] getMemoryCoords (String line) {
        return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
