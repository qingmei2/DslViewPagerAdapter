package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

class DslFragmentPagerAdapter internal constructor(
        private val fragmentManager: FragmentManager,
        val fragmentsProvider: () -> List<Fragment>,
        val recycleFragment: (old: Fragment, index: Int) -> Boolean = DEFAULT_RECYCLE_FUNC
) : FragmentPagerAdapter(fragmentManager) {

    init {
        getCurrentFragments()
    }

    private lateinit var fragments: List<Fragment>

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        val ft = fragmentManager.beginTransaction()
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

    companion object
}

fun DslFragmentPagerAdapter.Companion.build(
        fragmentManager: FragmentManager,
        fragmentsProvider: () -> List<Fragment>,
        recycleFragment: (old: Fragment, index: Int) -> Boolean = DEFAULT_RECYCLE_FUNC
): DslFragmentPagerAdapter =
        DslFragmentPagerAdapter(
                fragmentManager,
                fragmentsProvider,
                recycleFragment
        )

val DEFAULT_RECYCLE_FUNC: (Fragment, Int) -> Boolean = { _, _ -> true }