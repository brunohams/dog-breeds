package datasource.dto

import datasource.dto.JsonResponseDTO
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class JsonResponseDTOTest {

    @Test
    fun equality() {
        val first = JsonResponseDTO<String>("success", "ok")
        val second = JsonResponseDTO<String>("success", "ok")
        val differentStatus = JsonResponseDTO<String>("error", "ok")
        val differentMessage = JsonResponseDTO<String>("success", "error")
        val totallyDifferent = JsonResponseDTO<String>("error", "error")

        assertEquals(first, second)
        assertNotEquals(differentStatus, second)
        assertNotEquals(differentMessage, second)
        assertNotEquals(totallyDifferent, second)
    }

}
