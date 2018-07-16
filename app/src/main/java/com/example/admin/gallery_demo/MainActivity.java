package com.example.admin.gallery_demo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static int PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private List<String> mImageFileNames;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_galleryRecyclerView);
        String imageDirectory = Environment
                .getExternalStorageDirectory().toString() + "/Images";
        new Async().execute(imageDirectory);

        // Check Permission: Read external storage
        if(ContextCompat.checkSelfPermission
                (this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("need permission");
                builder.setMessage("this app need permissipn");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_READ_EXTERNAL_STORAGE );
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public class Async extends AsyncTask<String,Void,List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            // Get image directory

            File file = new File(strings[0]);
//            Log.e("thinh", imageDirectory + "");
            String[] extensions = getResources()
                    .getStringArray(R.array.image_extention);
            if (file != null) {
                mImageFileNames = FileLoader.loadFileName(file,
                        extensions);
            }
            return mImageFileNames;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            // RecyclerView
            mImageAdapter = new GalleryAdapter(strings, getBaseContext());
            RecyclerView.LayoutManager layoutManager = new
                    GridLayoutManager(MainActivity.this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mImageAdapter);
        }
    }



}
