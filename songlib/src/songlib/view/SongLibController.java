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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	@FXML TextArea info;
	
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
		
		//method for selected song information, show title, artist, album, year when clicking on the song in detail pane
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() { 
			public void changed(ObservableValue<? extends Song> obs, Song oldSong, Song newSong) {
				if(newSong!=null)
					info.setText(newSong.textbox());
			}
		});
		
		//set it to blank when nothing is selected. when I delete everything last selected item still has its info in the box!!!!
		
		list.setItems(songs);
		list.getSelectionModel().select(0);
	}
	
	//should consider a way to back out of button actions? maybe do a pop up before
	
	public void buttonPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		boolean number = year.getText().matches(".*\\d.*");
		
		if(b==Delete) {   //delete button functions
			int selection = list.getSelectionModel().getSelectedIndex();
	
			if (selection != -1) {
				Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Button yesB = new Button("Yes");
				Button cancelB = new Button("Cancel");
				VBox vbox = new VBox(new Text("Would you like to complete this action? Hit yes to continue or cancel to go back"), yesB, cancelB);  //or add a button?
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(15));

				dialogStage.setScene(new Scene(vbox));
				dialogStage.show();
				//set action to buttons
				yesB.setOnAction(new EventHandler<ActionEvent>() {
					@Override
		            public void handle(ActionEvent event)
		            {
						list.getItems().remove(selection);
						
						popUpDisplay("Song has been removed from library"); 
						
						int newSelection = selection - 1;
						if(newSelection == -1)
							newSelection = 0;
						if(songs.size()==0)
							info.setText("");
						list.getSelectionModel().select(newSelection);
		                dialogStage.close();
		            }
				});
				cancelB.setOnAction(new EventHandler<ActionEvent>() {
					@Override
		            public void handle(ActionEvent event)
		            {
		                dialogStage.close();
		            }
				});

	

			}
			
			
		} else if(b==Add) {		//add button functions

			Song temp = new Song(title.getText().trim(), artist.getText().trim(), album.getText().trim(), year.getText().trim());
			boolean exists = false;
			for(Song i:songs) {
				if(i.getLTitle().equals(temp.getLTitle()) && i.getAuthor().toLowerCase().equals(temp.getAuthor().toLowerCase()))
					exists = true;
			}
			
			if (title.getText().isEmpty() || artist.getText().isEmpty()) {
				//show pop up that you need a title and artist minimum to create a song entry
				popUpDisplay("Both a title and artist are necessary for a song entry");

			} else if(exists){
					popUpDisplay("Duplicate Song");
		
			} else if(!number && year.getText().trim().length()>0) {
					popUpDisplay("Year must be a number");
			} else if(year.getText().trim().length()> 0 && Integer.parseInt(year.getText().trim()) < 0) {
					popUpDisplay("Year must be positive"); //make sure year is only positive integer
			} else {
				Stage dialogStage = new Stage();
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Button yesB = new Button("Yes");
				Button cancelB = new Button("Cancel");
				VBox vbox = new VBox(new Text("Would you like to complete this action? Hit yes to continue or cancel to go back"), yesB, cancelB);  //or add a button?
				vbox.setAlignment(Pos.CENTER);
				vbox.setPadding(new Insets(15));

				dialogStage.setScene(new Scene(vbox));
				dialogStage.show();
				//set action to buttons
				yesB.setOnAction(new EventHandler<ActionEvent>() {
					@Override
		            public void handle(ActionEvent event)
		            {
						songs.add(temp);
						Comparator<Song> comparator = Comparator.comparing(Song::getLTitle).thenComparing(Song::getLAuthor);
						list.getSelectionModel().select(temp);
						FXCollections.sort(songs,comparator);
						title.setText("");
						artist.setText("");
						album.setText("");
						year.setText("");
						popUpDisplay("Song has been added to library"); 
		                dialogStage.close();
		            }
				});
				cancelB.setOnAction(new EventHandler<ActionEvent>() {
					@Override
		            public void handle(ActionEvent event)
		            {

		                dialogStage.close();
		            }
				});
				
					
					// consider trailing spaces? get rid of them

			}
			
		} else if (b==Edit){   //edit button functions
			
			//ANY of the fields can be changed. Again, if name and artist conflict with those of an existing song, the edit should NOT be allowed 
			//a message should be shown in a pop-up dialog, or by some other means within the main application window. 
			int selection = list.getSelectionModel().getSelectedIndex();
			
			if (selection != -1) {
				Song temp = new Song(title.getText().trim(), artist.getText().trim(), album.getText().trim(), year.getText().trim());
				boolean exists = false;
				for(Song i:songs) {   //check if song already exists as conflict
					if(i.getLTitle().equals(temp.getLTitle()) && i.getAuthor().toLowerCase().equals(temp.getAuthor().toLowerCase()))
						exists = true;
				}
				
				if (title.getText().isEmpty() || artist.getText().isEmpty()) {
					//show pop up that you need a title and artist minimum to create a song entry
					popUpDisplay("Both a title and artist are necessary for a song entry");

				} else if(exists){
						popUpDisplay("Duplicate Song");
			
				} else if(!number && year.getText().trim().length()>0) {
						popUpDisplay("Year must be a number");
				} else if(year.getText().trim().length()> 0 && Integer.parseInt(year.getText().trim()) < 0) {
						popUpDisplay("Year must be positive"); //make sure year is only positive integer
				} else if (title.getText().isEmpty() && artist.getText().isEmpty() && album.getText().isEmpty() && year.getText().isEmpty()) {
						popUpDisplay("No changes are being made");
				} else {
					Stage dialogStage = new Stage();
					dialogStage.initModality(Modality.WINDOW_MODAL);
					Button yesB = new Button("Yes");
					Button cancelB = new Button("Cancel");
					VBox vbox = new VBox(new Text("Would you like to complete this action? Hit yes to continue or cancel to go back"), yesB, cancelB);  //or add a button?
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(15));

					dialogStage.setScene(new Scene(vbox));
					dialogStage.show();
					//set action to buttons
					yesB.setOnAction(new EventHandler<ActionEvent>() {
						@Override
			            public void handle(ActionEvent event)
			            {
							list.getItems().remove(selection);
							songs.add(temp);
							Comparator<Song> comparator = Comparator.comparing(Song::getLTitle).thenComparing(Song::getLAuthor);
							list.getSelectionModel().select(temp);
							FXCollections.sort(songs,comparator);
							title.setText("");
							artist.setText("");
							album.setText("");
							year.setText("");
							popUpDisplay("Song entry has been successfully edited by the user"); 
			                dialogStage.close();
			            }
					});
					cancelB.setOnAction(new EventHandler<ActionEvent>() {
						@Override
			            public void handle(ActionEvent event)
			            {
			                dialogStage.close();
			            }
					});
						

				}

			}
			
		}
	}
	
	
	//method for displaying error and button dialog since it is used often
	public void popUpDisplay(String text) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);

		VBox vbox = new VBox(new Text(text));  //or add a button?
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));

		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
	}
	
}
