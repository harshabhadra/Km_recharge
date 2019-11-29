package com.formaxit.kmrecharge.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.formaxit.kmrecharge.Constants;
import com.formaxit.kmrecharge.DummyData;
import com.formaxit.kmrecharge.Adapters.ItemAdapter;
import com.formaxit.kmrecharge.Items;
import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.Adapters.SliderAdapter;
import com.formaxit.kmrecharge.Utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements ItemAdapter.OnItemclickListener ,NavigationView.OnNavigationItemSelectedListener{

    private TextView slidingText;
    private RecyclerView itemRecycler;
    private ItemAdapter itemAdapter;
    DrawerLayout drawer;
    private String session_id, authKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing drawer layout
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Setting up Navigation Drawer
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

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

        //Getting Intent
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.SESSION_ID)){
            session_id = intent.getStringExtra(Constants.SESSION_ID);
        }
    }

    @Override
    public void onItemClick(int position) {

        Items items = itemAdapter.getItem(position);
        String itemName = items.getName();
        switch (itemName){
            case "Mobile Prepaid":
            case "Mobile Postpaid":
            case "DTH":
                Intent rechargeIntent = new Intent(HomeActivity.this,OperatorActivity.class);
                rechargeIntent.putExtra(Constants.ITEMS_NAME, itemName);
                rechargeIntent.putExtra(Constants.SESSION_ID,session_id);
                startActivity(rechargeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_log_out){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to Log Out?");
            builder.setIcon(R.drawable.frp_icon);
            builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                    intent.putExtra(Constants.LOG_OUT, "logout");
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
