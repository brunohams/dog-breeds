package presentation.ui.breedList.adapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.BreedRowItemBinding
import presentation.core.BaseAdapter
import presentation.core.BaseViewHolder

class BreedAdapter(
    val onSelectBreed: (item: BreedCell) -> Unit
) : BaseAdapter<BreedCell, BreedRowItemBinding>(R.layout.breed_row_item) {

    override fun createViewHolder(binding: BreedRowItemBinding): BaseViewHolder<BreedCell> {
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: BreedRowItemBinding) : BaseViewHolder<BreedCell>(binding) {
        private val cardView: CardView = binding.cardView
        private val textViewGroup: TextView = binding.textViewGroup

        init {
            cardView.setOnClickListener {
                val position = absoluteAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    onSelectBreed(getItem(position))
                }
            }
        }

        override fun bind(item: BreedCell) {
            textViewGroup.text = item.name
        }
    }
}