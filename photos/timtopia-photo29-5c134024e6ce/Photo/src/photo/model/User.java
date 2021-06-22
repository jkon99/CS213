package photo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The structure of a user
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class User implements Serializable{
	/**
	 * The serial version of the user
	 */
	private static final long serialVersionUID = -2055916401879909972L;
	/**
	 * The list of albums for the user
	 */
	private List<Album> albums;
	/**
	 * The name of a user
	 */
	private String name;
	/**
	 * The list of tags for the user
	 */
	private List<String> tags;
	
	
	/**
	 * Creates a user with a specified name
	 * 
	 * @param name the name of the user
	 */
	public User(String name) {
		tags = new ArrayList<String>();
		albums = new ArrayList<Album>();
		this.name = name;
	}
	
	/**
	 * Deletes an album of a specified name
	 * 
	 * @param name the name of the album to be deleted
	 */
	public void deleteAlbum(String name) {
		for(Album a: albums) {
			if(a.getName().equals(name)) {
				albums.remove(a);
			}
		}
	}
	
	/**
	 * Sets the name of the user to a specified string
	 * 
	 * @param name the name to be changed to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of the User
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds an album to the user
	 * 
	 * @param album the album to attach to the user
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	/**
	 * Returns the list of albums attached to the user
	 * 
	 * @return a list containing the albums
	 */
	public List<Album> getAlbums(){
		return albums;
	}
	
	/**
	 * Adds a tag type to the user
	 * 
	 * @param tag the tag of type
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	/**
	 * Returns a list of tags of the user
	 * 
	 * @return a list of strings of tags
	 */
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * Returns the name of the album as a string
	 */
	public String toString() {
		return name;
	}
}
