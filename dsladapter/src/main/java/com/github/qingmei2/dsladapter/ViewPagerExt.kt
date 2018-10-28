package com.github.qingmei2.dsladapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager

fun ViewPager.buildAdapter(
        fragmentManager: FragmentManager,
        currentItem: Int = DEFAULT_CURRENT_ITEM,
        offscreenPageLimit: Int = DEFAULT_OFF_SCREEN_PAGE_LIMIT,
        fragments: () -> List<Fragment> = { listOf() },
        recycle: (Fragment, Int) -> Boolean = DEFAULT_RECYCLE_FUNC
): DslFragmentPagerAdapter =
        DslFragmentPagerAdapter.build(
                fragmentManager = fragmentManager,
                fragmentsProvider = fragments,
                recycleFragment = recycle
        ).also {
            adapter = it
            this.currentItem = currentItem
            this.offscreenPageLimit = offscreenPageLimit
        }

const val DEFAULT_CURRENT_ITEM: Int = 0
const val DEFAULT_OFF_SCREEN_PAGE_LIMIT = 1
