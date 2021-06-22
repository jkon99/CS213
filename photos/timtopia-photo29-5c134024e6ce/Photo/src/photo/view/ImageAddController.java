package photo.view;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import photo.app.Photos;
import javafx.stage.Stage;
import photo.model.Album;
import photo.model.Photo;



/**
 * The controller for the display of a page to add a photo
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class ImageAddController {
	
	/**
	 * The album where the photo will be added
	 */
	public static Album album;
	
	/**
	 * Button used to select a photo
	 */
	@FXML Button select;
	/**
	 * Button used to add the photo selected
	 */
	@FXML Button add;
	/**
	 * Button used go back to the Album view
	 */
	@FXML Button goBack;
	/**
	 * TextField used to display the path
	 */
	@FXML TextField path;
	
	
	/**
	 * Initializes the window
	 */
	public void initialize(){
		album = Photos.album;
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles the select of the photo using the System explorer
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void select(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif")
            );
		File photo = fileChooser.showOpenDialog(null);
		if(photo != null) {
			String listNames = photo.getAbsolutePath();
			path.setText(listNames);
		}
	}
	
	/**
	 * Handles add the image to the album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void add(ActionEvent e) {
		if(path.getText().length() == 0) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Use the select button to select a photo"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			return;
		}
		File photo = new File(path.getText());
		for(Album a : Photos.user.getAlbums()) {
			for(Photo p: a.getPhotos()) {
				if(path.getText().equals(p.getPath())) {
					album.addPhoto(p);
					return;
				}
			}
		}
		Photo newPhoto = new Photo(photo.getAbsolutePath(), photo.lastModified());
		if(!album.containsPhoto(newPhoto)) {
			album.addPhoto(newPhoto);
			path.clear();
		}
		else {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Photo already exists in album"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			path.clear();
		}
	}
	
	/**
	 * Handle goes back to the album view
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void goBack(ActionEvent e){
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
}
