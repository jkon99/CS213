package com.example.android29;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android29.model.Album;
import com.example.android29.model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public static ArrayList<Album> albums;
    public static Album album;
    public static Photo photo;
    private FloatingActionButton create;
    private Button search;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //detail more activity here for home screen, initialize list of albums and redirects to other xml
        albums  = new ArrayList<Album>();

        try {
            FileInputStream fis = openFileInput("albums.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Album temp = (Album)ois.readObject();
            while (temp!= null) {
                albums.add(temp);
                temp = (Album) ois.readObject();
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {

        }
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { search(v);}
        });

        listView = findViewById(R.id.albumList);
        registerForContextMenu(listView);
        create = (FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create(v);
            }
        });
        listView.setAdapter(
                new ArrayAdapter<Album>(this, R.layout.albums, albums));

        ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                album = albums.get(position);
                Intent CurrentAlbum = new Intent(MainActivity.this, AlbumViewActivity.class);
                startActivity(CurrentAlbum);
            }
        });
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId() == R.id.albumList) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Album album = (Album) lv.getItemAtPosition(acmi.position);

            menu.add("Edit");
            menu.add("Delete");
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        String type = item.getTitle().toString();
        if(type.equals("Edit")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Enter the album's new name:");

            EditText newName = new EditText(MainActivity.this);
            alertDialog.setView(newName);

            alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newAlbumName = newName.getText().toString().trim();
                    if (newAlbumName == null || newAlbumName.isEmpty()) {
                        Context context = getApplicationContext();
                        String error = "Album name cannot be blank";
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean sameName = false;
                    for (Album a : albums) {
                        if (a.getName().equals(newAlbumName)) {
                            sameName = true;
                            break;
                        }
                    }
                    if (sameName) {
                        Context context = getApplicationContext();
                        String error = "Album name already exists";
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    albums.get(pos).setName(newAlbumName);
                    save();
                    ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        else if(type.equals("Delete")){
            albums.remove(pos);
            save();
            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
            Context context = getApplicationContext();
            String error = "Album deleted";
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onContextItemSelected(item);
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

    protected void onStop() {
        super.onStop();
        save();
    }

    public void create(View view) { //when create button clicked pop up new album to make
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Enter the new album name:");

        EditText name = new EditText(this);

        alertDialog.setView(name);
        alertDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newAlbumName = name.getText().toString().trim();
                if (newAlbumName == null || newAlbumName.isEmpty()) {
                    Context context = getApplicationContext();
                    String error = "Album name cannot be blank";
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean sameName = false;
                for (Album a : albums) {
                    if (a.getName().equals(newAlbumName)) {
                        sameName = true;
                        break;
                    }
                }
                if (sameName) {
                    Context context = getApplicationContext();
                    String error = "Album name already exists";
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    return;
                }
                Album temp = new Album(newAlbumName);
                albums.add(temp);
                save();
                ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public void search(View view) {
        //go to search xml
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(i);
    }
}