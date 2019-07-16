package edu.gwu.csci6010.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

class BoardController {

    private final GridPane gameBoard;
    private final AlgorithimBoard algoBoard;

    BoardController(GridPane gameBoard, Label queensToPlaceText) {
        this.gameBoard = gameBoard;
        this.algoBoard = new AlgorithimBoard(gameBoard.getRowCount());
        queensToPlaceText.textProperty().bind(algoBoard.getBoundQueenCount().asString());
    }

    void updateBoard(BoardPane selected) {
        //Check to see if we can place queens
        boolean occupied = algoBoard.isOccupied(selected.getRowPos(), selected.getColPos());
        if (algoBoard.getQueenCount() == 0 && !occupied)
            return;

        //Mark the cell
        selected.markSquare(!occupied);
        algoBoard.set(selected.getRowPos(), selected.getColPos(), !occupied);

        //Mark all cells with conflicts
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                BoardPane currentCell = getCell(i, j);
                currentCell.markConflict(false);
                boolean currentOccupied = algoBoard.isOccupied(currentCell.getRowPos(), currentCell.getColPos());
                if (currentOccupied && hasConflict(currentCell))
                    currentCell.markConflict(true);
            }
        }
    }

    private boolean hasConflict(BoardPane currentCell) {
        //Check Rows
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            if (i == currentCell.getColPos())
                continue;
            BoardPane nextCell = getCell(currentCell.getRowPos(), i);
            boolean nextOccupied = algoBoard.isOccupied(nextCell.getRowPos(), nextCell.getColPos());
            if (nextOccupied)
                return true;

        }

        //Check columns
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            if (i == currentCell.getRowPos())
                continue;
            BoardPane nextCell = getCell(i, currentCell.getColPos());
            boolean nextoccupied = algoBoard.isOccupied(nextCell.getRowPos(), nextCell.getColPos());
            if (nextoccupied)
                return true;
        }

        //Check diagonals
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                if (i == currentCell.getRowPos() && j == currentCell.getColPos())
                    continue;

                //Calculate the row diffence
                int rowDifference = Math.abs(i - currentCell.getRowPos());
                int colDifference = Math.abs(j - currentCell.getColPos());
                if (rowDifference != colDifference)
                    continue;

                BoardPane nextCell = getCell(i, j);
                boolean nextOccupied = algoBoard.isOccupied(nextCell.getRowPos(), nextCell.getColPos());
                if (nextOccupied) {
                    return true;
                }
            }
        }
        return false;
    }

    private BoardPane getCell(int row, int col) {
        if (row >= gameBoard.getRowCount() || col >= gameBoard.getColumnCount()) {
            String mess = String.format("(%d, %d) is out of bounds for board", row, col);
            throw new IndexOutOfBoundsException(mess);
        }
        return (BoardPane) gameBoard.getChildren().get((row * gameBoard.getRowCount()) + col);
    }

    private void removeConflicts(){
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                BoardPane currentCell = getCell(i, j);
                currentCell.markConflict(false);
                if(algoBoard.isOccupied(i,j) && hasConflict(currentCell) ) {
                    algoBoard.set(i, j, false);
                }
            }
        }
    }

    boolean solve() {
        removeConflicts();
        boolean result = algoBoard.solve();
        if (result) {
            for (int i = 0; i < gameBoard.getRowCount(); i++) {
                for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                    getCell(i, j).markSquare(false);
                    if (algoBoard.isOccupied(i, j)) {
                        getCell(i, j).markSquare(true);
                    }
                }
            }
        }
        return result;
    }
}
