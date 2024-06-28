package ua.oshevchuk.heartratetestapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.oshevchuk.heartratetestapp.common.PreferencesKeys
import ua.oshevchuk.heartratetestapp.di.annotations.IoDispatcher
import ua.oshevchuk.heartratetestapp.domain.repository.LocalUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalUserRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dataStore: DataStore<Preferences>
) : LocalUserRepository {
    override suspend fun saveOpening() {
        withContext(ioDispatcher) {
            dataStore.edit {
                it[PreferencesKeys.isOpenedBefore] = true
            }
        }
    }

    override suspend fun isOpenedBefore(): Boolean = withContext(ioDispatcher) {
        dataStore.data.map {
            it[PreferencesKeys.isOpenedBefore]
        }.firstOrNull() ?: false
    }
}