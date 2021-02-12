//NAMES
//Jonathan Konopka
//Timothy Zhang

package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
		Platform.setImplicitExit(true);
		try {
			File file = new File("SongLib.txt"); //load the text file of current songs, include persistency between sessions
			Scanner lineReader = new Scanner(file);
			while(lineReader.hasNextLine()) {
				String line = lineReader.nextLine();
				String[] song = line.split("\\t", 4); //split by each detail of a song entry
				Song temp = new Song(song[0], song[1], song[2], song[3]); 
				songs.add(temp);
			}
			lineReader.close();
			file.delete();
		} catch (FileNotFoundException e) {
		}
		
		list.setItems(songs);
	}
	

	
	public void buttonPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b==Delete) {   //delete button functions
			
			
		} else if(b==Add) {		//add button functions
			if (title.getText().isEmpty() || artist.getText().isEmpty()) {
				//show pop up that you need a title and artist minimum to create a song entry
				Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.WINDOW_MODAL);

				VBox vbox = new VBox(new Text("Both a title and artist are necessary for a song entry"));  //or add a button?
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(15));

				dialogStage.setScene(new Scene(vbox));
				dialogStage.show();
			} else {
				songs.add(new Song(title.getText(), artist.getText(), album.getText(), year.getText()));
				Comparator<Song> comparator = Comparator.comparing(Song::getLTitle);
				FXCollections.sort(songs,comparator);
				title.setText("");
				artist.setText("");
				album.setText("");
				year.setText("");
				// consider trailing spaces? get rid of them
				//make sure year is only positive integer
			}
			
		} else if (b==Edit){   //edit button functions
			
		}
	}
	
	
	//method for selected song information, show title, artist, album, year when clicking on the song
	public void selectSong () {
		
	}
}
