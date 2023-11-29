package Game;

import java.io.*;
import java.util.ArrayList;

public class WordReader {
  private static final String fileName = "src/main/java/game/words.txt";

  private ArrayList<String> words = new ArrayList<String>();

  public WordReader() {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
      String line;
      while ((line = reader.readLine()) != null) {
        words.add(line);
      }
    }
    catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }

  public String getRandomWord() {
    if (words.isEmpty()) return "NO-DATA";
    return words.get((int)(Math.random()*words.size()));
  }
}