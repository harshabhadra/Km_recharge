package com.formaxit.kmrecharge;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.formaxit.kmrecharge.Model.Bank;
import com.formaxit.kmrecharge.Model.BillPay;
import com.formaxit.kmrecharge.Model.ElectricStatus;
import com.formaxit.kmrecharge.Model.FetchOperator;
import com.formaxit.kmrecharge.Model.FundResponse;
import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.Model.Recharge;
import com.formaxit.kmrecharge.Model.Support;
import com.formaxit.kmrecharge.Network.ApiService;
import com.formaxit.kmrecharge.Network.ApiUtills;
import com.formaxit.kmrecharge.Network.Operator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private ApiService apiService = ApiUtills.getApiService();

    //Store Password LiveData
    private MutableLiveData<String> passwordMutableLiveData = new MutableLiveData<>();

    //Store Operator list
    private MutableLiveData<List<Prepaid>> operatorListLiveData;

    //Store a single recharge status
    private MutableLiveData<Recharge> rechargeLiveData = new MutableLiveData<>();

    //Store a list of recharge status
    private MutableLiveData<List<RechargeDetails>> rechargeListLiveData = new MutableLiveData<>();

    //Store support details
    private MutableLiveData<Support> supportMutableLiveData = new MutableLiveData<>();

    //Store report by date
    private MutableLiveData<List<RechargeDetails>> dateRechargeListLiveData = new MutableLiveData<>();

    //Store list of operator by state
    private MutableLiveData<List<Prepaid>> operatorByStateLiveData = new MutableLiveData<>();

    //Store response of electric bill status
    private MutableLiveData<ElectricStatus> electricStatusMutableLiveData = new MutableLiveData<>();

    //Store ElectricBillPayment details
    private MutableLiveData<BillPay> billPayMutableLiveData = new MutableLiveData<>();

    //Store passbook details
    private MutableLiveData<List<Passbook>> passbookDetailsLiveData = new MutableLiveData<>();

    //Store list of bank details
    private MutableLiveData<List<Bank>> bankDetailsListLiveData;

    //Store response of fund request
    private MutableLiveData<String> fundResponseMutableLiveData = new MutableLiveData<>();

    //Store credit summary list
    private MutableLiveData<List<Passbook>> creditSummaryMutableLiveData = new MutableLiveData<>();

    //Store credit summary list by date
    private MutableLiveData<List<Passbook>> creditSummaryByDateLiveData = new MutableLiveData<>();

    //Store credit summary list
    private MutableLiveData<List<Passbook>> debiitSummaryMutableLiveData = new MutableLiveData<>();

    //Store debit summary list by date
    private MutableLiveData<List<Passbook>> debiitSummaryByDateLiveData = new MutableLiveData<>();

    //Store dth plan values
    private MutableLiveData<List<DTH>> dthMutableLiveData = new MutableLiveData<>();

    //Store Dth customer info
    private MutableLiveData<DthCustomerInfo> dthCustomerInfoMutableLiveData = new MutableLiveData<>();

    //Store fetch operator detials
    private MutableLiveData<FetchOperator> fetchOperatorMutableLiveData = new MutableLiveData<>();

    //Store commision details
    private MutableLiveData<List<Commision>> commisionMutableLiveData = new MutableLiveData<>();

    public static Repository getInstance() {
        return new Repository();
    }

    //Get Commision details
    public LiveData<List<Commision>> getCommision(String session_id, String authKey){

        myCommision(session_id, authKey);
        return commisionMutableLiveData;
    }

    //Get list of debit summary by date
    public LiveData<List<Passbook>> getCreditListByDate(String id, String auth, String from, String to) {

        getCreditByDate(id, auth, from, to);

        return creditSummaryByDateLiveData;
    }

    //Get credit summary
    public LiveData<List<Passbook>> getCreditSummaryList(String id, String auth) {

        getCreditSummary(id, auth);
        return creditSummaryMutableLiveData;
    }

    //Get debit summary
    public LiveData<List<Passbook>> getDebitSummaryList(String id, String auth) {

        getDebitSummary(id, auth);
        return debiitSummaryMutableLiveData;
    }

    //Get fund response
    public LiveData<String> getFundRequestResponse(String id, String auth, String amount, String bank, String pmode, String pdate, String tranId, String walletType) {

        getFundResponse(id, auth, amount, bank, pmode, pdate, tranId, walletType);
        return fundResponseMutableLiveData;
    }

    //Get list of bank details
    public LiveData<List<Bank>> getBankDetialsList() {

        if (bankDetailsListLiveData == null) {

            bankDetailsListLiveData = new MutableLiveData();
            getBankDetails();
        }

        return bankDetailsListLiveData;
    }

    //Get Passbook details by date
    public LiveData<List<Passbook>> getPassbookdetailsByDate(String id, String auth, String fromDate, String toDate) {

        getPassbookByDate(id, auth, fromDate, toDate);
        return passbookDetailsLiveData;
    }

    //Get Passbook details
    public LiveData<List<Passbook>> getPassBookDetails(String id, String auth) {

        getPassbook(id, auth);
        return passbookDetailsLiveData;
    }

    //Get bill payment details
    public LiveData<BillPay> getBillPaymentDetails(String auth, String session_id, String counsumer_id, String code, int amount, String ref_id) {

        getBillPayDetails(auth, session_id, counsumer_id, code, amount, ref_id);
        return billPayMutableLiveData;
    }

    //Get response of electric bill
    public LiveData<ElectricStatus> getElectricBillStatus(String auth, String customer_id, String code) {

        getElectricStatus(auth, customer_id, code);
        return electricStatusMutableLiveData;
    }

    // Get response of Electric bill with two parameter
    public LiveData<ElectricStatus> getElectricBillStatusWithP(String auth, String customer_id, String code, String parameter2) {
        getElectricBillStatusTwo(auth, customer_id, code, parameter2);
        return electricStatusMutableLiveData;
    }

    //Get list of operator by state
    public LiveData<List<Prepaid>> getOperatorByState(String operatorType) {

        getOperatorListByState(operatorType);
        return operatorByStateLiveData;
    }

    //Get a single recharge status
    public LiveData<Recharge> getRechargeStatus(String userId, String auth, String number, String operatorId, String amount) {

        getStatus(userId, auth, number, operatorId, amount);
        return rechargeLiveData;
    }

    //Get a list of recharge status
    public LiveData<List<RechargeDetails>> getRechargeList(String session_id, String auth) {

        getRechargeListLiveData(session_id, auth);
        return rechargeListLiveData;
    }

    //Get list of recharge status by date
    public LiveData<List<RechargeDetails>> getRechargeStatusByDate(String id, String auth, String from, String to) {
        getRechargeListByDate(id, auth, from, to);
        return dateRechargeListLiveData;
    }


    //Get Password
    public LiveData<String> getPassword(String name) {

        resetPassword(name);
        return passwordMutableLiveData;
    }

    //Get Prepaid Operator List
    public LiveData<List<Prepaid>> getPrepaidOperator(String operator) {

        if (operatorListLiveData == null) {
            operatorListLiveData = new MutableLiveData<>();
        }
        getOperatorList(operator);
        return operatorListLiveData;
    }

    //Get support details
    public LiveData<Support> getSupportDetails(String key) {

        getSupport(key);
        return supportMutableLiveData;
    }

    //Network call to get support details
    private void getSupport(String key) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getSupport(key);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Support response is full: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String company = jsonObject.getString("company_name");
                        String mobile1 = jsonObject.getString("mobile");
                        String mobile2 = jsonObject.getString("phone");
                        String mobile3 = jsonObject.getString("phone_1");
                        String email_1 = jsonObject.getString("email");
                        String email_2 = jsonObject.getString("email_2");
                        String billEmail = jsonObject.getString("email_2");
                        String add = jsonObject.getString("address");
                        String website = jsonObject.getString("website_link");
                        String about = jsonObject.getString("about_us");

                        Support support = new Support(company, mobile1, mobile2, mobile3, email_1, email_2, billEmail, add, website, about);
                        supportMutableLiveData.setValue(support);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Support Response failure: " + t.getMessage());
                Support support = new Support(t.getMessage());
                supportMutableLiveData.setValue(support);

            }
        });
    }

    //Network call to get list of bank
    private void getBankDetails() {

        final List<Bank> bankList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getBankDetails();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        Log.e(TAG, "Bank details response is successful: " + response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String account = object.getString("name_in_bank");
                            String bankName = object.getString("bank_name");
                            String accountNo = object.getString("account_no");
                            String ifsc = object.getString("ifsc_code");
                            String status = object.getString("status");

                            Bank bank = new Bank(account, bankName, accountNo, ifsc, status);
                            bankList.add(bank);
                            bankDetailsListLiveData.setValue(bankList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.getMessage());
                Bank bank = new Bank(t.getMessage());
                bankList.add(bank);
                bankDetailsListLiveData.setValue(bankList);
            }
        });

    }

    //Network call to get list of recharge status by Date
    private void getRechargeListByDate(String id, String auth, String from, String to) {

        final List<RechargeDetails> rechargeDetailsList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);

        Call<String> call = operator.getReportByDate(id, auth, from, to);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "By Date response: " + response.body());
                if (response.isSuccessful() && response.body() != null) {


                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String number = jsonObject.optString("number");
                            String amount = jsonObject.optString("amount");
                            String status = jsonObject.optString("status");
                            String operator_name = jsonObject.optString("operator_name");
                            String txn_id = jsonObject.optString("txn_id");
                            String opt_txn_id = jsonObject.optString("opt_txn_id");
                            String time = jsonObject.optString("created_on");
                            String message = jsonObject.optString("api_response");
                            String logo = jsonObject.optString("operator_logo");
                            String recharge_by = jsonObject.optString("recharge_by");
                            String closing_balance = jsonObject.optString("closing_bal");

                            RechargeDetails details = new RechargeDetails(number, amount, status, operator_name, txn_id, opt_txn_id, time, logo, closing_balance, recharge_by, message);
                            rechargeDetailsList.add(details);

                            dateRechargeListLiveData.setValue(rechargeDetailsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Faliure on Date by: " + t.getMessage());
                RechargeDetails details = new RechargeDetails(t.getMessage());
                rechargeDetailsList.add(details);
                dateRechargeListLiveData.setValue(rechargeDetailsList);
            }
        });
    }

    //Network request for list of recharge
    private void getRechargeListLiveData(String session_id, String auth) {

        final List<RechargeDetails> rechargeList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRechargeList(session_id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"REsponse is : " + response.body());
                if (response.body() != null && response.isSuccessful()) {
                    Log.e(TAG, "Response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String number = jsonObject.optString("number");
                            String amount = jsonObject.optString("amount");
                            String status = jsonObject.optString("status");
                            String operator_name = jsonObject.optString("operator_name");
                            String txn_id = jsonObject.optString("txn_id");
                            String opt_txn_id = jsonObject.optString("opt_txn_id");
                            String time = jsonObject.optString("created_on");
                            String message = jsonObject.optString("api_response");
                            String logo = jsonObject.optString("operator_logo");
                            String operator_logo = logo;
                            String recharge_by = jsonObject.optString("recharge_by");
                            String closing_balance = jsonObject.optString("closing_bal");
                            RechargeDetails details = new RechargeDetails(number, amount, status, operator_name, txn_id, opt_txn_id, time, operator_logo, closing_balance, recharge_by, message);
                            rechargeList.add(details);

                            rechargeListLiveData.setValue(rechargeList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Support response failure: " + t.getMessage());
                RechargeDetails details = new RechargeDetails(t.getMessage());
                rechargeList.add(details);
                dateRechargeListLiveData.setValue(rechargeList);
            }
        });
    }

    //Network call to get list of debit summary
    private void getDebitSummary(String id, String auth) {

        final List<Passbook> debitList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getDebititSummary(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Debit response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            debitList.add(passbook);
                            debiitSummaryMutableLiveData.setValue(debitList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Passbook passbook = new Passbook(t.getMessage());
                debitList.add(passbook);
                debiitSummaryMutableLiveData.setValue(debitList);
            }
        });
    }

    //Network call to get list list of credit summary
    private void getCreditSummary(String id, String auth) {

        final List<Passbook> creditList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCreditSummary(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Credit response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            creditList.add(passbook);
                            creditSummaryMutableLiveData.setValue(creditList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                creditList.add(passbook);
                creditSummaryMutableLiveData.setValue(creditList);
            }
        });
    }


    //Get recharge status
    private void getStatus(String userId, String auth, String number, String operatorId, String amount) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRechargeStatus(userId, auth, number, operatorId, amount);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"Response is: "  + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        String number = jsonObject.getString("number");
                        String amount = jsonObject.getString("amount");
                        String status = jsonObject.getString("status");
                        String operator_name = jsonObject.getString("operator_name");
                        String txn_id = jsonObject.getString("txn_id");
                        String opt_txn_id = jsonObject.optString("opt_txn_id");
                        String time = jsonObject.getString("created_on");
                        String message = jsonObject.getString("message");
                        String logo = jsonObject.getString("operator_logo");
                        String operator_logo =logo;

                        Recharge recharge = new Recharge(number, amount, status, operator_name, txn_id, opt_txn_id, time, message, operator_logo);
                        rechargeLiveData.setValue(recharge);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "No response " + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Recharge response: " + t.getMessage());
                Recharge recharge = new Recharge(t.getMessage());
                rechargeLiveData.setValue(recharge);
            }
        });
    }

    //Network request to get list of electric operator by state
    private void getOperatorListByState(String operatorType) {

        final List<Prepaid> operatorBySList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getOperatorListByState(operatorType);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"Response is: " + response.body() );
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Operator by state is successful" + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String operator = jsonObject.getString("operator_name");
                            String code = jsonObject.getString("id");
                            String logo = jsonObject.getString("logo");

                            Prepaid operatorByS = new Prepaid(logo, code, operator);
                            operatorBySList.add(operatorByS);
                            operatorByStateLiveData.setValue(operatorBySList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Operator by state is failed" + t.getMessage());
                Prepaid prepaid = new Prepaid(t.getMessage());
                operatorBySList.add(prepaid);
                operatorByStateLiveData.setValue(operatorBySList);
            }
        });
    }

    //Network call to get electric bill pay
    private void getBillPayDetails(String auth, String session_id, String counsumer_id, String code, int amount, final String ref_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getElectricBillPayDetails(auth, session_id, counsumer_id, code, amount, ref_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Bill pay Response success: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String number = jsonObject.getString("number");
                        String amount = jsonObject.getString("amount");
                        String status = jsonObject.getString("status");
                        String operator = jsonObject.getString("operator_name");
                        String txn = jsonObject.optString("txn_id");
                        String opt_txn = jsonObject.optString("opt_txn_id");
                        String created = jsonObject.getString("created_on");
                        String resp = jsonObject.getString("response");

                        BillPay billPay = new BillPay(number, amount, status, operator, txn, opt_txn, created, resp);
                        billPayMutableLiveData.setValue(billPay);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                BillPay billPay = new BillPay(t.getMessage());
                billPayMutableLiveData.setValue(billPay);
                Log.e(TAG, "Bill pay response failure");
            }
        });
    }

    //Network request to get electric bill status
    private void getElectricStatus(String auth, String customer_id, String code) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);

        Call<ElectricStatus> call = operator.getElectricBillStatus(auth, customer_id, code);
        call.enqueue(new Callback<ElectricStatus>() {
            @Override
            public void onResponse(Call<ElectricStatus> call, Response<ElectricStatus> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Electric response success: " + response.body().toString());

                    electricStatusMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ElectricStatus> call, Throwable t) {
                Log.e(TAG, "Electric response Failure: " + t.getMessage());
                ElectricStatus electricStatus = new ElectricStatus(t.getMessage());
                electricStatusMutableLiveData.setValue(electricStatus);
            }
        });

    }

    //Network request to get electric bill status with more than one parameter
    private void getElectricBillStatusTwo(String auth, String customer_id, String code, String parameter2) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<ElectricStatus> call = operator.getElectricBillStatus(auth, customer_id, code, parameter2);
        call.enqueue(new Callback<ElectricStatus>() {
            @Override
            public void onResponse(Call<ElectricStatus> call, Response<ElectricStatus> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Electric response success");
                    ElectricStatus electricStatus = new ElectricStatus(response.body().getStatus(), response.body().getCustomerId(),
                            response.body().getCustomerName(), response.body().getBillNumber(), response.body().getBillDate(), response.body().getBillDueDate(),
                            response.body().getBillPeriod(), response.body().getBillAmount(), response.body().getRefId(), response.body().getMessage());

                    electricStatusMutableLiveData.setValue(electricStatus);
                }
            }

            @Override
            public void onFailure(Call<ElectricStatus> call, Throwable t) {
                Log.e(TAG, "Electric response success: " + t.getMessage());
                ElectricStatus electricStatus = new ElectricStatus(t.getMessage());
                electricStatusMutableLiveData.setValue(electricStatus);
            }
        });
    }

    //Network Call to get Operator List
    private void getOperatorList(String operator) {

        final List<Prepaid> list = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator1 = retrofit.create(Operator.class);

        Call<String> call = operator1.getOperatorList(operator);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"response is : " + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Operator response successful");
                    try {
                        JSONArray array = new JSONArray(response.body());
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String image = object.getString("logo");
                            String name = object.getString("operator_name");
                            Log.e(TAG,"image link: " + image);
                            Log.e(TAG, "opertor: " + id + " : " + name);
                            list.add(new Prepaid(image, id, name));
                            operatorListLiveData.setValue(list);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Prepaid prepaid = new Prepaid(t.getMessage());
                list.add(prepaid);
                operatorListLiveData.setValue(list);

            }
        });
    }

    //Network call to get Fund request response
    private void getFundResponse(String id, String auth, String amount, String bank, String paymentMode, String paymentDate, String transaction_id, String walletType) {

        Call<FundResponse> call = apiService.getFundResponse(id, auth, amount, bank, paymentMode, paymentDate, transaction_id, walletType);
        call.enqueue(new Callback<FundResponse>() {
            @Override
            public void onResponse(Call<FundResponse> call, Response<FundResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();

                    fundResponseMutableLiveData.setValue(message);
                }
            }

            @Override
            public void onFailure(Call<FundResponse> call, Throwable t) {

            }
        });
    }

    //Network call to reset password
    private void resetPassword(String name) {
        String message;

        Call<Password> call = apiService.resetPassword(name);
        call.enqueue(new Callback<Password>() {
            @Override
            public void onResponse(Call<Password> call, Response<Password> response) {

                if (response.isSuccessful() && response.body() != null) {

                    passwordMutableLiveData.setValue(response.body().getMessage());
                    Log.e(TAG, "Message: " + response.body().getMessage());
                } else {
                    Log.e(TAG, "empty body");
                }
            }

            @Override
            public void onFailure(Call<Password> call, Throwable t) {

                Log.e(TAG, t.getMessage());
                passwordMutableLiveData.setValue(t.getMessage());
            }
        });
    }


    //Network call to get list of passbook details
    private void getPassbook(String id, String auth) {

        final List<Passbook> passbookList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getPassbookDetails(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Passbook response successful: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            passbookList.add(passbook);
                            passbookDetailsLiveData.setValue(passbookList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                passbookList.add(passbook);
                passbookDetailsLiveData.setValue(passbookList);
            }
        });

    }

    //Network call to get passbook details by date
    private void getPassbookByDate(String id, String auth, String fromDate, String toDate) {

        final List<Passbook> passbookList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getPassbookDetails(id, auth, fromDate, toDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Passbook response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            passbookList.add(passbook);
                            passbookDetailsLiveData.setValue(passbookList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


                Passbook passbook = new Passbook(t.getMessage());
                passbookList.add(passbook);
                passbookDetailsLiveData.setValue(passbookList);
            }
        });
    }

    //Network call to get credit list by date
    private void getCreditByDate(String id, String auth, String from, String to) {

        final List<Passbook> creditList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCreditSummaryByDate(id, auth, from, to);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Credit response by date: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            creditList.add(passbook);
                            creditSummaryMutableLiveData.setValue(creditList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                creditList.add(passbook);
                creditSummaryMutableLiveData.setValue(creditList);
            }
        });

    }

    //Network request to get commision details
    private void myCommision(String session_id, String authKey) {

        List<Commision>commisionList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<List<Commision>> call = operator.getCommision(session_id,authKey);
        call.enqueue(new Callback<List<Commision>>() {
            @Override
            public void onResponse(Call<List<Commision>> call, Response<List<Commision>> response) {
                Log.e(TAG, "Commision response is: " + response.body().get(0).getServiceName());
                if (response.body() != null && response.isSuccessful()){
                    Log.e(TAG,"Commision response is successful");
                    commisionMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Commision>> call, Throwable t) {

                Log.e(TAG,"Commision response is failure: " + t.getMessage());
            }
        });
    }
}
