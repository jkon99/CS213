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
 * The controller for the window to add tags types
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class TagsAddController {
	
	/**
	 * Button used to go back to the albums page
	 */
	@FXML Button back;
	/**
	 * Button used to add a tag type
	 */
	@FXML Button add;
	/**
	 * Button used to delete a tag type
	 */
	@FXML Button delete;
	/**
	 * Listview used to display tag types
	 */
	@FXML ListView<String> list;
	
	/**
	 * List of tags types
	 */
	public static List<String> tags;
	/**
	 * Observable list for the listview
	 */
	public static final ObservableList<String> display = FXCollections.observableArrayList();
	/**
	 * The user with the tag types
	 */
	public static User user;
	
	/**
	 * Initializes the window
	 * Sets the display of the tag type list
	 */
	public void initialize(){
		
		user = Photos.user;
		tags = user.getTags();
		display.setAll(tags);
		list.setItems(display);
		list.getSelectionModel().select(0);
		
		Platform.setImplicitExit(true);
	}
	
	/**
	 * Handles going back to the albumlist
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
	 * Handles adding a tag type
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void add(ActionEvent e) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Button enterB = new Button("Enter");
		Button cancelB = new Button("Cancel");
		TextField tag = new TextField();
		VBox vbox = new VBox(new Text("Enter the name of the tag"), tag, enterB, cancelB);  //or add a button?
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));

		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();
		//set action to buttons
		enterB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event)
            {
				if(tag.getText().length()>=0) {
					boolean contains = false;
					for(String s: tags) {
						//System.out.println(a.getName());
						if(s.equalsIgnoreCase(tag.getText())) {
							tag.clear();
							contains = true;
							break;
						}
					}
					if(!contains) {
						tags.add(tag.getText());
						tag.clear();
						display.setAll(tags);
						list.getSelectionModel().select(tags.size()-1);
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
	 * Handles deleting a tag type
	 * 
	 * @param e the object that had an action event done on it
	 */
	public void delete(ActionEvent e){
		String temp = list.getSelectionModel().getSelectedItem();
		int selection = list.getSelectionModel().getSelectedIndex()-1;
		if(selection<0)
			selection = 0;
		list.getSelectionModel().select(selection);
		tags.remove(temp);
		display.setAll(tags);
	}
}
