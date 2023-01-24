package presentation.ui.breedList

import domain.core.wrapper.DataState
import domain.core.wrapper.ProgressState
import domain.usecase.fetchBreeds.FetchBreeds
import domain.usecase.fetchBreeds.FetchBreedsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import com.example.dogbreeds.presentation.R
import presentation.ui.breedList.adapter.BreedCell

@ExperimentalCoroutinesApi
class BreedListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: BreedListViewModel
    private lateinit var fetchBreeds: FetchBreedsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        fetchBreeds = mockk()
        viewModel = BreedListViewModel(fetchBreeds)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should execute fetch breeds useCase`() = runTest {
        viewModel.requestBreeds()
        coVerify(exactly = 1) { fetchBreeds() }
    }

    @Test
    fun `should set loading when fetching breeds useCase `() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Progress(ProgressState.LOADING))
        }
        viewModel.requestBreeds()
        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `should set not loading when fetching breeds useCase `() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Progress(ProgressState.LOADING))
            emit(DataState.Progress(ProgressState.IDLE))
        }
        viewModel.requestBreeds()
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `should breed list when fetching breeds useCase`() = runTest {
        val expectedBreeds = listOf(
            BreedCell("breed1", "Breed1"),
            BreedCell("breed2", "Breed2"),
            BreedCell("breed3", "Breed3"),
        )
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Success(listOf("breed1", "breed2", "breed3")))
        }
        viewModel.requestBreeds()
        assertEquals(expectedBreeds, viewModel.state.value.breeds)
    }

    @Test
    fun `should format breeds with capitalize names when fetching breeds useCase`() = runTest {
        val expectedBreeds = listOf(
            BreedCell("one", "Breed1 One"),
            BreedCell("breed2", "Breed2"),
            BreedCell("three", "Breed3 Three"),
        )
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Success(listOf("breed1 one", "breed2", "breed3 three")))
        }
        viewModel.requestBreeds()
        assertEquals(expectedBreeds, viewModel.state.value.breeds)
    }

    @Test
    fun `should clear breed list when fetching breeds useCase multiple times`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Success(listOf("breed1", "breed2", "breed3")))
            emit(DataState.Success(listOf("breed4", "breed5", "breed6")))
            emit(DataState.Success(listOf("breed7", "breed8", "breed9")))
        }
        val expectedResult = listOf(
            BreedCell("breed7", "Breed7"),
            BreedCell("breed8", "Breed8"),
            BreedCell("breed9", "Breed9"),
        )
        viewModel.requestBreeds()
        assertEquals(expectedResult, viewModel.state.value.breeds)
    }

    @Test
    fun `should set service error when fetching breeds useCase`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Error(FetchBreeds.Error.SERVICE_ERROR))
        }
        viewModel.requestBreeds()
        assertEquals(R.string.service_error, viewModel.state.value.errorRes)
    }

    @Test
    fun `should set no connection error when fetching breeds useCase`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Error(FetchBreeds.Error.NO_CONNECTION_ERROR))
        }
        viewModel.requestBreeds()
        assertEquals(R.string.no_connection_error, viewModel.state.value.errorRes)
    }

    @Test
    fun `should set unknown error when fetching breeds useCase`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Error(FetchBreeds.Error.UNKNOWN_ERROR))
        }
        viewModel.requestBreeds()
        assertEquals(R.string.unknown_error, viewModel.state.value.errorRes)
    }


    @Test
    fun `should replace error on multiple errors when fetching breeds useCase`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Error(FetchBreeds.Error.SERVICE_ERROR))
            emit(DataState.Error(FetchBreeds.Error.UNKNOWN_ERROR))
        }
        viewModel.requestBreeds()
        assertEquals(R.string.unknown_error, viewModel.state.value.errorRes)
    }

    @Test
    fun `should remove older error on success`() = runTest {
        coEvery { fetchBreeds() } returns flow {
            emit(DataState.Progress(ProgressState.LOADING))
            emit(DataState.Error(FetchBreeds.Error.SERVICE_ERROR))
            emit(DataState.Progress(ProgressState.IDLE))
            emit(DataState.Progress(ProgressState.LOADING))
            emit(DataState.Success(listOf("breed4", "breed5", "breed6")))
            emit(DataState.Progress(ProgressState.IDLE))
        }
        val expectedResult = listOf(
            BreedCell("breed4", "Breed4"),
            BreedCell("breed5", "Breed5"),
            BreedCell("breed6", "Breed6"),
        )
        viewModel.requestBreeds()
        assertEquals(null, viewModel.state.value.errorRes)
        assertEquals(expectedResult, viewModel.state.value.breeds)
        assertEquals(false, viewModel.state.value.isLoading)
    }


}