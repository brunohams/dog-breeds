package domain.usecase.unfavoriteImage

import domain.entity.DogImage

interface UnfavoriteImageUseCase {
    operator fun invoke(image: DogImage): DogImage
}