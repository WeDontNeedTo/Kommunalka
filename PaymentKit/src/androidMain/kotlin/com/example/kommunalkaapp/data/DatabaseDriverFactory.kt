package com.example.kommunalkaapp.data

import android.content.Context
import com.example.kommunalkaapp.db.KommunalkaDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(KommunalkaDatabase.Schema, context, "test.db")
    }
}