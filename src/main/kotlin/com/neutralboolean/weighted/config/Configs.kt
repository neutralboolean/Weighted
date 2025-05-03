package com.neutralboolean.weighted.config

import me.fzzyhmstrs.fzzy_config.api.ConfigApi

object Configs {
    val serverConfig = ConfigApi.registerAndLoadConfig(::ServerConfig)

    fun init() {}
}