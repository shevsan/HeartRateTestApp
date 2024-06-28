package ua.oshevchuk.heartratetestapp.ui.screens.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.oshevchuk.heartratetestapp.common.Response
import ua.oshevchuk.heartratetestapp.common.executeSafely
import ua.oshevchuk.heartratetestapp.domain.usecase.interfaces.AddHeartRateResultUseCase
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import javax.inject.Inject

@HiltViewModel
class MeasuringResultsViewModel @Inject constructor(
    private val addHeartRateResultUseCase: AddHeartRateResultUseCase
) : ViewModel() {
    private val _insertResultState = MutableStateFlow<Response<Unit>?>(null)
    val insertResultState = _insertResultState.asStateFlow()

    fun insertResult(result: HeartRateResultEntity) {
        viewModelScope.launch {
            executeSafely {
                _insertResultState.emit(Response.Loading())
                addHeartRateResultUseCase(result)
            }.onSuccess {
                _insertResultState.emit(Response.Success(Unit))
            }.onFailure {
                _insertResultState.emit(Response.Error(it.message ?: ""))
            }
        }
    }
}