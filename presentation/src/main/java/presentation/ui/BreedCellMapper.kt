package presentation.ui

import presentation.ui.breedList.adapter.BreedCell

object BreedCellMapper {

    fun toCell(breed: String): BreedCell {
        val id = breed.split(" ").last()
        val capitalizedName = breed.split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { char -> char.uppercase() }
        }
        return BreedCell(
            id = id,
            name = capitalizedName
        )
    }
}