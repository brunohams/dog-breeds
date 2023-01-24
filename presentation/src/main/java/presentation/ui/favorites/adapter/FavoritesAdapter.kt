package presentation.ui.favorites.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.FavoritesRowItemBinding
import com.squareup.picasso.Picasso
import domain.entity.DogImage
import presentation.core.BaseAdapter
import presentation.core.BaseViewHolder

class FavoritesAdapter(
    val picasso: Picasso,
) : BaseAdapter<DogImage, FavoritesRowItemBinding>(R.layout.favorites_row_item) {

    override fun createViewHolder(binding: FavoritesRowItemBinding): BaseViewHolder<DogImage> {
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: FavoritesRowItemBinding) : BaseViewHolder<DogImage>(binding) {
        private val breedView: TextView = binding.tvBreed
        private val imageView: ImageView = binding.imageView

        override fun bind(item: DogImage) {
            // TODO: Add a placeholder to better ux
            picasso.load(item.url)
                .into(imageView)

            breedView.text = item.breedId
        }
    }
}