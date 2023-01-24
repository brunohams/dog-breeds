package presentation.ui.favorites.adapter

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreeds.presentation.R
import com.example.dogbreeds.presentation.databinding.FilterRowItemBinding
import presentation.core.BaseAdapter
import presentation.core.BaseViewHolder

class FilterAdapter(
    val onSelectFilter: (item: String) -> Unit
) : BaseAdapter<String, FilterRowItemBinding>(R.layout.filter_row_item) {

    override fun createViewHolder(binding: FilterRowItemBinding): BaseViewHolder<String> {
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: FilterRowItemBinding) : BaseViewHolder<String>(binding) {
        private val cardView: CardView = binding.cardView
        private val textViewFilter: TextView = binding.textViewFilter

        init {
            cardView.setOnClickListener {
                val position = absoluteAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    onSelectFilter(getItem(position))
                }
            }
        }

        override fun bind(item: String) {
            textViewFilter.text = item
        }
    }
}