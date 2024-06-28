package ua.oshevchuk.heartratetestapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ua.oshevchuk.heartratetestapp.common.Constants
import ua.oshevchuk.heartratetestapp.data.database.AppDatabase
import ua.oshevchuk.heartratetestapp.di.annotations.DefaultDispatcher
import ua.oshevchuk.heartratetestapp.di.annotations.IoDispatcher
import ua.oshevchuk.heartratetestapp.di.annotations.MainDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "heart_results"
    ).build()

    @Singleton
    @Provides
    fun provideDataPreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }),
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    Constants.PREFERENCES_NAME
                )
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(Constants.PREFERENCES_NAME) })


    @Singleton
    @Provides
    fun provideHeartRateDao(db: AppDatabase) = db.heartRateDao()

    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}