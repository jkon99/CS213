package photo.view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photo.app.Photos;
import photo.model.Album;
import photo.model.Photo;
import photo.model.User;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;

/**
 * The controller for the tag search window
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */

public class TagSearchController {

	//search by specified tags
	
	/**
	 * Button to search given specified tags
	 */
	@FXML Button search;
	/**
	 * Button to go back to main search pane
	 */
	@FXML Button goBack;
	/**
	 * Combo box containing current tags
	 */
	@FXML ComboBox<String> tag1; //get tags made by TagAdd
	/**
	 * Combo box containing current tags, but this one is for second pair
	 */
	@FXML ComboBox<String> tag2;
	/**
	 * Value associated with tag1
	 */
	@FXML TextField value1;
	/**
	 * Value associated with tag2
	 */
	@FXML TextField value2;
	
	/**
	 * Check box for if the user wants to the search conjunctively. If left unchecked then search will disjunctive
	 */
	@FXML CheckBox conjunctive; // if conjunctive checked then pairs are AND, if not checked then pairs are OR
	/**
	 * Current user operating
	 */
	public static User user;
	/**
	 * List of tags associated with user
	 */
	public static List<String> tags;
	/**
	 * List of albums associated with user
	 */
	public static List<Album> albums;
	/**
	 * List of photos from a specific album
	 */
	public static List<Photo> photos;
	/**
	 * Currently used album
	 */
	public static Album tempAlbum;
	/**
	 * List of photos found to match given tags
	 */
	public static List<Photo> searched;
	/**
	 * All the searched photos compiled into one hidden album with duplicates removed
	 */
	public static Album searchedAlbum;
	/**
	 * Initializes conditions necessary for this panel to function
	 */
	public void initialize(){
		user = Photos.user;
		albums = user.getAlbums();
		tags = user.getTags();
		
		tag1.getItems().addAll(tags);
		tag2.getItems().addAll(tags);
		//add blank space to reset selection?
		

		searched = new ArrayList<Photo>();

		searchedAlbum = new Album(" ");
		
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles the actions of return to the search
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/search.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			Photos.stg.close();
			Photos.stg = stage;
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	
	/**
	 * Handles the action of doing a search
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void search(ActionEvent e){
		String tag1s = tag1.getSelectionModel().getSelectedItem();
		String tag2s = tag2.getSelectionModel().getSelectedItem();
		
		String value1s = value1.getText();
		String value2s = value2.getText();
		
		//System.out.println(tag1s);
		//System.out.println(tag2s);
		//System.out.println(value1s);
		//System.out.println(value2s);
		
		
		if ((tag1s == null && value1s == "") || (( tag1s == null && value1s == "") && (value2s != "" && tag2s != null))) { //check make sure there is a tag in first slot. make sure cant search with second value if there isnt one in the first slot

			return;
		}
		
		// if only value in first pair
		
		if (tag1s != null && value1s != "" && value2s == "" && tag2s == null) {

			if(value1s.length()>=0) {

				for(String s: tags) {

					
					if(s.equalsIgnoreCase(tag1s)) {

						for (int i =0; i < albums.size(); i++) {
							tempAlbum = albums.get(i);
							photos = tempAlbum.getPhotos();
							
							for (int j = 0; j < photos.size(); j++) {
									
								//find way to fix this, make association between tag and pair
							
								if (value1s.equalsIgnoreCase(photos.get(j).getTagValue(tag1s))) {
									searched.add(photos.get(j));
									//System.out.println(in);
								} 
							
							}
							
						}
					}
				}
			}

			
			//works with two pairs
		} else if (tag1s != null && value1s != "" && value2s != "" && tag2s != null) {
			
			
			if (conjunctive.isSelected()) { //if checked then conjunctive AND
				if(value1s.length()>=0 && value2s.length() >= 0) {
					//System.out.println("third check failed");
					for(String s: tags) {
						//System.out.println(a.getName());
						//System.out.println("loop failed");
						for (String t: tags) {
							if(s.equalsIgnoreCase(tag1s) && t.equalsIgnoreCase(tag2s)) {
								
								//System.out.println("boolean checked");
								for (int i =0; i < albums.size(); i++) {
									tempAlbum = albums.get(i);
									photos = tempAlbum.getPhotos();
									
									for (int j = 0; j < photos.size(); j++) {
											
										//find way to fix this, make association between tag and pair
									
										if (value1s.equalsIgnoreCase(photos.get(j).getTagValue(tag1s)) && value2s.equalsIgnoreCase(photos.get(j).getTagValue(tag2s))) {
											searched.add(photos.get(j));
											//System.out.println(in);
										} 
									
									}
									
								}
							}
						}
					}
				}
				
			} else { //disjunctive OR
				if(value1s.length()>=0 && value2s.length() >= 0) {

					for(String s: tags) {

						for (String t: tags) {
							if(s.equalsIgnoreCase(tag1s) || t.equalsIgnoreCase(tag2s)) {
								
								for (int i =0; i < albums.size(); i++) {
									tempAlbum = albums.get(i);
									photos = tempAlbum.getPhotos();
									
									for (int j = 0; j < photos.size(); j++) {
											
									
										if (value1s.equalsIgnoreCase(photos.get(j).getTagValue(tag1s)) || value2s.equalsIgnoreCase(photos.get(j).getTagValue(tag2s))) {
											searched.add(photos.get(j));
											//System.out.println(in);
										} 
									
									}
									
								}
							}
						}
					}
				}
			}
				
		}
		

		//list of photos go in for search.fxml
		
		for (int k=0; k < searched.size(); k++) {
			
			if (!searchedAlbum.containsPhoto(searched.get(k))) { // REMOVE DUPLICATES, check if does not not contain photo first
				searchedAlbum.addPhoto(searched.get(k));
			}
		}
		
		Photos.album = searchedAlbum; //curren album now
		SearchController.checkIfSearched = 1; //check that is searched now
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Search completed"));
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
	}
	
}
