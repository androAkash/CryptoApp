package com.akash.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.akash.cryptoapp.api.CryptoApiService
import com.akash.cryptoapp.db.FavouriteCryptoDao
import com.akash.cryptoapp.db.FavouriteCryptoDatabase
import com.akash.cryptoapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRetrofitInstance():CryptoApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApiService::class.java)

    @Provides
    @Singleton
    fun provideHabitDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FavouriteCryptoDatabase::class.java,
        "fav_crypto_db"
    ).build()
    @Singleton
    @Provides
    fun provideDao(database: FavouriteCryptoDatabase) = database.getFabCryptoDao()
}