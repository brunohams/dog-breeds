package presentation.ui.breedList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreeds.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.core.wrapper.DataState
import domain.core.wrapper.ErrorType
import domain.core.wrapper.ProgressState
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchBreeds.FetchBreedsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.ui.BreedCellMapper
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel
@Inject
constructor(
    private val fetchBreeds: FetchBreedsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BreedListModel> = MutableStateFlow(BreedListModel())
    val state = _state as StateFlow<BreedListModel>

    fun requestBreeds() {
        viewModelScope.launch {
            fetchBreeds().collect { dataState ->
                handle(dataState)
            }
        }
    }

    private fun handle(dataState: DataState<List<String>>) {
        when (dataState) {
            is DataState.Error -> {
                setErrorFor(dataState.error)
            }
            is DataState.Progress -> {
                setProgressState(isLoading = dataState.progressState == ProgressState.LOADING)
            }
            is DataState.Success -> {
                setBreeds(dataState.data)
                clearError()
            }
        }
    }

    private fun setBreeds(breeds: List<String>) {
        val cells = breeds.map { BreedCellMapper.toCell(it) } // This mapper could be injected, using it like this can be difficult to mock objects.
        _state.update { it.copy(breeds = cells) }
    }

    private fun setProgressState(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun setErrorFor(error: ErrorType) {
        when(error) {
            FetchBreeds.Error.NO_CONNECTION_ERROR -> {
                _state.update { it.copy(errorRes = R.string.no_connection_error) }
            }
            FetchBreeds.Error.SERVICE_ERROR -> {
                _state.update { it.copy(errorRes = R.string.service_error) }
            }
            FetchBreeds.Error.UNKNOWN_ERROR -> {
                _state.update { it.copy(errorRes = R.string.unknown_error) }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(errorRes = null) }
    }


}

