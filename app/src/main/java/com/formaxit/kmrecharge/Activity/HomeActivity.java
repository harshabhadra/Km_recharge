package com.formaxit.kmrecharge.Activity;

import android.graphics.Color;
import android.os.Bundle;

import com.formaxit.kmrecharge.DummyData;
import com.formaxit.kmrecharge.Adapters.ItemAdapter;
import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.Adapters.SliderAdapter;
import com.formaxit.kmrecharge.Utility;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements ItemAdapter.OnItemclickListener {

    private TextView slidingText;
    private RecyclerView itemRecycler;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing drawer layout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Setting up Navigation Drawer
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();

        //Setting up Sliding text
        slidingText = findViewById(R.id.slide_text_view);
        slidingText.setSelected(true);

        //Setting up slider adapter
        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(sliderAdapter);

        //Setting up item recycler
        DummyData dummyData = new DummyData();
        itemRecycler = findViewById(R.id.home_item_recyclerView);
        itemRecycler.setHasFixedSize(true);
        int noOfCol = Utility.calculateNoOfColumns(this,110);
        itemRecycler.setLayoutManager(new GridLayoutManager(this,noOfCol));
        itemAdapter = new ItemAdapter(this,this,dummyData.getItemsList());
        itemRecycler.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
