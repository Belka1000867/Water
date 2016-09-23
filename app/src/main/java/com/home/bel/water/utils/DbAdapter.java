package com.home.bel.water.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class for work with the SQL database
 */
public final class DbAdapter {

//    private final Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private static final String TAG = "Debug_WaterDB";

    /* Prevent initialization from accidentally instantiating the WaterDb class */
    public DbAdapter(Context ctx){
        Log.d(TAG, "DbAdapter()");
//        this.context = ctx;
        dbHelper = new DbHelper(ctx);
    }

    /*
    * Inner class that defines the table contents.
    * Implements BaseColumn class in order to have the field of primary key _ID
    * */
    static abstract class StatisticsTable implements BaseColumns {
        static final String TABLE_NAME = "statistics";
        static final String COLUMN_DATE = "date";
        static final String COLUMN_AMOUNT = "amount";
        static final String COLUMN_DRUNK = "drunk";

        static String[] getColumns(){
            return new String[]{
                    COLUMN_DATE,
                    COLUMN_AMOUNT,
                    COLUMN_DRUNK
                    };
        }

    }

    public static class DbHelper extends SQLiteOpenHelper {

        /* SQL types for variables */
        private static final String TYPE_NULL = " NULL"; // null
        private static final String TYPE_TEXT = " TEXT"; // String
        private static final String TYPE_INTEGER = " INTEGER"; // long
        private static final String TYPE_REAL = " REAL"; // double
        private static final String TYPE_NUMERIC = " NUMERIC"; // boolean, date
        private static final String TYPE_BLOB = " BLOB"; // binary
        private static final String COMMA_SEP = ",";

        /* SQL queries to execute */
        private static final String SQL_CREATE_DB =
                "CREATE TABLE " + StatisticsTable.TABLE_NAME + " (" +
                        StatisticsTable._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                        StatisticsTable.COLUMN_DATE + TYPE_INTEGER + COMMA_SEP +
                        StatisticsTable.COLUMN_AMOUNT + TYPE_REAL + COMMA_SEP +
                        StatisticsTable.COLUMN_DRUNK + TYPE_REAL +
                        " )";

        private static final String SQL_DELETE_DB =
                "DROP TABLE IF EXISTS " + StatisticsTable.TABLE_NAME;



        /* Database inner variables*/
        static final String DATABASE_NAME = "water_db";
        static final int DB_VERSION = 1;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(SQL_CREATE_DB);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

            //Delete old version of DB and create a new DB
            db.execSQL(SQL_DELETE_DB);
            onCreate(db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

    }
    // end of DatabaseHelper class

    /* Open database */
    /* !!! IF the disk is full than it will be a problem of opening the database */
    public DbAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /* Close database */
    public void close(){
        if(db != null){ db.close(); }
    }

    /* Insert data to the table */
    public long insertRow(Date date, double amount, double drunk){

        Log.d(TAG, "Inserting row into the database..");

        String dateString = formatDate(date);

        Log.d(TAG, "Date in the new format : " + dateString);

        ContentValues contentValues = new ContentValues();
        contentValues.put(StatisticsTable.COLUMN_DATE, dateString);
        contentValues.put(StatisticsTable.COLUMN_AMOUNT, amount);
        contentValues.put(StatisticsTable.COLUMN_DRUNK, drunk);

        return db.insert(StatisticsTable.TABLE_NAME, null, contentValues);
    }

    /* Update row in the table */
    public boolean updateRow(Date date, double amount, double drunk){

        Log.d(TAG, "Update row with the new information.");

        String dateString = formatDate(date);

        ContentValues contentValues = new ContentValues();
        contentValues.put(StatisticsTable.COLUMN_AMOUNT, amount);
        contentValues.put(StatisticsTable.COLUMN_DRUNK, drunk);

        String where = StatisticsTable.COLUMN_DATE + "=" + dateString;

        return db.update(StatisticsTable.TABLE_NAME, contentValues, where, null) > 0;
    }

    public boolean isCurrentDay(){
        Date date = new Date();
        String dateString = formatDate(date);
        String[] selectionArgs = new String[1];
        selectionArgs[0] = dateString;

        return db.rawQuery("SELECT * FROM statistics WHERE date = ? LIMIT 1", selectionArgs).getCount() > 0;
    }

    /* Get data only about one day */
    public Cursor getRow(){

        Log.d(TAG, "Getting row from the database..");

        int oneDay = 1;

        Calendar calendar = Calendar.getInstance();
        int curWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int curYear = calendar.get(Calendar.YEAR);
        int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String table = StatisticsTable.TABLE_NAME;
//        String orderBy = StatisticsTable.COLUMN_DATE;
        String orderBy = null;
        String limit = String.valueOf(oneDay);
        String selection = StatisticsTable.COLUMN_DATE + " LIKE " + "%" + curYear + curWeekOfYear + curDayOfWeek +"%";

        return db.query(true, table, StatisticsTable.getColumns(), selection, null, null, null, orderBy, limit);
    }

    /* Get data about the current week */
    public Cursor getCurWeekRows(){

        Log.d(TAG, "Getting rows for a week from the database..");

        String tableSelection = "(SELECT * FROM statistics ORDER BY _id DESC LIMIT 7)";
        String orderBy = StatisticsTable.COLUMN_DATE + " ASC";
        String limit = "7";

       return db.query(true, tableSelection, StatisticsTable.getColumns(), null, null, null, null, orderBy, limit);

        /* ! CORRECT !*/
//        return db.rawQuery("SELECT date, amount, drunk FROM (SELECT * FROM statistics ORDER BY _id DESC LIMIT 7) ORDER BY date ASC", null);
    }

    /*
    * Private methods
    * */

    private String formatDate(Date date){
        String format = "dd-MM-yyyy";
        // Format date as - year - week number of year - day in year - month - day
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


}
