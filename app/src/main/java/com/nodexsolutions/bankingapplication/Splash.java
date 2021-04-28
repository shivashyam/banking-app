package com.nodexsolutions.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.nodexsolutions.bankingapplication.Databases.DataBaseHelper;
import com.nodexsolutions.bankingapplication.ModelClasses.CashPojo;

public class Splash extends AppCompatActivity {

    DataBaseHelper helper;
    CashPojo pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        helper = new DataBaseHelper(getApplicationContext());

        Cursor cursor = helper.getLogins();
        if (cursor.getCount()==0){
            addData();
        }
        cursor.close();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        },2300);
    }

    private void addData(){
        helper.addToLogin("prudhvi","5555555555554444","123456");
        helper.addToLogin("shiva","4111111111111111","12345");
        helper.addToLogin("shravani","6011111111111117","1234567");


        pojo = new CashPojo();
        pojo.setName("prudhvi account 1");
        pojo.setAccount("PRU123456");
        pojo.setPin("5555555555554444");
        pojo.setAmount("20000");
        helper.addToCash(pojo);

        pojo = new CashPojo();
        pojo.setName("prudhvi account 2");
        pojo.setAccount("PRU1234");
        pojo.setPin("5555555555554444");
        pojo.setAmount("30000");
        helper.addToCash(pojo);

        pojo = new CashPojo();
        pojo.setName("shiva");
        pojo.setAccount("SHI12345");
        pojo.setPin("4111111111111111");
        pojo.setAmount("50000");
        helper.addToCash(pojo);

        pojo = new CashPojo();
        pojo.setName("shravani");
        pojo.setAccount("SHR1234567");
        pojo.setPin("6011111111111117");
        pojo.setAmount("10000");
        helper.addToCash(pojo);
    }
}