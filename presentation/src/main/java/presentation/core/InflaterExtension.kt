package presentation.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <VB : ViewDataBinding> inflateAdapterView(parent: ViewGroup, res: Int): VB {
    return DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), res, parent, false
    )
}
