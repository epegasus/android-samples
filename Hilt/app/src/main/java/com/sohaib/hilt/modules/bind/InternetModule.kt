package com.sohaib.hilt.modules.bind

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: SOHAIB AHMED
 * @Date: 11/02/2025
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class InternetModule {
    @Binds
    @Singleton
    abstract fun bindInternetManager(internetManagerImpl: InternetManagerImpl): InternetManager
}