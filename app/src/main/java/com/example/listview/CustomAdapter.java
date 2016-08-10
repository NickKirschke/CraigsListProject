package com.example.listview;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import static com.example.listview.BikeData.*;

/**
 * Created by nickkirschke on 4/7/16.
 */
public class CustomAdapter extends BaseAdapter {

    private static final int LOC = 2;
    private static final int PRI = 1;
    private LayoutInflater inflater;
    private Activity_ListView myActivity;
    private static class ViewHolder {
        TextView price;
        TextView description;
        TextView model;
        ImageView picture;
        public int picPosition;
    }

    public void sortList(int iSort) {
        switch (iSort) {
            case LOC:
                Collections.sort(myActivity.bikeList, new LocationComparator());
                break;
            case PRI:
                Collections.sort(myActivity.bikeList, new PriceComparator());
                break;
            default:
                Collections.sort(myActivity.bikeList, new CompanyComparator());
                break;
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return Activity_ListView.getBikeListSize();
    }
    public CustomAdapter(Activity_ListView myActivity) {
        this.myActivity = myActivity;
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
        ViewHolder holder;
        if (convertView == null) {
            if (inflater == null) {
                inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_row_layout, null);
            holder = new ViewHolder();
            holder.price = (TextView) convertView.findViewById(R.id.Price);
            holder.description = (TextView) convertView.findViewById(R.id.Description);
            holder.model = (TextView) convertView.findViewById(R.id.Model);
            holder.picture = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.picPosition = position;
        String pictureTemp = myActivity.myURL+Activity_ListView.bikeList.get(position).PICTURE;
        new DownloadImageTask(Activity_ListView.bikeList.get(position).PICTURE, holder.picture).execute(pictureTemp);
        String temp = "$ " + Activity_ListView.bikeList.get(position).PRICE.toString();
        holder.price.setText(temp);
        holder.description.setText(Activity_ListView.bikeList.get(position).DESCRIPTION);
        holder.model.setText(Activity_ListView.bikeList.get(position).MODEL);

        return convertView;
    }
}
