package photo.view;

import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photo.app.Photos;
import photo.model.Album;
import photo.model.Photo;
import photo.model.User;

/**
 * The controller for the search window
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class SearchController {
	
	/**
	 * Button for date search
	 */
	@FXML Button date;
	/**
	 * Button for tag search
	 */
	@FXML Button tag;
	/**
	 * Button to return back to album list
	 */
	@FXML Button back;
	/**
	 *  Button to create an album out of searched photos
	 */
	@FXML Button create;
	/**
	 *  Lists the photos search results
	 */

	@FXML ListView<Photo> picList;
	
	/**
	 *  The album being opened
	 */
	public static Album album;
	/**
	 * The current user operating
	 */
	public static User user;
	
	/**
	 * List of albums from the user
	 */
	public static List<Album> albums;
	
	/**
	 * Used to display photos
	 */
	public static final ObservableList<Photo> display = FXCollections.observableArrayList();
	
	/**
	 * Int used to make sure a search of some sort was run before creating an album or displaying photos
	 */
	public static int checkIfSearched;
	
	/**
	 * Initializes conditions necessary for this panel to function
	 */
	public void initialize() {
		user = Photos.user;
		albums = user.getAlbums();
		
		//now display list of searched photos
		picList.setCellFactory(new PhotoCellFactory());
		
		picList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Photo>() {
		    @Override
		    public void changed(ObservableValue<? extends Photo> observable, Photo oldValue, Photo newValue) {
		    	if(newValue==null)
		    		return;

		    	Photos.photo = newValue;
		    }
		});
		if (checkIfSearched == 1) {
			album = Photos.album;
			display.setAll(album.getPhotos());
			picList.setItems(display);
			if(Photos.photo == null)
				picList.getSelectionModel().select(0);
			else
				picList.getSelectionModel().select(Photos.photo);
		}
		
		Platform.setImplicitExit(true);
		
	}
	
	/**
	 * Handles the actions of opening tag search
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void tagSearch(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/tagSearch.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			Photos.stg.close();
			Photos.stg = stage;
			//checkIfSearched = 1;
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	/**
	 * Handles the creation of an album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void create(ActionEvent e) {
		if (checkIfSearched == 1) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Button enterB = new Button("Enter");
			Button cancelB = new Button("Cancel");
			TextField name = new TextField();
			VBox vbox = new VBox(new Text("Enter the name of the Album"), name, enterB, cancelB);  //or add a button?
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));

			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			//set action to buttons
			enterB.setOnAction(new EventHandler<ActionEvent>() {
				@Override
	            public void handle(ActionEvent event)
	            {
					if(name.getText().length()>=0) {
						boolean contains = false;
						for(Album a: albums) {
							//System.out.println(a.getName());
							if(a.getName().equalsIgnoreCase(name.getText())) {
								name.clear();
								contains = true;
								break;
							}
						}
						if(!contains) {
							album = Photos.album;
							album.setName(name.getText());
							albums.add(album);
							name.clear();
							dialogStage.close();
							Stage dialogStage = new Stage();
							dialogStage.initModality(Modality.WINDOW_MODAL);
							VBox vbox = new VBox(new Text("Album created from search results"));
							vbox.setAlignment(Pos.CENTER);
							vbox.setPadding(new Insets(15));
							dialogStage.setScene(new Scene(vbox));
							dialogStage.show();
						}else {
							Stage dialogStage = new Stage();
							dialogStage.initModality(Modality.WINDOW_MODAL);
							VBox vbox = new VBox(new Text("Album name already exists"));
							vbox.setAlignment(Pos.CENTER);
							vbox.setPadding(new Insets(15));
							dialogStage.setScene(new Scene(vbox));
							dialogStage.show();
						}
					}
	            }
			});
			cancelB.setOnAction(new EventHandler<ActionEvent>() {
				@Override
	            public void handle(ActionEvent event)
	            {
	                dialogStage.close();
	            }
			});
			
			
			// add album to user albums
		}
		checkIfSearched = 0;
		
		
		//reset for next search
	}
	
	/**
	 * Handles the actions of opening tag search
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void dateSearch(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/dateSearch.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			Photos.stg.close();
			Photos.stg = stage;
			//checkIfSearched = 1;
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	/**
	 * Handles the actions of going back to the album list
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e){
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/albumList.fxml"));
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
}
