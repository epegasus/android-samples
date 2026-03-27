package com.sohaib.hilt.modules.provide

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * @Author: SOHAIB AHMED
 * @Date: 11/02/2025
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

@Module
@InstallIn(ActivityComponent::class)
object InternetModule {

    @Provides
    fun provideInternetManager(@ApplicationContext context: Context): InternetManager {
        return InternetManager(context)
    }
}