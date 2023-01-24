package domain.usecase.unfavoriteImage

import domain.entity.DogImage
import domain.repository.FavoritesRepository

class UnfavoriteImage(
    private val favoritesRepository: FavoritesRepository
) : UnfavoriteImageUseCase {

    override fun invoke(image: DogImage): DogImage {
        val newImage = image.copy(isFavorite = false)
        favoritesRepository.set(newImage)
        return newImage
    }

}