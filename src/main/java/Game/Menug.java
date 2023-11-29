package Game;

import com.app.dictionaryapp.DataAccessLayer.Txt;
import com.app.dictionaryapp.PresentationLayer.Presentation;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menug extends StackPane {
  private Rectangle bg = new Rectangle();

  private ObservableList<Node> menucontrol;
  private HBox rowmenu = new HBox(10);
  private int highestscore;
  private Text textscore;
  Button newgame = new Button("Start");
  private String line;
  Button exitgame = new Button("exit");
  BufferedReader reader = null;
  public boolean play = false;
  private Txt txt = new Txt("src/main/resources/Game/score.txt");
  public void switchtoMain(ActionEvent e) {

    Platform.runLater(new Runnable() {
      public void run() {
        try {
          Stage newstage = (Stage)((Node)e.getSource()).getScene().getWindow();
          new Presentation().start(newstage);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });
    //primaryScene = HelloApplication.helloScene;
  }


  public Menug(int score) {
    txt.connect();
    line = txt.read1();

    highestscore = Math.max(Integer.parseInt(line), score);
    txt.deleteAll();
    txt.write(String.valueOf(highestscore));

    textscore = new Text(String.valueOf(highestscore));
    menucontrol = rowmenu.getChildren();


    rowmenu.setAlignment(Pos.CENTER);
    rowmenu.getChildren().addAll(newgame, exitgame, textscore);
    //bg.setFill(def == "" ? Color.DARKSEAGREEN : Color.RED);
    bg.setHeight(50);
    bg.setWidth(300);

    bg.setFill(Color.DEEPSKYBLUE);
    bg.setStroke(Color.BLACK);

//        Image img = new Image(getClass().getResourceAsStream("cloud.png"));
//        bg.setFill(new ImagePattern(img));

    //text = new Text(String.valueOf(letter).toUpperCase());


    setAlignment(Pos.CENTER);
    getChildren().addAll(bg,  rowmenu);

    ImageView view = new ImageView(new Image(getClass().getResourceAsStream("play-button.png")));
    view.setFitHeight(25);
    view.setFitWidth(25);
    view.setPreserveRatio(true);
    newgame.setGraphic(view);
    newgame.setOnAction(event -> {
      play = true;
      System.out.println(play);
    });

    view = new ImageView(new Image(getClass().getResourceAsStream("logout.png")));
    view.setFitHeight(25);
    view.setFitWidth(25);
    view.setPreserveRatio(true);
    exitgame.setGraphic(view);
    exitgame.setOnAction(this::switchtoMain);
  }
  public boolean Getplay() {
    return play;
  }







}