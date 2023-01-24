package presentation.ui.breedList

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.dogbreeds.presentation.databinding.FragmentBreedListBinding
import com.example.dogbreeds.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import presentation.core.BaseFragment
import presentation.ui.breedList.adapter.BreedAdapter
import presentation.ui.breedList.adapter.BreedCell

@AndroidEntryPoint
class BreedListFragment : BaseFragment<FragmentBreedListBinding>(R.layout.fragment_breed_list) {

    private val viewModel: BreedListViewModel by activityViewModels()
    private val navController: NavController by lazy { findNavController() }
    private val breedAdapter: BreedAdapter by lazy { BreedAdapter(::onSelectBreed) }
    private var isAlreadyShowingError = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestBreeds()

        setupList()
        observeViewModelState()

        binding.btnFavorites.setOnClickListener {
            navController.navigate(BreedListFragmentDirections.actionToFavoritesFragment())
        }
    }

    private fun setupList() {
        binding.rvBreeds.adapter = breedAdapter
    }

    private fun onSelectBreed(item: BreedCell) {
        navController.navigate(
            BreedListFragmentDirections.actionToBreedDetailFragment(item.id)
        )
    }

    private fun observeViewModelState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                updateLoading(state.isLoading)
                updateBreeds(state.breeds)
                if (state.errorRes != null) {
                    showErrorDialog(state.errorRes)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrorDialog(stringRes: Int) {
        if (isAlreadyShowingError) return
        isAlreadyShowingError = true
        val builder = AlertDialog.Builder(this.context)
            .setCancelable(false)
            .setMessage(stringRes)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.clearError()
                isAlreadyShowingError = false
            }
            .create()

        builder.show()
    }

    private fun updateBreeds(breeds: List<BreedCell>) {
        with(breedAdapter) {
            submitList(breeds)
            notifyDataSetChanged()
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }
}
