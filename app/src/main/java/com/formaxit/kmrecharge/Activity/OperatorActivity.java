package com.formaxit.kmrecharge.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.formaxit.kmrecharge.Adapters.OperatorAdapter;
import com.formaxit.kmrecharge.Constants;
import com.formaxit.kmrecharge.MainViewModel;
import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.R;

import java.util.List;

public class OperatorActivity extends AppCompatActivity implements OperatorAdapter.OnOperatorItemClickListener{

    //Store item name received from the intent
    String itemName;

    private static final String TAG = OperatorActivity.class.getSimpleName();
    //MainViewModel variable
    MainViewModel mainViewModel;

    RecyclerView operatorRecyclerView;
    OperatorAdapter operatorAdapter;
    ProgressBar operatorProgressBar;
    private String session_id;
    private boolean isPrepaid, isDth, isPostPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        operatorProgressBar = findViewById(R.id.operator_list_loading);
        //Initializing ViewModel class
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //Setting up Operator Recycler View
        operatorRecyclerView = findViewById(R.id.operator_list_recycler);
        operatorRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        operatorRecyclerView.setLayoutManager(linearLayoutManager);
        operatorAdapter = new OperatorAdapter(this, this);
        operatorRecyclerView.setAdapter(operatorAdapter);

        //Getting intent
        Intent intent = getIntent();

        //Getting item name and session_id
        itemName = intent.getStringExtra(Constants.ITEMS_NAME);
        session_id = intent.getStringExtra(Constants.SESSION_ID);
        Log.e(TAG, "Session id: " + session_id);

        if (itemName.equals("Mobile Prepaid")) {

            getPrepaidOperatorList("Prepaid-Mobile");
            isPrepaid = true;
        } else if (itemName.equals("Mobile Postpaid")) {
            getPrepaidOperatorList("Postpaid-Mobile");
            isPostPaid = true;
        } else {
            getPrepaidOperatorList("DTH");
            isDth = true;
        }
    }

    @Override
    public void onOperatorItemClick(int position) {

        Prepaid prepaid = operatorAdapter.getPrepaidItem(position);
        Toast.makeText(getApplicationContext(),prepaid.getOperatorName(),Toast.LENGTH_SHORT).show();
        if (isPrepaid) {
            startRechageActivity(prepaid,Constants.PREPAID);
        } else if (isPostPaid) {
            startRechageActivity(prepaid,Constants.POSTPAID);
        } else {
            startRechageActivity(prepaid,Constants.DTH);
        }
    }

    //Get Prepaid operator list
    private void getPrepaidOperatorList(String operatorType) {

        mainViewModel.getOperators(operatorType).observe(this, new Observer<List<Prepaid>>() {
            @Override
            public void onChanged(List<Prepaid> prepaids) {

                operatorProgressBar.setVisibility(View.GONE);
                if (prepaids != null) {
                    Log.e(TAG, "Operator list is full");
                    operatorAdapter.setOperatorList(prepaids);
                } else {
                    Toast.makeText(getApplicationContext(), "No Operator", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Start Recharge Activity
    private void startRechageActivity(Prepaid prepaid, String name){

        Intent intent = new Intent(OperatorActivity.this, RechargeActivity.class);
        intent.putExtra(Constants.OPERATOR_ITEM, prepaid);
        intent.putExtra(Constants.SESSION_ID, session_id);
        intent.putExtra(Constants.RECHARGE, name);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
