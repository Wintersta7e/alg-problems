package model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.*;

/**
 * A plane class that can be set up with predefined edges and the starting position
 */
public class Plane {

    private static final Paint EDGE_COLOR = Color.RED;
    private static final Paint POINT_COLOR = Color.AQUAMARINE;
    private static final int FILL_RADIUS = 1;
    private static final Random RANDOM = new Random();
    private static final double EDGE_RADIUS = 2;
    private static final double FACTOR = 0.5;

    private GraphicsContext gc;
    private List<List<Point2D>> edges;
    private List<Point2D> activePoints;
    private int activeEdgeGroup;
    private int selectedEdgeGroup;

    /**
     * Construct a new empty plane associated with the given canvas
     * @param canvas
     */
    public Plane (Canvas canvas)
    {
        gc = canvas.getGraphicsContext2D();

        edges = new ArrayList<>();
        edges.add(new ArrayList<>());
        activePoints = new ArrayList<>();
        activeEdgeGroup = 0;
        selectedEdgeGroup = 0;
    }

    /**
     * Adds a new edge with the given position
     * @param position
     */
    public void addEdge (Point2D position)
    {
        gc.setFill(EDGE_COLOR);
        gc.fillOval(position.getX(), position.getY(), EDGE_RADIUS, EDGE_RADIUS);
        edges.get(selectedEdgeGroup).add(position);
    }

    /**
     * Add a new starting point with the given position
     * @param position
     */
    public void addStartingPoint (Point2D position)
    {
        gc.setFill(POINT_COLOR);
        gc.fillOval(position.getX(), position.getY(), FILL_RADIUS, FILL_RADIUS);
        activePoints.add(position);
    }

    /**
     * Generate the next state of the plane
     */
    public void tick()
    {
        List<Point2D> nextActivePoints = new ArrayList<>();

        for (Point2D point : activePoints)
        {
            nextActivePoints.add(nextPoint(point));
            gc.setFill(POINT_COLOR);
            gc.fillOval(point.getX(), point.getY(), FILL_RADIUS, FILL_RADIUS);
        }

        activePoints = nextActivePoints;
    }

    /**
     * Produce the next active point
     * @param point
     * @return
     */
    public Point2D nextPoint(Point2D point)
    {
        Optional<Point2D> randomEdge = pickRandomEdge();

        while (!randomEdge.isPresent())
        {
            randomEdge = pickRandomEdge();
        }

        return (point.add(randomEdge.get()).multiply(FACTOR));
    }

    /**
     * Pick random edge, if edge is present
     * @return
     */
    private Optional<Point2D> pickRandomEdge()
    {
        List<Point2D> edgeGroup = getNextEdgeGroup();

        if (edgeGroup.size() > 0)
        {
            return Optional.of(edgeGroup.get(RANDOM.nextInt(edgeGroup.size())));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Produce the next edge group, loop back when needed
     * @return
     */
    private List<Point2D> getNextEdgeGroup()
    {
        assert (edges.size() > 0);
        activeEdgeGroup = (activeEdgeGroup + 1) % edges.size();
        return edges.get(activeEdgeGroup);
    }

    /**
     * Completely clears the plane
     */
    public void clear()
    {
        Canvas canvas = gc.getCanvas();

        edges.clear();
        selectedEdgeGroup = 0;
        edges.add(new ArrayList<>());
        activePoints.clear();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Select/create next edge group
     */
    public void nextEdgeGroup()
    {
        edges.add(new ArrayList<>());
        selectedEdgeGroup++;
    }

    /**
     * If possible, switch to previous edge group
     */
    public void prevEdgeGroup()
    {
        selectedEdgeGroup = Math.max(selectedEdgeGroup - 1, 0);
    }
}
