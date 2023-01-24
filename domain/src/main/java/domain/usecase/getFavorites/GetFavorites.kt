package domain.usecase.getFavorites

import domain.entity.DogImage
import domain.repository.FavoritesRepository

class GetFavorites(
    private val favoritesRepository: FavoritesRepository,
): GetFavoritesUseCase {

    override fun invoke(): List<DogImage> {
        return favoritesRepository.getAll()
    }

}