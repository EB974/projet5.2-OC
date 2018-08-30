package com.eric_b.mynews.controllers;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.eric_b.mynews.controllers.fragments.BusinessFragment;
import com.eric_b.mynews.controllers.fragments.MostPopularFragment;
import com.eric_b.mynews.controllers.fragments.TopStoriesFragment;
import com.eric_b.mynews.R;

public class MynewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //FOR FRAGMENTS
    // 1 - Declare fragment handled by Navigation Drawer
    private Fragment fragmentTop;
    private Fragment fragmentPopular;
    private Fragment fragmentBusiness;

    //FOR DATAS
    // 2 - Identify each fragment with a number
    private static final int FRAGMENT_TOP = 0;
    private static final int FRAGMENT_POPULAR = 1;
    private static final int FRAGMENT_BUSSINESS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynews);



        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();


        this.configureViewPagerAndTabs();
        this.configureNavigationView();
        this.showFirstFragment();

    }

    private void configureViewPagerAndTabs(){
        // 1 - Get ViewPager from layout
        ViewPager pager = findViewById(R.id.activity_mynews_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // 1 - Get TabLayout from layout
        TabLayout tabs= findViewById(R.id.activity_mynews_tabs);

        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);

        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        switch (id){
            case R.id.activity_mynews_drawer_topstories :
                this.showFragment(FRAGMENT_TOP);
                break;
            case R.id.activity_mynews_drawer_mostpopular:
                this.showFragment(FRAGMENT_POPULAR);
                break;
            case R.id.activity_mynews_drawer_business:
                this.showFragment(FRAGMENT_BUSSINESS);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_mynews_toolbar);
        setSupportActionBar(toolbar);
    }


    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_mynews_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = findViewById(R.id.activity_mynews_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_mynews, menu);
        return true;
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // 1 - Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_mynews_frame_layout);

        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_TOP);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }



    // 5 - Show fragment according an Identifier

    private void showFragment(int fragmentIdentifier){

         switch (fragmentIdentifier){
            case FRAGMENT_TOP :
                this.showTopFragment();
                break;
            case FRAGMENT_POPULAR:
                this.showPopularFragment();
                break;
            case FRAGMENT_BUSSINESS:
                this.showBusinessFragment();
                break;
            default:
                break;
        } 
    }


    // ---

    // 4 - Create each fragment page and show it

    private void showTopFragment(){
        if (this.fragmentTop == null) this.fragmentTop = new TopStoriesFragment();
        this.startTransactionFragment(this.fragmentTop);
    }


    private void showPopularFragment(){
        if (this.fragmentPopular == null) this.fragmentPopular = new MostPopularFragment();
        this.startTransactionFragment(this.fragmentPopular);
    }


    private void showBusinessFragment(){
        if (this.fragmentBusiness == null) this.fragmentBusiness = new BusinessFragment();
        this.startTransactionFragment(this.fragmentBusiness);
    }


    // ---

    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            Log.d("mynews","strat ransaction");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_mynews_frame_layout, fragment).show(fragment).commit();
        }
    }


}
