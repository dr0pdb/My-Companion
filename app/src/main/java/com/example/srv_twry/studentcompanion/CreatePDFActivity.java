package com.example.srv_twry.studentcompanion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePDFActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {

    private static final int REQUEST_CODE_PICKER = 13;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    private static final int CREATE_PDF_LOADER_ID = 23;

    @BindView(R.id.select_images) Button selectImages;
    @BindView(R.id.create_pdf) Button createPdf;

    ArrayList<String> imageUri;
    String filename;

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
            new MaterialDialog.Builder(CreatePDFActivity.this)
                    .title("Creating PDF")
                    .content("Enter file name")
                    .input("Example : Name", null, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            if (input == null) {
                                Toast.makeText(CreatePDFActivity.this, "Name cannot be blank", Toast.LENGTH_LONG).show();
                            } else {
                                filename = input.toString();

                                //Start the loader here.
                                getSupportLoaderManager().initLoader(CREATE_PDF_LOADER_ID,null,CreatePDFActivity.this);

                            }
                        }
                    })
                    .show();

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



    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Boolean>(CreatePDFActivity.this) {

            //Create the dialog to be shown while creating pdf
            MaterialDialog.Builder builder = new MaterialDialog.Builder(CreatePDFActivity.this)
                    .title("Please Wait")
                    .content("Creating PDF...")
                    .cancelable(false)
                    .progress(true, 0);
            MaterialDialog progressDialog = builder.build();

            @Override
            protected void onStartLoading() {
                //Show the dialog to wait.
                progressDialog.show();
                forceLoad();
                Log.v("Loader","Started Loader");
            }

            @Override
            public Boolean loadInBackground() {
                //Create the pdf here
                Log.v("Loader","Started loadInBackground");
                //create or open the folder
                File pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ getResources().getString(R.string.created_pdf)+"/");
                if (!pdfFolder.exists()) {
                    pdfFolder.mkdir();
                }
                Log.v("Loader","Created pdf folder");
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ getResources().getString(R.string.created_pdf)+"/";
                File file = new File(path);

                //setting the path of the pdf file
                path = path + filename + ".pdf";

                Document document = new Document(PageSize.A4, 38, 38, 50, 38);

                Rectangle documentRect = document.getPageSize();

                try{
                    //getting the instance of the PDfWriter
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

                    //opening the document
                    document.open();
                    Log.v("Loader","opened document");

                    for (int i = 0; i < imageUri.size(); i++) {


                        Bitmap bmp = BitmapFactory.decodeFile(imageUri.get(i));
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);


                        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imageUri.get(i));

                        //Setting the image to the document rectangle dimensions.
                        image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());


                        image.setAbsolutePosition((documentRect.getWidth() - image.getScaledWidth()) / 2, (documentRect.getHeight() - image.getScaledHeight()) / 2);

                        image.setBorder(com.itextpdf.text.Image.BOX);

                        image.setBorderWidth(15);

                        document.add(image);

                        document.newPage();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    document.close();
                    progressDialog.dismiss();
                    Log.v("Loader","closed document "+ path);
                }

                return Boolean.TRUE;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        imageUri.clear();
        Log.v("Loader","Done");
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }
}
