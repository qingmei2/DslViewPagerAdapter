package com.github.qingmei2.dsladapter

import android.support.v4.view.ViewPager

inline fun ViewPager.applyAdapter(apply: () -> DslFragmentPagerAdapter) {
    adapter = apply()
}