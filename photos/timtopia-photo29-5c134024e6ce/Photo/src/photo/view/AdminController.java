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
import photo.model.User;

/**
 * The controller for the admin page
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class AdminController {
	
	/**
	 * Button that the admin uses to logout
	 */
	@FXML Button logout;
	/**
	 * Button used to add a user
	 */
	@FXML Button add;
	/**
	 * Button used to delete a user
	 */
	@FXML Button delete;
	/**
	 * Listview used to display the users
	 */
	@FXML ListView<User> list;
	
	/**
	 * List of the users
	 */
	public static List<User> users;
	/**
	 * Observable List used by the ListView
	 */
	public static final ObservableList<User> display = FXCollections.observableArrayList();
	
	/**
	 * Initializes the window.
	 * Sets the listview to display the users
	 */
	public void initialize(){
		users = Photos.users;
		display.setAll(users);
		list.setItems(display);
		list.getSelectionModel().select(0);
		
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles the actions of logging out 
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void logout(ActionEvent e) {
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
	 * Handles adding a new user
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void add(ActionEvent e) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Button enterB = new Button("Enter");
		Button cancelB = new Button("Cancel");
		TextField name = new TextField();
		VBox vbox = new VBox(new Text("Enter the name of the User"), name, enterB, cancelB);  //or add a button?
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
					if(name.getText().equalsIgnoreCase("admin")) {
						Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						VBox vbox = new VBox(new Text("Cannot create admin user"));
						vbox.setAlignment(Pos.CENTER);
						vbox.setPadding(new Insets(15));
						dialogStage.setScene(new Scene(vbox));
						dialogStage.show();
						return;
					}
					boolean contains = false;
					for(User u: users) {
						//System.out.println(a.getName());
						if(u.getName().equalsIgnoreCase(name.getText())) {
							name.clear();
							contains = true;
							break;
						}
					}
					if(!contains) {
						User temp = new User(name.getText());
						users.add(temp);
						name.clear();
						display.setAll(users);
						list.getSelectionModel().select(temp);
						dialogStage.close();
					}else {
						Stage dialogStage = new Stage();
						dialogStage.initModality(Modality.WINDOW_MODAL);
						VBox vbox = new VBox(new Text("User name already exists"));
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
	 * Handles deleting a user
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void delete(ActionEvent e){
		User temp = list.getSelectionModel().getSelectedItem();
		int selection = list.getSelectionModel().getSelectedIndex()-1;
		if(selection < 0) {
			selection = 0;
		}
		list.getSelectionModel().select(selection);
		users.remove(temp);
		display.setAll(users);
	}
}
