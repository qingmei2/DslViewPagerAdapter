package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup

class DslFragmentPagerAdapter internal constructor(
        val fragmentManager: () -> FragmentManager,
        val fragmentsProvider: () -> List<Fragment>,
        val currentItem: () -> Int,
        val recycleFragment: (old: Fragment, index: Int) -> Boolean = { _, _ -> true }
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

    override fun getItemPosition(`object`: Any): Int =
            when (recycleFragment == DEFAULT_RECYCLE_FUNC) {
                true -> PagerAdapter.POSITION_UNCHANGED
                false -> PagerAdapter.POSITION_NONE
            }

    override fun notifyDataSetChanged() {
        getCurrentFragments()
        super.notifyDataSetChanged()
    }

    private fun getCurrentFragments() {
        // refresh fragments source
        fragments = fragmentsProvider()
    }
}

fun ViewPager.adapter(
        fragmentManager: () -> FragmentManager,
        fragments: () -> List<Fragment> = { listOf() },
        defaultItem: () -> Int = DEFAULT_CURRENT_ITEM,
        recycle: (Fragment, Int) -> Boolean = DEFAULT_RECYCLE_FUNC
) = DslFragmentPagerAdapter(fragmentManager, fragments, defaultItem, recycle)
        .also {
            adapter = it
            currentItem = it.currentItem()
        }

private val DEFAULT_RECYCLE_FUNC: (Fragment, Int) -> Boolean = { _, _ -> true }

private val DEFAULT_CURRENT_ITEM: () -> Int = { 0 }