package com.eric_b.mynews;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MynewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        //FOR DESIGN
        private Toolbar toolbar;
        private DrawerLayout drawerLayout;
        private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynews);
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.activity_mynews_drawer_topstories :
                break;
            case R.id.activity_mynews_drawer_mostpopular:
                break;
            case R.id.activity_mynews_drawer_business:
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_mynews_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_mynews_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_mynews, menu);
        return true;
    }


}
