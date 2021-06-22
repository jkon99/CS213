package com.example.android29;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android29.model.Album;
import com.example.android29.model.ImageAdapter;
import com.example.android29.model.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AlbumViewActivity extends AppCompatActivity {

    private GridView gridView;

    public final int REQUEST_CODE = 0;
    private List<Photo> photoList;
    private Album currentAlbum;
    private ArrayList<Album> albums;
    private FloatingActionButton create;
    private ImageAdapter ia;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumview);
        Bundle bundle = getIntent().getExtras();
        albums = MainActivity.albums;
        currentAlbum = MainActivity.album;
        photoList = currentAlbum.getPhotos();
        gridView = (GridView) findViewById(R.id.photoview);
        ia = new ImageAdapter(AlbumViewActivity.this, photoList);

        gridView.setAdapter(ia);
        registerForContextMenu(gridView);

        create = (FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                addPhoto.addCategory(Intent.CATEGORY_OPENABLE);
                addPhoto.setType("image/*");
                startActivityForResult(addPhoto, REQUEST_CODE);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent bigImage = new Intent(AlbumViewActivity.this, ImageActivity.class);
                MainActivity.photo = currentAlbum.getPhotos().get(position);
                startActivity(bigImage);
            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId() == R.id.photoview) {
            GridView gv = (GridView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Photo photo = (Photo) gv.getItemAtPosition(acmi.position);

            menu.add("Move");
            menu.add("Delete");
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String type = item.getTitle().toString();
        if(type.equals("Delete")){
            photoList.remove(info.position);
            save();
            ia.notifyDataSetChanged();
            Context context = getApplicationContext();
            String error = "Photo deleted";
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            return true;
        }
        else if(type.equals("Move")){
            PopupMenu pop = new PopupMenu(AlbumViewActivity.this, gridView);
            int i = 0;
            for(Album a : albums)
                pop.getMenu().add(Menu.NONE, i++, Menu.NONE, a.getName());
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Photo photo = currentAlbum.getPhotos().get(info.position);
                    if(albums.get(item.getItemId())!=currentAlbum) {
                        System.out.println("MOVE");
                        albums.get(item.getItemId()).getPhotos().add(photo);
                        currentAlbum.getPhotos().remove(photo);
                        save();
                        ia.notifyDataSetChanged();
                    }
                    return true;
                }
            });
            pop.show();

            return true;
        }
        return super.onContextItemSelected(item);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && data != null){
            Uri uri = null;
            if(data != null){
                uri = data.getData();
            }
            getApplicationContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String path = uri.toString();
            Photo newPhoto = new Photo(path);
            currentAlbum.addPhoto(newPhoto);
            save();
            gridView = findViewById(R.id.photoview);
            photoList = currentAlbum.getPhotos();

            ia.notifyDataSetChanged();
            gridView.setAdapter(ia);
        }
    }

    protected void onStop() {
        super.onStop();
        save();
    }

    public void save(){
        try {
            FileOutputStream fos = openFileOutput("albums.dat", 0);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Album a : albums) {
                System.out.println(a.getName());
                for(Photo p: a.getPhotos())
                    System.out.println(a.getName()+ " " +p.getPath());
                oos.writeObject(a);
            }
            oos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

}
