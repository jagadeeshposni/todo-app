package com.jposni.todo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;

import com.jposni.todo.R;
import com.jposni.todo.db.DBHelper;

import static android.content.ContentValues.TAG;


public class TodoCursorAdapter extends CursorAdapter {

    DBHelper dbHelper;

    public TodoCursorAdapter(Context context, Cursor c, boolean autoRequery, DBHelper dbHelper) {
        super(context, c, autoRequery);
        this.dbHelper = dbHelper;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        CheckBox checkBox = view.findViewById(R.id.checkBox);
         String thingText = cursor.getString(cursor.getColumnIndex("thing"));
         int isdone = cursor.getInt(cursor.getColumnIndex("isdone"));

        checkBox.setText(thingText);
        if (isdone == 1) {
            checkBox.setChecked(true);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            checkBox.setChecked(false);
        }



    }
}
