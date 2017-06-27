package com.example.srv_twry.studentcompanion.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.example.srv_twry.studentcompanion.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by srv_twry on 26/6/17.
 */

public class PDFFilesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> filePaths;
    private static LayoutInflater inflater;

    public PDFFilesAdapter(Context context , ArrayList<String> filePaths){
        this.context = context;
        this.filePaths = filePaths;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.pdf_file_list_single_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.textView = (TextView) convertView.findViewById(R.id.file_name);
            holder.mRipple = (RippleView) convertView.findViewById(R.id.ripple_view_pdf);
            convertView.setTag(holder);
        }

        //set the data to the view
        String[] name = filePaths.get(position).split("/");
        holder.textView.setText(name[name.length -1]);

        holder.mRipple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(R.string.title_pdf_options)
                        .items(R.array.pdf_dialog_options)
                        .itemsIds(R.array.pdf_dialog_itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                switch (which) {
                                    case 0:
                                        //open pdf
                                        openPDF(filePaths.get(position));
                                        break;


                                    case 1:
                                        //delete the pdf file.
                                        boolean success = deletePDF(filePaths.get(position));
                                        if (success){
                                            filePaths.remove(position);
                                        }
                                        break;

                                }
                            }
                        })
                        .show();
                //notifyDataSetChanged();
                // notifyDataSetInvalidated();
            }
        });

        return convertView;
    }

    //Helper method to delete the pdf file
    private boolean deletePDF(String s) {
        File file = new File(s);
        if (file.exists()){
            if (file.delete()){
                Toast.makeText(context,"Successfully deleted selected file",Toast.LENGTH_SHORT).show();
                return true;
            }else{
                Toast.makeText(context,"Cannot delete selected file",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

    //Helper method to open the PDF file
    private void openPDF(String s) {
        File file = new File(s);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"PDF viewer");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try{
            context.startActivity(intent);
        }catch(ActivityNotFoundException e){
            e.printStackTrace();
            Toast.makeText(context,"No suitable application found !",Toast.LENGTH_LONG).show();
        }

    }

    static class ViewHolder {

        TextView textView;
        RippleView mRipple;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
