package sample;
import java.sql.Connection;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.AzureDB;
import model.DB;
import model.Model;

//keep pane so we can remove add screens on top/bottom
public class ScreensController  extends StackPane {
    //Holds the screens to be displaye
    private String test = "test1";

    private HashMap<String, Node> screens = new HashMap<>();
  //  DB db;
    Connection conn;
  //  Model model;
                //id of screen, represents root of the screen graph for that scene
    public ScreensController() {
        super();//inherit StackPane class
//         db = new DB();
//         conn=db.dbConnect(
//                 "jdbc:mysql://localhost:3306/localsong","root","root");
   //     model = new Model();
    }



    /********************MODEL RETURN METHODS***************************************/
    public Void mediaViewGetMediaPlayerPause(){
     //   mediaView.getMediaPlayer().pause();
     //   playButton.setText("Play");
        return null;
    }
    /********************MODEL RETURN METHODS***************************************/

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {                            //fxml file
            AzureDB db = new AzureDB();
            Model mainmodel = new Model();
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
           // System.out.println(resource);
            Parent loadScreen = (Parent) myLoader.load();//class cast to controlled screen

            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());//Returns the controller associated with the root object.
            //inject screen controllers to myscreencontroller
            myScreenControler.setScreenParent(this,mainmodel,db);// inject screen controllers to each screen here

           // InterfaceModel myModel = ((InterfaceModel) myLoader.getController());
           // myModel.setModel(this.model);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreenV2(final String name, Boolean bool) {
        return true;
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String name) {
        //do we have a screen loaded?
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty())// getChildren returns a modifiable list of children
            {    //if there is more than one screen
                Timeline fade = new Timeline
                        (
                            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                            new KeyFrame
                                (
                                        new Duration(100),// once fade out is finished, call eventhandler
                                    new EventHandler<ActionEvent>()
                                    {
                                        @Override
                                        public void handle(ActionEvent t)
                                        {
                                             getChildren().remove(0);                    //remove the displayed screen
                                            getChildren().add(0, screens.get(name));     //add the screen, returns node
                                            Timeline fadeIn = new Timeline(
                                            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                            new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
                                            fadeIn.play();
                                        }
                                    }, new KeyValue(opacity, 0.0)
                                )//end new keyframe
                        );//end time line fade
                fade.play();
            } //end if (!getChildren().isEmpty())
           else {
                setOpacity(0.0);   // getting tree structure of screen given by name
                            //then adding it to the scene graph
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

      public String getTest() {
    return test;
}

    public void setTest(String test) {
    this.test = test;
}

    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}

