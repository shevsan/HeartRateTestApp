package ua.oshevchuk.heartratetestapp.domain.usecase.interfaces

interface ClearAllUseCase {
    suspend operator fun invoke()
}