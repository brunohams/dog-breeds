package domain.usecase.fetchImageLinks

import domain.core.wrapper.DataState
import domain.entity.DogImage
import kotlinx.coroutines.flow.Flow

interface FetchImageLinksUseCase {
    operator fun invoke(breedId: String): Flow<DataState<List<DogImage>>>
}