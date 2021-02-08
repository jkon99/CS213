package songlib.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public static final ObservableList<Song> data = FXCollections.observableArrayList();
	
	public void initialize() throws Exception{
		File file = new File("songLib.txt");
		try {
			if(!file.createNewFile())
				throw new IOException("File already Exists");
		} catch (IOException e) {
			
		}
		Scanner lineReader = new Scanner(file);
		while(lineReader.hasNextLine()) {
			String line = lineReader.nextLine();
			String[] song = line.split("\t", 4);
			Song temp = new Song(song[0], song[1], song[2], song[4]);
			data.add(temp);
		}
		lineReader.close();
		
		list.setItems(data);
		
	}
	
	public void buttonPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b==Delete) {
			
		} else if(b==Add) {
			
		} else {
			
		}
	}
}
