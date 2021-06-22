package photo.view;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import photo.model.Photo;

/**
 * A custom listcell for a photo
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class PhotoCell extends ListCell<Photo>{
	/**
	 * Imageview to display the thumbnail of the photo
	 */
	@FXML ImageView image;
	/**
	 * Label to display the caption
	 */
	@FXML Label caption;
	/**
	 * VBox to hold the image and caption
	 */
	@FXML VBox box;
	
	/**
	 * Constructor of the photocell
	 */
	public PhotoCell() {
		loadFXML();
	}

	/**
	 * Loads the FXML of the photocell
	 */
	private void loadFXML() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("photoCell.fxml"));
			loader.setController(this);
			loader.load();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Handles the cell when an item is updated
	 */
	@Override
	protected void updateItem(Photo photo, boolean empty) {
		super.updateItem(photo, empty);
	
		if(empty || photo == null) {
			setText(null);
			setGraphic(null);
			caption.setText(null);
			image.setImage(null);
		}
	    else {
	    	if(photo.getCaption()!= null)
	    		caption.setText(photo.getCaption());
	    	else
	    		caption.setText("");
	    	File path = new File(photo.getPath());
	        Image actualPhoto;
			actualPhoto = new Image(path.toURI().toString());
		    image.setImage(actualPhoto);
		    setGraphic(box);
	    }
	}
}
