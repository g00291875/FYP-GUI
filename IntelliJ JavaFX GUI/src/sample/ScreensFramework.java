package sample;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ScreensController;


public class ScreensFramework extends Application {
    
    public static String screen1ID = "main";
    public static String screen1File = "Screen1.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "Screen2a.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.screen1ID, ScreensFramework.screen1File);
        mainContainer.loadScreen(ScreensFramework.screen2ID, ScreensFramework.screen2File);
        mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
        
        mainContainer.setScreen(ScreensFramework.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//        String image = ScreensFramework.class.getResource("background_image.jpg").toExternalForm();
//        root.setStyle("-fx-background-image: url('" + image + "'); " +
//                "-fx-background-position: center center; " +
//                "-fx-background-repeat: stretch;");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
 //   public static void main(String[] args) {
  //      launch(args);
 //   }
}