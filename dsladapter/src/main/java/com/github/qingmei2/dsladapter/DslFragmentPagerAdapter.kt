package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class DslFragmentPagerAdapter private constructor(
        private val fragmentManager: FragmentManager,
        private val fragments: List<Fragment>,
        private val currentItem: Int
) : FragmentPagerAdapter(fragmentManager) {

    internal constructor(fragmentManager: FragmentManager, builder: Builder) : this(
            fragmentManager,
            builder.fragments(),
            builder.currentItem()
    )

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size

    companion object {

        fun build(fragmentManager: FragmentManager, supplier: Builder.() -> Unit): DslFragmentPagerAdapter =
                Builder(fragmentManager, supplier).build()
    }
}

class Builder(private val fragmentManager: FragmentManager, supplier: Builder.() -> Unit) {

    var fragments: () -> List<Fragment> = { listOf() }

    var currentItem: () -> Int = { DEFAULT_CURRENT_ITEM }

    init {
        supplier()
    }

    fun build(): DslFragmentPagerAdapter =
            DslFragmentPagerAdapter(fragmentManager, this)
}

const val DEFAULT_CURRENT_ITEM = 0