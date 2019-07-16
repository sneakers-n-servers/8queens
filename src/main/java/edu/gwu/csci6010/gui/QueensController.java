package edu.gwu.csci6010.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class QueensController {

    @FXML
    public GridPane gameBoard;

    @FXML
    public Label queensToPlace;

    @FXML
    public Label stausLabel;

    @FXML
    private ComboBox boardSizeSelector;

    private BoardController boardController;

    private static final int DEFAULT_BOARD_SIZE = 8;


    @FXML
    private void initialize() {
        initBoard(DEFAULT_BOARD_SIZE);
        boardController = new BoardController(gameBoard, queensToPlace);
    }

    @FXML
    private void newGame() {
        int boardSize = (int) boardSizeSelector.getValue();
        clearGameBoard();
        initBoard(boardSize);
        boardController = new BoardController(gameBoard, queensToPlace);
    }

    @FXML
    private void initBoard(int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setFillHeight(true);
            rowConstraint.setVgrow(Priority.ALWAYS);
            gameBoard.getRowConstraints().add(rowConstraint);
        }
        for (int col = 0; col < boardSize; col++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setFillWidth(true);
            columnConstraint.setHgrow(Priority.ALWAYS);
            gameBoard.getColumnConstraints().add(columnConstraint);
        }

        boolean color = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String style = color ? "dark-wood" : "light-wood";
                BoardPane boardPane = BoardPane.createPane(i, j, style);
                boardPane.setOnAction(boardclickHandler);
                gameBoard.add(boardPane, j, i);
                color = !color;
            }
            if (boardSize % 2 == 0)
                color = !color;
        }
    }

    private void clearGameBoard() {
        gameBoard.getChildren().clear();
        gameBoard.getColumnConstraints().clear();
        gameBoard.getRowConstraints().clear();
        stausLabel.setText("");
        stausLabel.getStyleClass().clear();
    }

    private EventHandler<ActionEvent> boardclickHandler = actionEvent -> {
        BoardPane selected = (BoardPane) actionEvent.getTarget();
        boardController.updateBoard(selected);
    };

    public void solve() {
        boolean success = boardController.solve();
        if (success) {
            stausLabel.setText("Success");
            stausLabel.getStyleClass().add("text-success");
        } else {
            stausLabel.setText("Failure");
            stausLabel.getStyleClass().add("text-failure");
        }
    }
}
