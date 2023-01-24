package presentation.ui.breedDetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.FragmentBreedDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import domain.entity.DogImage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import presentation.core.BaseFragment
import presentation.ui.breedDetail.adapter.ImagesAdapter

@AndroidEntryPoint
class BreedDetailFragment : BaseFragment<FragmentBreedDetailBinding>(R.layout.fragment_breed_detail) {

    private val viewModel: BreedDetailViewModel by activityViewModels()
    private val imagesAdapter: ImagesAdapter by lazy { ImagesAdapter(
        Picasso.with(context),
        ::onSelectImage
    ) }

    private val args: BreedDetailFragmentArgs by navArgs()
    private var isAlreadyShowingError = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestImages(args.breedId)
        setupList()
        observeViewModelState()
    }

    private fun setupList() {
        binding.rvImages.adapter = imagesAdapter
    }

    private fun observeViewModelState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { state ->
                updateImages(state.images)
                if (state.errorRes != null) {
                    showErrorDialog(state.errorRes)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onSelectImage(item: DogImage) {
        viewModel.toggleFavorite(item)
        imagesAdapter.notifyItemChanged(imagesAdapter.currentList.indexOf(item))
    }

    private fun updateImages(imageCells: List<DogImage>) {
        with(imagesAdapter) {
            submitList(imageCells)
            notifyDataSetChanged()
        }
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

}
