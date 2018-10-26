package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

class DslFragmentPagerAdapter internal constructor(
        private val fragmentManager: () -> FragmentManager,
        private val fragments: () -> List<Fragment>,
        private val currentItem: () -> Int
) : FragmentPagerAdapter(fragmentManager()) {

    override fun getItem(index: Int): Fragment = fragments()[index]

    override fun getCount(): Int = fragments().size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var fragment = super.instantiateItem(container, position) as Fragment
        if (fragment != getItem(position)) {
            val ft = fragmentManager().beginTransaction()
            ft.remove(fragment)
            fragment = getItem(position)
            ft.add(container.id, fragment, fragment.tag)
            ft.attach(fragment)
            ft.commitAllowingStateLoss()
        }
        return fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    companion object
}

fun DslFragmentPagerAdapter.Companion.builder(fragmentManager: () -> FragmentManager,
                                              fragments: () -> List<Fragment> = { listOf() },
                                              defaultItem: () -> Int = { DEFAULT_CURRENT_ITEM }
): DslFragmentPagerAdapter {

    return DslFragmentPagerAdapter(fragmentManager,
            fragments,
            defaultItem)
}

const val DEFAULT_CURRENT_ITEM = 0