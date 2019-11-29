package com.formaxit.kmrecharge.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.formaxit.kmrecharge.R;
import com.formaxit.kmrecharge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private AlertDialog loadingDialog;
    private String userName, password;
    private String logInmessage;
    private String session_id;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    loadingDialog = createLoadingDialog(MainActivity.this);
                    loadingDialog.show();
                }
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
