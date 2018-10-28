package com.github.qingmei2

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.qingmei2.databinding.ActivityMainBinding
import com.github.qingmei2.dsladapter.DslFragmentPagerAdapter
import com.github.qingmei2.dsladapter.build
import com.github.qingmei2.dsladapter.buildAdapter
import com.github.qingmei2.fragments.AFragment
import com.github.qingmei2.fragments.BFragment
import com.github.qingmei2.fragments.CFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragments: List<Fragment> =
            listOf(AFragment(), BFragment(), CFragment())

    val adapter: DslFragmentPagerAdapter = DslFragmentPagerAdapter.build(
            fragmentManager = supportFragmentManager,
            fragmentsProvider = { fragments },
            recycle = { old, _ ->
                when (old) {
                    is AFragment -> true
                    else -> false
                }
            }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil
                .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .also {
                    it.activity = this
                }

        // usage exclude dataBinding:
        viewPager.buildAdapter(
                fragmentManager = supportFragmentManager,
                currentItem = 0,
                offscreenPageLimit = 1,
                fragments = { fragments },
                recycle = { old, _ ->
                    when (old) {
                        is AFragment -> true
                        else -> false
                    }
                }
        )

        btnChange.setOnClickListener {
            fragments = listOf(AFragment(), BFragment(), CFragment())   // new Fragment instance
            viewPager.adapter?.notifyDataSetChanged()
        }
    }

    fun onPageSelectChanged(index: Int) {
        Toast.makeText(this, "onPageChanged = $index", Toast.LENGTH_SHORT).show()
    }
}