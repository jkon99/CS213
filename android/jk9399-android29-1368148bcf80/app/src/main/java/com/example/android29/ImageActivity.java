package com.example.android29;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android29.model.Album;
import com.example.android29.model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    public ImageView imageView;
    public FloatingActionButton floatingActionButton;
    public TextView location;
    public TextView person;
    public Photo photo;
    public Button next;
    public Button last;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity);
        imageView = findViewById(R.id.imageView2);
        floatingActionButton = findViewById(R.id.addtag);
        location = findViewById(R.id.Location);
        person = findViewById(R.id.Person);
        photo = MainActivity.photo;
        next = findViewById(R.id.nextPhoto);
        last = findViewById(R.id.lastPhoto);
        imageView.setImageURI(Uri.parse(photo.getPath()));
        if(photo.getLocation()!=null) location.setText(getString(R.string.locationstring).concat(photo.getLocation()));
        if(photo.getPerson()!= null) person.setText(getString(R.string.personString).concat(photo.getPerson()));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop = new PopupMenu(ImageActivity.this, floatingActionButton);
                pop.getMenu().add(Menu.NONE, 0, Menu.NONE, "Person Tag");
                pop.getMenu().add(Menu.NONE, 1, Menu.NONE, "Location Tag");

                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String type = item.getTitle().toString();
                        if(type.equals("Person Tag")){
                            AlertDialog.Builder alert = new AlertDialog.Builder(ImageActivity.this);
                            alert.setTitle("Person Tag");
                            alert.setMessage("Please enter a value for this tag (Leave Empty to Delete)");

                            EditText input = new EditText(ImageActivity.this);
                            alert.setView(input);

                            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tag = input.getText().toString().trim();
                                    if (tag.isEmpty()) {
                                        photo.setPerson(tag);
                                        Context context = getApplicationContext();
                                        CharSequence text = "Person Tag Deleted";
                                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                                        person.setText("");
                                        return;
                                    }
                                    photo.setPerson(tag);
                                    save();
                                    person.setText(getString(R.string.personString).concat(photo.getPerson()));
                                }
                            });

                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alert.create().show();
                            return true;
                        }
                        else if(type.equals("Location Tag")){
                            AlertDialog.Builder alert = new AlertDialog.Builder(ImageActivity.this);
                            alert.setTitle("Location Tag");
                            alert.setMessage("Please enter a value for this tag (Leave Empty to Delete)                                                                                                                                                                                                                                                                              ");

                            EditText input = new EditText(ImageActivity.this);
                            alert.setView(input);

                            alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tag = input.getText().toString().trim();
                                    if (tag.isEmpty()) {
                                        photo.setLocation(tag);
                                        Context context = getApplicationContext();
                                        CharSequence text = "Location Tag Deleted";
                                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                                        location.setText("");
                                        return;
                                    }
                                    photo.setLocation(tag);
                                    save();
                                    location.setText(getString(R.string.locationstring).concat(photo.getLocation()));
                                }
                            });

                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alert.create().show();
                            return true;
                        }
                        return true;
                    }
                });

                pop.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Photo> temp = MainActivity.album.getPhotos();
                int i;
                for(i = 0; i<temp.size(); i++){
                    if(temp.get(i).equals(photo)){
                        break;
                    }
                }
                int next = i--;
                if(i == -1)
                    i += temp.size();
                photo = temp.get(i);
                imageView.setImageURI(Uri.parse(photo.getPath()));
                if(photo.getLocation()!=null) location.setText(getString(R.string.locationstring).concat(photo.getLocation()));
                else location.setText("");
                if(photo.getPerson()!= null) person.setText(getString(R.string.personString).concat(photo.getPerson()));
                else person.setText("");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Photo> temp = MainActivity.album.getPhotos();
                int i;
                for(i = 0; i<temp.size(); i++){
                    if(temp.get(i).equals(photo)){
                        break;
                    }
                }
                int next = i++;
                if(i == temp.size())
                    i -= temp.size();
                photo = temp.get(i);
                imageView.setImageURI(Uri.parse(photo.getPath()));
                if(photo.getLocation()!=null) location.setText(getString(R.string.locationstring).concat(photo.getLocation()));
                else location.setText("");
                if(photo.getPerson()!= null) person.setText(getString(R.string.personString).concat(photo.getPerson()));
                else person.setText("");
            }
        });


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
