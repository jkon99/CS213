package photo.view;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photo.app.Photos;
import photo.model.Album;
import photo.model.Photo;
import photo.model.User;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The controller for the date search window
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */

public class DateSearchController {
	
	//search by date range
	
	/**
	 * Selection to pick the starting date from a calendar
	 */
	@FXML DatePicker startDate;
	
	/**
	 * Selection to pick the ending date from a calendar
	 */
	@FXML DatePicker endDate;
	
	/**
	 * Button to launch search based on given dates
	 */
	@FXML Button search;
	
	/**
	 * Button that returns to previous search panel
	 */
	@FXML Button goBack;
	
	/**
	 * The current user operating
	 */
	public static User user;
	
	/**
	 * List of albums associate by the user
	 */
	public static List<Album> albums;
	
	/**
	 * List of photos for specific album
	 */
	public static List<Photo> photos;
	
	/**
	 * Current album being manipulated
	 */
	public static Album tempAlbum;
	
	/**
	 * List of photos found that meet given date range
	 */
	public static List<Photo> searched;
	
	/**
	 * Searched photos compiled into one hidden album with duplicates removed
	 */
	public static Album searchedAlbum;
	
	
	/**
	 * Initializes conditions necessary for this panel to function
	 */
	public void initialize(){
		user = Photos.user;
		albums = user.getAlbums();
		

		searched = new ArrayList<Photo>();

		searchedAlbum = new Album(" ");
		
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles the action of doing a date search
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void search(ActionEvent e) {
		// get values of the start and end date range
					// if dates are empty or incomplete dont keep running the code here
					
					if (startDate.getValue() == null || endDate.getValue() == null) {
						//System.out.println("Returned");
						return;
					 }
					
					LocalDate startDateVal = startDate.getValue();
					LocalDate endDateVal = endDate.getValue();
					ZoneId currentTimeZone = ZoneId.systemDefault();
					
					Date startDateValue = Date.from(startDateVal.atStartOfDay(currentTimeZone).toInstant());
					Date endDateValue = Date.from(endDateVal.atStartOfDay(currentTimeZone).toInstant());
					//System.out.println(startDateValue);
					
					// when search is clicked you can click back and see results
					// go through all existing albums for the user and pull out photos that are within the date range
					//keep track make sure no duplicates of same photo if they are in multiple albums
					
					for (int i =0; i < albums.size(); i++) {
						tempAlbum = albums.get(i);
						photos = tempAlbum.getPhotos();
						
						for (int j = 0; j < photos.size(); j++) {
							
							Date in = new Date(photos.get(j).getDate());
							
							
							if (inDateRange(in, startDateValue, endDateValue)) {
								searched.add(photos.get(j));
								//System.out.println(in);
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
					SearchController.checkIfSearched = 1;
					
					Stage dialogStage = new Stage();
					dialogStage.initModality(Modality.WINDOW_MODAL);
					VBox vbox = new VBox(new Text("Search completed"));
					vbox.setAlignment(Pos.CENTER);
					vbox.setPadding(new Insets(15));
					dialogStage.setScene(new Scene(vbox));
					dialogStage.show();
					
	}
	
	
	/**
	 * Handles going back to the search selection window
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e){

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
	 * Helper method used to check if date of current photo is within specified range
	 */
	boolean inDateRange(Date input, Date starting, Date ending) { //return 1 if date of current photo is not outside of range
		   return !(input.before(starting) || input.after(ending));

	}
	
	
}
