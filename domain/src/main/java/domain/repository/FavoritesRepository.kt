package domain.repository

import domain.entity.DogImage

interface FavoritesRepository {
    fun set(image: DogImage)
    fun get(imageUrl: String): DogImage?
    fun getAll(): List<DogImage>
}