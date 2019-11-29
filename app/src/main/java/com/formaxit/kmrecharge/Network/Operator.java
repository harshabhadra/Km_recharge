package com.formaxit.kmrecharge.Network;

import com.formaxit.kmrecharge.Commision;
import com.formaxit.kmrecharge.Model.ElectricStatus;
import com.formaxit.kmrecharge.Model.FetchOperator;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Operator {

    String BASE_URL = "https://www.freerechargepay.co.in/api/";//http://192.168.0.112/frp_admin/api/

    String PLAN_URL = "http://planapi.in/api/Mobile/";

    @GET("recharge_operator?")
    Call<String> getOperatorList(@Query("operator_type") String operator);

    @GET("recharge_operator?")
    Call<String> getOperatorListByState(@Query("operator_type") String operator);

    @GET("do_recharge1?")
    Call<String> getRechargeStatus(@Query("user_id") String user_id,
                                   @Query("auth_key") String auth,
                                   @Query("number") String number,
                                   @Query("operator_id") String operator_id,
                                   @Query("amount") String amount);

    @GET("recharge_report?")
    Call<String> getRechargeList(@Query("user_id") String user_id, @Query("auth_key") String auth);

    @GET("recharge_report?")
    Call<String> getReportByDate(@Query("user_id") String id, @Query("auth_key") String auth, @Query("from_date") String fromDate, @Query("to_date") String toDate);

    @GET("support?")
    Call<String> getSupport(@Query("auth_key") String auth);

    @GET("ebill_fetch?")
    Call<ElectricStatus> getElectricBillStatus(@Query("auth_key") String auth,
                                               @Query("customer_id") String consumer_id,
                                               @Query("operator_id") String operator_id);

    @GET("ebill_fetch?")
    Call<ElectricStatus> getElectricBillStatus(@Query("auth_key") String auth,
                                               @Query("customer_id") String consumer_id,
                                               @Query("operator_id") String operator_id,
                                               @Query("parameter2") String parameter2);

    @GET("bill_pay?")
    Call<String> getElectricBillPayDetails(@Query("auth_key") String key,
                                           @Query("user_id") String session_id,
                                           @Query("number") String consumer_id,
                                           @Query("operator_id") String code,
                                           @Query("amount") int amount,
                                           @Query("ref_id") String ref_id);

    @GET("accounts_statement?")
    Call<String> getPassbookDetails(@Query("user_id") String user_id,
                                    @Query("auth_key") String auth);

    @GET("accounts_statement?")
    Call<String> getPassbookDetails(@Query("user_id") String user_id,
                                    @Query("auth_key") String auth,
                                    @Query("from_date") String from,
                                    @Query("to_date") String todate);

    @GET("bank_details")
    Call<String> getBankDetails();

    @GET("credit_summary?")
    Call<String> getCreditSummary(@Query("user_id") String id,
                                  @Query("auth_key") String auth);

    @GET("credit_summary?")
    Call<String> getCreditSummaryByDate(@Query("user_id") String user_id,
                                        @Query("auth_key") String auth,
                                        @Query("from_date") String from,
                                        @Query("to_date") String todate);

    @GET("debit_summary?")
    Call<String> getDebititSummary(@Query("user_id") String id,
                                   @Query("auth_key") String auth);

    @GET("debit_summary?")
    Call<String> getDebitSummaryByDate(@Query("user_id") String user_id,
                                       @Query("auth_key") String auth,
                                       @Query("from_date") String from,
                                       @Query("to_date") String todate);

    @GET("Operatorplan?")
    Call<String> getPlanDetails(@Query("apimember_id") String apiMemberId,
                                @Query("api_password") String apiPassword,
                                @Query("cricle") String circleCode,
                                @Query("operatorcode") String operatorCode);

    @GET("RofferCheck?")
    Call<String> getSpecialOffer(@Query("apimember_id") String apiMemberId,
                                 @Query("api_password") String apiPassword,
                                 @Query("mobile_no") String mobile,
                                 @Query("operator_code") String operatorCode);

    @GET("dth_plans.php?")
    Call<String> getDthPlans(@Query("apikey") String apiKey,
                             @Query("operator") String operator);

    @GET("DTHINFOCheck?")
    Call<String> getDthCustomerInfo(@Query("apimember_id") String apiMemberId,
                                    @Query("api_password") String api_password,
                                    @Query("Opcode") String operatorCode,
                                    @Query("mobile_no") String mobile);

    @GET("OperatorFetch?")
    Call<FetchOperator> fetchOperator(@Query("apimember_id") String apimemberId,
                                      @Query("api_password") String apiPassword,
                                      @Query("Mobileno") String mobileNumber);

    @GET("my_margin?")
    Call<List<Commision>> getCommision(@Query("user_id") String session_id,
                                       @Query("auth_key") String authKey);
}
