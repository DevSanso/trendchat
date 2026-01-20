package com.github.com.devsanso.trendchat.version

import com.github.com.devsanso.trendchat.version.collect.CollectVersion1
import com.github.com.devsanso.trendchat.version.config.ConfigVersion1
import org.jetbrains.exposed.v1.jdbc.Database

interface IVersion {
    fun getVersion(): Double
    fun upgrade()
    fun downgrade()
}