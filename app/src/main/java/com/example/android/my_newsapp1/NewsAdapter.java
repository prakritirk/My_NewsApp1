package com.example.android.my_newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PRAJBHANDA on 7/17/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> news) {
        super( context, 0, news );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from( getContext() ).inflate( R.layout.news_list_item, parent, false );
        }


        News currentNews = getItem( position );

        TextView titleView = (TextView) listItemView.findViewById( R.id.title );

        titleView.setText( currentNews.getTitle() );

        TextView categoryView = (TextView) listItemView.findViewById( R.id.category );

        categoryView.setText( currentNews.getSection() );


        TextView dateView = (TextView) listItemView.findViewById( R.id.date );
        //Format the date to remove time stamp
        String date = currentNews.getDate();
        try {
            Date dateNews =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date);

            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(dateNews);
            dateView.setText(formattedDate);
           // Log.d("ADebugTag", "Value: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return listItemView;
    }
}