package domain.usecase

import domain.core.Logger
import domain.core.wrapper.DataState
import domain.core.wrapper.ProgressState
import domain.entity.DogImage
import domain.repository.FavoritesRepository
import domain.service.BreedsService
import domain.usecase.fetchImageLinks.FetchImageLinks
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class FetchImageLinksTest {

    lateinit var useCase: FetchImageLinks
    lateinit var service: BreedsService
    lateinit var repository: FavoritesRepository
    lateinit var logger: Logger

    private val dummyImages: List<DogImage> = listOf(
        DogImage("https://images.dog.ceo/breeds/hound-walker/n02089867_712.jpg", "hound",false),
        DogImage("https://images.dog.ceo/breeds/hound-walker/n02089867_724.jpg", "hound",false),
        DogImage("https://images.dog.ceo/breeds/hound-walker/n02089867_758.jpg", "hound",false),
        DogImage("https://images.dog.ceo/breeds/hound-walker/n02089867_785.jpg", "hound",false),
        DogImage("https://images.dog.ceo/breeds/hound-walker/n02089867_822.jpg", "hound",false)
    )
    private val dummyImageLinks: List<String> = listOf(
        "https://images.dog.ceo/breeds/hound-walker/n02089867_712.jpg",
        "https://images.dog.ceo/breeds/hound-walker/n02089867_724.jpg",
        "https://images.dog.ceo/breeds/hound-walker/n02089867_758.jpg",
        "https://images.dog.ceo/breeds/hound-walker/n02089867_785.jpg",
        "https://images.dog.ceo/breeds/hound-walker/n02089867_822.jpg"
    )

    @Before
    fun setup() {
        service = mockk()
        repository = spyk()
        logger = spyk()
        useCase = FetchImageLinks(service, repository, logger)
    }

    @Test
    fun `should emit correctly on good request`() = runTest {
        // Arrange
        coEvery { service.fetchImagesLinks(any()) } returns dummyImageLinks
        val expectedResult = listOf(
            DataState.Progress(ProgressState.LOADING),
            DataState.Success(dummyImages),
            DataState.Progress(ProgressState.IDLE),
        )
        val emittedStates = mutableListOf<DataState<List<DogImage>>>()

        val breedId = "hound"

        // Act
        useCase(breedId).collect(emittedStates::add)

        // Assert
        assertEquals(expectedResult, emittedStates)
        coVerify(exactly = 1) { service.fetchImagesLinks(breedId) }
    }

    @Test
    fun `should not execute if given empty input`() = runTest {
        // Arrange
        coEvery { service.fetchImagesLinks(any()) } returns dummyImageLinks
        val expectedResult = listOf<DataState<List<DogImage>>>(
            DataState.Error(FetchImageLinks.Error.EMPTY_BREED_ID)
        )
        val emittedStates = mutableListOf<DataState<List<DogImage>>>()

        // Act
        useCase("").collect(emittedStates::add)

        // Assert
        assertEquals(expectedResult, emittedStates)
        coVerify(exactly = 0) { service.fetchImagesLinks(any()) }
    }

    @Test
    fun `should emit correctly on bad request`() = runTest {
        // Arrange
        coEvery { service.fetchImagesLinks(any()) } throws Exception("Generic Error")
        val expectedResult = listOf<DataState<List<DogImage>>>(
            DataState.Progress(ProgressState.LOADING),
            DataState.Error(FetchImageLinks.Error.SERVICE_ERROR),
            DataState.Progress(ProgressState.IDLE),
        )
        val emittedStates = mutableListOf<DataState<List<DogImage>>>()

        // Act
        useCase("hound").collect(emittedStates::add)

        // Assert
        assertEquals(expectedResult, emittedStates)
        coVerify(exactly = 1) { service.fetchImagesLinks("hound") }
    }

    @Test
    fun `should log error on bad request`() = runTest {
        // Arrange
        coEvery { service.fetchImagesLinks(any()) } throws Exception("Generic Error")

        // Act
        useCase("hound").collect()

        // Assert
        verify(exactly = 1) { logger.e(any()) }
    }

    @Test
    fun `should not log error on good request`() = runTest {
        // Arrange
        coEvery { service.fetchImagesLinks(any()) } returns dummyImageLinks

        // Act
        useCase("hound").collect()

        // Assert
        verify(exactly = 0) { logger.e(any()) }
    }

}
