package edu.gwu.csci6010.gui;

import javafx.scene.control.Button;

class BoardPane extends Button {

    private int rowPos, colPos;

    private BoardPane(int rowPos, int colPos) {
        this.rowPos = rowPos;
        this.colPos = colPos;
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    int getRowPos() {
        return rowPos;
    }

    int getColPos() {
        return colPos;
    }

    static BoardPane createPane(int rowPos, int colPos, String styleClass) {
        BoardPane boardPane = new BoardPane(rowPos, colPos);
        boardPane.getStyleClass().add(styleClass);
        return boardPane;
    }

    void markSquare(boolean value) {
        if (value) {
            this.getStyleClass().add("queen");
            return;
        }
        this.getStyleClass().remove("queen");
    }

    void markConflict(boolean conflict) {
        if (conflict) {
            this.getStyleClass().add("conflict");
            return;
        }
        this.getStyleClass().remove("conflict");
    }
}
