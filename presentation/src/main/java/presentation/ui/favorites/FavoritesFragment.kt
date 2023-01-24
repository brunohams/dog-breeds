package presentation.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.FragmentBreedListBinding
import com.example.dogbreeds.presentation.databinding.FragmentFavoritesBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import domain.entity.DogImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import presentation.core.BaseFragment
import presentation.ui.favorites.adapter.FavoritesAdapter
import presentation.ui.favorites.adapter.FilterAdapter

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by activityViewModels()
    private val filterAdapter: FilterAdapter by lazy { FilterAdapter(::onSelectFilter) }
    private val favoritesAdapter: FavoritesAdapter by lazy { FavoritesAdapter(
        Picasso.with(context)
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFavoriteImages()

        setupLists()
        observeViewModelState()
    }

    private fun setupLists() {
        binding.rvImages.adapter = favoritesAdapter
        binding.rvFilter.adapter = filterAdapter
    }

    private fun onSelectFilter(item: String) {
        viewModel.filterBy(item)
    }

    private fun observeViewModelState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                updateImages(state.filteredImages)
                updateFilter(state.filters)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateImages(images: List<DogImage>) {
        with(favoritesAdapter) {
            submitList(images)
            notifyDataSetChanged()
        }
    }

    private fun updateFilter(filters: List<String>) {
        with(filterAdapter) {
            submitList(filters)
            notifyDataSetChanged()
        }
    }
}
