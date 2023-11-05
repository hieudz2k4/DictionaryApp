package com.app.dictionaryapp.BusinessLogicLayer;

import com.app.dictionaryapp.DataAccessLayer.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class SuggestionWordLogic {
    private Database database = new Database("jdbc:mysql://localhost:3306/DictionaryDatabase", "root", "Khongco2004@");
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public ObservableList<String> getObservableList(String text) {
        database.connectToDatabase();
        ResultSet resultSet = database.queryGetData("select word from av where word like '" + text + "%'");
        Data data = new Data();
        try {
            while (resultSet.next()) {
                String word = resultSet.getString("word");
                //data.setWord(word);
                observableList.add(word);
            }
            return observableList;
        } catch (Exception e) {
            System.out.println("Loi SuggestionWordLogic -> getObservableList");
            e.printStackTrace();
            return null;
        }
    }
}
