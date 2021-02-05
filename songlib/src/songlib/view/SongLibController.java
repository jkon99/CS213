package songlib.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SongLibController {
	
	@FXML Button Add;
	@FXML Button Delete;
	@FXML Button Edit;
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	public void buttonPress(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b==Delete) {
			
		} else if(b==Add) {
			
		} else {
			
		}
	}
}
