package com.nodexsolutions.bankingapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nodexsolutions.bankingapplication.Adapters.CashAdapter;
import com.nodexsolutions.bankingapplication.Databases.DataBaseHelper;
import com.nodexsolutions.bankingapplication.Databases.PrefManager;
import com.nodexsolutions.bankingapplication.ModelClasses.CashPojo;
import com.nodexsolutions.bankingapplication.ModelClasses.Gloables;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataBaseHelper helper;
    private List<CashPojo> data;
    TextView total;
    double total_amount = 0;
    PrefManager prefManager;
    AlertDialog dialog;
    String currency_str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        helper = new DataBaseHelper(getApplicationContext());
        data = new ArrayList<>();
        total = findViewById(R.id.total);
        prefManager = new PrefManager(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData(){
        data.clear();
        total_amount = 0;
        Cursor cursor = helper.getCash(prefManager.getUniqueKey());
        while (cursor.moveToNext()){
            CashPojo cashPojo = new CashPojo();
            cashPojo.setName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.NAME)));
            cashPojo.setAmount(cursor.getString(cursor.getColumnIndex(DataBaseHelper.BALANCE)));
            total_amount = total_amount + Float.parseFloat(cursor.getString(cursor.getColumnIndex(DataBaseHelper.BALANCE)));

            data.add(cashPojo);
        }

        if (data.size()==1){
            if (prefManager.getSelectedAmountName() == null){
                prefManager.setSelectedAmount(data.get(0).getAmount());
                prefManager.setSelectedAmountName(data.get(0).getName());
            }
        }

        cursor.close();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        total.setText("INR "+formatter.format(total_amount)+"");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        CashAdapter adapter = new CashAdapter(getApplicationContext(), data, new CashAdapter.onItemClick() {
            @Override
            public void onClick(CashPojo pojo) {
                prefManager.setSelectedAmount(pojo.getAmount());
                prefManager.setSelectedAmountName(pojo.getName());
                optionDialogue();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void optionDialogue(){

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.transfer_option,null);

        view.findViewById(R.id.pay_bill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(getApplicationContext(),AddTransaction.class));
            }
        });

        view.findViewById(R.id.transfer_amount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(getApplicationContext(),TransferAmount.class));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
}