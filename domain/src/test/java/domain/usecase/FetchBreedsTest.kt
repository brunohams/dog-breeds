package domain.usecase

import domain.core.Logger
import domain.core.wrapper.DataState
import domain.core.wrapper.ProgressState
import domain.service.BreedsService
import domain.usecase.fetchBreeds.FetchBreeds
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class FetchBreedsTest {

    lateinit var useCase: FetchBreeds
    lateinit var service: BreedsService
    lateinit var logger: Logger

    private val dummyBreedsNames: List<String> = listOf(
        "sharpei", "shiba", "tervuren", "wolfhound"
    )

    @Before
    fun setup() {
        service = mockk()
        logger = spyk()
        useCase = FetchBreeds(service, logger)
    }

    @Test
    fun `should emit correctly on good request`() = runTest {
        // Arrange
        coEvery { service.fetchBreedsNames() } returns dummyBreedsNames
        val expectedResult = listOf(
            DataState.Progress(ProgressState.LOADING),
            DataState.Success(dummyBreedsNames),
            DataState.Progress(ProgressState.IDLE),
        )
        val emittedStates = mutableListOf<DataState<List<String>>>()

        // Act
        useCase().collect(emittedStates::add)

        // Assert
        assertEquals(expectedResult, emittedStates)
        coVerify(exactly = 1) { service.fetchBreedsNames() }
    }

    @Test
    fun `should emit correctly on bad request`() = runTest {
        // Arrange
        coEvery { service.fetchBreedsNames() } throws Exception("Generic Error")
        val expectedResult = listOf<DataState<List<String>>>(
            DataState.Progress(ProgressState.LOADING),
            DataState.Error(FetchBreeds.Error.SERVICE_ERROR),
            DataState.Progress(ProgressState.IDLE),
        )
        val emittedStates = mutableListOf<DataState<List<String>>>()

        // Act
        useCase().collect(emittedStates::add)

        // Assert
        assertEquals(expectedResult, emittedStates)
        coVerify(exactly = 1) { service.fetchBreedsNames() }
    }

    @Test
    fun `should log error on bad request`() = runTest {
        // Arrange
        coEvery { service.fetchBreedsNames() } throws Exception("Generic Error")

        // Act
        useCase().collect()

        // Assert
        verify(exactly = 1) { logger.e(any()) }
    }

    @Test
    fun `should not log error on good request`() = runTest {
        // Arrange
        coEvery { service.fetchBreedsNames() } returns  dummyBreedsNames

        // Act
        useCase().collect()

        // Assert
        verify(exactly = 0) { logger.e(any()) }
    }

}
