package domain.core.wrapper

sealed class DataState<T> {
    data class Error<T>(val error: ErrorType) : DataState<T>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Progress<T>(val progressState: ProgressState) : DataState<T>()
}
