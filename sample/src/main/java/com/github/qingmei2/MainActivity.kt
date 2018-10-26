package com.github.qingmei2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.qingmei2.dsladapter.DslFragmentPagerAdapter
import com.github.qingmei2.fragments.AFragment
import com.github.qingmei2.fragments.BFragment
import com.github.qingmei2.fragments.CFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter =
                DslFragmentPagerAdapter
                        .build(supportFragmentManager) {
                            fragments = {
                                listOf(AFragment(), BFragment(), CFragment())
                            }
                            currentItem = {
                                1
                            }
                        }
    }
}
