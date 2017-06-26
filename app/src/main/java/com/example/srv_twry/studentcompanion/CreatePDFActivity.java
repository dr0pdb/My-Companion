package com.example.srv_twry.studentcompanion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePDFActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICKER = 13;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;


    @BindView(R.id.select_images) Button selectImages;
    @BindView(R.id.create_pdf) Button createPdf;

    ArrayList<String> imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        setTitle(getResources().getString(R.string.create_pdf));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        imageUri = new ArrayList<>();

        selectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionAndStart();
            }
        });

        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdfFromImage();
            }
        });

    }

    private void createPdfFromImage() {
        if (imageUri.size() <= 0){
            Toast.makeText(CreatePDFActivity.this,"Select Images First!",Toast.LENGTH_SHORT).show();
        }else{
            //TODO: Start the pdf file creation operation here.
        }
    }

    private void requestPermissionAndStart() {
        // Get runtime permissions if build version >= Android M only once
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(CreatePDFActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT);
            } else {
                openImageSelector();
            }
        } else {
            openImageSelector();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageSelector();
                } else {
                    Toast.makeText(CreatePDFActivity.this, "Cannot create pdf without Permissions!", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void openImageSelector() {
        ImagePicker.create(this)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .multi() // multi mode (default mode)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .start(REQUEST_CODE_PICKER); // start image picker activity with request code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            for (Image image: images){
                imageUri.add(image.getPath());
                Log.v("Adding images",image.getName());
            }
            Toast.makeText(this,"Images added !",Toast.LENGTH_SHORT).show();
        }
    }
}
