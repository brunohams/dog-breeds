package domain.usecase.fetchImageLinks

import domain.core.Logger
import domain.service.BreedsService
import domain.core.wrapper.DataState
import domain.core.wrapper.ErrorType
import domain.core.wrapper.ProgressState
import domain.entity.DogImage
import domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchImageLinks(
    private val breedsService: BreedsService,
    private val favoritesRepository: FavoritesRepository,
    private val logger: Logger
): FetchImageLinksUseCase {

    enum class Error : ErrorType {
        EMPTY_BREED_ID,
        SERVICE_ERROR,
    }

    override fun invoke(breedId: String): Flow<DataState<List<DogImage>>> = flow {

        if (breedId.isEmpty()) {
            emit(DataState.Error(Error.EMPTY_BREED_ID))
            return@flow
        }

        emit(DataState.Progress(ProgressState.LOADING))

        try {
            val images = breedsService.fetchImagesLinks(breedId)
                .map { imageUrl ->

                    println(favoritesRepository.get(imageUrl))

                    favoritesRepository.get(imageUrl) ?: run {
                        DogImage(imageUrl, breedId, false)
                    }
                }
            emit(DataState.Success(images))
        } catch (e: Exception) {
            logger.e(e)
            emit(DataState.Error(Error.SERVICE_ERROR))
        }

        emit(DataState.Progress(ProgressState.IDLE))
    }

}