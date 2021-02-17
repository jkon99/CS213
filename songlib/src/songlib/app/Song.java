//NAMES
//Jonathan Konopka
//Timothy Zhang

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
	
	public boolean equals(Song song) {
		if(this.songTitle.toLowerCase().equals(song.getLTitle()) && this.songAuthor.toLowerCase().equals(song.getAuthor().toLowerCase())) {
			return true;
		}
		return false;
	}

	public String textbox() {
		String result = ("Title: " + songTitle + "\n" +
						 "Author: "  + songAuthor + "\n");
		if(songAlbum.length()>0) {
			result = result.concat("Album: " + songAlbum + "\n");
		}
		if(songYear.length()>0) {
			result = result.concat("Year Released: " + songYear);
		}
		return result;
	}


	
	public String former() {
		return (songTitle + "\t" + songAuthor + "\t" + songAlbum + "\t" + songYear + "\n");
	}
	
	public String toString() {
		String temp = songTitle + " | " + songAuthor;    //should accept everything as an input except the | character
		return temp;
	}
}
