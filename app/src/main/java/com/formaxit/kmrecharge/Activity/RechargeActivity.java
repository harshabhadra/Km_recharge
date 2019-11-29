package com.formaxit.kmrecharge.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.formaxit.kmrecharge.Constants;
import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.databinding.ActivityRechargeBinding;
import com.squareup.picasso.Picasso;

public class RechargeActivity extends AppCompatActivity {

    private String session_id, authKey;
    private ActivityRechargeBinding rechargeBinding;
    private Prepaid prepaid;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        //Initializing DatabBinding Class
        rechargeBinding = DataBindingUtil.setContentView(this,R.layout.activity_recharge);

        Intent intent = getIntent();
        session_id = intent.getStringExtra(Constants.SESSION_ID);
        prepaid = intent.getParcelableExtra(Constants.OPERATOR_ITEM);
        itemName = intent.getStringExtra(Constants.RECHARGE);

        //Setting Operator logo and name
        Picasso.get().load(prepaid.getImage()).into(rechargeBinding.rechargeOperatorLogo);
        rechargeBinding.rechargeOperatorName.setText(prepaid.getOperatorName());

        if (itemName.equals(Constants.DTH)){
            rechargeBinding.specialOfferButton.setText("Customer Info.");
        }else if (itemName.equals(Constants.POSTPAID)){
            rechargeBinding.specialOfferButton.setText("Fetch Bill");
        }else {
            rechargeBinding.specialOfferButton.setText("Special Offer");
        }
    }
}
