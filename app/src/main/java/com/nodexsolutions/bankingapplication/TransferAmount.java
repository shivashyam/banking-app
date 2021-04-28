package com.nodexsolutions.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nodexsolutions.bankingapplication.Databases.DataBaseHelper;
import com.nodexsolutions.bankingapplication.Databases.PrefManager;
import com.nodexsolutions.bankingapplication.Databases.Utils;

public class TransferAmount extends AppCompatActivity {

    private EditText amount,name,account;
    private PrefManager prefManager;
    private DataBaseHelper dataBaseHelper;
    private static final String TAG = "TransferAmount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_amount);

        prefManager = new PrefManager(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        name = findViewById(R.id.name);
        account = findViewById(R.id.account);
        amount = findViewById(R.id.amount);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isEmpty(amount) || Utils.isEmpty(name)|| Utils.isEmpty(account)){
                    Toast.makeText(TransferAmount.this, "Fill all data", Toast.LENGTH_SHORT).show();
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

    private void addToDB(){
        if (dataBaseHelper.isAlreadyExist(Utils.getText(name),Utils.getText(account))){
            if (Double.parseDouble(Utils.getText(amount)) < Double.parseDouble(prefManager.getSelectedAmount())){
                double new_amount = 0;
                new_amount = Double.parseDouble(prefManager.getSelectedAmount())-Double.parseDouble(Utils.getText(amount));
                prefManager.setSelectedAmount(new_amount+"");
                dataBaseHelper.updateAmount(prefManager.getSelectedAmountName(),prefManager.getSelectedAmount());

                Cursor cursor = dataBaseHelper.getAccount(Utils.getText(name),Utils.getText(account));
                cursor.moveToFirst();
                double am = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DataBaseHelper.BALANCE))) + Double.parseDouble(Utils.getText(amount));;
                String acc_name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.NAME));
                dataBaseHelper.updateAmount(acc_name,am+"");
                Toast.makeText(this, "Money Transferred", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "You dont have enough balance", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Incorrect account details", Toast.LENGTH_SHORT).show();
        }
    }
}