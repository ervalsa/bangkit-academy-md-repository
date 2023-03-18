package com.palsaloid.mynotesapp

import android.provider.BaseColumns
import com.palsaloid.mynotesapp.DatabaseContract.NoteColumns.Companion.TABLE_NAME

internal class DatabaseContract {

    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }
}