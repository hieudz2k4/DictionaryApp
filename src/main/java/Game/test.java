package Game;





import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class test extends Application {

  @Override
  public void start(Stage stage) {

    Image image = new Image(getClass().getResourceAsStream("peterandship1.png"));
    // Setting the image view
    ImageView imageView = new ImageView(image);
    // Setting the position of the image
    imageView.setX(0);
    imageView.setY(0);
    // setting the fit height and width of the image view
    imageView.setFitHeight(200);
    imageView.setFitWidth(400);
    // Setting the preserve ratio of the image view
    imageView.setPreserveRatio(true);



    // Creating a Group object
    Group root = new Group(imageView);

    // Creating a scene object
    Scene scene = new Scene(root, 600, 300);
    // Setting title to the Stage
    stage.setTitle("Coloradjust effect example");
    // Adding scene to the stage
    stage.setScene(scene);

    // Displaying the contents of the stage
    stage.show();
  }

  public static void main(String args[]) {
    launch(args);
  }
}

