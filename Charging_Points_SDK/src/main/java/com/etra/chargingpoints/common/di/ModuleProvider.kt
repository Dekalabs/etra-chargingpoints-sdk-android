package com.etra.chargingpoints.common.di

import org.koin.core.module.Module

interface ModuleProvider {
    fun getModule(isMocked: Boolean = false): Module
}
