package com.hoangson.xavier.core.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.hoangson.xavier.core.helper.KeyboardHelper
import com.hoangson.xavier.core.ui.PlasmaTheme
import com.hoangson.xavier.core.util.LayoutResId

abstract class BaseFragment : Fragment() {
    protected var rootView: View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId: Int = getLayout()
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            rootView = inflater.inflate(layoutResId, container, false)
        }
        arguments?.let {
            onArgumentsReady(it)
        }
        return if (layoutResId == LayoutResId.LAYOUT_NOT_DEFINED) {
            ComposeView(requireContext()).apply {
                setContent {
                    PlasmaTheme {
                        Surface(color = MaterialTheme.colors.surface) {
                            Box {
                                setContent()
                            }
                        }
                    }
                }
            }
        } else {
            inflater.inflate(layoutResId, container, false)
        }
    }

    protected open fun getLayout(): Int {
        return if (javaClass.getAnnotation(LayoutResId::class.java) != null) javaClass.getAnnotation(
            LayoutResId::class.java
        )!!.layout else LayoutResId.LAYOUT_NOT_DEFINED
    }

    @Composable
    open fun setContent() {
        onSyncViews()
        onSyncEvents()
        onSyncData()
    }

    protected abstract fun onSyncViews()

    protected abstract fun onSyncEvents()

    protected abstract fun onSyncData()

    open fun onArgumentsReady(bundle: Bundle) {}

    protected open fun hideKeyboardIfNeed() {
        KeyboardHelper.hideKeyboardIfNeed(requireActivity())
    }

    override fun onPause() {
        super.onPause()
        hideKeyboardIfNeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
    }

}