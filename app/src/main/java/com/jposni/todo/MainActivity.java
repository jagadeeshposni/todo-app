package com.jposni.todo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jposni.todo.adapter.TodoCursorAdapter;
import com.jposni.todo.db.DBHelper;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    EditText thing;
    DBHelper dbHelper;
    ListView listView;
    TodoCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(this, "todo", null, 1);
        final Button button = (Button) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thing = (EditText) findViewById(R.id.thing);
                int thingLength = thing.getText().toString().trim().length();
                if (thingLength > 0) {
                    String insertSQL = "insert into things (thing) values('" + thing.getText().toString() + "')";
                    dbHelper.getWritableDatabase().execSQL(insertSQL);
                    dbHelper.close();
                    thing.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Cannot add empty text!", Toast.LENGTH_SHORT).show();
                }
                displayListView();

            }
        });

        displayListView();

    }

    private void displayListView() {
        listView = (ListView) findViewById(R.id.listview);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from things", null);

        cursorAdapter = new TodoCursorAdapter(this, cursor, false, dbHelper);
        listView.setAdapter(cursorAdapter);
    }

    public void addThingToDb(View view) {
        CheckBox checkBox = (CheckBox) view;
        ContentValues cv = new ContentValues();
        String[] args = new String[]{checkBox.getText().toString()};
        if (checkBox.isChecked()) {
            cv.put("isdone", 1);
            checkBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            checkBox.setPaintFlags(checkBox.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            cv.put("isdone", 0);
        }
//                dbHelper.getWritableDatabase().execSQL(sql);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.update("things", cv, "thing=?", args);
        Log.d(TAG, "onCheckedChanged: No. of rows updated:" + count);
        dbHelper.close();
    }

}
