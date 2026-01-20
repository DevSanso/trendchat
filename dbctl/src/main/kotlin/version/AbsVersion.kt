package com.github.com.devsanso.trendchat.version

import org.jetbrains.exposed.v1.jdbc.Database

abstract class AbsVersion : IVersion {
    abstract val collect : IVersion
    abstract val config : IVersion

    abstract override fun getVersion(): Double

    override fun upgrade() {
        collect.upgrade()
        config.upgrade()
    }

    override fun downgrade() {
        collect.downgrade()
        config.downgrade()
    }
}