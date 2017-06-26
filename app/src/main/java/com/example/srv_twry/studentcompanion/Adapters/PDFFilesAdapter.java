package com.example.srv_twry.studentcompanion.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by srv_twry on 26/6/17.
 */

public class PDFFilesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> filePaths;

    public PDFFilesAdapter(Context context , ArrayList<String> filePaths){
        this.context = context;
        this.filePaths = filePaths;
    }

    @Override
    public int getCount() {
        return filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
