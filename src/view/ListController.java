package view;

/**
 *
 * @author : Chenqi Zhao, Ruiqi Zhang
 * @date : ${2020.9.24}
 */

import SongManagement.SongInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

public class ListController {
    @FXML
    ListView<String> listView;
    @FXML
    TextArea detail;
    @FXML
    TextArea add_name, add_artist, add_album, add_year;
    @FXML
    TextArea edit_name, edit_artist, edit_album, edit_year;

    private ObservableList<String> obsList;
    public SongInfo songInfo;

    public void start(Stage mainStage) {
        obsList = FXCollections.observableArrayList();
        songInfo = new SongInfo();
        ArrayList<String[]> songList = songInfo.songList;
        for (int i = 0; i < songList.size(); i ++) {
            String temp = "Name: " + songList.get(i)[0] + " Artist: " + songList.get(i)[1];
            obsList.add(temp);
        }
        listView.setItems(obsList);
        listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updateDetail(mainStage));
        if (obsList.size() != 0) {
            listView.getSelectionModel().select(0);
        }
    }

    private void updateDetail(Stage mainStage) {
        String item = listView.getSelectionModel().getSelectedItem();
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            detail.setText("");
            return;
        }
        String[] songDetail = this.songInfo.songList.get(index);
        String album = songDetail[2];
        String year = songDetail[3];
        if ("no_info".equals(songDetail[2])) {
            album = "";
        }
        if ("no_info".equals(songDetail[3])) {
            year = "";
        }
        String newText = "Name: " + songDetail[0] + "\nArtist: " + songDetail[1] + "\nAlbum: " + album + "\nYear:" + year;
        detail.setText(newText);

        String[] info = this.songInfo.songList.get(index);
        this.edit_name.setText(info[0]);
        this.edit_artist.setText(info[1]);
        this.edit_album.setText(info[2]);
        this.edit_year.setText(info[3]);
    }

    public void addSong() {
        String name = add_name.getText();
        String artist = add_artist.getText();
        String album = add_album.getText();
        String year = add_year.getText();
        int overlapNum = NumOfOverlap(name, artist);
        if (overlapNum == 0 && ("".equals(name) == false) && ("".equals(artist) == false)) {
            boolean confirm = confirm("add");
            if (confirm == false) {
                return;
            }
            String[] tempInfo = {name, artist, album, year};
            if ("".equals(tempInfo[2])) {
                tempInfo[2] = "no_info";
            }
            if ("".equals(tempInfo[3])) {
                tempInfo[3] = "no_info";
            }
            String showString = "Name: " + tempInfo[0] + " Artist: " + tempInfo[1];
            int position = this.insertInfo(showString);
            this.songInfo.songList.add(position, tempInfo);
            this.listView.getSelectionModel().select(position);
            this.songInfo.writeBack();
            add_name.setPromptText("");
            add_artist.setPromptText("");
            add_album.setPromptText("");
            add_year.setPromptText("");
        }
        else if (("".equals(name) == true) || ("".equals(artist) == true)) {
            popUp("Can not have an empty song name or an empty artist");
        }
        else if (overlapNum != 0) {
            popUp("the name and artist are the same as an existing song");
        }
    }

    public void del() {
        int n = obsList.size();
        int position = this.listView.getSelectionModel().getSelectedIndex();
        this.obsList.remove(position);
        this.songInfo.songList.remove(position);
        if (n == 1) {
            listView.getSelectionModel().select(-1);;
        }
        else if (position < n - 1) {
            this.listView.getSelectionModel().select(position);
        }
        else if (position > 0){
            this.listView.getSelectionModel().select(position - 1);
        }
        this.songInfo.writeBack();
    }

    public void edit() {
        String name = edit_name.getText();
        String artist = edit_artist.getText();
        String album = edit_album.getText();
        String year = edit_year.getText();
        int index = listView.getSelectionModel().getSelectedIndex();
        int overlapNum = NumOfOverlap(name, artist);
        if (overlapNum <= 1 && ("".equals(name) == false) && ("".equals(artist) == false)) {
            boolean confirm = confirm("edit");
            if (confirm == false) {
                return;
            }
            String abbrInfo = "Name: " + name + " Artist: " + artist;
            String[] completeInfo = {name, artist, album, year};
            this.obsList.remove(index);
            this.songInfo.songList.remove(index);
            int position = this.insertInfo(abbrInfo);
            this.songInfo.songList.add(position, completeInfo);
            this.listView.getSelectionModel().select(position);
            this.songInfo.writeBack();
        }
        else if (("".equals(name) == true) || ("".equals(artist) == true)) {
            popUp("Can not have an empty song name or an empty artist");
        }
        else if (overlapNum > 1) {
            popUp("the name and artist are the same as an existing song");
        }
    }

    private int NumOfOverlap(String name, String artist) {
        /*
        find whether the song and artist are in the list
         */
        int n = 0;
        for (int i = 0; i < this.songInfo.songList.size(); i ++) {
            String[] tempInfo = this.songInfo.songList.get(i);
            if (tempInfo[0].equals(name) && tempInfo[1].equals(artist)) {
                n ++;
            }
        }
        return n;
    }

    private int insertInfo(String showString) {
        this.listView.getSelectionModel().select(-1);
        this.obsList.add(showString);
        Collections.sort(this.obsList, String.CASE_INSENSITIVE_ORDER);
        int pos = this.obsList.indexOf(showString);
        return pos;
    }

    private boolean confirm(String str) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Aew you sure to " + str + " a song?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    private void popUp(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message");
        alert.setHeaderText("There is an error in the previous command");
        alert.setContentText(str);
        alert.showAndWait();
    }
}
