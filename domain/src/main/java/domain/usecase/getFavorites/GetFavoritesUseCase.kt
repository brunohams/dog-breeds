package domain.usecase.getFavorites

import domain.entity.DogImage

interface GetFavoritesUseCase {
    operator fun invoke(): List<DogImage>
}