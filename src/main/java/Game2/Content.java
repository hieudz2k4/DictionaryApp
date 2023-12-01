package Game2;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Content extends AnchorPane {
  Button Answer1 = new Button("ans1");
  Button Answer2 = new Button("ans2");
  Button Answer3 = new Button("ans3");
  Button Answer4 = new Button("ans4");
  Button Question = new Button("question");
  SearchData sd = new SearchData();

  int status = 0;
  public Content() {
    this.setWidth(1200.0);
    this.setHeight(800);
    int idQuestion = (int)(Math.random()*20 + 1);
    //int idQuestion = 6;
    int ans = sd.getRealAnswer(idQuestion);
    //qb.setText(sd.getQuestion(idQuestion));
    String qs = sd.getQuestion(idQuestion);
    QuestionBox qb = new QuestionBox(qs, Color.DARKSEAGREEN);
    Answer1.setText(sd.getAns(idQuestion, 1));
    System.out.println(sd.getRealAnswer(idQuestion));
    Answer2.setText(sd.getAns(idQuestion, 2));
    Answer3.setText(sd.getAns(idQuestion, 3));
    Answer4.setText(sd.getAns(idQuestion, 4));

    Answer1.setOnAction(event -> {
      if(ans == 1) {
        status = 1;
        // sua mau
        String newColor = "-fx-background-color: green;";
        Answer1.setStyle(newColor);
      } else {
        status = 2;
        // sua mau
        String newColor = "-fx-background-color: red;";
        Answer1.setStyle(newColor);
      }
      System.out.println(status);
    });

    Answer2.setOnAction(event -> {
      if(ans == 2) {
        status = 1;
        // sua mau
        String newColor = "-fx-background-color: green;";
        Answer2.setStyle(newColor);
      } else {
        status = 2;
        // sua mau
        String newColor = "-fx-background-color: red;";
        Answer2.setStyle(newColor);
      }
      System.out.println(status);
    });

    Answer3.setOnAction(event -> {
      if(ans == 3) {
        status = 1;
        // sua mau
        String newColor = "-fx-background-color: green;";
        Answer3.setStyle(newColor);
      } else {
        status = 2;
        // sua mau
        String newColor = "-fx-background-color: red;";
        Answer3.setStyle(newColor);
      }
      System.out.println(status);
    });
    Answer4.setOnAction(event -> {
      if(ans == 4) {
        status = 1;
        // sua mau
        String newColor = "-fx-background-color: green;";
        Answer4.setStyle(newColor);
      } else {
        status = 2;
        // sua mau
        String newColor = "-fx-background-color: red;";
        Answer4.setStyle(newColor);
      }
      System.out.println(status);
    });
    AnchorPane.setTopAnchor(Answer1, 200.0);
    AnchorPane.setLeftAnchor(Answer1, 600.0 - 3.5 * qs.length()-20);
    // AnchorPane.setRightAnchor(Answer1, 70.0);

    // (B2) Neo vào Top + Left + Right
    AnchorPane.setTopAnchor(Answer2, 200.0);
    AnchorPane.setLeftAnchor(Answer2,  (600.0 + 3.5 * qs.length()  - 20));
    //AnchorPane.setRightAnchor(Answer2, 320.0);


    // (B3) Neo vào Top + Left + Right
    AnchorPane.setTopAnchor(Answer3, 400.0);
    AnchorPane.setLeftAnchor(Answer3, 600.0 - 3.5 * qs.length()-20);
    //AnchorPane.setRightAnchor(Answer3, 20.0);

    // (B4) Neo vào 4 cạnh của AnchorPane
    AnchorPane.setTopAnchor(Answer4, 400.0);
    AnchorPane.setLeftAnchor(Answer4,  (600.0 + 3.5 * qs.length()  - 20));
//        AnchorPane.setRightAnchor(Answer4, 50.0);
//        AnchorPane.setBottomAnchor(Answer4, 45.0);

    AnchorPane.setLeftAnchor(qb, 600.0 - 3.5 * qs.length() );
    AnchorPane.setTopAnchor(qb, 100.0);

    Answer1.setPrefHeight(50);
    Answer2.setPrefHeight(50);
    Answer3.setPrefHeight(50);
    Answer4.setPrefHeight(50);
    Question.setPrefHeight(50);

    //Answer1.setPrefWidth(200);
//        Answer2.setPrefWidth(100);
//        Answer3.setPrefWidth(100);
//        Answer4.setPrefWidth(100);
    //Question.setPrefWidth();

    this.getChildren().addAll(qb, Answer1,Answer2, Answer3, Answer4);
  }
  public int getStatus() {
    return status;
  }
}