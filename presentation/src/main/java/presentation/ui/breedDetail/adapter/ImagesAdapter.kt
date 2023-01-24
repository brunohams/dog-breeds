package presentation.ui.breedDetail.adapter

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.ImageRowItemBinding
import com.squareup.picasso.Picasso
import domain.entity.DogImage
import presentation.core.BaseAdapter
import presentation.core.BaseViewHolder

class ImagesAdapter(
    val picasso: Picasso,
    val onSelectImage: (item: DogImage) -> Unit
) : BaseAdapter<DogImage, ImageRowItemBinding>(R.layout.image_row_item) {

    override fun createViewHolder(binding: ImageRowItemBinding): BaseViewHolder<DogImage> {
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: ImageRowItemBinding) : BaseViewHolder<DogImage>(binding) {
        private val cardView: CardView = binding.cardView
        private val imageViewFavorite: ImageView = binding.imageViewFavorite
        private val imageView: ImageView = binding.imageView

        init {
            cardView.setOnClickListener {
                val position = absoluteAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    onSelectImage(getItem(position))
                }
            }
        }

        override fun bind(item: DogImage) {
            // TODO: Add a placeholder to better ux
            picasso.load(item.url)
                .into(imageView)

            if (item.isFavorite) {
                imageViewFavorite.visibility = View.VISIBLE
            } else {
                imageViewFavorite.visibility = View.GONE
            }
        }
    }
}