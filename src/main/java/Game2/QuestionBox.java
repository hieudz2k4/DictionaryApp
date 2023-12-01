package Game2;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class QuestionBox extends StackPane {
  private Rectangle bg = new Rectangle();
  private Text text = new Text() ;
  public Color c = Color.DARKSEAGREEN;


  public QuestionBox(String def, Color c) {
    text.setText(def);
    System.out.println(def);
    //bg.setFill(def == "" ? Color.DARKSEAGREEN : Color.RED);
    bg.setHeight(50);
    bg.setWidth(7 * def.length());

    bg.setFill(c);
    bg.setStroke(Color.PINK);

    //text = new Text(String.valueOf(letter).toUpperCase());
    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
    text.setVisible(true);

    setAlignment(Pos.CENTER);
    getChildren().addAll(bg, text);
  }

}