package com.example.learnrevisetwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView txt;
    private Button btn;

    private ExpandableListView expandableListView;
    private List<String> langs;
    private Map<String, List<String>> topics;
    private ExpandableListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createView();
        setUpNavigationView();
        fillExListData();

        listAdapter = new MyExListAdapter(this, langs, topics);
        expandableListView.setAdapter(listAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setChildDivider(null);
        expandableListView.setDivider(null);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this, topics.get(langs.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void fillExListData() {
        langs = new ArrayList<>();
        topics = new HashMap<>();

        langs.add("C");
        langs.add("java");

        List<String> java = new ArrayList<>();
        java.add("Super");
        java.add("Encapsulation");
        java.add("Inheritance");

        List<String> c = new ArrayList<>();
        c.add("Method");
        c.add("Pointers");
        c.add("Arrrays");

        topics.put(langs.get(0),c);
        topics.put(langs.get(1),java);

    }

    private void setUpNavigationView() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        btn = navigationView.findViewById(R.id.btn);
        btn.setText("Hello");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:

                        drawerLayout.closeDrawers();
                        txt.setText("Profile");
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        drawerLayout.closeDrawers();
                        txt.setText("Settings");
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mainScreen:
                        drawerLayout.closeDrawers();
                        txt.setText("Main Screen");
                        Toast.makeText(MainActivity.this, "Main Screen", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
    }

    private void createView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        expandableListView = findViewById(R.id.expandableListView);
        txt = findViewById(R.id.txt);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return actionBarDrawerToggle.onOptionsItemSelected(item) ||super.onOptionsItemSelected(item);
    }
}