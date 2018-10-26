package com.github.qingmei2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.qingmei2.dsladapter.DslFragmentPagerAdapter
import com.github.qingmei2.dsladapter.applyAdapter
import com.github.qingmei2.dsladapter.builder
import com.github.qingmei2.fragments.AFragment
import com.github.qingmei2.fragments.BFragment
import com.github.qingmei2.fragments.CFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fragments: List<Fragment> =
            listOf(AFragment.newInstance(), BFragment(), CFragment())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.applyAdapter {
            DslFragmentPagerAdapter.builder(
                    fragmentManager = { supportFragmentManager },
                    fragments = { fragments },
                    defaultItem = { 1 }
            )
        }

        btnChange.setOnClickListener {
            fragments = listOf(AFragment.newInstance("AFragment Changed"), BFragment(), CFragment())

            viewPager.adapter?.apply {
                notifyDataSetChanged()
            }
        }
    }
}
