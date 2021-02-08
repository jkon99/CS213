package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import songlib.app.Song;

public class SongLibController {
	
	@FXML Button Add;
	@FXML Button Delete;
	@FXML Button Edit;
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	@FXML ListView<Song> list;
	
	public static final ObservableList<Song> songs = FXCollections.observableArrayList();
	
	public void initialize(){
		File file = new File("SongLib.txt");
		try {
			Scanner lineReader = new Scanner(file);
			while(lineReader.hasNextLine()) {
				String line = lineReader.nextLine();
				String[] song = line.split("\\t", 4);
				Song temp = new Song(song[0], song[1], song[2], song[4]);
				songs.add(temp);
			}
			lineReader.close();
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (IOException r) {
			}
		}
		
		list.setItems(songs);
	}

	
	public void buttonPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b==Delete) {
			
			
		} else if(b==Add) {
			songs.add(new Song(title.getText(), artist.getText(), album.getText(), year.getText()));
			Comparator<Song> comparator = Comparator.comparing(Song::getLTitle);
			FXCollections.sort(songs,comparator);
			title.setText("");
			artist.setText("");
			album.setText("");
			year.setText("");
		} else {
			
		}
	}
}
