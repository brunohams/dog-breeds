package domain.usecase.fetchBreeds

import domain.core.wrapper.DataState
import kotlinx.coroutines.flow.Flow

interface FetchBreedsUseCase {
    operator fun invoke(): Flow<DataState<List<String>>>
}