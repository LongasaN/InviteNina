package com.example.susie.invite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    ListView hostListView;
    Hosts hostsAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, null);

        hostListView = (ListView) findViewById(R.id.hostListView);

        hostsAdapter = new Hosts(this, dbHandler.getHosts(), 0);

        hostListView.setAdapter(hostsAdapter);

        // The subtitle displays how many hosts were listed in the app
        toolbar.setSubtitle("Hosts listed: " + dbHandler.getHostTotal());

        // Needs and on Item Click listener to pass called
        hostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start up the ViewList Activity Class
                intent = new Intent(MainActivity.this, AddMeeting.class);
                intent.putExtra("_id", id);
                startActivity(intent);
            }
        });

    }

    public void addHost (View view){
        intent = new Intent(this, AddHost.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
