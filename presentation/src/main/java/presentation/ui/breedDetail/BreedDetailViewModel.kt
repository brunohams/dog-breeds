package presentation.ui.breedDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreeds.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.core.wrapper.DataState
import domain.core.wrapper.ErrorType
import domain.core.wrapper.ProgressState
import domain.entity.DogImage
import domain.usecase.favoriteImage.FavoriteImageUseCase
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchImageLinks.FetchImageLinksUseCase
import domain.usecase.unfavoriteImage.UnfavoriteImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel
@Inject
constructor(
    private val fetchImageLinks: FetchImageLinksUseCase,
    private val favoriteImage: FavoriteImageUseCase,
    private val unfavoriteImage: UnfavoriteImageUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<BreedDetailModel> = MutableStateFlow(BreedDetailModel())
    val state = _state as StateFlow<BreedDetailModel>

    fun requestImages(brandId: String) {
        viewModelScope.launch {
            fetchImageLinks(brandId).collect { dataState ->
                handle(dataState)
            }
        }
    }

    private fun handle(dataState: DataState<List<DogImage>>) {
        when (dataState) {
            is DataState.Error -> {
                setErrorFor(dataState.error)
            }
            is DataState.Progress -> {
                setProgressState(isLoading = dataState.progressState == ProgressState.LOADING)
            }
            is DataState.Success -> {
                setImages(dataState.data)
                clearError()
            }
        }
    }

    private fun setImages(images: List<DogImage>) {
        _state.update { it.copy(images = images) }
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

    fun toggleFavorite(image: DogImage) {
        val newImage = if (image.isFavorite) {
            unfavoriteImage(image)
        } else {
            favoriteImage(image)
        }
        _state.value.images.indexOfFirst { it.url == image.url }.let { index ->
            _state.update { it.copy(images = it.images.toMutableList().apply { set(index, newImage) }) }
        }
    }

}

