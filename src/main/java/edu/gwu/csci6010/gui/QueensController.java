package edu.gwu.csci6010.gui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class QueensController {

    @FXML
    public GridPane gameBoard, queenArray;

    @FXML
    private ComboBox boardSizeSelector;

    private static final int DEFAULT_BOARD_SIZE = 8;


    @FXML
    private void initialize() {
        initBoard(DEFAULT_BOARD_SIZE);
        initQueens(DEFAULT_BOARD_SIZE);
    }

    @FXML
    private void newGame(){
        int boardSize = (int) boardSizeSelector.getValue();
        clearGameBoard();
        initBoard(boardSize);
        initQueens(boardSize);
    }

    @FXML
    private void initBoard(int boardSize){
        for (int row = 0 ; row < boardSize ; row++ ){
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setFillHeight(true);
            rowConstraint.setVgrow(Priority.ALWAYS);
            gameBoard.getRowConstraints().add(rowConstraint);
        }
        for (int col = 0 ; col < boardSize; col++ ) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setFillWidth(true);
            columnConstraint.setHgrow(Priority.ALWAYS);
            gameBoard.getColumnConstraints().add(columnConstraint);
        }

        boolean color = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String style = color ? "dark-wood" : "light-wood";
                gameBoard.add(BoardPane.createPane(i, j, style), j, i);
                color = !color;
            }
            if(boardSize % 2 == 0)
                color = !color;
        }
    }

    private void clearGameBoard(){
        gameBoard.getChildren().clear();
        gameBoard.getColumnConstraints().clear();
        gameBoard.getRowConstraints().clear();

        queenArray.getChildren().clear();
        queenArray.getColumnConstraints().clear();
        queenArray.getRowConstraints().clear();
    }

    private void initQueens(int numQueens){
        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setFillWidth(true);
        columnConstraint.setHgrow(Priority.ALWAYS);
        queenArray.getColumnConstraints().add(columnConstraint);

        for(int i = 0; i < numQueens; i++){
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setFillHeight(true);
            rowConstraint.setVgrow(Priority.ALWAYS);
            queenArray.getRowConstraints().add(rowConstraint);
        }

        for(int i = 0; i < numQueens; i++){
            Pane queenPane = new QueenPane(i);
            draggableQueen(queenPane);
            queenArray.add(queenPane, 0, i);
        }
    }

    private void draggableQueen(Pane queenPane) {
        queenPane.setCursor(Cursor.HAND);
        queenPane.setOnMousePressed(circleOnMousePressedEventHandler);
        queenPane.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        queenPane.setOnMouseReleased(stopDrag);
    }

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> circleOnMousePressedEventHandler = t -> {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((Pane) (t.getSource())).getTranslateX();
        orgTranslateY = ((Pane) (t.getSource())).getTranslateY();
    };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            ((Pane) (t.getSource())).setTranslateX(newTranslateX);
            ((Pane) (t.getSource())).setTranslateY(newTranslateY);
        }
    };

    EventHandler<MouseEvent> stopDrag = mouseEvent -> {
        double currentX = mouseEvent.getSceneX();
        double currentY = mouseEvent.getSceneY();

//        double currentX = (Pane) mouseEvent.getSource();
//        double currentY = orgTranslateY;
        Pane p = (Pane) mouseEvent.getSource();

//
//        double currentX = p.ge;
//        double currentY = orgTranslateY;
//        System.out.println(currentX + "," + currentY);

        ObservableList<Node> allSpaces = gameBoard.getChildren();
        allSpaces.addAll(queenArray.getChildren());
        for(Node n : allSpaces){
            GamePane gp = (GamePane) n;
            double d = gp.calculateDistance(currentX, currentY);
            System.out.println(gp + ":" + d);
        }



//        GamePane closestSquare = allSpaces.stream()
//                .map(square -> (GamePane) square)
//                .filter(GamePane::isOccupied)
//                .min(Comparator.comparingDouble(s -> s.calculateDistance(currentX, currentY)))
//                .orElseThrow(NoSuchElementException::new);
//        System.out.println(closestSquare);
//
//        for(Node n : allSpaces){
//            System.out.println(n.getClass());
//        }
    };
}
