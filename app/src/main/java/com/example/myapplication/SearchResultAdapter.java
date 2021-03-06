package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultAdapter extends BaseAdapter {
    private ArrayList<SearchResultData> list;
    private LayoutInflater layoutInflater;

    public SearchResultAdapter(Context context, ArrayList<SearchResultData> list){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        //these two methods tells the system that whether adapter
        // needs to recycle the rows or not, and also how many types of rows we want to display
        return getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgMeal);
            Glide.with(convertView).load(R.drawable.loading).into(imageView);

            viewHolder = new ViewHolder(
                    (ConstraintLayout)convertView.findViewById(R.id.constraintLayoutItem),
                    (TextView)convertView.findViewById(R.id.tvName),
                    (TextView)convertView.findViewById(R.id.tvCreator),
                    (ImageView)convertView.findViewById(R.id.imgMeal));
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SearchResultData data = (SearchResultData)getItem(position);


//        viewHolder.tvName.setText(data.getName());
//        viewHolder.tvIngredient.setText(data.getIngredients());
//        viewHolder.tvInstruction.setText(data.getInstruction());

        if(data.getTitle().length()>14){
            viewHolder.tvName.setText(data.getTitle().substring(0,14)+"...");
        }
        else{
            viewHolder.tvName.setText(data.getTitle());
        }
        viewHolder.tvCreator.setText(data.getName());

        if(!data.getImg_path().equals("none")){
            if(data.getImg_path().startsWith("/media")){
                data.setImg_path(MainActivity.domain+data.getImg_path());
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = getBitmapFromURL(data.getImg_path());
                    viewHolder.imgMeal.post(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.imgMeal.setImageBitmap(bitmap);
                        }
                    });
                }
            }).start();
        }

        return convertView;
    }

    private class ViewHolder {
        private ConstraintLayout constraintLayoutItem;
        private TextView tvName,tvCreator;
        private ImageView imgMeal;

        public ViewHolder(ConstraintLayout constraintLayoutItem,TextView tvName,TextView tvCreator,ImageView imgMeal){
            this.constraintLayoutItem = constraintLayoutItem;
            this.tvName = tvName;
            this.tvCreator = tvCreator;
            this.imgMeal = imgMeal;
        }
    }

    public static Bitmap getBitmapFromURL(String src){
        try{
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
