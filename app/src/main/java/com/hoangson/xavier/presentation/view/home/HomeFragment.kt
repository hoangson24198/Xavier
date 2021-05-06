package com.hoangson.xavier.presentation.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import com.hoangson.xavier.core.bases.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fullScreen()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Composable
    override fun setContent() {
        super.setContent()
    }
}