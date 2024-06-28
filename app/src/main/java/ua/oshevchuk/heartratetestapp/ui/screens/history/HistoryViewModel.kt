package ua.oshevchuk.heartratetestapp.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.oshevchuk.heartratetestapp.common.Response
import ua.oshevchuk.heartratetestapp.common.executeSafely
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.ClearAllUseCase
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.GetAllHeartResultsUseCase
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllHeartResultsUseCase: GetAllHeartResultsUseCase,
    private val clearAllUseCase: ClearAllUseCase
) : ViewModel() {

    private val _clearAllState = MutableStateFlow<Response<Unit>?>(null)
    val clearAllState = _clearAllState.asStateFlow()

    private val _resultsState = MutableStateFlow<Response<List<HeartRateResultEntity?>?>?>(null)
    val resultsState = _resultsState.asStateFlow()

    fun clearAll() {
        viewModelScope.launch {
            executeSafely {
                _clearAllState.emit(Response.Loading())
                clearAllUseCase()
            }.onSuccess {
                _clearAllState.emit(Response.Success(Unit))
            }.onFailure {
                _clearAllState.emit(Response.Error(it.message ?: ""))
            }
        }
    }

     fun getAllResults() {
        viewModelScope.launch {
            executeSafely {
                _resultsState.emit(Response.Loading())
                getAllHeartResultsUseCase()
            }.onSuccess {
                _resultsState.emit(Response.Success(it))
            }.onFailure {
                _resultsState.emit(Response.Error(it.message ?: ""))
            }
        }
    }


}