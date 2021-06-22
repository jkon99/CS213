package photo.view;

import java.io.File;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photo.app.Photos;

/**
 * The controller for the metadata editing
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class MetadataController {

	/**
	 * Imageview to display the image
	 */
	@FXML ImageView image;
	/**
	 * Button to go back
	 */
	@FXML Button back;
	/**
	 * Button used to save a tag
	 */
	@FXML Button saveTag;
	/**
	 * Button used to save a caption
	 */
	@FXML Button saveCaption;
	/**
	 * Label used to display the date
	 */
	@FXML Label date;
	/**
	 * Combobox used to list the tags
	 */
	@FXML ComboBox<String> tagList;
	/**
	 * Textfield used to hold a tag value
	 */
	@FXML TextField tagValue;
	/**
	 * Textfield used to hold the caption
	 */
	@FXML TextField caption;
	
	
	/**
	 * Initializes the window
	 * Sets the label of the date.
	 * Sets the list of tags in the comboBox
	 */
	public void initialize(){
		File path = new File(Photos.photo.getPath());
		Image actualPhoto;
		actualPhoto = new Image(path.toURI().toString());
	    image.setImage(actualPhoto);
	    Date temp = new Date(Photos.photo.getDate());
	    String dateString = temp.toString();
	    date.setText(dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24));
	    caption.setText(Photos.photo.getCaption());
	    
	    ObservableList<String> albums = FXCollections.observableArrayList(Photos.user.getTags());
	    tagList.setItems(albums);
	    tagList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> List, String oldValue, String newValue) {
				if(newValue.equals(""))
					tagValue.setText("");
				else {
					if(Photos.photo.getTagValue(newValue)==null)
						tagValue.setText("");
					else
						tagValue.setText(Photos.photo.getTagValue(newValue));
				}
				
			}
	    });
	}
	
	/**
	 * Handles saving the caption
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void saveCaption(ActionEvent e) {
		Photos.photo.setCaption(caption.getText());
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Caption Saved"));
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
	}
	
	/**
	 * Handles going back to the image
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void back(ActionEvent e) {
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
	
	/**
	 * Handles saving a tag and it's value
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void saveTag(ActionEvent e){
		if(tagList.getSelectionModel().getSelectedItem().equals("")) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Use Dropdown to Select tag Types"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}
		else {
			Photos.photo.addTag(tagList.getSelectionModel().getSelectedItem(), tagValue.getText());
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Tag Added/Edited"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
		}
	}
}
