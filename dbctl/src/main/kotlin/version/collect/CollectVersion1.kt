package com.github.com.devsanso.trendchat.version.collect

import com.datastax.oss.driver.api.core.CqlSession
import com.github.com.devsanso.trendchat.version.IVersion
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.JdbcTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class CollectVersion1(val db : CqlSession) : IVersion {
    override fun getVersion(): Double = 1.0

    fun createTcServiceLog() {
        db.execute("""CREATE TABLE IF NOT EXISTS tc_service_log (
                collect_time timestamp,
                service_id   int,
                category     Text,
                log_data     Text,
                PRIMARY KEY (service_id, collect_time, category)
                )""")
    }

    override fun upgrade() {
        createTcServiceLog()

    }

    override fun downgrade() {
        throw Exception("init version, not exists downgrade")
    }
}