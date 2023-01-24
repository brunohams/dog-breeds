package presentation.ui

import org.junit.Test
import presentation.ui.breedList.adapter.BreedCell
import kotlin.test.assertEquals

class BreedCellMapperTest {

    @Test
    fun `should map breed to cell`() {
        val breed = "affenpinscher"
        val expected = BreedCell(
            id = "affenpinscher",
            name = "Affenpinscher"
        )

        val actual = BreedCellMapper.toCell(breed)

        assertEquals(expected, actual)
    }

    @Test
    fun `should map breed with spacing to cell`() {
        val breed = "african hunting dog"
        val expected = BreedCell(
            id = "dog",
            name = "African Hunting Dog"
        )

        val actual = BreedCellMapper.toCell(breed)

        assertEquals(expected, actual)
    }

}