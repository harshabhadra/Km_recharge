package com.formaxit.kmrecharge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.formaxit.kmrecharge.Model.Bank;
import com.formaxit.kmrecharge.Model.Prepaid;
import com.formaxit.kmrecharge.Model.Support;

import java.util.List;

public class MainViewModel extends ViewModel {

    Repository repository;

    public MainViewModel() {

        repository = Repository.getInstance();
    }

    //Reset Password
    public LiveData<String> getPasswordLiveData(String name) {
        return repository.getPassword(name);
    }

    //get operator list
    public LiveData<List<Prepaid>> getOperators(String type) {
        return repository.getPrepaidOperator(type);
    }

    //Get support Details
    public LiveData<Support> getSupportDetails(String auth) {
        return repository.getSupportDetails(auth);
    }

    //Get list of operators by state
    public LiveData<List<Prepaid>> getOperatorListByState(String operator) {
        return repository.getOperatorByState(operator);
    }

    //Get List of bank
    public LiveData<List<Bank>> getBankDetails() {
        return repository.getBankDetialsList();
    }

    //Get fundresponse
    public LiveData<String> getFundResponse(String id, String auth, String amount, String bank, String pMode, String pDate, String tranId, String walltet) {
        return repository.getFundRequestResponse(id, auth, amount, bank, pMode, pDate, tranId, walltet);
    }
}
