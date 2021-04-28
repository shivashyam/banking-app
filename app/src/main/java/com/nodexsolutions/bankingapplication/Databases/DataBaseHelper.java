package com.nodexsolutions.bankingapplication.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nodexsolutions.bankingapplication.ModelClasses.CashPojo;
import com.nodexsolutions.bankingapplication.ModelClasses.TransactionPojo;


public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "bankapp";

    // Table Names
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_CASH = "cash";
    private static final String TABLE_TRANS = "trans";
    private static final String TABLE_CATEGORY = "cate";

    // Common column names
    public static final String KEY_ID = "id";

    //cash column
    public static final String ACCOUNT = "account";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";

    //transaction columns
    public static final String AMOUNT = "amount";
    public static final String CATEGORY = "category";
    public static final String DATE = "date";
    public static final String NOTE = "note";
    public static final String CASH = "cash";
    public static final String TYPE = "type";
    public static final String OPEN = "open";
    public static final String END = "end_balance";

    //login table
    public static final String PASSWORD = "password";
    public static final String PIN = "pin";


    //  table create statement
    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ PIN + " TEXT,"
            + PASSWORD + " TEXT," + NAME + " TEXT" + ")";

    private static final String CREATE_TABLE_CASH = "CREATE TABLE " + TABLE_CASH
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ACCOUNT + " TEXT," + NAME + " TEXT," + PIN + " TEXT,"+ BALANCE + " TEXT" + ")";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT," + TYPE + " TEXT" + ")";

    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ AMOUNT + " TEXT,"
            + OPEN + " TEXT," + END + " TEXT," + TYPE + " TEXT," + CASH + " TEXT," + CATEGORY + " TEXT," + DATE + " TEXT,"+ NOTE + " TEXT" + ")";


    private Context context;
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        SQLiteDatabase database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CASH);
        db.execSQL(CREATE_TABLE_TRANSACTION);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        // create new tables
        onCreate(db);
    }


    public void addToCash(CashPojo model){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,model.getName());
        contentValues.put(PIN,model.getPin());
        contentValues.put(BALANCE,model.getAmount());
        contentValues.put(ACCOUNT,model.getAccount());
        try {
            database.insertOrThrow(TABLE_CASH,null,contentValues);
        }catch (Exception e){
            Log.d("Databasehelper", "addToRecords: "+e.getMessage());
        }
    }

    public void addToLogin(String na, String p, String pass){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,na);
        contentValues.put(PIN,p);
        contentValues.put(PASSWORD,pass);
        try {
            database.insertOrThrow(TABLE_LOGIN,null,contentValues);
        }catch (Exception e){
            Log.d("Databasehelper", "addToRecords: "+e.getMessage());
        }
    }

    public void addToTransaction(TransactionPojo model){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT,model.getAmount());
        contentValues.put(CASH,model.getCash());
        contentValues.put(CATEGORY,model.getCategory());
        contentValues.put(DATE,model.getDate());
        contentValues.put(NOTE,model.getNote());
        contentValues.put(TYPE,model.getType());
        contentValues.put(OPEN,model.getOpen());
        contentValues.put(END,model.getEnd());
        try {
            database.insertOrThrow(TABLE_TRANS,null,contentValues);
        }catch (Exception e){
            Log.d("Databasehelper", "addToTrans: "+e.getMessage());
        }
    }

    public void addToCategory(TransactionPojo model){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,model.getCategory());
        contentValues.put(TYPE,model.getType());
        try {
            database.insertOrThrow(TABLE_CATEGORY,null,contentValues);
        }catch (Exception e){
            Log.d("Databasehelper", "addToTrans: "+e.getMessage());
        }
    }


    public Cursor getCash(String pi){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor =  database.query(TABLE_CASH,new String[]{KEY_ID,NAME,PIN,BALANCE,ACCOUNT},PIN + "=?",new String[]{pi},null,null,null);
        return cursor;
    }

    public Cursor getCategory(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor d = database.rawQuery("SELECT * FROM "+TABLE_CATEGORY ,null);
        return d;
    }

    public Cursor getLogins(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor d = database.rawQuery("SELECT * FROM "+TABLE_LOGIN ,null);
        return d;
    }

    public Cursor getTrans(String startDate, String endDate, String cash){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor mCursor = database.rawQuery("SELECT * FROM "+ TABLE_TRANS +
                " WHERE "+ CASH+"=? AND " + DATE +
                " BETWEEN ?  AND ? ORDER BY "+KEY_ID+" ASC", new String[]{cash,startDate, endDate});
        return mCursor;
    }

    public Cursor getTransByDate(String d, String cash){

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor mCursor = database.rawQuery("SELECT * FROM "+ TABLE_TRANS +
                " WHERE "+ CASH+"=? AND " + DATE +
                " = ? ORDER BY "+KEY_ID+" ASC", new String[]{cash,d});
        return mCursor;

    }

    public Boolean login(String pin, String password){
        Log.d(TAG, "login:pin "+pin);
        Log.d(TAG, "login:password "+password);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor =  database.query(TABLE_LOGIN,new String[]{KEY_ID,NAME,PIN,PASSWORD},PIN + "=? AND "+PASSWORD+"=?",new String[]{pin,password},null,null,null);
        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        }else{
            cursor.close();
        }
        return false;
    }

    public Boolean isAlreadyExist(String name, String account){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor =  database.query(TABLE_CASH,new String[]{KEY_ID,NAME,ACCOUNT,PIN,BALANCE},NAME + "=? AND "+ACCOUNT + "=?",new String[]{name,account},null,null,null);
        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        }else{
            cursor.close();
        }
        return false;
    }

    public Cursor getAccount(String name, String account){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor =  database.query(TABLE_CASH,new String[]{KEY_ID,NAME,ACCOUNT,PIN,BALANCE},NAME + "=? AND "+ACCOUNT + "=?",new String[]{name,account},null,null,null);
        return cursor;
    }

    public void deleteTrans(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_TRANS,KEY_ID+"=?",new String[]{id});
    }

    public void emptyHistory(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_CASH);
        database.execSQL("vacuum");
    }

    public void updateAmount(String key, String value){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE,value);
        database.update(TABLE_CASH,contentValues,NAME+"=?",new String[]{key});

    }

    public void updateTrans(TransactionPojo model, String key){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT,model.getAmount());
        contentValues.put(CASH,model.getCash());
        contentValues.put(CATEGORY,model.getCategory());
        contentValues.put(DATE,model.getDate());
        contentValues.put(NOTE,model.getNote());
        contentValues.put(TYPE,model.getType());
        contentValues.put(OPEN,model.getOpen());
        contentValues.put(END,model.getEnd());
        database.update(TABLE_TRANS,contentValues,KEY_ID+"=?",new String[]{key});

    }

}
