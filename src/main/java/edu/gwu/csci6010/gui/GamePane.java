package edu.gwu.csci6010.gui;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

import java.awt.geom.Point2D;

public abstract class GamePane extends Pane {


    private boolean occupied;

    GamePane(boolean occupied){
        this.occupied = occupied;
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    double calculateDistance(double x, double y){
        Bounds bounds = this.getBoundsInParent();
        double middleX = bounds.getMinX() + (bounds.getMaxX() - bounds.getMinX());
        double middleY = bounds.getMinY() + (bounds.getMaxY() - bounds.getMinY());
        return Math.abs(Point2D.distance(x, y, middleX, middleY));
    }

    public abstract String toString();
}
