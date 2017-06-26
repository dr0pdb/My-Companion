package com.example.srv_twry.studentcompanion.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.srv_twry.studentcompanion.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        //TODO:set the onClickListener to the view

        return convertView;
    }

    static class ViewHolder {

        TextView textView;
        RippleView mRipple;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
