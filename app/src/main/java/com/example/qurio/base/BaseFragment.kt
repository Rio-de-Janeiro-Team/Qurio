package com.example.qurio.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, V : BaseView, > :
    Fragment() {

    private lateinit var _binding: VB
     protected val binding: VB
        get() = _binding
   //abstract val presenter: P

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }


    protected fun updateToolbar(
        title: String? = null,
        showToolbar: Boolean = true,
        showBackButton: Boolean = false
    ) {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            if (showToolbar) {
                show()
                title?.let { this.title = it }
                setDisplayHomeAsUpEnabled(showBackButton)
                setDisplayShowHomeEnabled(showBackButton)
            } else {
                hide()
            }
        }
    }

}