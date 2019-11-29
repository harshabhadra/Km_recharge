package com.formaxit.kmrecharge.Network;

import com.formaxit.kmrecharge.Model.Details;
import com.formaxit.kmrecharge.Model.FundResponse;
import com.formaxit.kmrecharge.Model.Post;
import com.formaxit.kmrecharge.Model.Profile;
import com.formaxit.kmrecharge.Password;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @POST("login")
    @FormUrlEncoded
    Observable<Post> logIn(@Field("login_id") String login_id,
                           @Field("password") String password);

    @POST("profile?")
    @FormUrlEncoded
    Observable<Details> setDetailsPost(@Field("session_id") String session_id,
                                       @Field("auth_key") String auth_key);

    @POST("forgot_password?")
    @FormUrlEncoded
    Call<Password> resetPassword(@Field("user_id") String user_id);

    @POST("profile?")
    @FormUrlEncoded
    Observable<Profile> getProfileDetails(@Field("session_id") String session_id,
                                          @Field("auth_key") String auth_key);

    @GET("fund_request?")
    Call<FundResponse> getFundResponse(@Query("session_id") String id,
                                       @Query("auth_key") String auth,
                                       @Query("amount") String amount,
                                       @Query("bank") String bank,
                                       @Query("payment_mode") String paymentMode,
                                       @Query("payment_date") String paymentDate,
                                       @Query("transaction_id") String transaction_id,
                                       @Query("wallet_type") String walletType);

    @GET("change_password?")
    Observable<Password> changePassword(@Query("session_id") String session_id,
                                        @Query("auth_key") String auth_key,
                                        @Query("current_password") String cPassword,
                                        @Query("new_password") String nPassword,
                                        @Query("confrim_new_password") String conNewPassword);

}
