package com.app.dictionaryapp.BusinessLogicLayer;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.nio.file.Files;
import javafx.util.Duration;

public class AudioLogic {
    private final APITextToSpeech apiTextToSpeech = new APITextToSpeech("https://text-to-speech27.p.rapidapi.com/speech?text=",
                                                                        "fc10970bb6msh2aad50d7bfa8cdap1b6d23jsna6a14e4b2126");

    private MediaPlayer mediaplayer;
    private static final AudioLogic instance = new AudioLogic();
    private AudioLogic() {

    }

    public String setTextFollowFormatRapidApi(String text) {
        return text.replace(" ", "%20");
    }


    /**
     * play audio
     */
    public void playAudio(String text, String lang) {
        text = setTextFollowFormatRapidApi(text);
        byte[] bytes = apiTextToSpeech.textToSpeech(text, lang);
        try {
            File file = File.createTempFile("temp", ".mp3");
            Files.write(file.toPath(), bytes);

            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

        } catch (Exception e) {
            System.out.println("Loi AudioLogic.playAudio(): ");
            e.printStackTrace();
        }
    }

    /**
     * pause audio
     */
    public void pauseAudio() {

    }

    public static AudioLogic getInstance() {
        return instance;
    }

    public void playAudio2(String text) {
        Media media = new Media(text);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void playSoundtrack(String text) {
        Media media = new Media(text);
        mediaplayer = new MediaPlayer(media);
        mediaplayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaplayer.seek(Duration.ZERO);
            }
        });
        mediaplayer.play();

    }
}
