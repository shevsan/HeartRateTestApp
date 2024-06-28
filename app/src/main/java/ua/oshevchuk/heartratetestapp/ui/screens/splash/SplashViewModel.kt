package ua.oshevchuk.heartratetestapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.oshevchuk.heartratetestapp.domain.repository.LocalUserRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository
) : ViewModel() {
    private val _isOpenedBeforeState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isOpenedBeforeState = _isOpenedBeforeState.asStateFlow()

    init {
        viewModelScope.launch {
            _isOpenedBeforeState.update {
                localUserRepository.isOpenedBefore().also {
                    if (!it) {
                        localUserRepository.saveOpening()
                    }
                }
            }
        }
    }
}