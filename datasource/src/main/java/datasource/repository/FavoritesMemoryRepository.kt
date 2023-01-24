package datasource.repository

import domain.entity.DogImage
import domain.repository.FavoritesRepository

class FavoritesMemoryRepository : FavoritesRepository {

    private val images = mutableListOf<DogImage>()

    override fun set(image: DogImage) {
        val index = images.indexOfFirst { it.url == image.url }
        if (index != -1) {
            images.removeAt(index)
        }
        images.add(image)
    }

    override fun get(imageUrl: String): DogImage? {
        return images.firstOrNull { it.url == imageUrl }
    }

    override fun getAll(): List<DogImage> {
        return images
    }

}