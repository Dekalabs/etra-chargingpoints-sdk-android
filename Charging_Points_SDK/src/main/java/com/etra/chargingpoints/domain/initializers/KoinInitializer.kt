package com.etra.chargingpoints.domain.initializers

import android.content.Context
import androidx.startup.Initializer
import com.etra.chargingpoints.di.DataModule
import com.etra.chargingpoints.di.NetworkModule
import com.etra.chargingpoints.di.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        val modules = mutableListOf<Module>().apply {
            add(DataModule.getModule())
            add(NetworkModule.getModule())
            add(UseCasesModule.getModule())
        }

        return startKoin {
            androidContext(context)
            modules(modules)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}