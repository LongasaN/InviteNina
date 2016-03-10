package com.example.susie.invite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by susie on 3/3/2016.
 */
public class Hosts extends CursorAdapter {

    public Hosts (Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_host, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ((TextView) view.findViewById(R.id.nameTextView)).
                setText("Host " + cursor.getString(cursor.getColumnIndex("host_name")));
        ((TextView) view.findViewById(R.id.emailTextView)).
                setText("Email " + cursor.getString(cursor.getColumnIndex("host_email")));

    }
}
