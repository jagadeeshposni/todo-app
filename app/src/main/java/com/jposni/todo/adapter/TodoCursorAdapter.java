package com.jposni.todo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.jposni.todo.R;


public class TodoCursorAdapter extends CursorAdapter {

    public TodoCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        String thingText = cursor.getString(cursor.getColumnIndex("thing"));
        String isdone = cursor.getString(cursor.getColumnIndex("isdone"));

        checkBox.setText(thingText);
        if(isdone.equalsIgnoreCase("true")){
            checkBox.setChecked(true);
        }
    }
}
