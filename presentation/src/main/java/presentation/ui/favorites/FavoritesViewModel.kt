package presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.usecase.getFavorites.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
@Inject
constructor(
    private val getFavorites: GetFavoritesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FavoritesModel> = MutableStateFlow(FavoritesModel())
    val state = _state as StateFlow<FavoritesModel>

    fun loadFavoriteImages() {
        val images = getFavorites()
        val filters = listOf("all") + images.map { it.breedId }.distinct()
        _state.update { it.copy(images = images, filteredImages = images, filters = filters) }
    }

    fun filterBy(item: String) {
        val filteredImages = if (item == "all") {
            _state.value.images
        } else {
            _state.value.images.filter { it.breedId == item }
        }
        _state.update { it.copy(filteredImages = filteredImages) }
    }

}

