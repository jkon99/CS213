package com.example.android29;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android29.model.Album;
import com.example.android29.model.ImageAdapter;
import com.example.android29.model.Photo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static List<Album> albums;
    public static Album currAlbum;
    public List<Photo> photos;
    //private static List<String> tags;
    public static List<Photo> searched;
    public GridView searchView;
    public ImageAdapter ia;
    public Button searchTags;
    public static Album searchedAlbum;

    //public static int checkIfSearched;
    public TextView location;
    public TextView person;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        //remember to iterate through albums and their photos for searches, all included
        albums = MainActivity.albums;
        location = findViewById(R.id.person);
        person = findViewById(R.id.location);
        //currAlbum = MainActivity.album;

        searched = new ArrayList<Photo>();
        searchView = (GridView) findViewById(R.id.searchView);
        searchedAlbum = new Album("");

        //System.out.println("reaches here in create");

        ia = new ImageAdapter(SearchActivity.this, searched);
        searchView.setAdapter(ia);
        registerForContextMenu(searchView);

        searchTags = (Button) findViewById(R.id.searchTags);
        searchTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { searchTags(v);}
        });

        //for clicking on photo and going to image view

               searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bigImage = new Intent(SearchActivity.this, ImageActivity.class);
                //MainActivity.photo = currAlbum.getPhotos().get(position);
                MainActivity.photo = searchedAlbum.getPhotos().get(position);
                startActivity(bigImage);
            }
        });

        //photos = currAlbum.getPhotos();

        //remember to implement autocomeplete so all values with starting text are included, case insensivitiy, only location and person tags
        //no date search
        //conjunctive searches?
        //dont need to save matches to an album, just display search results. dont add tag types

    }

    public void searchTags(View view) {
        //search for given results on click
        //pop up to enter tag

        //System.out.println("This button activates");
        //final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        LinearLayout lila = new LinearLayout(this);
        lila.setOrientation(LinearLayout.VERTICAL);
        //final EditText personIn = new EditText(this);
        EditText personIn = new EditText(this);
        //final EditText locationIn = new EditText(this);
        EditText locationIn = new EditText(this);
        lila.addView(personIn);
        lila.addView(locationIn);
        alert.setView(lila);

        alert.setTitle("Tag Search");
        alert.setMessage("Enter tags to search for person (first field) and then location (second field). Go back to album list to reset and do another search");

        //System.out.println("GOes through initializations");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //bottom is algorithm to execute after ok on pop up

                String personStr = personIn.getText().toString();
                String locationStr = locationIn.getText().toString();

                String currLocation = "";
                String currPerson = "";

                if (personStr.matches("") && locationStr.matches("") ) { //make sure tag values are not empty
                    return;
                }

                personStr = personStr.trim().toLowerCase();
                locationStr = locationStr.trim().toLowerCase();


                for (int i=0; i<albums.size();i++) {
                    currAlbum = albums.get(i);
                    photos = currAlbum.getPhotos();

                    //System.out.println("Access an album");

                    for (int j=0; j<photos.size();j++) {
                        // handle searches for location, person, and simultaenous tags as well as autocomplete (match substring)
                        //check for dupes
                        //make sure case insensititve
                        if (photos.get(j).getPerson() == null || photos.get(j).getLocation()==null) {
                            continue;
                        }
                        currPerson = photos.get(j).getPerson().toLowerCase();
                        currLocation = photos.get(j).getLocation().toLowerCase();

                        //System.out.println("Accessed a photo");

                        if ((currPerson.startsWith(personStr) && !personStr.matches("")) || (currLocation.startsWith(locationStr)&& !locationStr.matches(""))) { //maybe delete last part

                            searched.add(photos.get(j));

                            //System.out.println("Added photo");

                        }
                    }
                }

                for (int k=0; k<searched.size();k++) {

                    if (searched.get(k) == null) {
                        continue;
                    }
                    searchedAlbum.addPhoto(searched.get(k));
                    // && !searched.contains(photos.get(j))
                }
                person.setText("Person: ".concat(personStr));
                location.setText("Location: ".concat(locationStr));

                //System.out.println("Ok works");
            }                     });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.cancel();

                        //System.out.println("Cancel works");
                    }     });

        alert.show();
    }

    protected void onStop() {
        super.onStop();
        save();
    }

    public void save(){
        try {

            FileOutputStream fos = openFileOutput("albums.dat", 0);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Album a : MainActivity.albums) {
                oos.writeObject(a);
            }
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
