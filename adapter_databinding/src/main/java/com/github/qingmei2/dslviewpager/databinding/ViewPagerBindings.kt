package com.github.qingmei2.dslviewpager.databinding

import android.databinding.BindingAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.github.qingmei2.dslviewpager.DEFAULT_CURRENT_ITEM
import com.github.qingmei2.dslviewpager.DEFAULT_OFF_SCREEN_PAGE_LIMIT

@BindingAdapter(
        "viewPagerAdapter",
        "viewPagerDefaultItem",
        "viewPagerPageLimit",
        requireAll = false
)
fun bindingViewPagerAdapter(viewPager: ViewPager,
                            adapter: PagerAdapter,
                            defaultItem: Int?,
                            pageLimit: Int?
) {
    viewPager.adapter = adapter
    viewPager.currentItem = defaultItem ?: DEFAULT_CURRENT_ITEM
    viewPager.offscreenPageLimit = pageLimit ?: DEFAULT_OFF_SCREEN_PAGE_LIMIT
}

@BindingAdapter(
        "onViewPagerPageChanged",
        requireAll = false
)
fun onPageChangeListener(viewPager: ViewPager,
                         onPageSelected: ViewPagerConsumer) =
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) = onPageSelected.accept(position)


            override fun onPageScrollStateChanged(state: Int) {

            }
        })

interface ViewPagerConsumer : Consumer<Int>

interface Consumer<T> {

    fun accept(t: T)
}