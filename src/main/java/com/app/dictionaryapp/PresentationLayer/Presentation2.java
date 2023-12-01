package com.app.dictionaryapp.PresentationLayer;

import animatefx.animation.Bounce;
import com.app.dictionaryapp.BusinessLogicLayer.BusinessLogic;
import com.app.dictionaryapp.DataAccessLayer.Cache;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Presentation2 extends Application {
  private FXMLLoader fxmlLoader;
  private Scene scene;
  private BusinessLogic businessLogic;
  private static Stage primaryStage;
  private static Cache cache = new Cache();

  @Override
  public void start(Stage stage) throws IOException {
    // load file index.fxml
    fxmlLoader = new FXMLLoader(Presentation.class.getResource("index.fxml"));

    // init scene from fxml file
    scene = new Scene(fxmlLoader.load());

    // set title
    stage.setTitle("DictionaryApp");

    // set scene for stage
    stage.setScene(scene);

    // show
    stage.show();

    // ImageView to intro
    ImageView imageViewIntro = (ImageView) fxmlLoader.getNamespace().get("imageViewIntro");

    AnchorPane introPane = (AnchorPane) fxmlLoader.getNamespace().get("introPane");

    ImageView imageView = (ImageView) fxmlLoader.getNamespace().get("logoIntro");
    Bounce bounce = new Bounce(imageView);
    bounce.setCycleCount(1);
    bounce.play();

//        MediaView mediaView = (MediaView) fxmlLoader.getNamespace().get("mediaIntro");
//        Media media = new Media(new File("src/main/resources/com/app/dictionaryapp/PresentationLayer/Audio/MediaIntro.mp4").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaView.setMediaPlayer(mediaPlayer);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.setAutoPlay(true);

//        Media mediaAudio = new Media(new File("src/main/resources/com/app/dictionaryapp/PresentationLayer/Audio/Intro.mp3").toURI().toString());
//        MediaPlayer mediaPlayerAudio = new MediaPlayer(mediaAudio);
//        mediaPlayerAudio.play();

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
      introPane.setVisible(false);
////            mediaPlayer.pause();
////            mediaPlayerAudio.pause();
    }));
//
    timeline.play();
    primaryStage = stage;
  }

  public static Stage getStage() {
    return primaryStage;
  }

  public static void main(String[] args) {
    launch();
    cache.deleteAllDataInCache();
  }
}
