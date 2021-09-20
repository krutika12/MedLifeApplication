package com.namita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.namita.Common.Constants;
import com.namita.Database.User;
import com.namita.ViewModel.UserViewModel;
import com.namita.medlifeapplication.R;
import com.namita.medlifeapplication.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    String mobileNo, password;
    boolean flag;
    List<User> users;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        users = new ArrayList<>();
        userViewModel = new ViewModelProvider(LoginActivity.this).get(UserViewModel.class);

        userViewModel.getUsers().observe(this, users -> {
            this.users = users;
        });

        userViewModel.getUser().observe(this, user -> {
            activityLoginBinding.progressBar.setVisibility(View.GONE);
            if(user!=null){
                if(user.getPassword().equalsIgnoreCase(password)) {
                    Intent intent = new Intent(LoginActivity.this, HomescreenActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Constants.showAlertDialog(LoginActivity.this, "Mobile No. or Password may be wrong.");
                }
            }else{
                Constants.showAlertDialog(LoginActivity.this, "Mobile No. is not registered. Please register first.");
            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLoginBinding.progressBar.setVisibility(View.VISIBLE);
                mobileNo = activityLoginBinding.mobileNo.getText().toString();
                password = activityLoginBinding.password.getText().toString();
                flag = true;
                if(mobileNo == null || mobileNo.isEmpty()){
                    activityLoginBinding.mobileNo.setError("Enter Mobile No");
                    flag = false;
                }else if(mobileNo.length()<10){
                    activityLoginBinding.mobileNo.setError("Enter Valid Mobile No.");
                    flag = false;
                }
                if(password == null || password.isEmpty()){
                    activityLoginBinding.password.setError("Enter Password");
                    flag = false;
                }
                if(flag){
                    if(users!=null && users.size()>0){
                        userViewModel.getUserBg(mobileNo);
                    }else{
                        activityLoginBinding.progressBar.setVisibility(View.GONE);
                        Constants.showAlertDialog(LoginActivity.this, "You are not registered. Please register first.");
                    }
                }else{
                    return;
                }
            }
        });

        activityLoginBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}