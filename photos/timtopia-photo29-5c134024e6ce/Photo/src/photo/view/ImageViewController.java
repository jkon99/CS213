package photo.view;

import java.io.File;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photo.app.Photos;

/**
 * The controller for the display of an image
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class ImageViewController {
	/**
	 * Imageview used to display an image
	 */
	@FXML ImageView image;
	/**
	 * Button used to go back to list of all images
	 */
	@FXML Button back;
	/**
	 * Button used to go to the previous image
	 */
	@FXML Button lastPhoto;
	/**
	 * Button used to go to the next image
	 */
	@FXML Button nextPhoto;
	/**
	 * Button used to edit the metadata
	 */
	@FXML Button editMeta;
	/**
	 * Button used to move/copy the image
	 */
	@FXML Button move;
	/**
	 * Label to display the date
	 */
	@FXML Label date;
	/**
	 * Label to display the caption
	 */
	@FXML Label cap;
	
	/**
	 * Initializes the window
	 * Sets the labels with the captions and the date
	 */
	public void initialize(){
		cap.setText(Photos.photo.getCaption());
		File path = new File(Photos.photo.getPath());
        Image actualPhoto;
		actualPhoto = new Image(path.toURI().toString());
	    image.setImage(actualPhoto);
	    Date temp = new Date(Photos.photo.getDate());
	    String dateString = temp.toString();
	    date.setText(dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24));
	}
	
	/**
	 * Handles going back to the list of photos
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/albumView.fxml"));
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
	 * Handles going to the previous photo
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void lastPhoto(ActionEvent e) {
		int index = Photos.album.getPhotos().indexOf(Photos.photo);
		index--;
		if(index<0)
			index = Photos.album.getPhotos().size()-1;
		Photos.photo = Photos.album.getPhotos().get(index);
		File path = new File(Photos.photo.getPath());
        Image actualPhoto;
		actualPhoto = new Image(path.toURI().toString());
		image.setImage(actualPhoto);
		Date temp = new Date(Photos.photo.getDate());
	    String dateString = temp.toString();
	    date.setText(dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24));
	    cap.setText(Photos.photo.getCaption());
	}
	
	/**
	 * Handles going to the next photo
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void nextPhoto(ActionEvent e) {
		int index = Photos.album.getPhotos().indexOf(Photos.photo);
		index++;
		if(index>Photos.album.getPhotos().size()-1)
			index = 0;
		Photos.photo = Photos.album.getPhotos().get(index);
		File path = new File(Photos.photo.getPath());
        Image actualPhoto;
		actualPhoto = new Image(path.toURI().toString());
		image.setImage(actualPhoto);
		Date temp = new Date(Photos.photo.getDate());
	    String dateString = temp.toString();
	    date.setText(dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24));
	    cap.setText(Photos.photo.getCaption());
	}
	
	/**
	 * Handles going to the move/copy menu
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void move(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/movePhoto.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	/**
	 * Handles going to the menu to edit metadata
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void editMeta(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/metadata.fxml"));
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
