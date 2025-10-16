package com.example.qurio.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, V : BaseView, P : BasePresenter<V>> :
    Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    abstract val presenter: P

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun initViews()

    protected open fun initObservers() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
        initViews()
        initObservers()
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
    override fun onDestroyView() {
        presenter.detachView()
        _binding = null
        super.onDestroyView()
    }
}