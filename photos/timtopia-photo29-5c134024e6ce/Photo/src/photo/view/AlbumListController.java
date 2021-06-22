package photo.view;

import java.util.List;

import javafx.application.Platform;
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
import photo.model.User;

/**
 * The controller for the list of albums page
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class AlbumListController {
	
	/**
	 * Button that the user uses to logout
	 */
	@FXML Button logout;
	/**
	 * Button used to add an album
	 */
	@FXML Button add;
	/**
	 * Button used to delete an album
	 */
	@FXML Button delete;
	/**
	 * Button used to open an album
	 */
	@FXML Button open;
	/**
	 * Button used to rename an album
	 */
	@FXML Button rename;
	/**
	 * Button used to search all albums
	 */
	@FXML Button search;
	/**
	 * Listview used to display the albums 
	 */
	@FXML ListView<Album> albumList;
	/**
	 * Button used to add a tag type
	 */
	@FXML Button addTag;
	
	/**
	 * The user in charge of the album
	 */
	public static User user;
	
	/**
	 * List of the albums that the user has
	 */
	public static List<Album> albums;
	/**
	 * Observable List used by the ListView
	 */
	public static final ObservableList<Album> display = FXCollections.observableArrayList();
	
	/**
	 * Initializes the window.
	 * Sets the listview to display the albums
	 */
	public void initialize(){
		user = Photos.user;
		albums = user.getAlbums();
		display.setAll(albums);
		albumList.setItems(display);
		if(Photos.album == null)
			albumList.getSelectionModel().select(0);
		else
			albumList.getSelectionModel().select(Photos.album);
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles the actions of logging out
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void logout (ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/login.fxml"));
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
	 * Handles the actions of opening a window to add tag types for the user 
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void addTag(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/tagsAdd.fxml"));
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
	 * Handles the actions of adding an album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void add(ActionEvent e) {
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
						Album temp = new Album(name.getText());
						albums.add(temp);
						name.clear();
						display.setAll(albums);
						albumList.getSelectionModel().select(temp);
						dialogStage.close();
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
	}
	
	/**
	 * Handles the actions of opening a window to add search for photos
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void search(ActionEvent e) {
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
	 * Handles the actions of deleting an album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void delete(ActionEvent e) {
		Album temp = albumList.getSelectionModel().getSelectedItem();
		albumList.getSelectionModel().select(albumList.getSelectionModel().getSelectedIndex()-1);
		albums.remove(temp);
		display.setAll(albums);
	}
	
	/**
	 * Handles the actions of opening an album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void open(ActionEvent e) {
		try {
			Photos.album = albumList.getSelectionModel().getSelectedItem();
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
	 * Handles the actions of rename an album
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void rename(ActionEvent e){
		Album temp = albumList.getSelectionModel().getSelectedItem();
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
						temp.setName(name.getText());
						display.setAll(albums);
						dialogStage.close();
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
	}
}