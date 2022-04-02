package com.example.cloneinstagram.common.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloneinstagram.R
import com.example.cloneinstagram.profile.view.ProfileFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.properties.Delegates

abstract class BaseFragment<P: BasePresenter>(
    @LayoutRes private val layoutId: Int
    ): Fragment(layoutId) {
    abstract var presenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(getMenu() != null) setHasOptionsMenu(true)
        setupPresenter()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        getMenu()?.let{
            inflater.inflate(it, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

    }

    abstract fun setupViews()
    abstract fun setupPresenter()
    @MenuRes
    open fun getMenu():Int? = null

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}