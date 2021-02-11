//NAMES
//Jonathan Konopka
//

package songlib.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SongLibApp extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		Platform.setImplicitExit(true);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));	
		
		
		VBox root = (VBox)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);   //initialize window with title
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
