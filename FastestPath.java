import java.util.*;

class Point {
    int x, y, dist;
    List<String> path;

    Point(int x, int y, int dist, List<String> path) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.path = path;
    }
}

public class FastestPath {

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static String[] directions = {"kanan", "kiri", "bawah", "atas"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> maze = new ArrayList<>();
        System.out.println("Masukkan labirin baris per baris, ketik 'OK' untuk menyelesaikan:");

        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("OK")) {
                break;
            }
            maze.add(line);
        }

        int n = maze.size();
        int m = maze.get(0).length();
        char[][] map = new char[n][m];

        for (int i = 0; i < n; i++) {
            map[i] = maze.get(i).toCharArray();
        }

        findShortestPath(map);
    }

    static void findShortestPath(char[][] map) {
        int n = map.length;
        int m = map[0].length;
        boolean[][] visited = new boolean[n][m];
        Point start = null, end = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '^') {
                    start = new Point(i, j, 0, new ArrayList<>());
                } else if (map[i][j] == '*') {
                    end = new Point(i, j, 0, new ArrayList<>());
                }
            }
        }

        if (start == null || end == null) {
            System.out.println("Tidak ada jalan");
            return;
        }

        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            if (p.x == end.x && p.y == end.y) {
                printPath(p.path);
                System.out.println(p.dist + " langkah");
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];

                if (isValid(nx, ny, n, m, map, visited)) {
                    visited[nx][ny] = true;
                    List<String> newPath = new ArrayList<>(p.path);
                    if (!newPath.isEmpty() && newPath.get(newPath.size() - 1).endsWith(directions[i])) {
                        int lastIndex = newPath.size() - 1;
                        String[] parts = newPath.get(lastIndex).split(" ");
                        int count = Integer.parseInt(parts[0]);
                        newPath.set(lastIndex, (count + 1) + " " + directions[i]);
                    } else {
                        newPath.add("1 " + directions[i]);
                    }
                    queue.add(new Point(nx, ny, p.dist + 1, newPath));
                }
            }
        }

        System.out.println("Tidak ada jalan");
    }

    static boolean isValid(int x, int y, int n, int m, char[][] map, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < n && y < m && map[x][y] != '#' && !visited[x][y];
    }

    static void printPath(List<String> path) {
        for (String step : path) {
            System.out.println(step);
        }
    }
}
