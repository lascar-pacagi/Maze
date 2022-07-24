package fr.insa_rennes.algo.maze.view;

import fr.insa_rennes.algo.maze.model.Maze;
import fr.insa_rennes.algo.maze.model.Solver;
import fr.insa_rennes.algo.maze.util.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PathView {

    public static void draw(Solver solver, GraphicsContext gc, double width, double height) {
        if (solver.getPath() == null) return;
        Maze maze = solver.getMaze();
        gc.save();
        gc.scale(width / (maze.size() + 2), height / (maze.size() + 2));
        gc.setFill(Color.BLUE);
        gc.setLineWidth(0.1);
        for (Coordinate c : solver.getPath()) {
            int x = c.getX();
            int y = maze.size() + 1 - c.getY();
            gc.fillOval(x + 0.25, y + 0.25, 0.5, 0.5);
        }
        gc.restore();
    }

}
