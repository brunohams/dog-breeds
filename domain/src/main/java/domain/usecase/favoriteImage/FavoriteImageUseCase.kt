package domain.usecase.favoriteImage

import domain.entity.DogImage

interface FavoriteImageUseCase {
    operator fun invoke(image: DogImage): DogImage
}