package songlib.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SongLibApp extends Application {
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songlib/view/songlib.fxml"));
		File list = new File("songLib.txt");
		try {
			if(!list.createNewFile())
				throw new FileNotFoundException("File already Exist");
		} catch (FileNotFoundException e) {
		}
		Scanner lineReader = new Scanner(list);
		while(lineReader.hasNextLine()) {
			String line = lineReader.nextLine();
			
		}
		
		
		
		
		VBox root = (VBox)loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
