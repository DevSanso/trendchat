package com.github.com.devsanso.trendchat.version

import com.datastax.oss.driver.api.core.CqlSession
import com.github.com.devsanso.trendchat.version.collect.CollectVersion1
import com.github.com.devsanso.trendchat.version.config.ConfigVersion1
import org.jetbrains.exposed.v1.jdbc.Database

class Version1(collectDB : CqlSession, configDB : Database) : AbsVersion() {
    override val collect = CollectVersion1(collectDB)
    override val config  = ConfigVersion1(configDB)

    override fun getVersion(): Double = 1.0
}