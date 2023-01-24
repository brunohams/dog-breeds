package presentation.core

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T, VB : ViewDataBinding>(
    @LayoutRes private val res: Int,
    diffUtil: DiffUtil.ItemCallback<T> = BaseDiffUtil()
) : ListAdapter<T, BaseViewHolder<T>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = inflateAdapterView<VB>(parent, res)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    abstract fun createViewHolder(binding: VB): BaseViewHolder<T>
}

