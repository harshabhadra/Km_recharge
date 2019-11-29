package com.formaxit.kmrecharge.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.formaxit.kmrecharge.Constants;
import com.formaxit.kmrecharge.Model.Post;
import com.formaxit.kmrecharge.Network.ApiService;
import com.formaxit.kmrecharge.Network.ApiUtills;
import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.databinding.ActivityMainBinding;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private AlertDialog loadingDialog;
    private String userName, password;
    private String logInmessage;
    private String session_id;
    private ApiService apiService;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Apiservice
        apiService = ApiUtills.getApiService();

        //Creating Loading dialog
        loadingDialog = createLoadingDialog(MainActivity.this);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //Set Text Watcher to User name Text Input
        activityMainBinding.logInUsernameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                activityMainBinding.textInputLayoutLogInUsername.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

                activityMainBinding.textInputLayoutLogInUsername.setErrorEnabled(true);
            }
        });

        //Set Text Watcher to Password Text Input
        activityMainBinding.logInPasswordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                activityMainBinding.textInputLayoutLogInPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

                activityMainBinding.textInputLayoutLogInPassword.setErrorEnabled(true);
            }
        });

        //On Log In button Click
        activityMainBinding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = activityMainBinding.logInUsernameTextInput.getText().toString();
                password = activityMainBinding.logInPasswordTextInput.getText().toString().trim();

                if (userName.isEmpty()) {

                    activityMainBinding.textInputLayoutLogInUsername.setError("Enter User Name");
                } else if (password.isEmpty()) {
                    activityMainBinding.textInputLayoutLogInPassword.setError("Enter Password");
                } else {

                    loadingDialog.show();
                    logInUser(userName,password);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Get saved values from Shared preference
        SharedPreferences preferences = getSharedPreferences(Constants.SAVE_ID_PASS,MODE_PRIVATE);
        userName = preferences.getString(Constants.USER_ID," ");
        password = preferences.getString(Constants.PASSWORD," ");

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.LOG_OUT)){
            preferences.edit().clear().apply();
        }else {
            if (!userName.equals(" ") && !password.equals(" ")) {

                loadingDialog.show();
                activityMainBinding.logInUsernameTextInput.setText(userName);
                activityMainBinding.logInPasswordTextInput.setText(password);
                logInUser(userName, password);
            } else {
                Toast.makeText(getApplicationContext(), "Enter User Id and Password to LogIn", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Log in to app
    private void logInUser(final String userId, final String password){

        //Network request to log in
        apiService.logIn(userId,password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Post post) {

                        Log.e(TAG,"Getting response");

                        //Dismiss dialog
                        loadingDialog.dismiss();

                        logInmessage = post.getMessage();
                        if (logInmessage.equals(getResources().getString(R.string.retailer_access))){

                            //Get session id
                            session_id = post.getSession_id();

                            //Save user id and password
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.SAVE_ID_PASS,MODE_PRIVATE).edit();
                            editor.putString(Constants.USER_ID,userId);
                            editor.putString(Constants.PASSWORD,password);
                            editor.apply();

                            Toast.makeText(getApplicationContext(),"Log In Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            intent.putExtra(Constants.SESSION_ID,session_id);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                            finish();
                        }else {

                            Log.e(TAG,logInmessage);
                            Toast.makeText(getApplicationContext(),logInmessage,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG,"Error Fetching details: " + e.getMessage());

                        loadingDialog.dismiss();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                        Log.e(TAG,"Request Completed");
                    }
                });

    }

    //Create Alert Dialog
    private AlertDialog createLoadingDialog(Context context) {
        View layout = getLayoutInflater().inflate(R.layout.loading_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        builder.setCancelable(false);
        return builder.create();
    }
}
