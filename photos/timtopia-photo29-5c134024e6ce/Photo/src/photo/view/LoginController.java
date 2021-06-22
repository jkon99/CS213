package photo.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photo.app.Photos;
import photo.model.User;

/**
 * The controller for the login page
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class LoginController {
	
	/**
	 * Button used to login
	 */
	@FXML Button login;
	/**
	 * Textfield used to get username
	 */
	@FXML TextField username;
	
	/**
	 * Initializes the window
	 */
	public void initialize(){
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles logging in 
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void login(ActionEvent e){
		if(username.getText().equals("admin")) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/photo/view/admin.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.show();
				Photos.stg.close();
				Photos.stg = stage;
			} catch(Exception error) {
				error.printStackTrace();
			}
		} else if(checkNames(username.getText())!= null) {
			Photos.user = checkNames(username.getText());
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
	
	/**
	 * Checks if the name is in the list of users
	 * 
	 * @param name the name to be checked
	 * @return <code>true</code> if the user exists; <code>false</code> if not.
	 */
	public User checkNames(String name) {
		for(User u:Photos.users) {
			if(u.getName().equals(name))
				return u;
		}
		return null;
	}
}
