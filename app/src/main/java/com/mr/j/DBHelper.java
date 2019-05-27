package com.mr.j;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class DBHelper {

    private Context context;
    private SQLiteDatabase database;

    DBHelper(Context context) {
        this.context = context;
    }

    void createDB() {
        database = context.openOrCreateDatabase(Constants.DATABASE_NAME, context.MODE_PRIVATE, null);
        database.execSQL(Constants.QUERY_CREATE_TABLE);
        database.execSQL(Constants.QUERY_CREATE_COUNT_TABLE);
    }

    void insertInFollowers(String value) {
        database.execSQL(String.format(Constants.QUERY_INSERT_IN_FOLLOWERS, value));
    }

    void insertInCounts(int value) {
        database.execSQL(String.format(Constants.QUERY_INSERT_IN_COUNTS, value));
    }

    int getRowCount() {
        int count;
        Cursor resultSet = database.rawQuery(Constants.QUERY_COUNT_ROWS, null);
        count = resultSet.getInt(0);
        resultSet.close();
        return count;
    }
}
