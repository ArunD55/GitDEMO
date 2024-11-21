package com.arun.arundiary.datasqlpackage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DATE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DESCRIPTION
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_TITLE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.TABLE_NAME
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry._ID


class DiaryDBHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "mydiaries.db"
        private const val DATABASE_VERSION = 1
      private const val SQL_CREATE_DIARY_TABLE = "CREATE TABLE $TABLE_NAME (" +
              "$_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
              "$COLUMN_DATE TEXT," +
              "$COLUMN_TITLE TEXT," +
              "$COLUMN_DESCRIPTION TEXT)"

          
          //"CREATE TABLE" + TABLE_NAME +"  ("+
//                _ID + "INTEGER PRIMARY KEY AUTOINCREMENT" +
//                COLUMN_DATE + "TEXT" +
//                COLUMN_TITLE + "TEXT" +
//                COLUMN_DESCRIPTION + "TEXT"

//        private const val SQL_CREATE_DIARY_TABLE =
//            ((((("CREATE TABLE $TABLE_NAME" + "("
//                    + _ID) + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + COLUMN_DATE) + " TEXT,"
//                    + COLUMN_TITLE) + " TEXT,"
//                    + COLUMN_DESCRIPTION) + " TEXT,")


    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_DIARY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
}