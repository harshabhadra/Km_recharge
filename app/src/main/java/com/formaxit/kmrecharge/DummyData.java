package com.formaxit.kmrecharge;

import com.formaxit.kmrecharge.Items;
import com.formaxit.kmrecharge.R;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public DummyData() {
    }


    //Get Utility service items
    public List<Items> getItemsList() {

        List<Items> itemsList = new ArrayList<>();
        itemsList.add(new Items(R.drawable.mobile_icon, "Mobile Prepaid"));
        itemsList.add(new Items(R.drawable.dth_icon, "DTH"));
        itemsList.add(new Items(R.drawable.electricity_icon, "Electricity"));
        itemsList.add(new Items(R.drawable.mobile_icon,"Mobile Postpaid"));
        itemsList.add(new Items(R.drawable.insurance_icon,"Insurance"));
        itemsList.add(new Items(R.drawable.landline_icon,"Phone"));

        return itemsList;
    }

    //Get Payment mode
    public List<String>getPaymentMode(){
        List<String>paymentList = new ArrayList<>();
        paymentList.add("NEFT");
        paymentList.add("IMPS");
        paymentList.add("UPI/BHIM");
        paymentList.add("RTGS");
        paymentList.add("CASH DEPOSIT");
        paymentList.add("BANK TRANSFER");
        return paymentList;
    }
}
