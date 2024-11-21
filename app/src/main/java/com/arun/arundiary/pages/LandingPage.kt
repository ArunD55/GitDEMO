package com.arun.arundiary.pages

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arun.arundiary.RecyclerViewAdapter
import com.arun.arundairy.databinding.ActivityLandingPageBinding
import com.arun.arundiary.datasqlpackage.DiaryDBHelper
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DATE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_DESCRIPTION
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.COLUMN_TITLE
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry.TABLE_NAME
import com.arun.arundiary.datasqlpackage.DatabaseManager.DairyEntry._ID
import com.arun.arundiary.datasqlpackage.Dataclass

class LandingPage : AppCompatActivity() {

    private lateinit var mDBHelper: DiaryDBHelper

    private lateinit var binding : ActivityLandingPageBinding
    private var entryList : ArrayList<Dataclass?> =ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDBHelper = DiaryDBHelper(this)

        displayDataInfo()

//        entryList.add(Dataclass("GOT", "The Sifi book", "10-20-1989"))
//        entryList.add(Dataclass("Matter", "The Science book", "10-29-1973") )
//        entryList.add(Dataclass("FiftyShades", "The Romance book", "12-02-2001"))
    }

    private fun displayDataInfo() {
        val db = mDBHelper.readableDatabase

        val projection = arrayOf(_ID, COLUMN_DATE, COLUMN_TITLE, COLUMN_DESCRIPTION)

        val cursor : Cursor = db.query(TABLE_NAME, projection, null,null,null,null,null,null)

        val idColumnIndex = cursor.getColumnIndexOrThrow(_ID)
        val dateColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val descriptionColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)

        while (cursor.moveToNext()){
            val currentId = cursor.getInt(idColumnIndex)
            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle = cursor.getString(titleColumnIndex)
            val currentDescription = cursor.getString(descriptionColumnIndex)

            entryList.add(Dataclass(currentId, currentDate, currentTitle, currentDescription))
        }
        cursor.close()

        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewid.layoutManager = linearLayoutManager

        adapter = RecyclerViewAdapter(entryList)
        binding.recyclerviewid.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        entryList.clear()
        displayDataInfo()
    }

    fun createNewDiary(vew: View){
        val intent = Intent(this, NewDiaryEntry::class.java)
        startActivity(intent)
    }
}