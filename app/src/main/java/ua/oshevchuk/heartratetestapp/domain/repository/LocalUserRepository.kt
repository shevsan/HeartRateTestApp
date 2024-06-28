package ua.oshevchuk.heartratetestapp.domain.repository

interface LocalUserRepository {
    suspend fun saveOpening()
    suspend fun isOpenedBefore() : Boolean
}