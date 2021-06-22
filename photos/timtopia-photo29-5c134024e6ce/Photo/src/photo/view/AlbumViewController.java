package photo.view;

import java.io.File;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photo.app.Photos;
import photo.model.Album;
import photo.model.Photo;

/**
 * The controller for the display of an album page
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class AlbumViewController {
	
	
	/**
	 * Button that is used to go back to the list of albums
	 */
	@FXML Button back;
	/**
	 * Button used to add a photo
	 */
	@FXML Button add;
	/**
	 * Button used to remove a photo
	 */
	@FXML Button delete;
	/**
	 * Button used to open a photo
	 */
	@FXML Button open;
	/**
	 * Imageview used to display an image
	 */
	@FXML ImageView image;
	
	/**
	 * Listview used to display the photos within an album
	 */
	@FXML ListView<Photo> picList;
	
	/**
	 * The album being opened
	 */
	public static Album album;
	/**
	 * Observable List used by the ListView
	 */
	public static final ObservableList<Photo> display = FXCollections.observableArrayList();
	
	/**
	 * Initializes the window.
	 * Sets the listview to display the photos with a thumbnail and captions
	 */
	public void initialize(){
		
		
		picList.setCellFactory(new PhotoCellFactory());
		
		picList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Photo>() {
		    @Override
		    public void changed(ObservableValue<? extends Photo> observable, Photo oldValue, Photo newValue) {
		    	if(newValue==null)
		    		return;
		    	File path = new File(newValue.getPath());
		    	image.setImage(new Image(path.toURI().toString()));
		    	Photos.photo = newValue;
		    }
		});
		
		album = Photos.album;
		display.setAll(album.getPhotos());
		picList.setItems(display);
		if(Photos.photo == null)
			picList.getSelectionModel().select(0);
		else
			picList.getSelectionModel().select(Photos.photo);
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles going back to the list of albums
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e) {
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
	
	/**
	 * Handles the actions of adding a photo
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void add(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/imageAdd.fxml"));
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
	 * Handles the actions of deleting a photo
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void delete(ActionEvent e) {
		Photo temp = picList.getSelectionModel().getSelectedItem();
		int selection = picList.getSelectionModel().getSelectedIndex()-1;
		if(selection < 0) {
			selection = 0;
		}
		picList.getSelectionModel().select(selection);
		album.getPhotos().remove(temp);
		display.setAll(album.getPhotos());
	}
	/**
	 * Handles the actions of opening a photo
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void open(ActionEvent e){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/imageView.fxml"));
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
