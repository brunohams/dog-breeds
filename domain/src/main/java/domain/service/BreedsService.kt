package domain.service

interface BreedsService {
    suspend fun fetchBreedsNames(): List<String>
    suspend fun fetchImagesLinks(breedId: String): List<String>
}

