package com.formaxit.kmrecharge;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Commision {

    @SerializedName("member_comm")
    @Expose
    private String memberComm;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("is_charges")
    @Expose
    private String isCharges;
    @SerializedName("is_flat")
    @Expose
    private String isFlat;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("operator_logo")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMemberComm() {
        return memberComm;
    }

    public void setMemberComm(String memberComm) {
        this.memberComm = memberComm;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIsCharges() {
        return isCharges;
    }

    public void setIsCharges(String isCharges) {
        this.isCharges = isCharges;
    }

    public String getIsFlat() {
        return isFlat;
    }

    public void setIsFlat(String isFlat) {
        this.isFlat = isFlat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @BindingAdapter("operator_logo")
    public static void loadImage(ImageView imageView, String imageUrl){

        Picasso.get().load(imageUrl).placeholder(R.drawable.frp_icon).error(R.drawable.frp_icon).into(imageView);
    }
}
