package photo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The structure of a photo
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class Photo implements Serializable{
	
	/**
	 * The serial version of the photo 
	 */
	private static final long serialVersionUID = 3009566018279169179L;
	/**
	 * The path of the photo on the computer
	 */
	private String path;
	/**
	 * The user set phrase associated with the photo
	 */
	private String caption;
	/**
	 * The date as milliseconds from the epoch
	 */
	private Long date;
	/**
	 * A map of tags and their values
	 */
	private Map<String, String> tags;
	
	
	/**
	 * Creates an instance of a photo with a specified path and date
	 * 
	 * @param path the path of were the photo is in the computer
	 * @param date the date of the photo was created
	 */
	public Photo(String path, Long date) {
		tags = new HashMap<String, String>();
		this.path = path;
		this.date = date;
	}

	/**
	 * Gets the path of the photo
	 * 
	 * @return the path of the photo on the computer
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Gets the date of the photo
	 * 
	 * @return a long contain the number of milliseconds before the epoch
	 */
	public long getDate() {
		return date;
	}

	/**
	 * Gets the User generated caption
	 * 
	 * @return the phrase set by the user
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Sets the caption to a specified phrase
	 * 
	 * @param cap the phrase to be set to
	 */
	public void setCaption(String cap) {
		caption = cap;
	}
	
	/**
	 * Adds a value to a certain tag
	 * 
	 * @param tag the tag type
	 * @param value the value of the tag
	 */
	public void addTag(String tag, String value) {
		tags.put(tag, value);
	}
	
	/**
	 * Returns the value to a specified tag type
	 * 
	 * @param tag the tag type
	 * @return the value of the tag
	 */
	public String getTagValue(String tag) {
		return tags.get(tag);
	}
}
