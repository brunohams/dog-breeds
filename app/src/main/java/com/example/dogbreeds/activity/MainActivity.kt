package com.example.dogbreeds.activity

import android.os.Bundle
import com.example.dogbreeds.R
import com.example.dogbreeds.core.BaseActivity
import com.example.dogbreeds.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
