//NAMES
//Jonathan Konopka
//Timothy Zhang

package songlib.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import songlib.view.SongLibController;

public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));	
		
		
		VBox root = (VBox)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);   //initialize window with title
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void stop() {   //for persistancy, write to txt when the program terminates
		try {
			File file = new File("SongLib.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for(Song i: SongLibController.songs) {
				writer.write(i.former());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.exit();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
