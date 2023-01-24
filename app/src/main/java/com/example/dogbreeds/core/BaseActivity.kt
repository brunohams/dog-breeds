package com.example.dogbreeds.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val layoutRes: Int) : AppCompatActivity() {
    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutRes)
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
