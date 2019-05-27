package com.mr.j;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.Date;

class DBHelper {

    private SQLiteDatabase database;
    private Context context;

    DBHelper(Context context) {
        this.context = context;
    }

    void createDB() {
        database = context.openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
        database.execSQL(Constants.QUERY_CREATE_TABLE);
        database.execSQL(Constants.QUERY_CREATE_COUNT_TABLE);
        database.close();
    }

    void insertInFollowers(String sql) {
        try {
            database = context.openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
            database.execSQL(sql);
//Test code for entries
//            Cursor cursor = database.rawQuery("select * from followers", null);
//            if (cursor != null && cursor.moveToFirst()) {
//                StringBuilder s = new StringBuilder();
//                do{
//                    s.append(cursor.getString(0) + "\n");
//                }while (cursor.moveToNext());
//
//                Log.e("printing",s.toString());
//            }
            database.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    void insertInCounts(int value) {
        database = context.openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
        database.execSQL(String.format(Constants.QUERY_INSERT_IN_COUNTS, value));
    }

    int getRowCount() {
        database = context.openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
        int count;
        Cursor resultSet = database.rawQuery(Constants.QUERY_COUNT_ROWS, null);
        count = resultSet.getInt(0);
        resultSet.close();
        database.close();
        return count;
    }
}
