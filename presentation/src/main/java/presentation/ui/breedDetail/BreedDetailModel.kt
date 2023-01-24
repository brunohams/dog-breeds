package presentation.ui.breedDetail

import androidx.annotation.IdRes
import domain.entity.DogImage

data class BreedDetailModel(
    val isLoading: Boolean = false,
    val images: List<DogImage> = emptyList(),
    @IdRes val errorRes: Int? = null
)
