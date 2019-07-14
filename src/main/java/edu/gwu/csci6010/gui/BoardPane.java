package edu.gwu.csci6010.gui;

public class BoardPane extends GamePane {

    private int rowPos, colPos;

    private BoardPane(int rowPos, int colPos){
        super(false);
        this.rowPos = rowPos;
        this.colPos = colPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getColPos() {
        return colPos;
    }

    static BoardPane createPane(int rowPos, int colPos, String styleClass){
        BoardPane boardPane = new BoardPane(rowPos, colPos);
        boardPane.getStyleClass().add(styleClass);
        return boardPane;
    }


    @Override
    public String toString() {
        return "[Board: " + rowPos + "," + colPos + "]";
    }
}
