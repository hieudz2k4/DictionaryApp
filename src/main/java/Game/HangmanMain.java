package Game;
import com.app.dictionaryapp.BusinessLogicLayer.*;


import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.app.dictionaryapp.PresentationLayer.Presentation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
//import main.dictionary.DictionaryManagement;

public class HangmanMain extends Application {

  //private String word = "ANDEPTRAIVCL";

  private static final int APP_W = 1198;
  private static final int APP_H = 800;
  private static final Font DEFAULT_FONT = new Font("Courier", 36);
  private static final Font scorefont = new  Font("SansSerif", 24);
  public Scene primaryScene;

  /**
   * test color changing.
   */
  static Paint value0 = Paint.valueOf("#0000FF");
  static Paint value1 = Paint.valueOf("#008000");
  static Paint value2 = Paint.valueOf("#FA8072");



  private static final int POINTS_PER_LETTER = 100;
  private static final float BONUS_MODIFIER = 0.2f;

  /**
   * The word to guess
   */
  private SimpleStringProperty word = new SimpleStringProperty();


  /**
   * How many letters left to guess
   */
  private SimpleIntegerProperty lettersToGuess = new SimpleIntegerProperty();

  /**
   * Current score
   */
  private SimpleIntegerProperty score = new SimpleIntegerProperty();


  /**
   * Is game playable
   */
  private SimpleBooleanProperty playable = new SimpleBooleanProperty();

  /**
   * List for letters of the word {@link #word}
   * It is backed up by the HBox children list,
   * so changes to this list directly affect the GUI
   */
  private ObservableList<Node> letters;
  private ObservableList<Node> letterboxs;
  private ObservableList<Node> controlboxs;
  private ObservableList<Node> controlDef;
  private ObservableList<Node> controlDefscore;
  private ObservableList<Node> controlSubnSound;
  private ObservableList<Node> controlMenu;
  private final SearchLogic slg = new SearchLogic();
  private HashMap<Character, Text> alphabet = new HashMap<Character, Text>();
  private String word1 = "";
  // private HangmanImage hangman = new HangmanImage();


  private WordReader wordReader = new WordReader();
  boolean haveWrongAns = false;
  Button submit = new Button("Submit");
  Button buttontest = new Button();
  Button btnAgain = new Button("NEW WORD");
  Button tryAgain = new Button("Try Again");
  Canvas canvas = new Canvas(1100,800);
  AudioLogic al = AudioLogic.getInstance();
  Button soundipa = new Button();
  Menug mng = new Menug(0);
  private double humanpos = 390;
  int level  = 0;
  private int status;
  private long statusTime = 0;
  private boolean play = false ;
  boolean daadd = false;


  public Parent createContent() {
    Image img = new Image(getClass().getResourceAsStream("sky2.png"), 1300, 800, false, false);
    BackgroundImage bgImg = new BackgroundImage(img,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(1100, 800, false, false, true, false));
    Background bg = new Background(bgImg);


    HBox row_Sound_Submit = new HBox();
    controlSubnSound = row_Sound_Submit.getChildren();
    ImageView view = new ImageView(new Image(getClass().getResourceAsStream("Speaker.png")));
    view.setFitHeight(50);
    view.setFitWidth(50);
    view.setPreserveRatio(true);
    soundipa.setGraphic(view);
    view = new ImageView(new Image(getClass().getResourceAsStream("check.png")));
    view.setFitHeight(50);
    view.setFitWidth(50);
    view.setPreserveRatio(true);
    submit.setGraphic(view);
    controlSubnSound.addAll(btnAgain,submit, soundipa);


    /** score */
    Text textScore = new Text();
    textScore.textProperty().bind(score.asString().concat(" Points"));
    textScore.setFont(scorefont);
    defBox scoreletter = new defBox(textScore.getText(), Color.GREEN);
    VBox vbox1 = new VBox();
    vbox1.setAlignment(Pos.CENTER);
    vbox1.getChildren().add(scoreletter);
    controlDefscore = vbox1.getChildren();

    /** test show dap an truoc */
    HBox rowLetters = new HBox();
    rowLetters.setAlignment(Pos.CENTER);
    letters = rowLetters.getChildren();

    /** hang ra dinh nghia o vi tri giua */
    HBox rowDef = new HBox();
    rowDef.setAlignment(Pos.CENTER);
    controlDef = rowDef.getChildren();

    //playable.bind(hangman.lives.greaterThan(0).and(lettersToGuess.greaterThan(0)));
    playable.addListener((obs, old, newValue) -> {
      if (!newValue.booleanValue())
        stopGame();
    });

    /** button cho choi tu khoa moi */
    view = new ImageView(new Image(getClass().getResourceAsStream("rotate.png")));
    view.setFitHeight(50);
    view.setFitWidth(50);
    view.setPreserveRatio(true);
    btnAgain.setGraphic(view);
    //btnAgain.setPrefHeight(54);

    btnAgain.setOnAction(event -> {
      if(play) {
        startGame();
        letterboxs.get(0).requestFocus();
      }
    });

    /** button cho choi lai tu khoa truoc do */
    view = new ImageView(new Image(getClass().getResourceAsStream("undo.png")));
    view.setFitHeight(50);
    view.setFitWidth(50);
    view.setPreserveRatio(true);
    tryAgain.setGraphic(view);
    tryAgain.setOnAction(event -> {
      if(play) {letterboxs.get(0).requestFocus();
      }});


    /** back button */
    Button backButton = new Button("BACK");
    backButton.setOnAction(event -> {play = false;});
    backButton.setAlignment(Pos.BOTTOM_LEFT);
    view = new ImageView(new Image(getClass().getResourceAsStream("back2.png")));
    view.setFitHeight(50);
    view.setFitWidth(50);
    view.setPreserveRatio(true);
    backButton.setGraphic(view);
    HBox hboxlast = new HBox();
    controlboxs = hboxlast.getChildren();
    controlboxs.addAll(backButton, tryAgain);


    // layout
    HBox row1 = new HBox();
    row1.setAlignment(Pos.BOTTOM_CENTER);

    letterboxs = row1.getChildren();
    //controlboxs = rowControl.getChildren(); // controlbox thay doi se thay doi GUI cua rowControl

    canvas.setStyle("-fx-border-width: 0px");
    VBox vBox = new VBox(10);
    // vertical layout
    vBox.getChildren().addAll(
        row1,
        rowDef,
        rowLetters,

        row_Sound_Submit,
        hboxlast,
        vbox1,
        canvas);


    vBox.setBackground(bg);
    return vBox;
  }

  /** Func limit ky tu co the nhap vao */
  public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
        if (tf.getText().length() > maxLength) {
          String s = tf.getText().substring(0, maxLength);
          tf.setText(s);
        }
      }
    });
  }

  /** Func chuyen sang chuong trinh dich tu */
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

  private void stopGame() {
    for (Node n : letters) {
      Letter letter = (Letter) n;
      letter.show();
    }
  }

  private int  startGame() {
    System.out.println("da bat dau");// debug

    if(controlSubnSound.size() > 3) {
      controlSubnSound.remove(3);
    }
    /** hangman.reset(); */
    if(level == 0) {
      word.set("OOP");
    } else
      word.set(wordReader.getRandomWord().toUpperCase());
    word1 = word.get();
    System.out.println(word1);

    /** goi y 1 */
    soundipa.setOnAction(event -> {
      if(play)
        al.playAudio(word1, "UK");
      if(controlSubnSound.size() > 3) {
        controlSubnSound.remove(3);
      }
      controlSubnSound.add(new defBox(slg.getPronounciation(word1), Color.DARKSEAGREEN));
    });

    /** what is this */
    al.playAudio2(getClass().getResource("what-is-this.mp3").toString());


    /** Them def */
    controlDef.clear();
    controlDef.add(new defBox(slg.getDetail(word1), Color.DARKSEAGREEN));

    /** dap an tam thoi show */
    lettersToGuess.set(word.length().get());
    letters.clear();
    for (char c : word.get().toCharArray()) {
      Letter l = new Letter(c);
      if(level == 0) l.text.setVisible(true);
      letters.add(l);
    }

    /** viet lai sml con me no dcm cam vcl */
    letterboxs.clear();
    for(int i = 0; i < word1.length(); i++) {
      TextField tf = new TextField();
      tf.setStyle("-fx-text-fill: black; -fx-control-inner-background: #"+value0.toString().substring(2));
      tf.setFont(Font.font("Calibi", FontWeight.BOLD, 15));
      addTextLimiter(tf,1); // gioi han so luong ky tu nhap vao 1 o
      letterboxs.add(tf); // them o vao row
    }
    /** NHAP, XOA, CHU CAI */
    for (int i = 0; i < word1.length(); i++) {
      TextField tf = (TextField) letterboxs.get(i);
      int ind = i;
      if (0 < ind && ind < word1.length() - 1) {
        TextField tf0 = (TextField) letterboxs.get(ind - 1);
        TextField tf2 = (TextField) letterboxs.get(ind + 1);
        if(play) tf.setOnKeyPressed(event -> {
          if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            tf0.requestFocus();
          } else if (tf.getText().length() == 1 && event.getCode() != (KeyCode.ENTER)) {
            tf2.requestFocus();
          }
        });
      } else if (ind == 0) {
        TextField tf2 = (TextField) letterboxs.get(i + 1);
        if(play) tf.setOnKeyPressed(event -> {
          if (tf.getText().length() == 1 && event.getCode() != (KeyCode.ENTER)) {
            tf2.requestFocus();
          }
        });
      } else if (ind == word1.length() - 1) {
        TextField tf0 = (TextField) letterboxs.get(ind - 1);
        if(play) tf.setOnKeyPressed(event -> {
          if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            tf0.requestFocus();
          } else if(event.getCode().equals(KeyCode.ENTER)) {
            submit.requestFocus();

          }
        });
      }
    }
    /** THAY DOI MAU CUA O GO CHU, XU LY DUNG SAI */
    AtomicInteger check = new AtomicInteger(0);
    status = 0;
    if(play) submit.setOnKeyPressed(event -> {

          if(event.getCode().equals(KeyCode.ENTER)){
            int cntWrong = 0;
            for (int j = 0; j < word1.length(); j++) {
              TextField tfcolor = (TextField) letterboxs.get(j);
              char c = word1.charAt(j);
              String tmp1 = "" + c; tmp1 = tmp1.toUpperCase();
              //System.out.println(tmp1 + " " + tfcolor.getText());
              if (tfcolor.getText().toUpperCase().equals(tmp1)) {

                tfcolor.setStyle("-fx-control-inner-background: #"+value1.toString().substring(2));
                Letter lt = (Letter) letters.get(j);
                lt.show();
              } else {
                tfcolor.setStyle("-fx-control-inner-background: #"+value2.toString().substring(2));
                cntWrong ++;
                check.set(1);
                status = 1;
                statusTime = System.nanoTime();
              }
            }
            //controlboxs.clear();
            if (cntWrong != 0) {
              //controlboxs.add(tryAgain);
              tryAgain.requestFocus();
              //startNanoTime.set(System.nanoTime());
              statusTime = System.nanoTime();
            } else {
              //controlboxs.addAll(btnAgain);
              btnAgain.requestFocus();
              check.set(2);
              status = 2;
              statusTime = System.nanoTime();
              //startNanoTime.set(System.nanoTime());
            }
          }

        }

    );


    /** ket thuc ve canvas */

    return check.get();

  }


  private static class Letter extends StackPane {
    private Rectangle bg = new Rectangle(40, 60);
    public Text text;
    public Color lettercolor = Color.SLATEBLUE;

    public Letter(char letter) {
      bg.setFill(letter == ' ' ? Color.DARKSEAGREEN : lettercolor);
      bg.setStroke(Color.PINK);

      text = new Text(String.valueOf(letter).toUpperCase());
      text.setFont(DEFAULT_FONT);
      text.setVisible(false);

      setAlignment(Pos.CENTER);
      getChildren().addAll(bg, text);
    }

    public void show() {
      RotateTransition rt = new RotateTransition(Duration.seconds(1), bg);
      rt.setAxis(Rotate.Y_AXIS);
      rt.setToAngle(180);
      rt.setOnFinished(event -> text.setVisible(true));
      rt.play();
    }

    public boolean isEqualTo(char other) {
      return text.getText().equals(String.valueOf(other).toUpperCase());
    }
  }

  private static class defBox extends StackPane {
    private Rectangle bg = new Rectangle();
    private Text text = new Text();
    public Color c = Color.DARKSEAGREEN;

    public void setText(Text text) {
      this.text = text;
    }
    public defBox(String def, Color c) {
      text.setText(def);
      System.out.println(def);
      //bg.setFill(def == "" ? Color.DARKSEAGREEN : Color.RED);
      bg.setHeight(50);
      bg.setWidth(12 * def.length());

      bg.setFill(c);
      bg.setStroke(Color.PINK);

      //text = new Text(String.valueOf(letter).toUpperCase());
      text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
      text.setVisible(true);

      setAlignment(Pos.CENTER);
      getChildren().addAll(bg, text);
    }

    public void show() {
      RotateTransition rt = new RotateTransition(Duration.seconds(1), bg);
      rt.setAxis(Rotate.Y_AXIS);
      rt.setToAngle(180);
      rt.setOnFinished(event -> text.setVisible(true));
      rt.play();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryScene = new Scene(createContent());
    primaryScene.setRoot(createContent());
    primaryScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
    primaryScene.setOnKeyPressed  ((KeyEvent event) -> {
      if (event.getText().isEmpty())
        return;

      char pressed = event.getText().toUpperCase().charAt(0);
      if ((pressed < 'A' || pressed > 'Z') && pressed != '-')
        return;


    });
    //startGame();
    primaryStage.setResizable(false);
    primaryStage.setWidth(APP_W);
    primaryStage.setHeight(APP_H);
    primaryStage.setTitle("Hangman");
    primaryStage.setScene(primaryScene);
    primaryStage.show();
    startGame();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setStroke(Color.RED);
    Image wave = new Image(getClass().getResourceAsStream("sea.png"));
    Image vebo = new Image(getClass().getResourceAsStream("peterandship1.png"));

    AnimatedImage ufo = new AnimatedImage();
    Image[] imageArray = new Image[4];
    for (int i = 0; i < 4; i++)
      imageArray[i] = new Image(getClass().getResourceAsStream("swimming" + (i + 2) + ".png"));
    ufo.frames = imageArray;
    ufo.duration = 0.100;
    //final long[] startNanoTime = {0};

    Shark shark = new Shark();

    new AnimationTimer()
    {

      double shipx = 100;
      double x = 0;
      double  ypos = 190;
      boolean lowestship = true;

      public void handle(long currentNanoTime)
      {
        //int check = startGame();
        double t = (currentNanoTime - statusTime) / 1000000000.0;
        //System.out.println(status);
        if (play) { // neu dang choi
          if(status == 0) { // dang cho tra loi
            x -= 0.75;
            humanpos -= 0.1;
          } else {

            if(t >0 && t <= 1) {
              //System.out.println(t);
              if(status == 1) { // tra loi sai
                x -= 4;
                humanpos -= 4;
              }
              if(status == 2) { // tra loi dung
                humanpos += 1.5;
                x -= 0.5;
                score.set(score.get() + 10);
                controlDefscore.remove(0);
                Text textScore = new Text();
                textScore.textProperty().bind(score.asString().concat(" Points"));
                textScore.setFont(scorefont);
                controlDefscore.add(new defBox(textScore.getText(), Color.GREEN));

                //x -= 0.5;
              }
            } if (t > 1) {
              //check.set(0);
              status = 0;
            }
          }
          if (humanpos < 50) {
            play = false;

          }
        } else {

          // neu khong choi
          level = 0;
          if (!daadd) {

            controlDefscore.remove(0);
            Menug mn = new Menug(score.get());
            mng = mn;
            controlDefscore.add(mn);
            mn.newgame.requestFocus();
            humanpos = 390;
            daadd = true;
            //play = mn.Getplay()play;
          } else {
            play = mng.Getplay();
            if(play) {
              controlDefscore.remove(0);
              level = 1;
              startGame();
              daadd = false;
              score.set(0);
              Text textScore = new Text();
              textScore.textProperty().bind(score.asString().concat(" Points"));
              textScore.setFont(scorefont);
              controlDefscore.add(new defBox(textScore.getText(), Color.GREEN));
            }
          }
        }

        //System.out.println(play);
        gc.drawImage( wave, x, 30);
        gc.drawImage(wave, 1100 + x, 30);

        gc.drawImage(vebo, 800,  shipx);
        if (lowestship) {
          shipx -= 0.05;
        }
        if(!lowestship) {
          shipx += 0.05;
        }
        if(shipx <= 95) {
          lowestship = false;
        }
        if(shipx >= 105) {
          lowestship = true;
        }

        if (x <= -1100) {
          x = 0;
        }
        //ypos -= 0.5;

        gc.drawImage( ufo.getFrame(2 + t % 4), humanpos, ypos );
        gc.drawImage(shark.sharkframe.getFrame(t % 6 + 1), shark.posx, shark.posy);


      }
    }.start();
    /** ket thuc ve canvas */


  }
}