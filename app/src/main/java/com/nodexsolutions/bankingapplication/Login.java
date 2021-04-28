package com.nodexsolutions.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nodexsolutions.bankingapplication.Databases.DataBaseHelper;
import com.nodexsolutions.bankingapplication.Databases.PrefManager;
import com.nodexsolutions.bankingapplication.Databases.Utils;

public class Login extends AppCompatActivity {

    private EditText email,pass;
    private PrefManager prefManager;
    private DataBaseHelper helper;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.card_bg));
        }


        init();

//        if (prefManager.getAlreadySignin()){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });
    }

    private void init(){
        helper = new DataBaseHelper(getApplicationContext());
        email = findViewById(R.id.login_email);
        pass = findViewById(R.id.login_pass);
        prefManager = new PrefManager(getApplicationContext());
    }

    private Boolean invalidate(){


        if (email.getText().toString().isEmpty()){
            email.setError("Enter Username");
            return false;
        }

        if (pass.getText().toString().isEmpty()){
            pass.setError("Enter Password");
            return false;
        }

        return true;

    }

    private void loginProcess(){
        if (!invalidate()) {
            return;
        }

        if (helper.login(Utils.getText(email),Utils.getText(pass))){
            prefManager.setUniqueKey(Utils.getText(email));
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else{
            Toast.makeText(this, "incorrect password or pin", Toast.LENGTH_LONG).show();
        }
    }
}