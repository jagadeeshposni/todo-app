package com.jposni.todo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jposni.todo.adapter.TodoCursorAdapter;
import com.jposni.todo.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    EditText thing;
    DBHelper dbHelper;
    ListView listView;
    TodoCursorAdapter cursorAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this, "todo", null, 1);

        displayListView();

    }

    private void displayListView() {
        listView = (ListView) findViewById(R.id.listview);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from things",null);

        cursorAdapter = new TodoCursorAdapter(this, cursor, false);
        listView.setAdapter(cursorAdapter);
    }

    public void addThingToDb(View view) {
        thing = (EditText) findViewById(R.id.thing);
        dbHelper.getWritableDatabase().execSQL("insert into things (thing, isdone) values(\"" + thing.getText().toString() + "\",\"false\")");
        dbHelper.close();
        cursorAdapter.notifyDataSetChanged();
        displayListView();
        thing.setText("");
    }

    public void updateThingToDone(View view){
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        String thingDone = checkBox.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put("isdone", true);
//        dbHelper.getWritableDatabase().update("things", cv, "thing=?", new String[]{thingDone});
        dbHelper.getWritableDatabase().execSQL("update things set isdone=\"true\" where thing=\""+thingDone+"\"");
        dbHelper.close();
//        displayListView();
        cursorAdapter.notifyDataSetChanged();
    }
}
