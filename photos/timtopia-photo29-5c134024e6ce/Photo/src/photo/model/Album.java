package photo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The structure of an album
 * 
 * 
 * @author Timothy Zhang
 * @author Jonathan Konopka
 */
public class Album implements Serializable{
	/**
	 * The serial version of the album 
	 */
	private static final long serialVersionUID = -7785900896802547814L;
	/**
	 * The list of photos within an album
	 */
	private List<Photo> photos;
	/**
	 * The name of the album
	 */
	private String name;
	
	/**
	 * Creates an instance of an album with a specified name
	 * 
	 * @param name the name of album
	 */
	public Album(String name) {
		photos = new ArrayList<Photo>();
		
		this.name = name;
	}
	
	/**
	 * Changes the name of album to a specified name
	 * 
	 * @param name the name to be changed to
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the current name of the album
	 * 
	 * @return the name of the album
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a photo to the album
	 * 
	 * @param photo the photo to be added
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * Gets the collection of the photos
	 * 
	 * @return the list of photos in the album
	 */
	public List<Photo> getPhotos(){
		return photos;
		
	}
	
	/**
	 * Checks if the photo already exists in the album
	 * 
	 * @param photo the photo to be added
	 * @return <code>true</code> if the photo is in the album; <code>false</code> if not.
	 */
	public boolean containsPhoto(Photo photo) {
		for(Photo p: photos) {
			if(p.getPath().equals(photo.getPath())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes a photo from the Album
	 * 
	 * @param photo the photo to be removed
	 */
	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}
	
	/**
	 * Gets the earliest date of a photo in the album
	 * 
	 * @return the earliest date of a photo in the album
	 */
	public String earliestDate() {
		long earlyDate = Long.MAX_VALUE;
		for(Photo p:photos) {
			if(p.getDate()<earlyDate)
				earlyDate = p.getDate();
		}
		Date date = new Date(earlyDate);
		String dateString = date.toString();
		return dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24);
	}
	
	/**
	 * Gets the latest date of a photo in the album
	 * 
	 * @return the latest date of a photo in the album
	 */
	public String latestDate() {
		long lateDate = Long.MIN_VALUE;
		for(Photo p:photos) {
			if(p.getDate()>lateDate)
				lateDate = p.getDate();
		}
		Date date = new Date(lateDate);
		String dateString = date.toString();
		return dateString.substring(4,7) + " " + dateString.substring(8,10) + ", " + dateString.substring(24);
	}
	
	/**
	 * Gets the number of photos in the album as a string
	 * 
	 * @return A string of the number of photos in the album
	 */
	public String numberOfPhotos(){
		int number = 0;
		for(@SuppressWarnings("unused") Photo p: photos) {
			number++;
		}
		return Integer.toString(number);
	}
	
	
	/**
	 *	Returns the number of photos in the album
	 */
	public String toString() {
		String temp = name + " Number of Photos: " + numberOfPhotos();
		int number = 0;
		for(@SuppressWarnings("unused") Photo p: photos) {
			number++;
		}
		if(number>0)
			temp = temp + " Date: " + earliestDate() + "-" + latestDate();
		
		return temp;
	}
	
}
