package datasource.service

import datasource.dto.JsonResponseDTO
import domain.service.BreedsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class BreedsHttpService(
    private val host: String,
    private val client: HttpClient
) : BreedsService {

    override suspend fun fetchBreedsNames(): List<String> {
        val endpoint = "/breeds/list/all"
        val response = client.get("${host}$endpoint")

        val jsonResponse: JsonResponseDTO<Map<String, List<String>>> = response.body()

        return jsonResponse.message.flatMap { (breed, subBreeds) ->
            if (subBreeds.isNotEmpty()) {
                subBreeds.map { subBreed -> "$subBreed $breed" }
            } else {
                listOf(breed)
            }
        }
    }

    override suspend fun fetchImagesLinks(breedId: String): List<String> {
        val endpoint = "/breed/${breedId}/images"
        val response = client.get("${host}$endpoint")

        val jsonResponse: JsonResponseDTO<List<String>> = response.body()
        return jsonResponse.message
    }
}
