package com.arun.arundiary.pages

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.arun.arundairy.R
import com.arun.arundairy.databinding.ActivityNewDairyEntryBinding
import com.arun.arundiary.datasqlpackage.DiaryDBHelper
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DATE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DESCRIPTION
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_TITLE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.TABLE_NAME
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry._ID
import java.text.SimpleDateFormat
import java.util.Date

class NewDiaryEntry : AppCompatActivity() {

    private var id = 0

    private lateinit var binding: ActivityNewDairyEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDairyEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id= intent.getIntExtra("IDofRow",0)

        if (id!= 0){
            readDiary(id)
        }

        Log.d("NewDiary", "The Pass ID is: $id")

        val currentDate = SimpleDateFormat("yyyy-MM-dd")
        binding.datefield.text= currentDate.format(Date())
    }

    private fun readDiary(id: Int) {
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.readableDatabase
        val projection = arrayOf(COLUMN_DATE, COLUMN_TITLE, COLUMN_DESCRIPTION)

        var selection = "$_ID = ?"
        var selectionArgs = arrayOf("$id")

        val cursor :Cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,null,null,null
        )

        val dateColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val descriptionColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)

        while (cursor.moveToNext()){
            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle = cursor.getString(titleColumnIndex)
            val currentDescription = cursor.getString(descriptionColumnIndex)

            binding.datefield.text = currentDate
            binding.titleentryfield.setText(currentTitle)
            binding.descentryfield.setText(currentDescription)
        }
        cursor.close()
    }


    private fun insertDiary() {
        val dateString = binding.datefield.text.toString()
        val titleString = binding.titleentryfield.text.toString().trim(){it <= ' ' }
        val descString = binding.descentryfield.text.toString().trim(){it <= ' ' }

        val mdbHelper = DiaryDBHelper(this)

        val db = mdbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_DATE, dateString)
            put(COLUMN_TITLE, titleString)
            put(COLUMN_DESCRIPTION, descString)
        }
        val rowId = db.insert(TABLE_NAME, null, values)
        if (rowId.equals(-1)){
            Toast.makeText(this, "New Diary didn't insert", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Diary has been inserted $rowId", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDiary(id: Int) {

        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, binding.titleentryfield.toString())
            put(COLUMN_DESCRIPTION,binding.descentryfield.toString())
        }
        db.update(TABLE_NAME, values, "$_ID=$id", null)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.diaryentrymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.savebutton ->{

                if (id == 0) {
                    insertDiary()
                }else(
                        updateDiary(id)
                )
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




}