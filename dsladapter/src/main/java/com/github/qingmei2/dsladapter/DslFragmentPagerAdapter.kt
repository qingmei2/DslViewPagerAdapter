package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

class DslFragmentPagerAdapter internal constructor(
        private val fragmentManager: () -> FragmentManager,
        private val fragmentsProvider: () -> List<Fragment>,
        private val currentItem: () -> Int,
        private val recycleFragment: (old: Fragment, index: Int) -> Boolean = { _, _ -> true }
) : FragmentPagerAdapter(fragmentManager()) {

    init {
        getCurrentFragments()
    }

    private lateinit var fragments: List<Fragment>

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        val ft = fragmentManager().beginTransaction()
        return when (fragment != getItem(position) && !recycleFragment(fragment, position)) {
            true -> getItem(position).apply {
                ft.remove(fragment)
                ft.add(container.id, this, tag)
                ft.attach(this)
                ft.commitAllowingStateLoss()
            }
            false -> fragment.apply {
                ft.attach(this)
                ft.commitAllowingStateLoss()
            }
        }
    }

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    override fun notifyDataSetChanged() {
        getCurrentFragments()
        super.notifyDataSetChanged()
    }

    private fun getCurrentFragments() {
        this.fragments = fragmentsProvider()
    }

    companion object
}

fun DslFragmentPagerAdapter.Companion.builder(
        fragmentManager: () -> FragmentManager,
        fragments: () -> List<Fragment> = { listOf() },
        defaultItem: () -> Int = { DEFAULT_CURRENT_ITEM },
        recycle: (Fragment, Int) -> Boolean = { _, _ -> true }
): DslFragmentPagerAdapter {

    return DslFragmentPagerAdapter(
            fragmentManager,
            fragments,
            defaultItem,
            recycle
    )
}

const val DEFAULT_CURRENT_ITEM = 0