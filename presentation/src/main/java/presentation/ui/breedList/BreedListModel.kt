package presentation.ui.breedList

import androidx.annotation.IdRes
import presentation.ui.breedList.adapter.BreedCell

data class BreedListModel(
    val isLoading: Boolean = false,
    val breeds: List<BreedCell> = emptyList(),
    @IdRes val errorRes: Int? = null
)
