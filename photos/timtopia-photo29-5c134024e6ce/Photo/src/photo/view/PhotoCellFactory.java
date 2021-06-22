package photo.view;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import photo.model.Photo;

/**
 * A custom factory for photocells
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 *
 */
public class PhotoCellFactory implements Callback<ListView<Photo>, ListCell<Photo>> {

	/**
	 * Constructs the factory that makes the photocell
	 */
	@Override
	public ListCell<Photo> call(ListView<Photo> arg0) {
		return new PhotoCell();
	}
	

}
