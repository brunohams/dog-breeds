package domain.usecase.fetchBreeds

import domain.core.Logger
import domain.service.BreedsService
import domain.core.wrapper.DataState
import domain.core.wrapper.ErrorType
import domain.core.wrapper.ProgressState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchBreeds(
    private val breedsService: BreedsService,
    private val logger: Logger
): FetchBreedsUseCase {

    enum class Error : ErrorType {
        NO_CONNECTION_ERROR,
        SERVICE_ERROR,
        UNKNOWN_ERROR
    }

    override fun invoke(): Flow<DataState<List<String>>> = flow {
        emit(DataState.Progress(ProgressState.LOADING))

        try {
            emit(DataState.Success(breedsService.fetchBreedsNames()))
        } catch (e: Exception) {
            // For this version we are only handling generic exceptions, but we could handle more specific exceptions
            // like NoConnectionException, ServiceException, etc.
            logger.e(e)
            emit(DataState.Error(Error.SERVICE_ERROR))
        }

        emit(DataState.Progress(ProgressState.IDLE))
    }

}