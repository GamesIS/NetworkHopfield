package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.NeuralHopfield;


public class ListImagesController {
    private static ListImagesController controller;
    @FXML
    private GridPane drawGrid;

    @FXML
    public Button remember;

    @FXML
    public Button clear;

    @FXML
    public Button restore;


    private static final int WIDTH_RECTANGLE = 45;
    private static final int COUNT_PIXEL = 10;

    private NeuralHopfield neuralHopfield;

    private Main main;
    private Rectangle[][] rec;


    private static ObservableList<String> obResList;

    @FXML
    public void initialize() {
        neuralHopfield = new NeuralHopfield(COUNT_PIXEL * COUNT_PIXEL);

        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (int i = 0; i < rec.length; i++) {
                    for (int j = 0; j < rec[i].length; j++) {
                        rec[i][j].setFill(Color.WHITE);
                    }
                }
            }
        });
        restore.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setIntValuesToGrid(neuralHopfield.execute(getDrawImageArray()));
            }
        });
        remember.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                neuralHopfield.learn(getDrawImageArray());
            }
        });
    }

    public ListImagesController() {
        controller = this;
    }

    public static ListImagesController getController() {
        return controller;
    }


    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
    }

    public void setMain(Main main) {
        this.main = main;

        makeGrid();
    }

    public GridPane makeGrid() {

        drawGrid.setGridLinesVisible(true);
        rec = new Rectangle[COUNT_PIXEL][COUNT_PIXEL];

        for (int i = 0; i < rec.length; i++) {
            for (int j = 0; j < rec[i].length; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * WIDTH_RECTANGLE);
                rec[i][j].setY(j * WIDTH_RECTANGLE);
                rec[i][j].setWidth(WIDTH_RECTANGLE);
                rec[i][j].setHeight(WIDTH_RECTANGLE);
                rec[i][j].setFill(null);
                rec[i][j].setStroke(Color.BLACK);
                //p.getChildren().add(rec[i][j]);
                drawGrid.add(rec[i][j], i, j);
            }
        }

        drawGrid.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw(event);
            }
        });

        drawGrid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw(event);
            }
        });

        return drawGrid;
    }

    private void draw(MouseEvent event) {
        double posX = event.getX();
        double posY = event.getY();

        System.out.println("posX = " + posX + " posY = " + posY);

        posX -= (posX / WIDTH_RECTANGLE);
        posY -= (posY / WIDTH_RECTANGLE);

        int colX = (int) ((posX / WIDTH_RECTANGLE));
        int colY = (int) ((posY / WIDTH_RECTANGLE));
        System.out.println("colX = " + colX + " colY = " + colY);

        if (colX < COUNT_PIXEL && colY < COUNT_PIXEL) {
            if (event.getButton() == MouseButton.SECONDARY) {
                rec[colX][colY].setFill(Color.WHITE);
            } else {
                rec[colX][colY].setFill(Color.RED);
            }
        }
    }

    private int[] getDrawImageArray() {
        int[] result = new int[COUNT_PIXEL * COUNT_PIXEL];
        int index = 0;
        for (int i = 0; i < rec.length; i++) {
            for (int j = 0; j < rec[i].length; j++) {
                if (rec[i][j].getFill() == Color.RED) {
                    result[index++] = 1;
                } else {
                    result[index++] = -1;
                }
            }
        }
        return result;
    }

    private void setIntValuesToGrid(int[] values) {

        for (int i = 0; i < rec.length; i++) {
            for (int j = 0; j < rec[i].length; j++) {
                if(values[i * COUNT_PIXEL + j] == 1){
                    rec[i][j].setFill(Color.RED);
                }
                else {
                    rec[i][j].setFill(Color.WHITE);
                }
            }
        }
    }
}
