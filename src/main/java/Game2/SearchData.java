package Game2;

import com.app.dictionaryapp.BusinessLogicLayer.Trie;
import com.app.dictionaryapp.DataAccessLayer.Cache;
import com.app.dictionaryapp.DataAccessLayer.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchData {
  private Database database = new Database("jdbc:mysql://localhost:3306/dictionarydatabase", "root", "Khongco2004@");
  //private final Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
  private final Cache cache = new Cache();

  public String getQuestion(int id) {
    ResultSet resultSet = database.queryGetData("select question from game2 where id = '" + id + "'");

    try {
      if (resultSet.next()) {
        return resultSet.getString("question");
      } else {
        return "";
      }
    } catch (Exception e) {
      return "";
    }
  }

  public String getAns(int id, int number) {
    ResultSet resultSet = database.queryGetData("select ans" + number +" from game2 where id = '" + id + "'");

    try {
      if (resultSet.next()) {
        return resultSet.getString("ans" + number);
      } else {
        return "";
      }
    } catch (Exception e) {
      return "";
    }

  }

  public int getRealAnswer(int id) {
    ResultSet resultSet = database.queryGetData("select rans from game2 where id = '" + id + "'");

    try {
      if (resultSet.next()) {
        return resultSet.getInt("rans");
      } else {
        return 0;
      }
    } catch (Exception e) {
      return 0;
    }
  }


  public String getPronounceFromCache(String text) {
    return cache.getDataFromCache(text).getFirst();
  }

  String processHtml(String text) {
    Document document = Jsoup.parse(text);

    Elements h1 = document.select("h1");
    Elements h3 = document.select("h3");

    h1.remove();
    h3.remove();


    return document.toString();
  }
}