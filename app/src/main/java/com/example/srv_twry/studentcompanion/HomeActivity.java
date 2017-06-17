package com.example.srv_twry.studentcompanion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Adapters.FeaturesRecyclerViewAdapter;
import com.example.srv_twry.studentcompanion.POJOs.Feature;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FeaturesRecyclerViewAdapter.FeaturesOnClickListener{


    ArrayList<Feature> featureArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Home");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        featureArrayList = fillWithData();
        RecyclerView featuresRecyclerView= (RecyclerView) findViewById(R.id.rv_features);
        FeaturesRecyclerViewAdapter featuresRecyclerViewAdapter = new FeaturesRecyclerViewAdapter(featureArrayList,this);
        int numberOfColumns = getResources().getInteger(R.integer.number_columns_grid_view_features);
        GridLayoutManager featuresGridLayoutManager = new GridLayoutManager(HomeActivity.this,numberOfColumns);
        featuresRecyclerView.setAdapter(featuresRecyclerViewAdapter);
        featuresRecyclerView.setLayoutManager(featuresGridLayoutManager);

    }

    // TODO: Solve the icons issue.
    // Method to create features arrayList
    private ArrayList<Feature> fillWithData() {
        ArrayList<Feature> featureArrayList = new ArrayList<>();
        featureArrayList.add(new Feature("Coding Calendar",R.mipmap.ic_code));
        featureArrayList.add(new Feature("Flash Cards",R.mipmap.ic_flash_cards));
        return featureArrayList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_coding_calendar) {

        } else if (id == R.id.nav_flash_cards) {

        }else if (id == R.id.nav_share){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Open the necessary activity responding to the clicks
    @Override
    public void onFeatureClicked(int position) {
        Toast toast = Toast.makeText(HomeActivity.this, "Item clicked "+ featureArrayList.get(position).getTitle(),Toast.LENGTH_SHORT);
        toast.show();
    }
}
