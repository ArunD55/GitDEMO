package com.arun.arundiary.datasqlpackage

import android.provider.BaseColumns

object DatabaseManager {

    object DairyEntry : BaseColumns{
        const val TABLE_NAME =  "diaries"
        const val _ID = BaseColumns._ID
        const val COLUMN_DATE = "date"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }

}