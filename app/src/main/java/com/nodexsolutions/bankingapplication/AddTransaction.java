package com.nodexsolutions.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nodexsolutions.bankingapplication.Databases.DataBaseHelper;
import com.nodexsolutions.bankingapplication.Databases.PrefManager;
import com.nodexsolutions.bankingapplication.Databases.Utils;
import com.nodexsolutions.bankingapplication.ModelClasses.TransactionPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransaction extends AppCompatActivity {

    private int year,month,day;
    private Calendar calendar;
    private TextView date;
    private Spinner category;
    private EditText amount,cash;
    private DataBaseHelper helper;
    private PrefManager prefManager;
    private String date_str = "";
    private static final String TAG = "AddTransaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        calendar = Calendar.getInstance();
        amount = findViewById(R.id.amount);
        category = findViewById(R.id.category);
        prefManager = new PrefManager(getApplicationContext());
        date = findViewById(R.id.date);
        cash = findViewById(R.id.cash);
        helper = new DataBaseHelper(getApplicationContext());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(amount) || Utils.isEmpty(cash)){
                    Toast.makeText(AddTransaction.this, "Fill all data", Toast.LENGTH_SHORT).show();
                }else{
                    addToDB();
                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void datePicker(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTransaction.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if ((monthOfYear + 1)<10 && dayOfMonth<10){
                            date_str  = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                            date.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        }else if ((monthOfYear + 1)<10){
                            date_str  = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                            date.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }else if (dayOfMonth<10){
                            date_str  = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                            date.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        }else{
                            date_str  = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }

                        Log.d(TAG, "onDateSet: "+date.getText().toString());
                        Log.d(TAG, "onDateSet: "+getDate());

                        if (date.getText().toString().equals(getDate())){
                            date_str = getDate();
                            date.setText("Today");
                        }else{
                            date.setText(changeDateFormat(date_str));
                        }

                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private String getDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String outputText = mdformat.format(currentTime);
        return outputText;
    }


    private void addToDB(){
        TransactionPojo pojo = new TransactionPojo();
        pojo.setAmount(Utils.getText(amount));
        pojo.setCash(Utils.getText(cash));
        pojo.setDate(date_str);
        pojo.setType(category.getSelectedItem().toString());
        pojo.setOpen(prefManager.getSelectedAmount());
        double new_amount = 0;
        Log.d(TAG, "addToDB: "+prefManager.getSelectedAmount());
        Log.d(TAG, "addToDB: "+Utils.getText(amount));
        if (Double.parseDouble(Utils.getText(amount)) < Double.parseDouble(prefManager.getSelectedAmount())){
            new_amount = Double.parseDouble(prefManager.getSelectedAmount())-Double.parseDouble(Utils.getText(amount));
            prefManager.setSelectedAmount(new_amount+"");
            helper.updateAmount(prefManager.getSelectedAmountName(),prefManager.getSelectedAmount());
            helper.addToTransaction(pojo);
            Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "You dont have enough balance", Toast.LENGTH_SHORT).show();
        }
    }

    public String changeDateFormat(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = prefManager.getDateFormate();

        Log.d(TAG, "changeDateFormat: "+outputPattern);
        Log.d(TAG, "changeDateFormat: "+time);

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}