package com.example.srv_twry.studentcompanion;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.srv_twry.studentcompanion.Adapters.PDFFilesAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFCreatorHomeActivity extends AppCompatActivity {

    @BindView(R.id.pdf_list_view_files) ListView pdfFilesListView;

    PDFFilesAdapter pdfFilesAdapter;
    File pdfFolder;
    File[] filesInDirectory;
    ArrayList<String> filePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfcreator_home);
        setTitle(getResources().getString(R.string.pdf_creator));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //create or open the folder
        pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ getResources().getString(R.string.created_pdf)+"/");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        //Initialize the list view with blank data while the files are being loaded
        filesInDirectory = pdfFolder.listFiles();
        filePaths = new ArrayList<>();
        pdfFilesAdapter = new PDFFilesAdapter(this,filePaths);
        pdfFilesListView.setAdapter(pdfFilesAdapter);

        loadFiles();
    }

    private void loadFiles() {
        filePaths = new ArrayList<>();
        filesInDirectory = pdfFolder.listFiles();

        if (filesInDirectory == null){
            //No files in the directory.
            Log.v("Load files","No files found");
        }else{
            for (File file : filesInDirectory) {
                if (!file.isDirectory() && file.getName().endsWith(".pdf")) {
                    filePaths.add(file.getPath());
                    Log.v("addingFile: ", file.getName());

                }
            }
        }

        //Now that loading is finished , reset the adapter
        pdfFilesAdapter = new PDFFilesAdapter(this,filePaths);
        pdfFilesListView.setAdapter(pdfFilesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFiles();    //Refresh the layout
    }
}
