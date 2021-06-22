package photo.view;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import photo.app.Photos;
import photo.model.Album;
import photo.model.Photo;

/**
 * The controller for the window to move photos
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class MovePhotoController {
	
	/**
	 * Button used to cancel the action
	 */
	@FXML Button cancel;
	/**
	 * Button used to move the photo
	 */
	@FXML Button move;
	/**
	 * Button used to copy the photo
	 */
	@FXML Button copy;
	/**
	 * Combobox used display the list of albums
	 */
	@FXML ComboBox<Album> albumList;
	
	/**
	 * Initialize the window
	 * Sets a custom way to display albums
	 */
	public void initialize(){
		ObservableList<Album> albums = FXCollections.observableArrayList(Photos.user.getAlbums());
		albumList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
			@Override
			public ListCell<Album> call(ListView<Album> l){
				return new ListCell<Album>() {
					@Override
					protected void updateItem(Album item, boolean empty) {
						super.updateItem(item, empty);
						if(item==null||empty) {
							setGraphic(null);
						}
						else {
							setText(item.getName());
						}
					}
				};
			}	
		});
		
		albumList.setItems(albums);
	}
	
	/**
	 * Handles if the user cancels
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void cancel(ActionEvent e) {
		((Stage) cancel.getScene().getWindow()).close();
	}
	
	/**
	 * Handles moving the photo to a new album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void move(ActionEvent e) {
		if(albumList.getSelectionModel().isEmpty()) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Use the dropdown menu select a album"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			return;
		}
		Album move = albumList.getSelectionModel().getSelectedItem();
		Photo photo = Photos.photo;
		move.addPhoto(photo);
		Photos.album.removePhoto(photo);
		((Stage) cancel.getScene().getWindow()).close();
		
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
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Photo moved"));
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
	}
	
	/**
	 * Handles copying a photo to another album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void copy(ActionEvent e) {
		if(albumList.getSelectionModel().isEmpty()) {
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox(new Text("Use the dropdown menu select a album"));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(15));
			dialogStage.setScene(new Scene(vbox));
			dialogStage.show();
			return;
		}
		Album copy = albumList.getSelectionModel().getSelectedItem();
		Photo photo = Photos.photo;
		copy.addPhoto(photo);
		((Stage) cancel.getScene().getWindow()).close();
		
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox(new Text("Photo copied"));
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
	}
}
