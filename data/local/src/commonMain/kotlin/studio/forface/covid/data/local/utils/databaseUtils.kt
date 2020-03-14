package studio.forface.covid.data.local.utils

import com.squareup.sqldelight.Transacter
import studio.forface.covid.data.local.Database

/**
 * Standalone class for execute a [Database.transaction]
 * Its purpose is to be able to run a Transaction without depending by [Database]
 *
 * @author Davide Farella
 */
internal class TransactionProvider(private val database: Database) {
    operator fun invoke(noEnclosing: Boolean = false, transaction: TransactionBlock) {
        database.transaction(noEnclosing, transaction)
    }
}

internal typealias TransactionBlock = Transacter.Transaction.() -> Unit
