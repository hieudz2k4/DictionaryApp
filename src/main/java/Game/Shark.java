package Game;

import javafx.scene.image.Image;

public class Shark {
  public Image image;
  public double posx;
  public AnimatedImage sharkframe;
  public double posy;
  public Shark() {

    posx = 20;
    posy = 240;
    sharkframe = new AnimatedImage();
    Image[] imageArray = new Image[6];
    for (int i = 0; i < 6; i++)
    {
      imageArray[i] = new Image(getClass().getResourceAsStream("s" + (i + 1) + ".png"), 170, 132, false, false);
    }
    sharkframe.frames = imageArray;
    sharkframe.duration = 0.100;
  }
  public void move() {

  }
}