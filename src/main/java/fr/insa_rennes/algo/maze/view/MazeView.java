package fr.insa_rennes.algo.maze.view;

import fr.insa_rennes.algo.maze.model.Maze;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeView {
    public static void draw(Maze maze, GraphicsContext gc, double width, double height) {
        gc.save();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        gc.scale(width / (maze.size() + 2), height / (maze.size() + 2));
        gc.setFill(Color.BLACK);
        gc.setLineWidth(0.1);
        for (int x = 1; x <= maze.size(); x++) {
            for (int y = 1; y <= maze.size(); y++) {
                if (maze.southWalls(x, y)) { gc.strokeLine(x, maze.size() + 2 - y, x + 1, maze.size() + 2 - y); }
                if (maze.northWalls(x, y)) { gc.strokeLine(x, maze.size() + 2 - (y + 1), x + 1, maze.size() + 2 - (y + 1)); }
                if (maze.westWalls(x, y))  { gc.strokeLine(x, maze.size() + 2 - y, x, maze.size() + 2 - (y + 1)); }
                if (maze.eastWalls(x, y))  { gc.strokeLine(x + 1, maze.size() + 2 - y, x + 1, maze.size() + 2 - (y + 1)); }
            }
        }
        gc.restore();
    }
}

