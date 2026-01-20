package com.github.com.devsanso.trendchat

import com.datastax.oss.driver.api.core.CqlSession
import com.github.com.devsanso.trendchat.conf.load
import com.github.com.devsanso.trendchat.version.AbsVersion
import com.github.com.devsanso.trendchat.version.IVersion
import com.github.com.devsanso.trendchat.version.Version1
import org.jetbrains.exposed.v1.jdbc.Database
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.URI
import java.net.URL
import java.util.SortedMap
import kotlin.reflect.KClass
import kotlin.system.exitProcess

val versionMap : SortedMap<Double, KClass<out AbsVersion>> = sortedMapOf(
    1.0 to Version1::class,
)

fun install(configDB : Database, collectDB : CqlSession) {
    val constructor = versionMap[1.0]?.constructors?.first()
    val ver = constructor?.call(collectDB, configDB);

    ver?.upgrade()
}

fun connectCollectDB(url : String, user : String, passwd : String) : CqlSession {
    val url = URI(url)
    if(url.scheme != "cassandra") {
        throw Exception("not cassandra url")
    }

    val split = url.path.split("/")
    val dc = split[1]
    val keyspace = split[2]

    val builder = CqlSession.builder()
    builder.addContactPoint(InetSocketAddress(url.host, url.port))
    builder.withAuthCredentials(user, passwd)
    builder.withKeyspace(keyspace)
    builder.withLocalDatacenter(dc)

    return builder.build()
}

fun main(args: Array<String>) {
    lateinit var configDB : Database
    lateinit var collectDB : CqlSession

    if (args.size < 2) {
        println("args count not match ${args.size}")
        return
    }

    try {
        val conf = load(args[1])
        collectDB = connectCollectDB(conf.collect.url, conf.collect.user, conf.collect.passwd)
        configDB = Database.connect(url = conf.config.url, user = conf.config.user, password = conf.config.passwd)

    } catch(e: Exception) {
        println("FAIL: init jdbc connection")
        println(e.message)
        exitProcess(2)
    }

    try {
        when (args[0]) {
            "install" -> install(configDB, collectDB)
            else -> throw Exception("unknown option ${args[0]}")
        }
    } catch(e: Exception) {
        println("FAIL: install error")
        println(e.message)
        println(e.stackTrace.joinToString("\n"))
    } finally {
        collectDB.close()
    }

}