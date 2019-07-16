package edu.gwu.csci6010.gui;

import javafx.beans.property.SimpleIntegerProperty;

class AlgorithimBoard {

    private final boolean[][] gameBoard;
    private final int size;

    private final SimpleIntegerProperty boundQueens;

    AlgorithimBoard(int size) {
        this.size = size;
        this.gameBoard = new boolean[size][size];
        this.boundQueens = new SimpleIntegerProperty(size);
    }

    void set(int x, int y, boolean value) {
        if (x >= size || y >= size) {
            String mess = String.format("(%d, %d) is out of bounds for board", x, y);
            throw new IndexOutOfBoundsException(mess);
        }

        gameBoard[x][y] = value;
        if (value) {
            boundQueens.set(boundQueens.get() - 1);
        } else {
            boundQueens.set(boundQueens.get() + 1);
        }
    }

    boolean isOccupied(int x, int y) {
        return gameBoard[x][y];
    }

    SimpleIntegerProperty getBoundQueenCount() {
        return this.boundQueens;
    }

    int getQueenCount() {
        return boundQueens.get();
    }

    private boolean hasConflict(int row, int col) {
        //Check Rows & columns
        for (int i = 0; i < size; i++) {
            if ((gameBoard[row][i]) || (gameBoard[i][col]))
                return true;
        }
        //checking for diagonals
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(i - row) != Math.abs(j - col))
                    continue;
                if (gameBoard[i][j])
                    return true;
            }
        }
        return false;
    }

    void removeConflicts(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(isOccupied(i,j) && hasConflict(i, j) ) {
                    System.out.println("Removing");
                    set(i, j, false);
                }
            }
        }
    }

    boolean solve() {
        if (boundQueens.get() == 0)
            return true;

        //Backtracking algorithm
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!gameBoard[i][j] && !hasConflict(i, j)) {
                    set(i, j, true);
                    if (solve()) {
                        return true;
                    }
                    set(i, j, false);
                }
            }
        }

        return false;
    }
}
