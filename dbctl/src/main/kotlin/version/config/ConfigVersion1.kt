package com.github.com.devsanso.trendchat.version.config

import com.github.com.devsanso.trendchat.version.IVersion
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.dao.id.UIntIdTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

class ConfigVersion1(val db : Database) : IVersion {
    object TcService : UIntIdTable("tc_service") {
        val name = varchar("name", 255)
        val service_type = varchar("service_type", 8)

        init {
            uniqueIndex(name)
        }
    }

    override fun getVersion(): Double = 1.0

    override fun upgrade() {
        transaction(db) {
            SchemaUtils.create(TcService)


        }
    }

    override fun downgrade() {
        throw Exception("init version, not exists downgrade")
    }

}