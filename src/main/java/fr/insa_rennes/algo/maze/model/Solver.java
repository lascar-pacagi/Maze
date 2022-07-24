package fr.insa_rennes.algo.maze.model;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

import fr.insa_rennes.algo.maze.util.Coordinate;


public class Solver {
    private Maze maze;
    private boolean[][] visited;
    private Deque<Coordinate> path;
    private int cost;

    public void setMaze(Maze maze) {
        this.maze = maze;
        visited = new boolean[maze.size() + 2][maze.size() + 2];
        path = new ArrayDeque<>();
        reset();
    }

    public Maze getMaze() {
        return maze;
    }

    public Iterable<Coordinate> getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    private void constructPath(SearchNode exit) {
        cost = exit.distance;
        path = new ArrayDeque<>();
        SearchNode current = exit;
        do {
            path.addFirst(current.getCoordinate());
            current = current.parent;
        } while (current != null);
    }

    private boolean possible(int x, int y) {
        return x != 0 && y != 0 && x != maze.size() + 1 && y != maze.size() + 1
                && !visited[x][y];
    }

    private class SearchNode {
        SearchNode parent;
        int x;
        int y;
        int distance;

        SearchNode(SearchNode parent, int x, int y, int distance) {
            this.parent = parent;
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        Coordinate getCoordinate() {
            return new Coordinate(x, y);
        }
    }

    private SearchNode dfs(int startX, int startY) {
        Deque<SearchNode> stack = new ArrayDeque<>();
        stack.addFirst(new SearchNode(null, startX, startY, 0));
        while (!stack.isEmpty()) {
            SearchNode node = stack.removeFirst();
            int x = node.x;
            int y = node.y;
            visited[x][y] = true;
            // reached goal
            if (x == maze.size() && y == maze.size())
                return node;

            if (!maze.northWalls(x, y) && possible(x, y + 1)) {
                stack.addFirst(new SearchNode(node, x, y + 1, node.distance + 1));
            }
            if (!maze.eastWalls(x, y) && possible(x + 1, y)) {
                stack.addFirst(new SearchNode(node, x + 1, y, node.distance + 1));
            }
            if (!maze.southWalls(x, y) && possible(x, y - 1)) {
                stack.addFirst(new SearchNode(node, x, y - 1, node.distance + 1));
            }
            if (!maze.westWalls(x, y) && possible(x - 1, y)) {
                stack.addFirst(new SearchNode(node, x - 1, y, node.distance + 1));
            }
        }
        return null;
    }

    public void reset() {
        path = new ArrayDeque<>();
        cost = -1;
        for (int x = 0; x <= maze.size() + 1; x++) {
            visited[0][x] = true;
            visited[x][0] = true;
            visited[maze.size() + 1][x] = true;
            visited[x][maze.size() + 1] = true;
        }
        for (int x = 1; x <= maze.size(); x++)
            for (int y = 1; y <= maze.size(); y++)
                visited[x][y] = false;
    }

    public void dfs() {
        reset();
        SearchNode node = dfs(1, 1);
        if (node != null)
            constructPath(node);
    }

    private SearchNode bfs(int startX, int startY) {
        Deque<SearchNode> queue = new ArrayDeque<>();
        queue.addLast(new SearchNode(null, startX, startY, 0));
        while (!queue.isEmpty()) {
            SearchNode node = queue.removeFirst();
            int x = node.x;
            int y = node.y;
            visited[x][y] = true;
            // reached goal
            if (x == maze.size() && y == maze.size())
                return node;
            if (!maze.northWalls(x, y) && possible(x, y + 1)) {
                queue.addLast(new SearchNode(node, x, y + 1, node.distance + 1));
            }
            if (!maze.eastWalls(x, y) && possible(x + 1, y)) {
                queue.addLast(new SearchNode(node, x + 1, y, node.distance + 1));
            }
            if (!maze.southWalls(x, y) && possible(x, y - 1)) {
                queue.addLast(new SearchNode(node, x, y - 1, node.distance + 1));
            }
            if (!maze.westWalls(x, y) && possible(x - 1, y)) {
                queue.addLast(new SearchNode(node, x - 1, y, node.distance + 1));
            }
        }
        return null;
    }

    public void bfs() {
        reset();
        SearchNode node = bfs(1, 1);
        if (node != null)
            constructPath(node);
    }

    private int transitionCost(boolean hasWall, int cost) {
        return hasWall ? cost : 1;
    }

    private SearchNode dijkstra(int startX, int startY, int breakingWallCost) {
        PriorityQueue<SearchNode> pq = new PriorityQueue<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode n1, SearchNode n2) {
                return Integer.compare(n1.distance, n2.distance);
            }
        });
        pq.add(new SearchNode(null, startX, startY, 0));
        while (!pq.isEmpty()) {
            SearchNode node = pq.remove();
            int x = node.x;
            int y = node.y;
            if (visited[x][y])
                continue;
            visited[x][y] = true;
            // reached goal
            if (x == maze.size() && y == maze.size())
                return node;

            if (possible(x, y + 1)) {
                pq.add(new SearchNode(node, x, y + 1, 
                        node.distance + transitionCost(maze.northWalls(x, y), breakingWallCost)));
            }
            if (possible(x + 1, y)) {
                pq.add(new SearchNode(node, x + 1, y,
                        node.distance + transitionCost(maze.eastWalls(x, y), breakingWallCost)));
            }
            if (possible(x, y - 1)) {
                pq.add(new SearchNode(node, x, y - 1, 
                        node.distance + transitionCost(maze.southWalls(x, y), breakingWallCost)));
            }
            if (possible(x - 1, y)) {
                pq.add(new SearchNode(node, x - 1, y,
                        node.distance + transitionCost(maze.westWalls(x, y), breakingWallCost)));
            }
        }
        return null;
    }

    public void dijkstra(int breakingWallCost) {
        reset();
        SearchNode node = dijkstra(1, 1, breakingWallCost);
        if (node != null)
            constructPath(node);
    }
}

