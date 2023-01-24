package presentation.ui.favorites

import domain.entity.DogImage

data class FavoritesModel(
    val images: List<DogImage> = emptyList(),
    val filteredImages: List<DogImage> = emptyList(),
    val filters: List<String> = emptyList()
)