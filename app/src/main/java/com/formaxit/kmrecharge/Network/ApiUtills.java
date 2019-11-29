package com.formaxit.kmrecharge.Network;

public class ApiUtills {

    private ApiUtills(){}

    public static String BASE_URL = "https://www.freerechargepay.co.in/api/";

    public static ApiService getApiService(){

        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
