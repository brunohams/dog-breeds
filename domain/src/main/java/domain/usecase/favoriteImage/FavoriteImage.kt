package domain.usecase.favoriteImage

import domain.entity.DogImage
import domain.repository.FavoritesRepository

class FavoriteImage(
    private val favoritesRepository: FavoritesRepository
) : FavoriteImageUseCase {

    override fun invoke(image: DogImage): DogImage {
        val newImage = image.copy(isFavorite = true)
        favoritesRepository.set(newImage)
        return newImage
    }

}