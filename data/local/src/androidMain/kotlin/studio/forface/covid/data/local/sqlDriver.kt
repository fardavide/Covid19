package studio.forface.covid.data.local

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.KoinComponent
import org.koin.core.get

private val contextGetter = object : KoinComponent {}

internal actual val sqlDriver: SqlDriver = AndroidSqliteDriver(Database.Schema, contextGetter.get(), "covid.db")
