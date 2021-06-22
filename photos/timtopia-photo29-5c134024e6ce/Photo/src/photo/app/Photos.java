package photo.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import photo.model.Album;
import photo.model.Photo;
import photo.model.User;

/**
 * The main app to launch the application
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class Photos extends Application implements Serializable {
	
	/**
	 * Holds the list of users
	 */
	public static List<User> users;
	
	/**
	 * The serial version of the app 
	 */
	private static final long serialVersionUID = 1673479208424075369L;
	/**
	 * Holds the current stage
	 */
	public static Stage stg;
	/**
	 * Holds the current user
	 */
	public static User user;
	/**
	 * Holds the current album
	 */
	public static Album album;
	/**
	 * Holds the current Photo
	 */
	public static Photo photo;
	
	/**
	 * Handles the start of the application
	 */
	public void start(Stage primaryStage) throws Exception {
		try{
			users = Photos.readApp();
			boolean contains = false;
			for(User u: users) {
				if(u.getName().equals("stock")) {
					contains = true;
					break;
				}
			}
			if(!contains) 
				createStock();
		}
		catch(Exception e) {
			users = new ArrayList<User>();
			createStock();
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photo/view/login.fxml"));
		Scene scene = new Scene(loader.load());
		Photos.stg = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	/**
	 * Handles the closing of the application
	 */
	public void stop() {
		try {
			writeApp(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launch the application
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * Serializes the list of users
	 * 
	 * @param users a list of users
	 * @throws IOException the location to be written to does not exist
	 */
	public static void writeApp(List<User> users) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data"+File.separator+"Users.dat"));
		oos.writeObject(users);
		oos.close();
	}
	/**
	 * Reads the serialized list of users
	 * 
	 * @return A list of users
	 * @throws IOException File does not exist
	 * @throws ClassNotFoundException the User does not exist
	 */
	public static List<User> readApp() throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data"+File.separator+"Users.dat"));
		@SuppressWarnings("unchecked")
		List<User> temp = (List<User>) ois.readObject();
		ois.close();
		return temp;
	}
	
	/**
	 * Creates the stock user (with stock photos)
	 */
	public void createStock() {
		User stock = new User("stock");
		Album album = new Album("stock");
		File one = new File("." + File.separator + "data" + File.separator + "stockPhotos" + File.separator + "1This.png");
		File two = new File("." + File.separator + "data" + File.separator + "stockPhotos" + File.separator + "2Person.png");
		File three = new File("." + File.separator + "data" + File.separator + "stockPhotos" + File.separator + "3Does.png");
		File four = new File("." + File.separator + "data" + File.separator + "stockPhotos" + File.separator + "4Not.png");
		File five = new File("." + File.separator + "data" + File.separator + "stockPhotos" + File.separator + "5Exist.png");
		Photo oneP = new Photo(one.getPath(), one.lastModified());
		Photo twoP = new Photo(two.getPath(), two.lastModified());
		Photo threeP = new Photo(three.getPath(), three.lastModified());
		Photo fourP = new Photo(four.getPath(), four.lastModified());
		Photo fiveP = new Photo(five.getPath(), five.lastModified());
		album.addPhoto(oneP);
		album.addPhoto(twoP);
		album.addPhoto(threeP);
		album.addPhoto(fourP);
		album.addPhoto(fiveP);
		stock.addAlbum(album);
		users.add(stock);
		
	}
}
