package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.ListImagesController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static final String RESOURCES_PATH = new File("resources").getAbsolutePath();
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Char> chars = FXCollections.observableArrayList();

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("NeuralHopfield");

        initRootLayout();

        showListImages();

        primaryStage.getScene().getStylesheets().add("sample/style.css");

    }


    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            //FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource("sample.fxml"));

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showListImages() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("listimages.fxml"));
            AnchorPane listImage = (AnchorPane) loader.load();

            rootLayout.setCenter(listImage);

            ListImagesController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
