package com.example.kommunalkaapp.data

import com.example.kommunalkaapp.db.KommunalkaDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(KommunalkaDatabase.Schema, "test.db")
    }
}