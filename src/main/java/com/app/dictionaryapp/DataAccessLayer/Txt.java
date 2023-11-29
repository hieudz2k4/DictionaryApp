package com.app.dictionaryapp.DataAccessLayer;

import java.io.PrintWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Txt {
    private final String pathFile; // luu path file
    private File file; // mo file

    public Txt(String pathFile) {
        this.pathFile = pathFile;
    }

    public void connect() {
        try {
            file = new File(pathFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String text) {
        try(FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(text + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleleTextInFile(String text) {
        connect();

        ObservableList<String> observableList = getContentInFile();

        observableList.remove(text);

        deleteAll();

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (String line : observableList) {
                fileWriter.write(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getContentInFile() {
        connect();

        ObservableList<String> contentList = FXCollections.observableArrayList();


        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                contentList.add(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }

    public void deleteAll() {
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.close();
            return;
        } catch (Exception e) {
            return;
        }
    }

    public String read1() {
        String ans = "";
        try (Scanner scanner = new Scanner(file)) {
            ans = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("loi");
            e.printStackTrace();
        }
        return ans;
    }
}
