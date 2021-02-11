//NAMES
//Jonathan Konopka
//

package songlib.app;

public class Song {
	private String songTitle;
	private String songAuthor;
	private String songAlbum;
	private String songYear;
	
	public Song(String title, String author, String album, String year) {   //creates a Song class, with following details
		songTitle = title;
		songAuthor = author;
		songAlbum = album;
		songYear = year;
	}
	
	public void setTitle(String title) {
		songTitle = title;
	}
	public void setAuthor(String author) {
		songAuthor = author;
	}
	public void setAlbum(String album) {
		songAlbum = album;
	}
	public void setYear(String year) {
		songYear = year;
	}
	public String getTitle() {
		return songTitle;
	}
	public String getLTitle() {
		return songTitle.toLowerCase();
	}
	public String getAuthor() {
		return songAuthor;
	}
	public String getAlbum() {
		return songAlbum;
	}
	public String getYear() {
		return songYear;
	}
	
	public String toString() {
		String temp = songTitle + " | " + songAuthor;
		return temp;
	}
}
