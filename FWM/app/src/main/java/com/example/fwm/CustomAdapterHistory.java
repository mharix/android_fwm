package com.example.fwm;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fwm.ui.myeventRegDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterHistory implements ListAdapter {
    ArrayList<SubjectDataHistory> arrayList;
    Context context;
    public CustomAdapterHistory(Context context, ArrayList<SubjectDataHistory> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        SubjectDataHistory subjectData = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row_history, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //actin on row clcik
                   ListView list =parent.findViewById(R.id.list);

                    String UserInfo = list.getItemAtPosition(position).toString();
                    //String userId = UserInfo.substring(0, UserInfo .indexOf(" "));

                    //Log.e("userid=",userId);
                    //Toast.makeText(context, "clicked id=", Toast.LENGTH_SHORT).show();
                }
            });
            TextView tittle = convertView.findViewById(R.id.title);
            TextView date = convertView.findViewById(R.id.date);
            ImageView imag = convertView.findViewById(R.id.list_image);
            tittle.setText(subjectData.SubjectName);
            date.setText(subjectData.Date);
             imag.setId(Integer.parseInt(subjectData.Id));
            imag.setOnClickListener(getOnClick());
            Picasso.get()
                    .load(subjectData.Image)
                    .into(imag);
        }
        return convertView;
    }

    boolean frag_clik=true;
    private View.OnClickListener getOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ON ROW click goto event regestration fragment
                Log.e("inside custom event","0");





            }
        };

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }


}//class end