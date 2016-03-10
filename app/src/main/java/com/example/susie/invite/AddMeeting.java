package com.example.susie.invite;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMeeting extends AppCompatActivity {

    Intent intent;
    DBHandler dbHandler;
    Bundle bundle;
    long id;
    Calendar calendar;
    EditText nameEditText;
    EditText locationEditText;
    EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = this.getIntent().getExtras();
        id = bundle.getLong("_id");

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDueDate();
            }
        };

        dateEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        AddMeeting.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        dbHandler = new DBHandler(this, null);
    }

    public void updateDueDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateEditText.setText(sdf.format(calendar.getTime()));
    }

    public void addMeeting (MenuItem menuItem){

        String name = nameEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String date = dateEditText.getText().toString();

        // Validate the information
        if (name.trim().equals("") || location.trim().equals("") || date.trim().equals("")){
            Toast.makeText(this, "Please enter a name, location and date!", Toast.LENGTH_LONG).show();
        } else {
            dbHandler.addMeeting(name, location, date, (int)id);
            Toast.makeText(this, "Meeting added!", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_invite :
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_host :
                intent = new Intent(this, AddHost.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
