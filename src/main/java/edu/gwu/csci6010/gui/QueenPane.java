package edu.gwu.csci6010.gui;

public class QueenPane extends GamePane{

    int rowPos;

    public QueenPane(int rowPos){
        super(true);
        this.rowPos = rowPos;
        this.getStyleClass().add("queen");
    }

    public int getRowPos() {
        return rowPos;
    }

    @Override
    public String toString() {
        return "[Queen: " + rowPos + "]";
    }
}
