package com.formaxit.kmrecharge.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.formaxit.kmrecharge.Constants;
import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.databinding.ActivityRechargeBinding;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

public class RechargeActivity extends AppCompatActivity {

    private String session_id, authKey;
    private ActivityRechargeBinding rechargeBinding;
    private Prepaid prepaid;
    private String itemName;
    private String mobileNumber, amount;
    private String operatorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        //Initializing DatabBinding Class
        rechargeBinding = DataBindingUtil.setContentView(this,R.layout.activity_recharge);

        //Get the authkey
        authKey = getResources().getString(R.string.auth_key);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i)
                {
                    if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches())
                    {
                        return "";
                    }
                }

                return null;
            }
        };

        Intent intent = getIntent();
        session_id = intent.getStringExtra(Constants.SESSION_ID);
        prepaid = intent.getParcelableExtra(Constants.OPERATOR_ITEM);
        itemName = intent.getStringExtra(Constants.RECHARGE);

        operatorName = prepaid.getOperatorName();

        //Setting Operator logo and name
        Picasso.get().load(prepaid.getImage()).into(rechargeBinding.rechargeOperatorLogo);
        rechargeBinding.rechargeOperatorName.setText(prepaid.getOperatorName());

        if (itemName.equals(Constants.DTH)){
            rechargeBinding.specialOfferButton.setText("Customer Info.");
            rechargeBinding.rechargeNumberInputText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(11)});
        }else if (itemName.equals(Constants.POSTPAID)){
            rechargeBinding.specialOfferButton.setText("Fetch Bill");
            rechargeBinding.rechargeNumberInputText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
        }else {
            rechargeBinding.specialOfferButton.setText("Special Offer");
            rechargeBinding.rechargeNumberInputText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
        }

        //Setting Text Watcher to Mobile Number
        rechargeBinding.rechargeNumberInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                rechargeBinding.textInputLayoutRechargeNumber.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                rechargeBinding.textInputLayoutRechargeNumber.setErrorEnabled(true);
            }
        });

        //Setting Text Watcher to Amount
        rechargeBinding.rechargeAmountInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                rechargeBinding.textInputLayoutRechargeAmount.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

                rechargeBinding.textInputLayoutRechargeAmount.setErrorEnabled(true);
            }
        });

        //Add On Click listener to recharge button
        rechargeBinding.rechargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNumber = rechargeBinding.rechargeNumberInputText.getText().toString().trim();
                amount = rechargeBinding.rechargeAmountInputText.getText().toString().trim();

                if (mobileNumber.isEmpty()){
                    rechargeBinding.textInputLayoutRechargeNumber.setError("Enter Valid Number");
                }else if (!itemName.equals(Constants.DTH) && mobileNumber.length()<10){
                    rechargeBinding.textInputLayoutRechargeNumber.setError("Enter Valid Number");
                }else if (itemName.equals(Constants.DTH) && mobileNumber.length()<11){
                    rechargeBinding.textInputLayoutRechargeNumber.setError("Enter Valid Number");
                }
                else if (amount.isEmpty() || (!isValidAmount(amount))){
                    rechargeBinding.textInputLayoutRechargeAmount.setError("Enter Valid Amount");
                }else {
                    showRechargeConfirmation(mobileNumber,amount);
                }
            }
        });
    }

    //Make recharge confirmation dialog
    private void showRechargeConfirmation(String number,String amt){

        LayoutInflater inflater = getLayoutInflater();
        final View confirmLayout = inflater.inflate(R.layout.confirmation_layout, null);

        final TextView numTextView = confirmLayout.findViewById(R.id.confirm_number);
        final TextView amountTextView = confirmLayout.findViewById(R.id.confirm_amount);
        final TextView retailer = confirmLayout.findViewById(R.id.confirm_retailer);
        final FancyButton cancelButton = confirmLayout.findViewById(R.id.cancel_confirm);
        final FancyButton confirmButton = confirmLayout.findViewById(R.id.cancel_button);
        //Getting number and amount from edit text

        AlertDialog.Builder builder = new AlertDialog.Builder(RechargeActivity.this, R.style.CustomDialog);
        builder.setView(confirmLayout);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        numTextView.setText(number);
        amountTextView.setText(amount);
        retailer.setText(operatorName);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                rechargeBinding.rechargeNumberInputText.getText().clear();
                rechargeBinding.rechargeAmountInputText.getText().clear();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    //check if the amount is valid
    private boolean isValidAmount(String amt){

        int amount = Integer.parseInt(amt);
        if (amount>7){
            return true;
        }else {
            return false;
        }
    }

    //Check if the number is valid
    private boolean isValidMobile(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }
}
