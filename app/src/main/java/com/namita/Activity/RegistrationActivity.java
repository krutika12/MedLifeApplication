package com.namita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.namita.Common.Constants;
import com.namita.Database.User;
import com.namita.ViewModel.UserViewModel;
import com.namita.medlifeapplication.R;
import com.namita.medlifeapplication.databinding.ActivityRegistrationBinding;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    String firstName, lastName, phoneNumber, emailId, password;
    boolean flag;
    Dialog mDialog;
    String otp;
    UserViewModel userViewModel;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);

        ActivityRegistrationBinding registrationBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        users = new ArrayList<>();
        userViewModel = new ViewModelProvider(RegistrationActivity.this).get(UserViewModel.class);

        userViewModel.getUsers().observe(this, users -> {
            this.users = users;
        });

        userViewModel.getUser().observe(this, user -> {
            registrationBinding.progressBar.setVisibility(View.GONE);
            if (user!=null && user.getMobileNo().equalsIgnoreCase(phoneNumber)) {
                Constants.showAlertDialog(RegistrationActivity.this, "Mobile No. is already registered.");
            } else {
                showOTP();
            }
        });

        registrationBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationBinding.progressBar.setVisibility(View.VISIBLE);
                firstName = registrationBinding.firstName.getText().toString();
                lastName = registrationBinding.lastName.getText().toString();
                phoneNumber = registrationBinding.mobileNo.getText().toString();
                emailId = registrationBinding.emailId.getText().toString();
                password = registrationBinding.password.getText().toString();
                flag = true;
                if (firstName == null || firstName.isEmpty()) {
                    registrationBinding.firstName.setError("Enter First Name");
                    flag = false;
                }
                if (lastName == null || lastName.isEmpty()) {
                    registrationBinding.lastName.setError("Enter Last Name");
                    flag = false;
                }
                if (phoneNumber == null || phoneNumber.isEmpty()) {
                    registrationBinding.mobileNo.setError("Enter Mobile No.");
                    flag = false;
                } else if (phoneNumber.length() < 10) {
                    registrationBinding.mobileNo.setError("Enter Valid Mobile No.");
                    flag = false;
                }
                if (password == null || password.isEmpty()) {
                    registrationBinding.password.setError("Enter Password");
                    flag = false;
                }
                if (flag) {
                    if (users != null && users.size() > 0) {
                        userViewModel.getUserBg(phoneNumber);
                    } else {
                        registrationBinding.progressBar.setVisibility(View.GONE);
                        showOTP();
                    }
                } else {
                    return;
                }
            }
        });

        registrationBinding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showOTP() {
        mDialog = new Dialog(RegistrationActivity.this, R.style.Theme_Transparent);
        mDialog.setContentView(R.layout.otp_popup);

        ImageView imgClose;
        EditText txtOtp;
        TextView txtSubmit;

        imgClose = (ImageView) mDialog.findViewById(R.id.close);
        txtOtp = (EditText) mDialog.findViewById(R.id.otp);
        txtSubmit = (TextView) mDialog.findViewById(R.id.btn_submit);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = txtOtp.getText().toString();
                if (otp == null || otp.isEmpty()) {
                    txtOtp.setError("Enter OTP");
                    return;
                }
                if (otp.equalsIgnoreCase(Constants.OTP)) {
                    userViewModel.insert(new User(firstName, lastName, phoneNumber, emailId, password));
                    Constants.showAlertDialog(RegistrationActivity.this, "Registered Successfully");
                    mDialog.dismiss();
                    Intent intent = new Intent(RegistrationActivity.this, HomescreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Constants.showAlertDialog(RegistrationActivity.this, "Incorrect OTP");
                }
            }
        });

        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.show();
    }

}