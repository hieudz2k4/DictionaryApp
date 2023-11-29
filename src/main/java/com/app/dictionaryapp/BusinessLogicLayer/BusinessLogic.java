package com.app.dictionaryapp.BusinessLogicLayer;

import Game.HangmanMain;
import animatefx.animation.*;
import com.app.dictionaryapp.PresentationLayer.Presentation;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.jfoenix.controls.JFXToggleButton;
import com.app.dictionaryapp.DataAccessLayer.Object;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet.Mode;

public class BusinessLogic {

    // Pane
    @FXML
    private AnchorPane introPane;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane displayWordSound;
    @FXML
    private AnchorPane textTranslation;
    @FXML
    private AnchorPane editPane;
    @FXML
    private AnchorPane settingPane;
    @FXML
    private AnchorPane miniSettingPane;
    @FXML
    private ScrollPane displaySuggest;

    @FXML
    private AnchorPane gameAnchorPane;


    // Button
    @FXML
    private Button btnDaily;

    @FXML
    private Button btnFavorites;

    @FXML
    private Button btnGames;

    @FXML
    private Button btnRecent;

    @FXML
    private Button btnSearch;


    // vbox
    @FXML
    private VBox dictionaryDisplay;

    // text field
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private TextField wordEdit;

    @FXML
    private TextArea inputTextTranslation;
    @FXML
    private TextArea descriptionEdit;

    @FXML
    private TextArea outputTextTranslation;

    // image
    @FXML
    private ImageView btnSetting;
    @FXML
    private ImageView btnStarToMark;
    @FXML
    private ImageView btnStarToUnMark;
    @FXML
    private ImageView download;
    @FXML
    private ImageView downloadClick;

    @FXML
    private TableView<String> suggestionWordTableView;

    @FXML
    private TableColumn<String, String> suggestionWordCol;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;

    //label
    @FXML
    private Label word;
    @FXML
    private Label pronunciation;

    @FXML
    private Button btnTextTranslation;
    @FXML
    private JFXToggleButton btnLang;
    @FXML
    private JFXToggleButton btnThemes;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnTextTranslate;

    @FXML
    private ImageView btnUS;
    @FXML
    private ImageView btnUK;
    @FXML
    private ImageView btnTranslate;
    @FXML
    private ImageView WhiteThemes1;
    @FXML
    private ImageView WhiteThemes2;

    @FXML
    private Label labelThemes;
    @FXML
    private Label labelLang;
    @FXML
    private Label labelEdit;
    @FXML
    private Label labelWord;
    @FXML
    private Label labelDetail;
    @FXML
    private Label labelTextTranslation;

    // recent logic
    private final RecentLogic recentLogic = RecentLogic.getInstance();

    // favorites logic
    private final FavoritesLogic favoritesLogic = FavoritesLogic.getInstance();

    // audio logic
    private final AudioLogic audioLogic = AudioLogic.getInstance();

    // search logic
    private final SearchLogic searchLogic = new SearchLogic();

    // edit logic
    private final EditLogic editLogic = EditLogic.getInstance();

    //SuggestionWord
    private final SuggestionWordLogic suggestionWordLogic = new SuggestionWordLogic();

    // TextTranslate
    private final APITextTranslate apiTextTranslate = new APITextTranslate("https://text-translator2.p.rapidapi.com/translate"
                                                                            , "6f22e6bbf4mshc8e808871b5b310p1c99cajsnda97e360477f");


    private final Object object = new Object();

    // Enum
    private enum MODE {
        SEARCH,
        RECENT,
        GAMES,
        TEXTTRANSLATION,
        FAVOURITES,
        EDIT
    }


    // mode
    private MODE mode = MODE.SEARCH;

    // Search
    @FXML
    void btnSearchAction(ActionEvent event) {
        if (mode == MODE.SEARCH) {
            try {
                searchLogic();
            } catch (Exception e) {
                return;
            }
        }
    }

    // key event text
    @FXML
    void textKeyEvent(KeyEvent event) throws SQLException {
        download.setVisible(true);
        downloadClick.setVisible(false);

        if (mode.equals(MODE.SEARCH)) {
            // User click enter.
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    searchLogic();
                } catch (Exception e) {
                    return;
                }
            } else if (event.getText().length() != 0) {
                // set visible
                suggestionWordTableView.setVisible(true);

                // change tablecol name
                if (btnLang.isSelected()) {
                    suggestionWordCol.setText("Từ gợi ý");
                }
                else {
                    suggestionWordCol.setText("Suggestion Word");
                }

                // clear table view
                suggestionWordTableView.getItems().clear();

                // remove horizontal scroll
                suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // get text
                String text = upperCaseFirstLetter(
                    (txtFieldSearch.getText() + event.getText()).toLowerCase());

                // get observable list
                ObservableList<String> observableList = suggestionWordLogic.autoSuggestionUsingTrie(
                    text);

                // set item for tableview
                suggestionWordTableView.setItems(observableList);
                suggestionWordCol.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue()));
            }
        }
    }

    @FXML
    void clickSuggestionWordTable(MouseEvent event) {
        String text = suggestionWordTableView.getSelectionModel().getSelectedItem();
        txtFieldSearch.setText(text);
    }

    // Text Translation Button
    @FXML
    void textTranslationAction(ActionEvent event) {
        mode = MODE.TEXTTRANSLATION;

        textTranslation.setVisible(true);

        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);
        txtFieldSearch.setText("");

        // set editPane, suggestionWordTableView, displayWordSound, webView visible false
        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, editPane, suggestionWordTableView, displayWordSound, webView, gameAnchorPane);
        setVisibleFalse(nodes);
    }

    @FXML
    void translateTextTranslation(ActionEvent event) {
        String text = inputTextTranslation.getText();
        outputTextTranslation.setText(apiTextTranslate.translate(text));
    }

    @FXML
    void close(MouseEvent event) {
        mode = MODE.SEARCH;

        // set visible false
        textTranslation.setVisible(false);
        editPane.setVisible(false);
        settingPane.setVisible(false);
        gameAnchorPane.setVisible(false);

        // set editable
        txtFieldSearch.setEditable(true);
    }

    // Favorites Button
    @FXML
    void favoritesAction(ActionEvent event) {
        mode = MODE.SEARCH;

        String title;

        if (btnLang.isSelected()) {
            title = "Yêu thích";
        } else {
            title = "Favourite Word";
        }

        updateTableView(favoritesLogic.getContentInFavourite(), title);

        // set editable
        txtFieldSearch.setEditable(true);

        // set displayWordSound, webView, textTranslation, editPane visible false
        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, displayWordSound, webView, textTranslation, editPane, gameAnchorPane);
        setVisibleFalse(nodes);
    }

    // Games Button
    @FXML
    void gamesAction(ActionEvent e) {
        mode = MODE.GAMES;
        gameAnchorPane.setVisible(true);

        List<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, textTranslation, editPane, settingPane, suggestionWordTableView, displayWordSound,
            webView);
        setVisibleFalse(nodeList);
    }

    @FXML
    void clickGame1(ActionEvent event) {

    }

    @FXML
    void clickGame2(ActionEvent event) {

    }

    // Recent.txt Button
    @FXML
    void recentAction(ActionEvent event) {
        mode = MODE.SEARCH;
        String title;

        if (btnLang.isSelected()) {
            title = "Gần đây";
        } else {
            title = "Recent Word";
        }
        updateTableView(recentLogic.getContentRecent(), title);

        // set txtFieldSearch
        txtFieldSearch.setText("");
        txtFieldSearch.setEditable(true);

        // set displayWordSound, webView, textTranslation, editPane visible false
        List<Node> nodes = new ArrayList<>();
        Collections.addAll(nodes, displayWordSound, webView, textTranslation, editPane, gameAnchorPane);
        setVisibleFalse(nodes);
    }

    // Setting Button
    @FXML
    void moveSettingBtn(MouseEvent mouseEvent) {
        miniSettingPane.setVisible(true);
    }
    @FXML
    void exitSettingBtn(MouseEvent mouseEvent) {
        miniSettingPane.setVisible(false);
    }

    @FXML
    void clickSettingBtn(MouseEvent mouseEvent) {
        settingPane.setVisible(true);
    }

    @FXML
    void changeTranslationSetting(MouseEvent event) {
        if (btnLang.isSelected()) {
            btnRecent.setText("Gần đây");
            btnGames.setText("Trò chơi");
            btnTextTranslation.setText("Văn bản");
            btnFavorites.setText("Yêu thích");
            btnEdit.setText("Chỉnh sửa");
            txtFieldSearch.setPromptText("Tìm kiếm");
            btnUS.setAccessibleText("Anh Mỹ");
            btnUK.setAccessibleText("Anh Anh");
            btnTextTranslation.setText("Dịch");
            suggestionWordCol.setText("Từ gợi ý");
            labelLang.setText("Ngôn ngữ");
            btnLang.setText("Tiếng Anh");
            labelThemes.setText("Chế độ màn hình");
            btnThemes.setText("Tối");
            btnTextTranslate.setText("Dịch");
            labelTextTranslation.setText("Dịch văn bản");
            inputTextTranslation.setPromptText("Đoạn văn bản cần dịch");
            outputTextTranslation.setPromptText("Đoạn văn bản đã dịch");
            labelEdit.setText("Chỉnh sửa từ điển");
        } else if(!btnLang.isSelected()) {
            btnRecent.setText("Recent");
            btnGames.setText("Game");
            btnTextTranslation.setText("Text Translation");
            btnFavorites.setText("Favourite");
            btnEdit.setText("Edit");
            txtFieldSearch.setPromptText("Search");
            btnUS.setAccessibleText("US");
            btnUK.setAccessibleText("UK");
            btnTextTranslation.setText("Translate");
            suggestionWordCol.setText("Suggestion Word");
            labelLang.setText("Language");
            btnLang.setText("Vietnamese");
            labelThemes.setText("Themes");
            btnThemes.setText("Dark");
            btnTextTranslation.setText("Translate");
            labelTextTranslation.setText("Text Translation");
            inputTextTranslation.setPromptText("Input");
            outputTextTranslation.setPromptText("Output");
            labelEdit.setText("Edit Dictionary");
        }

        if(btnThemes.isSelected())
        {
            mainPane.getStylesheets().remove(object.getDarkMode());
            mainPane.getStylesheets().add(object.getLightMode());
            webView.getEngine().setUserStyleSheetLocation(object.getLightModeWebView());
        } else {
            mainPane.getStylesheets().remove(object.getLightMode());
            mainPane.getStylesheets().add(object.getDarkMode());
            webView.getEngine().setUserStyleSheetLocation(object.getDarkModeWebView());
        }
    }

    // Edit Button
    @FXML
    void btnEditAction(ActionEvent event) {
        mode = MODE.EDIT;

        editPane.setVisible(true);

        // set edit txtFieldSearch
        txtFieldSearch.setEditable(false);
        txtFieldSearch.setText("");

        List<Node> nodeList = new ArrayList<>();
        Collections.addAll(nodeList, textTranslation, suggestionWordTableView, displayWordSound,
            webView, gameAnchorPane);
        setVisibleFalse(nodeList);

    }

    @FXML
    void addBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
            descriptionEdit.getText().length() == 0) {
            showAlert("Error add new word", "", AlertType.ERROR);
        } else {
            if (editLogic.insert(upperCaseFirstLetter(wordEdit.getText().toLowerCase())
                , upperCaseFirstLetter(descriptionEdit.getText()))) {
                showAlert("Add new word successfully", "", AlertType.INFORMATION);
            } else {
                showAlert("Error add new word", "Word Had In Your Dictionary", AlertType.ERROR);
            }
        }
    }

    @FXML
    void updateBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0 ||
            descriptionEdit.getText().length() == 0) {
            showAlert("Error update new word", "Word and description is required", AlertType.ERROR);
        } else {
            editLogic.update(upperCaseFirstLetter(wordEdit.getText().toLowerCase()),
                upperCaseFirstLetter(descriptionEdit.getText().toLowerCase()));
            showAlert("Update new word successfully", "", AlertType.INFORMATION);

        }
    }

    @FXML
    void deleteBtnEdit(ActionEvent event) {
        if (wordEdit.getText().length() == 0) {
            showAlert("Error delete word", "Word is required", AlertType.ERROR);
        } else {
            if (editLogic.delete(upperCaseFirstLetter(wordEdit.getText().toLowerCase()))) {
                showAlert("Delete word successfully", "", AlertType.INFORMATION);
            } else {
                showAlert("Error delete word", "No Word In Your Dictionary", AlertType.ERROR);
            }
        }
    }

    // Sound Button
    @FXML
    void clickBtnUK(MouseEvent event) {
        audioLogic.playAudio(word.getText(), "UK");
    }

    @FXML
    void clickBtnUS(MouseEvent event) {
        audioLogic.playAudio(word.getText(), "US");
    }

    @FXML
    void soundInTextTranslation(MouseEvent event) {
        audioLogic.playAudio(inputTextTranslation.getText(), "UK");
    }

    // Star Button
    @FXML
    void clickStarToMark(MouseEvent event) {
        btnStarToMark.setVisible(false);
        btnStarToUnMark.setVisible(true);

        favoritesLogic.addTextToFavorite(upperCaseFirstLetter(txtFieldSearch.getText()));

    }

    @FXML
    void clickStarToUnMark(MouseEvent event) {
        btnStarToUnMark.setVisible(false);
        btnStarToMark.setVisible(true);

        favoritesLogic.deleteTextInFavorite(upperCaseFirstLetter(txtFieldSearch.getText()));
    }

    // Download Button

    @FXML
    void clickDownload(MouseEvent event) throws IOException {
        download.setVisible(false);
        downloadClick.setVisible(true);

        String text = word.getText() + "\n"
            + pronunciation.getText() + "\n"
            + searchLogic.getDescription(txtFieldSearch.getText());


        System.out.println(text);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File To Download");
        File file = fileChooser.showSaveDialog(Presentation.getStage());

        if (file != null) {
            saveSystem(file, text);
        }
    }

    void loadCssForWebView() {
        webEngine = webView.getEngine();
        if (btnThemes.isSelected()) {
            webEngine.setUserStyleSheetLocation(object.getLightModeWebView());
        } else {
            webEngine.setUserStyleSheetLocation(object.getDarkModeWebView());
        }
    }


    String upperCaseFirstLetter(String text) {
        String firstText = text.substring(0, 1).toUpperCase();
        String remainText = text.substring(1);

        return firstText + remainText;
    }


    void updateTableView(ObservableList<String> observableList, String title) {
        suggestionWordTableView.getItems().clear();
        suggestionWordTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        suggestionWordTableView.setItems(observableList);
        suggestionWordCol.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue()));
        suggestionWordCol.setText(title);
        suggestionWordTableView.setVisible(true);
    }

    void setVisibleFalse(List<Node> listNode) {
        for (Node node : listNode) {
            node.setVisible(false);
        }
    }

    void showAlert(String title, String contentText, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    void intro() {

    }

    void setFavoritesBtn(String text) {
        // favourites
        if (favoritesLogic.checkTextInFavouriteFile(text)) {
            btnStarToMark.setVisible(false);
            btnStarToUnMark.setVisible(true);
        } else {
            btnStarToMark.setVisible(true);
            btnStarToUnMark.setVisible(false);
        }
    }

    void searchLogic() throws Exception {
        // get text from TextField Search
        String text = txtFieldSearch.getText().toLowerCase();

        // check length of String
        if (text.length() == 0) {
            new Shake(txtFieldSearch).play();
            new Shake(btnSearch).play();
        } else {
            text = upperCaseFirstLetter(text);

            String res = searchLogic.getHtmlFromCache(text.toLowerCase());
            if (res.length() == 0) {
                // webView
                webView.setVisible(true);
                loadCssForWebView();
                webEngine.loadContent("No result!");

                displayWordSound.setVisible(false);
            } else {
                // display word and pronounce
                word.setText(text);
                pronunciation.setText(searchLogic.getPronounciation(text));
                displayWordSound.setVisible(true);

                // webview
                loadCssForWebView();
                webView.setVisible(true);
                webEngine.loadContent(res);

                // add word to recent.txt
                recentLogic.addRecentWord(text);

                //Table View.
                updateTableView(suggestionWordLogic.autoSuggestionUsingTrie(text),
                    "Suggestion Word");

                setFavoritesBtn(text);
            }
        }
    }
    void saveSystem(File file, String text) throws IOException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(text);
        printWriter.close();
    }
}
